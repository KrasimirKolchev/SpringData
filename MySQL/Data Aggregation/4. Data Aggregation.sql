#-----------Lab-----------

USE restaurant;

#1.	 Departments Info
SELECT department_id, COUNT(department_id) AS 'Number of employees' FROM employees
GROUP BY department_id
ORDER BY 'Number of employees';

#2.	Average Salary
SELECT department_id, ROUND(AVG(salary), 2) AS 'Average salary' FROM employees
GROUP BY department_id
ORDER BY department_id;

#3.	 Min Salary
SELECT department_id, ROUND(MIN(salary), 2) AS 'Min Salary' FROM employees
GROUP BY department_id
HAVING MIN(salary) > 800;

#4.	 Appetizers Count
SELECT COUNT(category_id) AS 'Apetizers count' FROM products
WHERE category_id = 2 AND price > 8
GROUP BY category_id;

#5.	 Menu Prices
SELECT category_id, 
		ROUND(AVG(price), 2) AS 'Average Price', 
        ROUND(MIN(price), 2) AS 'Cheapest Product',
        ROUND(MAX(price), 2) AS 'Most Expensive Product'
FROM products
GROUP BY category_id
ORDER BY category_id;

#-----------Exercise----------

USE gringotts;

#1.	 Records’ Count
SELECT COUNT(id) AS 'count' FROM wizzard_deposits;

#2.	 Longest Magic Wand
SELECT MAX(magic_wand_size) AS longest_magic_wand FROM wizzard_deposits;

#3.  Longest Magic Wand Per Deposit Groups
SELECT deposit_group, MAX(magic_wand_size) AS longest_magic_wand FROM wizzard_deposits
GROUP BY deposit_group
ORDER BY longest_magic_wand, deposit_group;

#4.  Smallest Deposit Group Per Magic Wand Size
SELECT deposit_group FROM wizzard_deposits
GROUP BY deposit_group
ORDER BY AVG(magic_wand_size) LIMIT 1;

#5.	 Deposits Sum
SELECT deposit_group, SUM(deposit_amount) AS total_sum FROM wizzard_deposits
GROUP BY deposit_group
ORDER BY total_sum;

#6.  Deposits Sum for Ollivander Family
SELECT deposit_group, SUM(deposit_amount) AS total_sum FROM wizzard_deposits
WHERE magic_wand_creator = 'Ollivander family'
GROUP BY deposit_group
ORDER BY deposit_group;

#7.  Deposits Filter
SELECT deposit_group, SUM(deposit_amount) AS total_sum FROM wizzard_deposits
WHERE magic_wand_creator = 'Ollivander family'
GROUP BY deposit_group
HAVING SUM(deposit_amount) < 150000
ORDER BY total_sum DESC;

#8.  Deposit Charge
SELECT deposit_group, magic_wand_creator, MIN(deposit_charge) AS min_deposit_charge FROM wizzard_deposits
GROUP BY deposit_group, magic_wand_creator
ORDER BY magic_wand_creator, deposit_group;

#9.  Age Groups
SELECT CASE
	WHEN age <= 10 THEN '[0-10]'
    WHEN age <= 20 THEN '[11-20]'
    WHEN age <= 30 THEN '[21-30]'
    WHEN age <= 40 THEN '[31-40]'
    WHEN age <= 50 THEN '[41-50]'
    WHEN age <= 60 THEN '[51-60]'
    WHEN age > 60 THEN '[61+]'
END AS age_group,
COUNT('age') AS wizzard_count FROM wizzard_deposits
GROUP BY age_group
ORDER BY age_group;

#10.  First Letter
SELECT DISTINCT SUBSTRING(first_name, 1, 1) AS first_letter FROM wizzard_deposits
WHERE deposit_group = 'Troll Chest'
ORDER BY first_letter;

#11.  Average Interest 
SELECT deposit_group, is_deposit_expired, AVG(deposit_interest) AS average_interest FROM wizzard_deposits
WHERE deposit_start_date > DATE('1985-01-01')
GROUP BY deposit_group, is_deposit_expired
ORDER BY deposit_group DESC, is_deposit_expired;

#12.  Rich Wizard, Poor Wizard
SELECT SUM(f.deposit_amount - s.deposit_amount) AS sum_difference
FROM wizzard_deposits AS f
INNER JOIN wizzard_deposits AS s
ON f.id + 1 = s.id;


USE soft_uni;

#13.	 Employees Minimum Salaries
SELECT department_id, MIN(salary) AS minimum_salary FROM employees
WHERE hire_date > DATE('2000-01-01') 
GROUP BY department_id
HAVING department_id = 2 OR department_id = 5 OR department_id = 7
ORDER BY department_id;

#14.	Employees Average Salaries
CREATE TABLE high_paid_employees AS (
	SELECT * FROM employees 
    WHERE salary > 30000);

DELETE FROM high_paid_employees
WHERE manager_id = 42;

UPDATE high_paid_employees
SET salary = salary + 5000
WHERE department_id = 1;

SELECT department_id, AVG(salary) AS avg_salary FROM high_paid_employees
GROUP BY department_id
ORDER BY department_id;

#15.   Employees Maximum Salaries
SELECT department_id, MAX(salary) AS max_salary FROM employees
GROUP BY department_id
HAVING MAX(salary) < 30000 OR MAX(salary) > 70000
ORDER BY department_id;

#16.	Employees Count Salaries
SELECT COUNT(employee_id) AS 'count' FROM employees
WHERE manager_id IS NULL;

#17.	3rd Highest Salary
SELECT department_id,
    (SELECT DISTINCT empl2.salary
        FROM employees AS empl2
        WHERE empl2.department_id = empl1.department_id
        ORDER BY empl2.salary DESC
        LIMIT 2, 1) AS third_highest_salary
FROM employees AS empl1
GROUP BY department_id
HAVING third_highest_salary IS NOT NULL;

#18.	 Salary Challenge
SELECT first_name, last_name, department_id FROM employees AS empl
WHERE salary > (SELECT AVG(salary) FROM employees AS e WHERE empl.department_id = e.department_id)
ORDER BY department_id, employee_id LIMIT 10;

#19.	Departments Total Salaries
SELECT department_id, SUM(salary) as total_salary FROM employees
GROUP BY department_id
ORDER BY department_id;
