#------------Lab------------

CREATE SCHEMA gamebar;
USE gamebar;

CREATE TABLE employees (
	id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL
);

CREATE TABLE categories (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE products (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    category_id INT NOT NULL,
    CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES categories(id)
);

INSERT INTO employees (first_name, last_name)
VALUES ('Ivan', 'Ivanov'), ('Ivan1', 'Ivanov1'), ('Ivan2', 'Ivanov2');

ALTER TABLE employees
ADD middle_name VARCHAR(100) NOT NULL;

ALTER TABLE `products`
ADD CONSTRAINT fk_category_id FOREIGN KEY (`category_id`) REFERENCES `categories`(`id`);

#------------Exercise-------------


# 1. Create Tables
CREATE SCHEMA `minions` DEFAULT CHARACTER SET utf8;
USE `minions`;

CREATE TABLE minions (
id INT NOT NULL PRIMARY KEY,
name VARCHAR(50) NOT NULL,
age INT 
);
CREATE TABLE towns (
id INT NOT NULL PRIMARY KEY,
name VARCHAR(50) NOT NULL
);

#2. Alter Minions Table
ALTER TABLE minions
ADD town_id INT NOT NULL;

ALTER TABLE minions
	ADD CONSTRAINT fk_minions_town_id
    FOREIGN KEY (town_id) 
    REFERENCES towns (id);

#3. Insert Records in Both Tables
INSERT INTO `towns` (`id`, `name`) 
VALUES (1, "Sofia"), (2, "Plovdiv"), (3, "Varna");
INSERT INTO `minions` (`id`, `name`, `age`, `town_id`)
VALUES (1, "Kevin", 22, 1), (2, "Bob", 15, 3), (3, "Steward", NULL, 2);

#4.	Truncate Table Minions
TRUNCATE TABLE minions;

#5.	Drop All Tables
DROP TABLE minions;
DROP TABLE towns;

#6 Create Table People
CREATE TABLE people (
id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
name VARCHAR(200) NOT NULL,
picture BLOB(2000) NULL,
height DECIMAL(2),
weight DECIMAL(2),
gender CHAR(1) NOT NULL CHECK(gender = "m" OR gender = "f") ,
birthdate DATE NOT NULL,
biography NVARCHAR(4000)
);

INSERT INTO people (`name`, `picture`, `height`, `weight`, `gender`, `birthdate`, `biography`)
VALUES ('Krasimir Kolchev', NULL, 1.90, 92.30, 'm', CONVERT('1987-07-30', DATE), 'kagfshafgajhs a fga haf aif'), 
('Elena Trencheva', NULL, 1.60, 52.1, 'f', CONVERT('1990-09-03', DATE), 'kagf sgsf  sdf sdshafgajhs a fga haf aif'), 
('Stamat Stamatov', NULL, 1.78, 82, 'm', CONVERT('1986-12-05', DATE), 'kagfshafgajhs a sfdasffga haf aif'), 
('Pesho Peshev', NULL, 1.72, 77.50, 'm', CONVERT('1984-05-16', DATE), 'kagfshafgajhs a fga haasfasff aif'), 
('Maria Marinova', NULL, 1.45, 42.9, 'f', CONVERT('1988-02-22', DATE), 'kagfshafgajhs a fga haf aifsfasf');

#7 Create Table Users
CREATE TABLE users(
`id` BIGINT AUTO_INCREMENT PRIMARY KEY,
`username` VARCHAR(30) UNIQUE NOT NULL,
`password` VARCHAR(26) NOT NULL,
`profile_picture` BLOB,
`last_login_time` DATETIME,
`is_deleted` BOOLEAN
);

INSERT INTO users(`username`,`password`,`profile_picture`,`last_login_time`,`is_deleted`)
VALUES ('Krasi', '123123', NULL, NULL, 0), ('Krasi1', '123123', NULL, NULL, 1), ('Krasi2', '123123', NULL, NULL, 0),
('Krasi3', '123123', NULL, NULL, 1), ('Krasi4', '123123', NULL, NULL, 0);

#8.	Change Primary Key
ALTER TABLE `users`
MODIFY `id` INT NOT NULL;
ALTER TABLE `users`
DROP PRIMARY KEY;
ALTER TABLE `users`
ADD CONSTRAINT pk_users PRIMARY KEY(`id`, `username`);

#9.	Set Default Value of a Field
ALTER TABLE `users`
MODIFY `last_login_time` timestamp DEFAULT CURRENT_TIMESTAMP();

#10. Set Unique Field
ALTER TABLE users 
MODIFY id BIGINT NOT NULL;
ALTER TABLE users 
DROP PRIMARY KEY;
ALTER TABLE users 
ADD CONSTRAINT pk_users PRIMARY KEY(id);
ALTER TABLE users 
ADD CONSTRAINT uq_username UNIQUE (username);

#11. Movies Database
CREATE SCHEMA movies;
USE movies;

CREATE TABLE directors (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    director_name VARCHAR(100) NOT NULL,
    notes VARCHAR(10000) NULL
);

CREATE TABLE genres (
    id INT PRIMARY KEY AUTO_INCREMENT,
    genre_name VARCHAR(50) NOT NULL,
    notes VARCHAR(10000) NULL
);

CREATE TABLE categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(50) NOT NULL,
    notes VARCHAR(10000) NULL
);

