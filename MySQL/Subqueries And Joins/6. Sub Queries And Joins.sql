#-----------Lab-----------

USE soft_uni;

#1.	 Managers
SELECT e.employee_id, CONCAT(e.first_name, ' ', e.last_name) AS full_name, d.department_id, d.`name` AS department_name FROM employees AS e
JOIN departments AS d
ON e.employee_id = d.manager_id
ORDER BY employee_id LIMIT 5;

#2.	 Towns Addresses
SELECT t.town_id, t.name AS town_name, a.address_text FROM towns AS t
JOIN addresses AS a
ON a.town_id = t.town_id
WHERE t.`name` = 'Sofia' OR t.`name` = 'San Francisco' OR t.`name` = 'Carnation'
ORDER BY t.town_id, a.address_id;

#3.	 Employees Without Managers
SELECT employee_id, first_name, last_name, department_id, salary FROM employees
WHERE manager_id IS NULL;

#4.	 Higher Salary
SELECT COUNT(*) AS 'count' FROM employees
WHERE salary > (SELECT AVG(salary) FROM employees);

#-----------Exercise-----------

USE soft_uni;

#1.	 Employee Address
SELECT e.employee_id, e.job_title, e.address_id, a.address_text FROM employees AS e
INNER JOIN addresses AS a
ON e.address_id = a.address_id
ORDER BY e.address_id LIMIT 5;

#2.	 Addresses with Towns
SELECT e.first_name, e.last_name, t.`name` AS 'town', a.address_text FROM employees AS e
INNER JOIN addresses AS a
ON e.address_id = a.address_id
JOIN towns AS t
ON a.town_id = t.town_id
ORDER BY e.first_name, e.last_name LIMIT 5;

#3.	 Sales Employee
SELECT e.employee_id, e.first_name, e.last_name, d.`name` AS department_name FROM employees AS e
INNER JOIN departments AS d
ON e.department_id = d.department_id
WHERE d.`name` = 'Sales'
ORDER BY e.employee_id DESC;

#4.	 Employee Departments
SELECT e.employee_id, e.first_name, salary, d.`name` AS department_name FROM employees AS e
INNER JOIN departments AS d
ON e.department_id = d.department_id
WHERE e.salary > 15000
ORDER BY d.department_id DESC LIMIT 5;

#5.	 Employees Without Project
SELECT e.employee_id, e.first_name FROM employees AS e
LEFT JOIN employees_projects AS ep
ON e.employee_id = ep.employee_id
WHERE ep.project_id IS NULL
ORDER BY e.employee_id DESC LIMIT 3;

#6.	 Employees Hired After
SELECT e.first_name, e.last_name, e.hire_date, d.`name` AS dept_name FROM employees AS e
JOIN departments AS d
ON e.department_id = d.department_id
WHERE DATE(e.hire_date) > '1999-01-01' AND d.`name` IN('Sales', 'Finance')
ORDER BY e.hire_date;

#7.	 Employees with Project
SELECT e.employee_id, e.first_name, p.`name` AS project_name FROM employees AS e
INNER JOIN employees_projects AS ep
ON e.employee_id = ep.employee_id
JOIN projects AS p
ON ep.project_id = p.project_id
WHERE DATE(p.start_date) > '2002-08-13' AND p.end_date IS NULL
ORDER BY e.first_name, p.`name` LIMIT 5;

#8.  Employee 24
SELECT e.employee_id, e.first_name, IF(DATE(p.start_date) < '2005-01-01', p.`name`, NULL) AS project_name FROM employees AS e
JOIN employees_projects AS ep
ON e.employee_id = ep.employee_id
JOIN projects AS p
ON ep.project_id = p.project_id
WHERE e.employee_id = 24
ORDER BY project_name;

#9.	 Employee Manager
SELECT e.employee_id, e.first_name, m.employee_id, m.first_name AS manager_name FROM employees AS e
INNER JOIN employees AS m
ON e.manager_id = m.employee_id
WHERE e.manager_id = 3 OR e.manager_id = 7
ORDER BY e.first_name;

#10.  Employee Summary
SELECT e.employee_id, CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
		CONCAT(m.first_name, ' ', m.last_name) AS manager_name, d.`name` AS department_name FROM employees AS e
INNER JOIN employees AS m
ON e.manager_id = m.employee_id
JOIN departments AS d
ON e.department_id = d.department_id
ORDER BY e.employee_id LIMIT 5;	

#11.  Min Average Salary
SELECT AVG(salary) AS min_average_salary FROM employees
GROUP BY department_id
ORDER BY min_average_salary LIMIT 1;



USE geography;

#12.	Highest Peaks in Bulgaria
SELECT mc.country_code, m.mountain_range, p.peak_name, p.elevation FROM peaks AS p
JOIN mountains AS m
ON p.mountain_id = m.id
JOIN mountains_countries AS mc
ON m.id = mc.mountain_id
WHERE mc.country_code = 'BG' AND p.elevation > 2835
ORDER BY p.elevation DESC;

#13.	Count Mountain Ranges
SELECT mc.country_code, COUNT(mc.country_code) AS mountain_range FROM mountains_countries AS mc
WHERE mc.country_code IN('RU', 'BG', 'US')
GROUP BY mc.country_code
ORDER BY mountain_range DESC;

#14.	Countries with Rivers
SELECT c.country_name, r.river_name FROM countries AS c
LEFT JOIN countries_rivers AS cr
ON c.country_code = cr.country_code
LEFT JOIN rivers AS r
ON cr.river_id = r.id
WHERE c.continent_code = 'AF'
ORDER BY c.country_name LIMIT 5;

#15.	Continents and Currencies
SELECT co.continent_code, co.currency_code, co.currency_usage FROM
    (SELECT c.continent_code, c.currency_code,
    COUNT(c.currency_code) AS currency_usage FROM countries AS c
    GROUP BY c.currency_code, c.continent_code HAVING currency_usage > 1) as co
LEFT JOIN
    (SELECT c.continent_code, c.currency_code,
    COUNT(c.currency_code) AS currency_usage FROM countries as c
	GROUP BY c.currency_code, c.continent_code HAVING currency_usage > 1) as co1
ON co.continent_code = co1.continent_code AND co1.currency_usage > co.currency_usage

WHERE co1.currency_usage IS NULL
ORDER BY co.continent_code, co.currency_code;

#16.  Countries Without Any Mountains
SELECT COUNT(*) AS country_count FROM countries AS c
LEFT JOIN mountains_countries AS mc
ON c.country_code = mc.country_code
WHERE mc.mountain_id IS NULL;

#17.  Highest Peak and Longest River by Country
SELECT c.country_name, p.elevation AS highest_peak_elevation, r.length AS longest_river_length FROM countries AS c
INNER JOIN mountains_countries AS mc
ON c.country_code = mc.country_code
INNER JOIN peaks AS p
ON mc.mountain_id = p.mountain_id
INNER JOIN countries_rivers AS cr
ON c.country_code = cr.country_code
INNER JOIN rivers AS r
ON cr.river_id = r.id
GROUP BY c.country_name
ORDER BY p.elevation DESC, r.length DESC, c.country_name LIMIT 5;


