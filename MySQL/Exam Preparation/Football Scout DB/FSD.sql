#-----------Exam Prep: Footbal Scout Database-----------


#01.	Table Design
CREATE SCHEMA fsd;
USE fsd;

CREATE TABLE countries(
	id int auto_increment primary key,
    name varchar(45) not null
);

create table towns(
	id int auto_increment primary key,
    name varchar(45) not null,
    country_id int not null,
    constraint fk_towns_country_id foreign key (country_id) references countries(id) 
);

create table stadiums(
	id int primary key auto_increment,
    name varchar(45) not null,
    capacity int not null,
    town_id int not null,
    constraint fk_stadium_town_id foreign key (town_id) references towns(id)
);

create table teams(
	id int auto_increment primary key,
    name varchar(45) not null,
    established DATE,
    fan_base bigint(20) not null default 0,
    stadium_id int not null,
    constraint fk_team_stadium_id foreign key (stadium_id) references stadiums(id)
);

create table skills_data(
	id int auto_increment primary key,
    dribbling int default 0,
    pace int default 0,
    passing int default 0,
    shooting int default 0,
    speed int default 0,
    strength int default 0
);

create table coaches(
	id int auto_increment primary key,
    first_name varchar(10) not null,
    last_name varchar(20) not null,
    salary decimal(10,2) not null default 0,
    coach_level int not null default 0
);

CREATE TABLE players(
	id int auto_increment primary key,
    first_name varchar(10) not null,
    last_name varchar(20) not null,
    age int not null default 0,
    position char(1) not null,
    salary decimal(10,2) not null default 0,
    hire_date datetime null,
    skills_data_id int not null,
    team_id int null,
    constraint fk_player_team_id foreign key (team_id) references teams(id),
    constraint fk_player_skills_data_id foreign key (skills_data_id) references skills_data(id)
);

create table players_coaches(
	player_id int not null,
    coach_id int not null,
    constraint pk_players_coaches_id primary key (player_id, coach_id)
);

#02.	Insert
insert into coaches(first_name, last_name, salary, coach_level)
select p.first_name, p.last_name, p.salary * 2, length(p.first_name)  from players as p
where p.age >= 45;

#03.	Update
update coaches as c
set c.coach_level = c.coach_level + 1
WHERE SUBSTRING(c.first_name, 1, 1) = 'A' and (select count(p.player_id) from players_coaches as p where c.id = p.coach_id) >= 1;

#04.	Delete
delete from players as p
where p.age >= 45;

#05.	Players 
select p.first_name, p.age, p.salary from players as p
order by p.salary desc;

#06.	Young offense players without contract
select p.id, concat(p.first_name, ' ', p.last_name) as full_name, p.age, p.position, p.hire_date from players as p
join skills_data as sd on p.skills_data_id = sd.id
where p.hire_date is null and sd.strength > 50
order by p.salary, p.age;

#07.	Detail info for all teams
select t.name as team_name, t.established, t.fan_base, count(p.id) as count_of_players from teams as t
left join players as p on p.team_id = t.id
group by t.id
order by count_of_players desc, t.fan_base desc;

#08.	The fastest player by towns
select max(sd.speed) as max_speed, tn.name as town_name from teams as t
left join stadiums as s on t.stadium_id = s.id
left join towns as tn on s.town_id = tn.id
left join players as p on t.id = p.team_id
left join skills_data as sd on p.skills_data_id = sd.id
where t.name != 'Devify'
group by town_name
order by max_speed desc, town_name;

#09.	Total salaries and players by country
select c.name, count(p.id) as total_count_of_players, sum(p.salary) as total_sum_of_salaries from countries as c
left join towns as tn on c.id = tn.country_id
left join stadiums as s on tn.id = s.town_id
left join teams as t on s.id = t.stadium_id
left join players as p on t.id = p.team_id
group by c.id
order by total_count_of_players desc, c.name;

#10.	Find all players that play on stadium
USE `fsd`;
DROP function IF EXISTS `udf_stadium_players_count`;

DELIMITER $$
USE `fsd1`$$
CREATE FUNCTION `udf_stadium_players_count`(stadium_name VARCHAR(30))
RETURNS int
DETERMINISTIC
BEGIN
declare pl_count int;
set pl_count := (select count(*) from players as p
left join teams as t on p.team_id = t.id
left join stadiums as s on t.stadium_id = s.id
where s.name = stadium_name);
RETURN pl_count;
END$$
DELIMITER ;

SELECT udf_stadium_players_count ('Jaxworks') as `count`;
SELECT udf_stadium_players_count ('Linklinks') as `count`; 

#11.	Find good playmaker by teams
USE `fsd`;
DROP procedure IF EXISTS `udp_find_playmaker`;

DELIMITER $$
USE `fsd1`$$
CREATE PROCEDURE `udp_find_playmaker`(min_dribble_points int, team_name varchar(45))
BEGIN
select concat_ws(' ', p.first_name, p.last_name) as full_name, p.age, p.salary, sd.dribbling, sd.speed, t.name from players as p
join skills_data as sd on p.skills_data_id = sd.id
join teams as t on p.team_id = t.id
where t.name = team_name and sd.dribbling > min_dribble_points and sd.speed > 
(select avg(sd1.speed) from skills_data as sd1
join players as pl1 on sd1.id = pl1.skills_data_id
join teams as t1 on pl1.team_id = t1.id
where t.name = team_name) order by sd.speed desc limit 1;
END$$
DELIMITER ;

CALL udp_find_playmaker(20, 'Skyble');





