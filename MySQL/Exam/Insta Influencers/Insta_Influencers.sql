#-----------Exam: Insta Influencers-----------


#01.	Table Design
create schema insta;
use insta;

create table users(
	id int primary key auto_increment,
	username varchar(30) not null unique,
    password varchar(30) not null,
    email varchar(50) not null,
    gender char(1) not null,
    age int not null,
    job_title varchar(40) not null,
    ip varchar(30) not null
);

create table addresses(
	id int primary key auto_increment,
	address Varchar(30) not null,
    town varchar(30) not null,
    country varchar(30) not null,
    user_id int not null,
    constraint fk_address_user_id foreign key(user_id) references users(id)
);

create table photos(
	id int primary key auto_increment,
    description text not null,
    date datetime not null,
    views int not null default 0
);

create table comments(
	id int primary key auto_increment,
    comment varchar(255) not null,
    date datetime not null,
    photo_id int not null,
    constraint fk_comment_photo_id foreign key (photo_id) references photos(id)
);

create table users_photos(
	user_id int not null,
    photo_id int not null,
    constraint fk_users_photos_user_id foreign key(user_id) references users(id),
    constraint fk_users_photos_photo_id foreign key(photo_id) references photos(id)
);

create table likes(
	id int primary key auto_increment,
    photo_id int not null,
    user_id int not null,
    constraint fk_like_photo_id foreign key(photo_id) references photos(id),
    constraint fk_like_user_id foreign key(user_id) references users(id)
);

#02.	Insert
insert into addresses(address, town, country, user_id)
select u.username, u.password, u.ip, u.age from users as u
where u.gender like 'M';

#03.	Update
update addresses as a
set a.country = (
case
	when a.country like 'B%' then 'Blocked'
	when a.country like 'T%' then 'Test'
	when a.country like 'P%' then 'In Progress'
	else a.country
	end
);

#04.	Delete
delete from addresses as a
where a.id % 3 = 0;

#05.	Users
select u.username, u.gender, u.age from users as u
order by u.age desc, u.username asc;

#06.	Extract 5 Most Commented Photos
select p.id, p.date as date_and_time, p.description, count(c.id) as commentsCount from photos as p
left join comments as c on p.id = c.photo_id
group by p.id
order by commentsCount desc, p.id asc Limit 5;

#07.	Lucky Users
select concat(u.id, ' ', u.username) as id_username, u.email from users as u
left join users_photos as up on u.id = up.user_id
where up.user_id = up.photo_id
order by u.id asc;

#08.	Count Likes and Comments
select p.id as photo_id, 
(select count(*) from likes as l where l.photo_id = p.id) as likes_count,
(select count(*) from comments as c where c.photo_id = p.id) as comments_count from photos as p
group by photo_id
order by likes_count desc, comments_count desc, p.id asc;

#09.	The Photo on the Tenth Day of the Month
select concat(substring(p.description, 1, 30), '...') as summary, p.date from photos as p
where extract(DAY from p.date) like '10'
order by p.date desc;

#10.	Get User’s Photos Count
USE `insta`;
DROP function IF EXISTS `udf_users_photos_count`;

DELIMITER $$
USE `insta`$$
CREATE FUNCTION `udf_users_photos_count`(username varchar(30)) RETURNS int
    DETERMINISTIC
BEGIN
declare p_count int;
set p_count := (select count(up.photo_id) from users as u
join users_photos as up on u.id = up.user_id
where u.username = username);
RETURN p_count;
END$$
DELIMITER ;

SELECT udf_users_photos_count('ssantryd') AS photosCount;


#11.	Increase User Age
USE `insta`;
DROP procedure IF EXISTS `udp_modify_user`;

DELIMITER $$
USE `insta`$$
CREATE PROCEDURE `udp_modify_user`(address VARCHAR(30), town VARCHAR(30))
BEGIN
update users as u
join addresses as a on u.id = a.user_id
set age = u.age + 10
where a.address like address and a.town like town;
END$$
DELIMITER ;

CALL udp_modify_user ('97 Valley Edge Parkway', 'Divinópolis');
SELECT u.username, u.email,u.gender,u.age,u.job_title FROM users AS u
WHERE u.username = 'eblagden21';



