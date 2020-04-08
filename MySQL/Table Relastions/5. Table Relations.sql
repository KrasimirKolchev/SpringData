#-----------Lab-----------

USE camp;

#1.	 Mountains and Peaks
CREATE TABLE mountains (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `name` VARCHAR(50) NULL
);

CREATE TABLE peaks (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `name` VARCHAR(50) NULL,
    mountain_id INT NOT NULL,
    CONSTRAINT fk_peak_mountains
    FOREIGN KEY (mountain_id)
    REFERENCES mountains(id)
    ON DELETE CASCADE
);

#2.	 Trip Organization
SELECT driver_id, vehicle_type, CONCAT(first_name, ' ', last_name) AS driver_name FROM vehicles
	JOIN campers 
    ON vehicles.driver_id = campers.id;

#3.	SoftUni Hiking
SELECT starting_point AS route_starting_point, end_point AS route_ending_point,
	leader_id, CONCAT_WS(' ', first_name, last_name) AS leader_name
	FROM routes
	JOIN campers
	ON routes.leader_id = campers.id;

#4.	Delete Mountains
DROP TABLE mountains, peaks;

#-----------Exercise----------

CREATE SCHEMA myDB;
USE myDB;

#1.	One-To-One Relationship
CREATE TABLE persons (
	person_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	first_name VARCHAR(45) NOT NULL,
    salary DECIMAL(8, 2) NOT NULL
);

CREATE TABLE passports (
	passport_id INT PRIMARY KEY NOT NULL,
    passport_number VARCHAR(30) UNIQUE
);

ALTER TABLE persons
	ADD COLUMN passport_id INT NOT NULL UNIQUE,
	ADD CONSTRAINT fk_person_passport
    FOREIGN KEY (passport_id)
    REFERENCES passports(passport_id);
    
INSERT INTO passports (passport_id, passport_number)
VALUES (101, 'N34FG21B'), (102, 'K65LO4R7'), (103, 'ZE657QP2');
    
INSERT INTO persons (first_name, salary, passport_id) 
VALUES ('Roberto', 43300.00, 102), ('Tom', 56100.00, 103), ('Yana', 60200.00, 101);

#2.	One-To-Many Relationship
CREATE TABLE manufacturers (
	manufacturer_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `name` VARCHAR(40) NOT NULL,
    established_on DATE NOT NULL
);

CREATE TABLE models (
	model_id INT PRIMARY KEY NOT NULL,
    `name` VARCHAR(30) NOT NULL,
    manufacturer_id INT NOT NULL,
    CONSTRAINT fk_models_manufacturers
    FOREIGN KEY (manufacturer_id)
    REFERENCES manufacturers(manufacturer_id)
);

INSERT INTO manufacturers (`name`, established_on)
VALUES ('BMW', '1916-03-01'), ('Tesla', '2003-01-01'), ('Lada', '1966-05-01');
INSERT INTO models (model_id, `name`, manufacturer_id)
VALUES (101, 'X1', 1), (102, 'i6', 1), (103, 'Model S', 2), (104, 'Model X', 2),
(105, 'Model 3', 2), (106, 'Nova', 3);

#3.	Many-To-Many Relationship
CREATE TABLE students (
	student_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(30)
);

CREATE TABLE exams (
	exam_id INT PRIMARY KEY NOT NULL,
    `name` VARCHAR(30)
);
    
CREATE TABLE students_exams (
	student_id INT NOT NULL,
    exam_id INT NOT NULL,
    CONSTRAINT pk_students_exams PRIMARY KEY (student_id, exam_id),
    CONSTRAINT fk_students_exams_students FOREIGN KEY (student_id) REFERENCES students(student_id),
    CONSTRAINT fk_students_exams_exams FOREIGN KEY (exam_id) REFERENCES exams(exam_id)
);

