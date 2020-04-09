#-----------Exam Prep: Colonial Journey Management System-----------


# DDL Table Design 	
CREATE SCHEMA CJMS_Database;
USE CJMS_Database;

CREATE TABLE planets (
	id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL
);

CREATE TABLE spaceports (
	id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    planet_id INT NOT NULL,
    CONSTRAINT fk_spaceport_planet_id FOREIGN KEY (planet_id) REFERENCES planets(id)
);

CREATE TABLE spaceships (
	id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    manufacturer VARCHAR(30) NOT NULL,
    light_speed_rate INT DEFAULT 0
);

CREATE TABLE colonists (
	id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    ucn CHAR(10) NOT NULL UNIQUE,
    birth_date DATE NOT NULL
);

CREATE TABLE journeys (
	id INT PRIMARY KEY AUTO_INCREMENT,
    journey_start DATETIME NOT NULL,
    journey_end DATETIME NOT NULL,
    purpose ENUM('Medical', 'Technical', 'Educational', 'Military'),
    destination_spaceport_id INT NOT NULL,
    spaceship_id INT NOT NULL,
    CONSTRAINT fk_journey_spaceport_id FOREIGN KEY (destination_spaceport_id) REFERENCES spaceports(id),
    CONSTRAINT fk_journey_spaceship_id FOREIGN KEY (spaceship_id) REFERENCES spaceships(id)
);

CREATE TABLE travel_cards (
	id INT PRIMARY KEY AUTO_INCREMENT,
    card_number CHAR(10) NOT NULL UNIQUE,
    job_during_journey ENUM('Pilot', 'Engineer', 'Trooper', 'Cleaner', 'Cook'),
    colonist_id INT NOT NULL,
    journey_id INT NOT NULL,
    CONSTRAINT fk_travel_card_colonist_id FOREIGN KEY (colonist_id) REFERENCES colonists(id),
    CONSTRAINT fk_travel_card_journey_id FOREIGN KEY (journey_id) REFERENCES journeys(id)
);

#01.	Data Insertion
INSERT INTO travel_cards (card_number, job_during_journey, colonist_id, journey_id)
	SELECT 
		(CASE
			WHEN DATE(c.birth_date) > '1980-01-01' THEN CONCAT_WS('', YEAR(c.birth_date), DAY(c.birth_date), SUBSTRING(c.ucn, 1, 4))
			ELSE CONCAT_WS('', YEAR(c.birth_date), MONTH(c.birth_date), SUBSTRING(c.ucn, 1, 4))
            END
		) AS card_number,
        (CASE
			WHEN c.id % 2 = 0 THEN 'Pilot'
            WHEN c.id % 3 = 0 THEN 'Cook'
            ELSE 'Engineer'
            END
		) AS job_during_journey,
        c.id,
        (substr(c.ucn, 1,1)) AS journey_id
    FROM colonists AS c 
WHERE c.id BETWEEN 196 AND 200;

#02.	Data Update
UPDATE journeys
SET purpose = (CASE
	WHEN id % 2 = 0 THEN 'Medical'
    WHEN id % 3 = 0 THEN 'Technical'
    WHEN id % 5 = 0 THEN 'Educational'
    WHEN id % 7 = 0 THEN 'Military'
    END)
WHERE id % 2 = 0 OR id % 3 = 0 OR id % 5 = 0 OR id % 7 = 0;

#03.	Data Deletion
DELETE colonists FROM colonists
LEFT JOIN travel_cards AS tc ON colonists.id = tc.colonist_id
WHERE tc.journey_id IS NULL;

#04.    Extract all travel cards
SELECT card_number, job_during_journey FROM travel_cards
ORDER BY card_number;

#05.    Extract all colonists
SELECT c.id, CONCAT(c.first_name, ' ', c.last_name) AS full_name, c.ucn FROM colonists AS c
ORDER BY c.first_name, c.last_name, c.id;

#06.	Extract all military journeys
SELECT j.id, j.journey_start, j.journey_end FROM journeys AS j
WHERE j.purpose = 'Military'
ORDER BY j.journey_start;

#07.	Extract all pilots
SELECT c.id, CONCAT(c.first_name, ' ', c.last_name) FROM colonists AS c
JOIN travel_cards AS tc ON c.id = tc.colonist_id
WHERE tc.job_during_journey = 'Pilot'
ORDER BY c.id;

#08.	Count all colonists that are on technical journey
SELECT COUNT(*) AS `count` FROM colonists AS c
JOIN travel_cards AS tc ON c.id = tc.colonist_id
JOIN journeys AS j ON tc.journey_id = j.id
WHERE j.purpose = 'Technical';