CREATE TABLE movies (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(60) NOT NULL,
    director_id INT NOT NULL,
    copyright_year DATE NULL,
    length TIME NULL,
    genre_id INT NOT NULL,
    category_id INT NOT NULL,
    rating DECIMAL(2, 1) NOT NULL,
    notes VARCHAR(10000)
);

ALTER TABLE movies
ADD CONSTRAINT fk_movies_director_id FOREIGN KEY (director_id) REFERENCES directors(id); 
ALTER TABLE movies
ADD CONSTRAINT fk_movies_genre_id FOREIGN KEY (genre_id) REFERENCES genres(id); 
ALTER TABLE movies
ADD CONSTRAINT fk_movies_category_id FOREIGN KEY (category_id) REFERENCES categories(id);

INSERT INTO directors (director_name, notes) 
VALUES ("Director 1", "Notes 1"), ("Director 2", "Notes 2"), ("Director 3", "Notes 3"), 
("Director 4", "Notes 4"), ("Director 5", "Notes 5");

INSERT INTO genres (genre_name, notes) 
VALUES ("Genre 1", "Notes 1"), ("Genre 2", "Notes 2"), ("Genre 3", "Notes 3"), ("Genre 4", "Notes 4"), ("Genre 5", "Notes 5");

INSERT INTO categories (category_name, notes) 
VALUES ("Category 1", "Notes 1"), ("Category 2", "Notes 2"), ("Category 3", "Notes 3"), 
("Category 4", "Notes 4"), ("Category 5", "Notes 5");

INSERT INTO movies (title, director_id, copyright_year, length, genre_id, category_id, rating, notes)
VALUES ("Title 1", 1, CURDATE(), "02:12:46", 2, 3, 8.8, "Notes 1"),
("Title 2", 2, CURDATE(), "01:52:11", 1, 5, 7, "Notes 2"),
("Title 3", 3, CURDATE(), "02:58:26", 5, 4, 9.0, "Notes 3"),
("Title 4", 4, CURDATE(), "02:58:26", 4, 2, 7.6, "Notes 4"),
("Title 5", 5, CURDATE(), "01:52:11", 3, 1, 7.9, "Notes 5");

#12. Car Rental Database
CREATE SCHEMA car_rental;
USE car_rental;

CREATE TABLE `categories` (
`id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
`category` VARCHAR(255) NOT NULL,
`daily_rate` DECIMAL NOT NULL,
`weekly_rate` DECIMAL NOT NULL,
`monthly_rate` DECIMAL NOT NULL,
`weekend_rate` DECIMAL NULL
);

CREATE TABLE `cars` (
`id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
`plate_number` VARCHAR(10) UNIQUE NOT NULL,
`make` VARCHAR(20) NULL,
`model` VARCHAR(20) NULL,
`car_year` DATE NULL,
`category_id` INT NOT NULL,
`doors` INT NULL,
`picture` BLOB NULL,
`car_condition` VARCHAR(20) NULL,
`available` BOOLEAN NOT NULL
);