INSERT INTO students (`name`) VALUES ('Mila'), ('Toni'), ('Ron');
INSERT INTO exams (exam_id, `name`) VALUES (101, 'Spring MVC'), (102, 'Neo4j'), (103, 'Oracle 11g');
INSERT INTO students_exams (student_id, exam_id)
VALUES (1, 101), (1, 102), (2, 101), (3, 103), (2, 102), (2, 103);

#4.	Self-Referencing
CREATE TABLE teachers (
	teacher_id INT PRIMARY KEY NOT NULL,
    `name` VARCHAR(30) NOT NULL,
    manager_id INT NULL
);

INSERT INTO teachers (teacher_id, name, manager_id) 
VALUES (101, 'John', NULL), (102, 'Maya', 106), (103, 'Silvia', 106), (104, 'Ted', 105), (105, 'Mark', 101), (106, 'Greta', 101); 

ALTER TABLE teachers
ADD CONSTRAINT fk_teacher_manager FOREIGN KEY (manager_id) REFERENCES teachers(teacher_id);



#5.	Online Store Database
CREATE SCHEMA online_store;
USE online_store;

CREATE TABLE cities (
	city_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(50) NOT NULL
);

CREATE TABLE customers (
	customer_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    birthday DATE NOT NULL,
    city_id INT NOT NULL,
    CONSTRAINT fk_customer_city FOREIGN KEY (city_id) REFERENCES cities(city_id)
);

CREATE TABLE item_types (
	item_type_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(50)
);

CREATE TABLE items (
	item_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    item_type_id INT NOT NULL,
    CONSTRAINT fk_items_item FOREIGN KEY (item_type_id) REFERENCES item_types(item_type_id)
);

CREATE TABLE orders (
	order_id INT PRIMARY KEY NOT NULL,
    customer_id INT NOT NULL,
    CONSTRAINT fk_customers_orders_customer_id FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

CREATE TABLE order_items (
	order_id INT NOT NULL,
    item_id INT NOT NULL,
    CONSTRAINT pk_order_items PRIMARY KEY (order_id, item_id),
    CONSTRAINT fk_orders_orderitems_order_id FOREIGN KEY (order_id) REFERENCES orders(order_id),
    CONSTRAINT fk_items_orderitems_item_id FOREIGN KEY (item_id) REFERENCES items(item_id)
);


#6.	University Database
CREATE SCHEMA university;
USE university;

CREATE TABLE majors (
	major_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(50) NOT NULL
);

CREATE TABLE students (
	student_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    student_number VARCHAR(12) NOT NULL,
    student_name VARCHAR(50) NOT NULL,
    major_id INT NOT NULL,
    CONSTRAINT fk_students_major_id FOREIGN KEY (major_id) REFERENCES majors(major_id)
);

CREATE TABLE payments (
	payment_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    payment_date DATE NOT NULL,
    payment_amount DECIMAL(8,2) NOT NULL,
    student_id INT NOT NULL,
    CONSTRAINT fk_payments_student_id FOREIGN KEY (student_id) REFERENCES students(student_id)
);

CREATE TABLE subjects (
	subject_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    subject_name VARCHAR(50) NOT NULL
);

CREATE TABLE agenda (
	student_id INT NOT NULL,
    subject_id INT NOT NULL,
    CONSTRAINT pk_agenda_student_subject PRIMARY KEY (student_id, subject_id),
    CONSTRAINT fk_agenda_subject_id FOREIGN KEY (subject_id) REFERENCES subjects(subject_id),
    CONSTRAINT fk_agenda_student_id FOREIGN KEY (student_id) REFERENCES students(student_id)
);


USE geography;

#9.	Peaks in Rila
SELECT m.mountain_range, p.peak_name, p.elevation AS peak_elevation FROM peaks AS p
	JOIN mountains AS m
    ON m.id = p.mountain_id
WHERE m.mountain_range = 'Rila'
ORDER BY peak_elevation DESC;
