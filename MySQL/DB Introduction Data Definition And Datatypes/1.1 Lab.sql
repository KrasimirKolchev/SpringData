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


