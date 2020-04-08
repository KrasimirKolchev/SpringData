#-----------------Lab------------------

USE book_library;

#1.	Find Book Titles
SELECT title FROM books
WHERE SUBSTRING(title, 1, 3) = 'The'; 

#2.	Replace Titles
SELECT REPLACE(title, 'The', '***') AS 'Title' FROM books
WHERE SUBSTRING(title, 1, 3) = 'The'
ORDER BY id;

#3.	Sum Cost of All Books
SELECT FORMAT(SUM(cost), 2) FROM books;

#4.	Days Lived
SELECT CONCAT_WS(' ', first_name, last_name) AS 'Full Name',
TIMESTAMPDIFF(DAY, born, died) AS 'Days Lived'
FROM authors;

#5.	Harry Potter Books
SELECT title FROM books
WHERE title LIKE 'Harry Potter%'
ORDER BY id;

#-----------------Exercise------------------

USE soft_uni;

#1.	Find Names of All Employees by First Name
SELECT first_name, last_name FROM employees
WHERE SUBSTRING(UCASE(first_name), 1, 2) = UCASE('Sa')
ORDER BY employee_id;

#2.	Find Names of All Employees by Last Name
SELECT first_name, last_name FROM employees
WHERE LOCATE('ei', last_name)
ORDER BY employee_id;

#3.	Find First Names of All Employees
CREATE VIEW empl AS
SELECT first_name, hire_date FROM employees
WHERE department_id = '3' OR department_id = '10'
ORDER BY employee_id;
SELECT first_name FROM empl
WHERE EXTRACT(YEAR FROM hire_date) BETWEEN 1995 AND 2005;

#4.	Find All Employees Except Engineers
SELECT first_name, last_name FROM employees
WHERE NOT LOCATE('engineer', LCASE(job_title))
ORDER BY employee_id;

#5.	Find Towns with Name Length
SELECT name FROM towns
WHERE CHARACTER_LENGTH(name) BETWEEN 5 AND 6
ORDER BY name;

#6.	 Find Towns Starting With
SELECT town_id, name FROM towns
WHERE SUBSTRING(`name`, 1, 1) NOT IN('m', 'k', 'b', 'e')
ORDER BY name;

#Find Towns Not Starting With
SELECT town_id, name FROM towns
WHERE SUBSTRING(`name`, 1, 1) NOT IN('r', 'b', 'd')
ORDER BY name;

#8.	Create View Employees Hired After 2000 Year
CREATE VIEW v_employees_hired_after_2000 AS
SELECT first_name, last_name FROM employees
WHERE EXTRACT(YEAR FROM hire_date) > 2000;
SELECT * FROM v_employees_hired_after_2000;

#9.	Length of Last Name
SELECT first_name, last_name FROM employees
WHERE CHARACTER_LENGTH(last_name) = 5;


USE geography;

#10.	Countries Holding ‘A’ 3 or More Times
SELECT country_name, iso_code FROM countries
WHERE CHAR_LENGTH(country_name) - CHAR_LENGTH(REPLACE(LOWER(country_name), 'a', '')) >= 3
ORDER BY iso_code;

#11.	 Mix of Peak and River Names
SELECT peak_name, river_name, LOWER(CONCAT(peak_name, SUBSTRING(river_name, 2))) AS mix FROM peaks, rivers
WHERE RIGHT(peak_name, 1) = LEFT(river_name, 1)
ORDER BY `mix`;


USE diablo;

#12.	Games from 2011 and 2012 Year
SELECT `name`, DATE_FORMAT(`start`, '%Y-%m-%d') FROM `games`
WHERE EXTRACT(YEAR FROM start) IN(2011, 2012)
ORDER BY `start`, `name` LIMIT 50;

#13.	 User Email Providers
SELECT user_name, SUBSTRING(`email`, LOCATE('@', `email`) + 1) AS `Email Provider` FROM users
ORDER BY `Email Provider`, user_name;

#14.	 Get Users with IP Address Like Pattern
SELECT user_name, ip_address FROM users
WHERE ip_address LIKE '___.1%.%.___'
ORDER BY user_name;

#15.	 Show All Games with Duration and Part of the Day
SELECT `name` AS 'game', 
CASE
	WHEN EXTRACT(HOUR FROM `start`) >= 0 AND EXTRACT(HOUR FROM `start`) < 12 THEN 'Morning'
    WHEN EXTRACT(HOUR FROM `start`) >= 12 AND EXTRACT(HOUR FROM `start`) < 18 THEN 'Afternoon'
    ELSE 'Evening'
END AS 'Part Of The Day',
CASE
	WHEN duration <= 3 THEN 'Extra Short'
    WHEN duration > 3 AND duration <= 6 THEN 'Short'
    WHEN duration > 6 AND duration <= 10 THEN 'Long'
    ELSE 'Extra Long'
END AS 'Duration'
FROM games;


USE orders;

#16.	 Orders Table
SELECT product_name, order_date, 
ADDDATE(order_date, 3) AS pay_due,
ADDDATE(order_date, INTERVAL 1 MONTH) AS deliver_due 
FROM orders;
