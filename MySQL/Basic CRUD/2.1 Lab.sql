
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