CREATE TABLE `employees` (
`id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
`first_name` VARCHAR(20) NOT NULL,
`last_name` VARCHAR(20) NOT NULL,
`title` VARCHAR(20) NOT NULL,
`notes` TEXT NULL
);

CREATE TABLE `customers` (
`id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
`driver_licence_number` INT NOT NULL,
`full_name` VARCHAR(40) NOT NULL,
`address` VARCHAR(255) NULL,
`city` VARCHAR(20) NULL,
`zip_code` INT NULL,
`notes` TEXT NULL
);

CREATE TABLE `rental_orders` (
`id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
`employee_id` INT NOT NULL,
`customer_id` INT NOT NULL,
`car_id` INT NOT NULL,
`car_condition` VARCHAR(20) NULL,
`tank_level` VARCHAR(20) NULL,
`kilometrage_start` INT NULL,
`kilometrage_end` INT NULL,
`total_kilometrage` INT NULL,
`start_date` DATE NOT NULL,
`end_date` DATE NOT NULL,
`total_days` INT NOT NULL,
`rate_applied` VARCHAR(20) NOT NULL,
`tax_rate` DECIMAL NULL,
`order_status` VARCHAR(20) NULL,
`notes` TEXT NULL
);

ALTER TABLE rental_orders
ADD CONSTRAINT fk_rental_orders_employee_id FOREIGN KEY (employee_id) REFERENCES employees (id);
ALTER TABLE rental_orders
ADD CONSTRAINT fk_rental_orders_customer_id FOREIGN KEY (customer_id) REFERENCES customers (id);
ALTER TABLE rental_orders
ADD CONSTRAINT fk_rental_orders_car_id FOREIGN KEY (car_id) REFERENCES cars (id);
ALTER TABLE cars
ADD CONSTRAINT fk_car_category_id FOREIGN KEY (category_id) REFERENCES categories (id);

INSERT INTO categories (category, daily_rate, weekly_rate, monthly_rate, weekend_rate) 
VALUES ("Car", 10, 60, 200, 15),
("SUV", 20, 120, 420, 32),
("Minivan", 22, 80, 240, 40);

INSERT INTO cars (plate_number, make, model, category_id, doors, picture, car_condition, available)
VALUES ("E2087BP", "VW", "Polo 3", 1, 5, NULL, "average", 0),
("E0442KA", "Chevrolet", "Captiva", 2, 5, NULL, "good", 0),
("E2233BP", "FORD", "Transit", 3, 4, NULL, "good", 1);

INSERT INTO employees (first_name, last_name, title) 
VALUES ("Krasimir", "Kolchev", "Owner"),
("Elena", "Trencheva", "CO-Owner", "klahfkbaa"),
("Ivan", "Petrov", "Dealer", "lk ahf afhah fauf");

INSERT INTO customers (driver_licence_number, full_name, address, city, zip_code, notes) 
VALUES (515120051, "Krasimir Kolchev", "Razlog", "Razlog", "2769", "asdasdasd asd asdasd"),
(513120051, "Pesho Peshev", "Address 1", "City 1"),
(624626245, "Ivan Ivanov", "Address 2", "City 2", 1000);

INSERT INTO rental_orders (employee_id, customer_id, car_id, tank_level, kilometrage_start, start_date, end_date, total_days, rate_applied, tax_rate, order_status, notes)
VALUES (1, 2, 3, "full", 123, "2020-01-16", "2020-01-20", 4, "daily", 20, "confirmed", "akjsfha ahfakjf"),
(2, 1, 2, "middle", 200, "2020-01-10", "2020-01-17", 7, "weekly", 20, "in progress", "akjsfha ahfakjf"),
(3, 3, 1, "empty", 1500, "2020-01-17", "2020-02-17", 31, "monthly", 20, "pending");

#13. Hotel Database
CREATE SCHEMA Hotel;
USE hotel;

CREATE TABLE employees (
	id INT PRIMARY KEY AUTO_INCREMENT, 
	first_name VARCHAR(50) NOT NULL, 
	last_name VARCHAR(50) NOT NULL, 
	title VARCHAR(50) NOT NULL, 
	notes TEXT NULL
);

CREATE TABLE customers (
    account_number INT PRIMARY KEY NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(16) NULL,
    emergency_name VARCHAR(50) NULL,
    emergency_number VARCHAR(16) NULL,
    notes TEXT NULL
);

CREATE TABLE room_status (
    room_status VARCHAR(50) PRIMARY KEY NOT NULL,
    notes TEXT NULL
);

CREATE TABLE room_types (
    room_type VARCHAR(50) PRIMARY KEY NOT NULL,
    notes TEXT NULL
);

CREATE TABLE bed_types (
    bed_type VARCHAR(50) PRIMARY KEY NOT NULL,
    notes TEXT NULL
);

CREATE TABLE rooms (
    room_number INT UNIQUE PRIMARY KEY,
    room_type VARCHAR(50) NOT NULL,
    bed_type VARCHAR(50) NOT NULL,
    rate DECIMAL(6, 2) NOT NULL,
    room_status VARCHAR(50) NOT NULL,
    notes TEXT NULL
);

CREATE TABLE payments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id INT NOT NULL,
    payment_date DATE NOT NULL,
    account_number INT NOT NULL,
    first_date_occupied DATE NOT NULL,
    last_date_occupied DATE NOT NULL,
    total_days INT NOT NULL,
    amount_charged DECIMAL(10, 2) NOT NULL,
    tax_rate DECIMAL(10, 2) NOT NULL,
    tax_amount DECIMAL(10, 2) NOT NULL,
    payment_total DECIMAL(10, 2) NOT NULL,
    notes TEXT NULL
);

CREATE TABLE occupancies (
    id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id INT NOT NULL,
    date_occupied DATE NOT NULL,
    account_number INT,
    room_number INT NOT NULL,
    rate_applied DECIMAL(10, 2),
    phone_charge DECIMAL(10, 2),
    notes TEXT NULL
);

ALTER TABLE rooms ADD CONSTRAINT fk_room_type FOREIGN KEY (room_type) REFERENCES room_types (room_type);
ALTER TABLE rooms ADD CONSTRAINT fk_bed_type FOREIGN KEY (bed_type) REFERENCES bed_types (bed_type);
ALTER TABLE rooms ADD CONSTRAINT fk_room_status FOREIGN KEY (room_status) REFERENCES room_status (room_status);
ALTER TABLE payments ADD CONSTRAINT fk_employee_id FOREIGN KEY (employee_id) REFERENCES employees (id);
ALTER TABLE payments ADD CONSTRAINT fk_account_number FOREIGN KEY (account_number) REFERENCES customers (account_number);
ALTER TABLE occupancies ADD CONSTRAINT fk_occ_employee_id FOREIGN KEY (employee_id) REFERENCES employees (id);
ALTER TABLE occupancies ADD CONSTRAINT fk_occ_account_number FOREIGN KEY (account_number) REFERENCES customers (account_number);
ALTER TABLE occupancies ADD CONSTRAINT fk_occ_room_number FOREIGN KEY (room_number) REFERENCES rooms(room_number);

INSERT INTO employees(first_name, last_name, title, notes) 
VALUES ("Name1", "last1", "title1", "comment"), ("Name2", "Last2", "title2", "comment"), ("name3", "Last3", "title3", "comment");

INSERT INTO customers(account_number, first_name, last_name, phone_number, emergency_name, emergency_number, notes) 
VALUES (123, "fname1", "lname1", "+359888777666", "ename", "+359999888777", "some long notes"),
(456, "fname2", "lname2", "+359555444333", "ename", "+359222111000", "some long notes"),
(789, "fname3", "lname3", "+359123456789", "ename", "+359987654321", "some long notes");

INSERT INTO room_status(room_status, notes) 
VALUES ("occupied", "notes"), ("for cleaning", "notes"), ("for repair", "notes");

INSERT INTO room_types(room_type, notes) 
VALUES ("single", "notes 1"), ("studio", "notes 1"), ("one-bedroom apartment", "notes 1");

INSERT INTO bed_types(bed_type, notes) 
VALUES ("single bed", "for one person"), ("double bed", "for two person"), ("king-size bed", "for two person extra-large bed");

INSERT INTO rooms(room_number, room_type, bed_type, rate, room_status, notes)
VALUES (101, "single", "single bed", 100.1, "occupied", "notes 1"),
(102, "studio", "double bed", 120.50, "for cleaning", "notes 1"),
(103, "one-bedroom apartment", "king-size bed", 150.33, "for repair", "notes 1");

INSERT INTO payments(employee_id, payment_date, account_number, first_date_occupied, last_date_occupied, total_days, amount_charged, tax_rate, tax_amount, payment_total, notes) 
VALUES (1, "2020-01-16", 123, "2020-01-01", "2020-01-18", 7, 1000, 9, 90, 1090, "some notes"),
(2, "2020-01-16", 456, "2020-01-01", "2020-01-18", 7, 1000, 9, 90, 1090, "some notes"),
(3, "2020-01-16", 789, "2020-01-01", "2020-01-18", 7, 1000, 9, 90, 1090, "some notes");

INSERT INTO occupancies(employee_id, date_occupied, account_number, room_number, rate_applied, phone_charge, notes)
VALUES (3, "2020-01-01", 456, 101, 9, 120.22, "afasfasf"),
(2, "2020-01-01", 123, 102, 9, 121.22, "afasfasf"),
(1, "2020-01-01", 789, 103, 9, 122.22, "afasfasf");

#14. Create SoftUni Database
CREATE SCHEMA soft_uni;
USE soft_uni;

CREATE TABLE towns (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE addresses (
	id INT PRIMARY KEY AUTO_INCREMENT,
    address_text VARCHAR(80) NOT NULL,
    town_id INT NOT NULL
);

CREATE TABLE departments (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE employees (
	id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(30) NOT NULL,
    middle_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    job_title VARCHAR(30) NOT NULL,
    department_id INT NOT NULL,
    hire_date DATE NOT NULL,
    salary DECIMAL(8, 2) NOT NULL,
    address_id INT
);

ALTER TABLE addresses
ADD CONSTRAINT fk_town_id FOREIGN KEY (town_id) REFERENCES towns (id);
ALTER TABLE employees
ADD CONSTRAINT fk_department_id FOREIGN KEY (department_id) REFERENCES departments (id);
ALTER TABLE employees
ADD CONSTRAINT fk_address_id FOREIGN KEY (address_id) REFERENCES addresses (id);

#15. Basic Insert
INSERT INTO towns (`name`) VALUES ("Sofia"), ("Plovdiv"), ("Varna"), ("Burgas");
INSERT INTO departments (`name`) VALUES ("Engineering"), ("Sales"), ("Marketing"), ("Software Development"), ("Quality Assurance");
INSERT INTO employees (first_name, middle_name, last_name, job_title, department_id, hire_date, salary)
VALUES 
("Ivan", "Ivanov", "Ivanov", ".NET Developer", 4, "2013-02-01", 3500.00),
("Petar", "Petrov", "Petrov", "Senior Engineer", 1, "2004-03-02", 4000.00),
("Maria", "Petrova", "Ivanova", "Intern", 5, "2016-08-28", 525.25), 
("Georgi", "Terziev", "Ivanov", "CEO", 2, "2007-12-09", 3000.00),
("Peter", "Pan", "Pan", "Intern", 3, "2016-08-28", 599.88);

#16. Basic Select All Fields
SELECT * FROM towns;
SELECT * FROM departments;
SELECT * FROM employees;

#17. Basic Select All Fields and Order Them
SELECT * FROM towns ORDER BY name;
SELECT * FROM departments ORDER BY name;
SELECT * FROM employees ORDER BY salary DESC;

#18. Basic Select Some Fields
SELECT name FROM towns ORDER BY name;
SELECT name FROM departments ORDER BY name;
SELECT first_name, last_name, job_title, salary FROM employees ORDER BY salary DESC;

#19. Increase Employees Salary
UPDATE employees 
SET salary = salary + salary * 0.1;
SELECT salary FROM employees; 

#20. Decrease Tax Rate
UPDATE payments
SET tax_rate = tax_rate - tax_rate * 0.03;
SELECT tax_rate FROM payments;

#21. Delete All Records
TRUNCATE TABLE occupancies;
