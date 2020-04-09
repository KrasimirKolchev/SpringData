#-----------Exam Prep: Royal United Kingsman Bank-----------


#01.	Table Design
CREATE SCHEMA R_U_K_Bank;
USE R_U_K_Bank;

CREATE TABLE branches (
	id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE employees (
	id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    started_on DATE NOT NULL,
    branch_id INT NOT NULL,
    CONSTRAINT fk_employees_branches FOREIGN KEY (branch_id) REFERENCES branches(id)
);

CREATE TABLE clients (
	id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(50) NOT NULL,
    age INT NOT NULL
);

CREATE TABLE employees_clients (
	employee_id INT,
    client_id INT,
    CONSTRAINT fk_ec_employees_id FOREIGN KEY (employee_id) REFERENCES employees(id),
    CONSTRAINT fk_ec_clients_id FOREIGN KEY (client_id) REFERENCES clients(id)
);

CREATE TABLE bank_accounts (
	id INT PRIMARY KEY AUTO_INCREMENT,
    account_number VARCHAR(10) NOT NULL,
    balance DECIMAL(10,2) NOT NULL,
    client_id INT UNIQUE NOT NULL,
    CONSTRAINT fk_ba_clients_id FOREIGN KEY (client_id) REFERENCES clients(id)
);

CREATE TABLE cards (
	id INT PRIMARY KEY AUTO_INCREMENT,
    card_number VARCHAR(19) NOT NULL,
    card_status VARCHAR(7) NOT NULL,
    bank_account_id INT NOT NULL,
    CONSTRAINT fk_cards_bank_account_id FOREIGN KEY (bank_account_id) REFERENCES bank_accounts(id)
);

#02.	Insert
INSERT INTO cards (card_number, card_status, bank_account_id)
	SELECT REVERSE(c.full_name) AS card_number, 'Active' AS card_status, c.id AS 'bank_account_id' FROM clients AS c
WHERE c.id BETWEEN 191 AND 200;

#03.	Update
CREATE VIEW v_get_empl_with_lowest_clients_count AS
	SELECT employee_id FROM employees_clients
    GROUP BY employee_id
    ORDER BY COUNT(client_id), employee_id LIMIT 1;

UPDATE employees_clients AS ec
SET employee_id = (SELECT employee_id FROM v_get_empl_with_lowest_clients_count)
WHERE ec.employee_id = ec.client_id;

#04.	Delete
DELETE employees FROM employees
LEFT JOIN employees_clients AS ec ON employees.id = ec.employee_id
    WHERE employee_id IS NULL;


#05.	Clients
SELECT id, full_name FROM clients
ORDER BY id;

#06.	Newbies
SELECT id, CONCAT(first_name, ' ', last_name) AS full_name, CONCAT('$', salary) AS salary, started_on FROM employees
WHERE salary >= 100000 AND DATE(started_on) >= '2018-01-01'
ORDER BY salary DESC, id;

#07.	Cards against Humanity
SELECT c.id, CONCAT(c.card_number, ' : ', cl.full_name) AS card_token FROM cards AS c
LEFT JOIN bank_accounts AS b
ON c.bank_account_id = b.id
LEFT JOIN clients AS cl
ON b.client_id = cl.id
ORDER BY c.id DESC;

#08.	Top 5 Employees
SELECT CONCAT(e.first_name, ' ', e.last_name) AS name, 
e.started_on, 
	(SELECT COUNT(ec.client_id) AS count FROM employees_clients AS ec 
		WHERE ec.employee_id = e.id) AS count_of_clients
FROM employees AS e
ORDER BY count_of_clients DESC, e.id ASC LIMIT 5;

09.	Branch cards
SELECT b.`name`, COUNT(ca.id) AS count_of_cards FROM branches AS b
LEFT JOIN employees AS e ON b.id = e.branch_id
LEFT JOIN employees_clients AS ec ON e.id = ec.employee_id
LEFT JOIN clients AS cl ON ec.client_id = cl.id
LEFT JOIN bank_accounts AS ba ON cl.id = ba.client_id
LEFT JOIN cards AS ca ON ba.id = ca.bank_account_id
GROUP BY b.`name`
ORDER BY count_of_cards DESC, b.`name`; 

#10.	Extract client cards count
USE `r_u_k_bank`;
DROP function IF EXISTS `udf_client_cards_count`;

DELIMITER $$
USE `r_u_k_bank`$$
CREATE FUNCTION `udf_client_cards_count`(`name` VARCHAR(30)) RETURNS int
DETERMINISTIC
BEGIN
DECLARE card_count INT;
	SET card_count := (SELECT COUNT(*) FROM clients AS cl
		JOIN bank_accounts AS ba ON cl.id = ba.client_id
        JOIN cards AS c ON ba.id = c.bank_account_id
        WHERE cl.full_name = `name`
        GROUP BY cl.id);
	IF card_count IS NULL THEN SET card_count := 0;
    END IF;
RETURN card_count;
END$$
DELIMITER ;

SELECT c.full_name, udf_client_cards_count('Baxy David') as `cards` FROM clients c
WHERE c.full_name = 'Baxy David';


#11.	Extract Client Info
USE `r_u_k_bank`;
DROP procedure IF EXISTS `udp_clientinfo`;

DELIMITER $$
USE `r_u_k_bank`$$
CREATE PROCEDURE `udp_clientinfo`(full_name VARCHAR(50))
DETERMINISTIC
BEGIN
	SELECT cl.full_name, cl.age, ba.account_number, CONCAT('$', ba.balance) AS balance FROM clients AS cl
    JOIN bank_accounts AS ba ON cl.id = ba.client_id
    WHERE cl.full_name = full_name;
END$$
DELIMITER ;

CALL udp_clientinfo('Hunter Wesgate');
