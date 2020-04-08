#------------Lab------------

USE hospital;

#1. Select Employee Information
SELECT id, first_name, last_name, job_title FROM employees
ORDER BY id;

#2. Select Employees with Filter
SELECT id, concat_ws(' ', `first_name`, `last_name`) AS 'full_name', `job_title` AS 'job', `salary` AS 'salary'
FROM employees WHERE salary > 1000
ORDER BY id; 

#3. Update Employees Salary
UPDATE employees
SET salary = salary * 1.1
WHERE job_title = "Therapist";
SELECT salary FROM employees
ORDER BY salary;

#4. Top Paid Employee
CREATE VIEW top_paid_employee AS
SELECT * FROM employees
ORDER BY salary DESC LIMIT 1;
SELECT * from top_paid_employee;

#5. Select Employees by Multiple Filters
SELECT * FROM employees AS e
WHERE e.department_id = 4 AND e.salary >= 1600
ORDER BY id;

#6. Delete from Table
DELETE FROM employees
WHERE department_id = 2 OR department_id = 1;
SELECT * FROM employees
ORDER BY id;


#------------Exercise------------

USE soft_uni;

#1. Find All Information About Departments 
SELECT department_id, `name`, manager_id FROM departments
ORDER BY department_id;

#2. Find all Department Names 
SELECT `name` FROM departments
ORDER BY department_id;

#3. Find salary of Each Employee 
SELECT first_name, last_name, salary FROM employees
ORDER BY employee_id;

#4. Find Full Name of Each Employee 
SELECT first_name, middle_name, last_name FROM employees
ORDER BY employee_id;

#5. Find Email Address of Each Employee 
SELECT CONCAT(`first_name`, '.', `last_name`, '@softuni.bg') AS full_email_address
FROM employees;

#6. Find All Different Employee’s Salaries 
SELECT DISTINCT salary FROM employees
ORDER BY employee_id;

#7. Find all Information About Employees 
SELECT * FROM employees
WHERE job_title = "Sales Representative"
ORDER BY employee_id;

#8. Find Names of All Employees by salary in Range 
SELECT first_name, last_name, job_title FROM employees
WHERE salary BETWEEN 20000 AND 30000
ORDER BY employee_id;

#9.  Find Names of All Employees  
CREATE VIEW `Full Name` AS
SELECT CONCAT_WS(' ', first_name, middle_name, last_name)
FROM employees 
WHERE salary = 25000 OR 
	salary = 14000 OR 
    salary = 12500 OR 
    salary = 23600;
SELECT * FROM `Full Name`;

#10.  Find All Employees Without Manager 
SELECT first_name, last_name FROM employees
WHERE manager_id is NULL;

#11. Find All Employees with salary More Than 50000
SELECT first_name, last_name, salary FROM employees
WHERE salary > 50000
ORDER BY salary DESC;

#12. Find 5 Best Paid Employees 
SELECT first_name, last_name FROM employees
ORDER BY salary DESC LIMIT 5;

#13. Find All Employees Except Marketing 
SELECT first_name, last_name FROM employees
WHERE NOT department_id = 4;

#14. Sort Employees Table 
SELECT * FROM employees
ORDER BY salary DESC, first_name, last_name DESC, middle_name;

#15. Create View Employees with Salaries 
CREATE VIEW v_employees_salaries AS
SELECT first_name, last_name, salary FROM employees;
SELECT * FROM v_employees_salaries;

#16. Create View Employees with Job Titles 
CREATE VIEW v_employees_job_titles AS
SELECT CONCAT_WS(' ', `first_name`, IFNULL(`middle_name`, ''), `last_name`) AS `full_name`, `job_title` FROM employees;
SELECT * FROM v_employees_job_titles;

#17.  Distinct Job Titles 
SELECT DISTINCT job_title AS 'Job_title' FROM employees
ORDER BY job_title;

#18. Find First 10 Started Projects 
SELECT project_id AS 'id', `name` AS 'Name', description AS 'Description', start_date, end_date FROM projects
ORDER BY start_date, `name`, project_id LIMIT 10;

#19.  Last 7 Hired Employees 
SELECT first_name, last_name, hire_date FROM employees
ORDER BY hire_date DESC LIMIT 7;

#20. Increase Salaries 
UPDATE employees
SET salary = salary * 1.12
WHERE department_id = 1 OR department_id = 2 OR department_id = 4 OR department_id = 11;
SELECT salary FROM employees;


USE geography;

#21.  All Mountain Peaks 
SELECT peak_name FROM peaks
ORDER BY peak_name;

#22.  Biggest Countries by Population 
SELECT country_name, population FROM countries
WHERE continent_code = 'EU'
ORDER BY population DESC, country_name LIMIT 30;

#23.  Countries and Currency (Euro / Not Euro) 
CREATE VIEW country_currency AS
SELECT country_name, country_code, currency_code FROM countries
ORDER BY country_name;
SELECT country_name, country_code, IF(currency_code = "EUR", "Euro", "Not Euro") AS 'currency' FROM country_currency;


USE diablo;

#24.  All Diablo Characters 
SELECT `name` FROM characters
ORDER BY `name`;