#09.    Extract the fastest spaceship
SELECT ss.`name` AS spaceship_name, sp.`name` AS spaceport_name FROM spaceships AS ss
JOIN journeys AS j ON ss.id = j.spaceship_id
JOIN spaceports AS sp ON j.destination_spaceport_id = sp.id
ORDER BY ss.light_speed_rate DESC LIMIT 1;

#10.    Extract spaceships with pilots younger than 30 years
SELECT ss.`name`, ss.manufacturer FROM colonists AS c
JOIN travel_cards AS tc ON c.id = tc.colonist_id
JOIN journeys AS j ON tc.journey_id = j.id
JOIN spaceships AS ss ON j.spaceship_id = ss.id
WHERE tc.job_during_journey = 'Pilot' AND (DATEDIFF('2019-01-01', c.birth_date) / 365) < 30
ORDER BY ss.`name`;

#11.    Extract all educational mission planets and spaceports
SELECT p.`name` AS planet_name, sp.`name` AS spaceport_name FROM journeys AS j
JOIN spaceports AS sp ON j.destination_spaceport_id = sp.id
JOIN planets AS p ON sp.planet_id = p.id
WHERE j.purpose = 'Educational'
ORDER BY sp.`name` DESC;

#12.    Extract all planets and their journey count
SELECT p.`name` AS planet_name, COUNT(j.id) AS journeys_count FROM journeys AS j
JOIN spaceports AS sp ON j.destination_spaceport_id = sp.id
JOIN planets AS p ON sp.planet_id = p.id
GROUP BY p.`name`
ORDER BY journeys_count DESC, planet_name;

#13.    Extract the shortest journey
SELECT j.id, p.`name`, sp.`name`, j.purpose AS journey_purpose FROM journeys AS j
JOIN spaceports AS sp ON j.destination_spaceport_id = sp.id
JOIN planets AS p ON sp.planet_id = p.id
ORDER BY (j.journey_start - j.journey_end) DESC LIMIT 1;

#14.    Extract the less popular job
SELECT tc.job_during_journey AS job_name FROM journeys AS j
JOIN travel_cards AS tc ON j.id = tc.journey_id
ORDER BY (j.journey_start - j.journey_end), 
	(SELECT COUNT(tcj.job_during_journey) FROM travel_cards AS tcj
    WHERE tcj.journey_id = tc.journey_id)
LIMIT 1;


#15.    Get colonists count
USE `cjms_database`;
DROP function IF EXISTS `udf_count_colonists_by_destination_planet`;

DELIMITER $$
USE `cjms_database`$$
CREATE FUNCTION `udf_count_colonists_by_destination_planet`(planet_name VARCHAR(30)) RETURNS int
DETERMINISTIC
BEGIN
	DECLARE result INT;
	SET result := (SELECT COUNT(c.id) AS `count` FROM planets AS p
		JOIN spaceports AS sp ON p.id = sp.planet_id
		JOIN journeys AS j ON sp.id = j.destination_spaceport_id
		JOIN travel_cards AS tc ON j.id = tc.journey_id
		JOIN colonists AS c ON tc.colonist_id = c.id
		WHERE p.`name` = planet_name
		ORDER BY `count` DESC LIMIT 1);
RETURN result;
END$$
DELIMITER ;

SELECT p.name, udf_count_colonists_by_destination_planet(‘Otroyphus’) AS count
FROM planets AS p
WHERE p.name = ‘Otroyphus’;


#16.    Modify spaceship
USE `cjms_database`;
DROP procedure IF EXISTS `udp_modify_spaceship_light_speed_rate`;

DELIMITER $$
USE `cjms_database`$$
CREATE PROCEDURE `udp_modify_spaceship_light_speed_rate`(spaceship_name VARCHAR(50), light_speed_rate_increse INT)
DETERMINISTIC
BEGIN
	START TRANSACTION;
		UPDATE spaceships AS ss
		SET ss.light_speed_rate = light_speed_rate + light_speed_rate_increse
		WHERE ss.`name` = spaceship_name;
    
    IF((SELECT COUNT(*) FROM spaceships AS s WHERE s.`name` = spaceship_name) = 1)
		THEN COMMIT;
        ELSE 
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'Spaceship you are trying to modify does not exists.';
			ROLLBACK;
	END IF;
END$$
DELIMITER ;

CALL udp_modify_spaceship_light_speed_rate ('USS Templar', 5);
SELECT name, light_speed_rate FROM spaceships WHERE name = 'USS Templar';

