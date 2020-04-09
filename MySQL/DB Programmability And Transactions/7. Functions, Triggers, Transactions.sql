#-----------Lab-----------
USE soft_uni;

#1.	Count Employees by Town
DELIMITER $$
CREATE FUNCTION ufn_count_employees_by_town(town_name VARCHAR(20))
RETURNS DOUBLE
DETERMINISTIC
	BEGIN
		DECLARE e_count DOUBLE;
        SET e_count := (SELECT COUNT(employee_id) FROM employees AS e
        INNER JOIN addresses AS a ON e.address_id = a.address_id
        INNER JOIN towns AS t ON a.town_id = t.town_id
        WHERE t.`name` = town_name);
        RETURN e_count;
	END;$$
SELECT ufn_count_employees_by_town('Sofia') AS count;


#2.	Employees Promotion
DELIMITER $$
USE `soft_uni`$$
CREATE PROCEDURE `usp_raise_salaries`(department_name VARCHAR(50))
    DETERMINISTIC
BEGIN
	UPDATE employees AS e
    JOIN departments AS d ON e.department_id = d.department_id
    SET salary = salary * 1.05
    WHERE d.`name` = department_name;
END;$$

DELIMITER ;
CALL usp_get_salary_by_department("Finance");

#3. Employees Promotion by ID
DELIMITER $$
USE `soft_uni`$$
CREATE PROCEDURE `usp_raise_salary_by_id`(id INT)
BEGIN
	UPDATE employees AS e
	SET e.salary = e.salary * 1.05
	WHERE e.employee_id = id;
END;$$
DELIMITER ;
CALL usp_raise_salary_by_id(293);

#4. Triggered
CREATE TABLE deleted_employees (
	employee_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50) NOT NULL,
    job_title VARCHAR(50) NOT NULL,
    department_id INT NOT NULL,
    salary DECIMAL (19,4)
);

DELIMITER $$
CREATE TRIGGER tr_deleted_employees
AFTER DELETE ON employees FOR EACH ROW
BEGIN
	INSERT INTO deleted_employees (first_name, last_name, middle_name, job_title, department_id, salary)
	VALUES (OLD.first_name, OLD.last_name, OLD.middle_name, OLD.job_title, OLD.department_id, OLD.salary);
END; $$

DELETE FROM employees
WHERE employee_id IN (1);


#----------Exercise----------


USE soft_uni;

#1.	Employees with Salary Above 35000
DELIMITER $$
USE `soft_uni`$$
CREATE PROCEDURE `usp_get_employees_salary_above_35000` ()
BEGIN
	SELECT e.first_name, e.last_name FROM employees AS e
    WHERE e.salary > 35000
    ORDER BY e.first_name, e.last_name, e.employee_id;
END$$
DELIMITER ;
CALL usp_get_employees_salary_above_35000;


#2.	Employees with Salary Above Number
DELIMITER $$
USE `soft_uni`$$
CREATE PROCEDURE `usp_get_employees_salary_above`(num DECIMAL(19,2))
BEGIN
	SELECT e.first_name, e.last_name FROM employees AS e
    WHERE e.salary >= num
    ORDER BY e.first_name, e.last_name, e.employee_id;
END$$
DELIMITER ;
CALL usp_get_employees_salary_above(48100);


#3.	Town Names Starting With
DELIMITER $$
USE `soft_uni`$$
CREATE PROCEDURE `usp_get_towns_starting_with` (name_part VARCHAR(45))
BEGIN
	SELECT t.`name` AS town_name FROM towns AS t
    WHERE t.`name` LIKE CONCAT(name_part, '%')
    ORDER BY town_name;
END$$
DELIMITER ;
CALL usp_get_towns_starting_with('b');


#4.	Employees from Town
DELIMITER $$
CREATE PROCEDURE `usp_get_employees_from_town` (town_name VARCHAR(50))
BEGIN
	SELECT e.first_name, e.last_name FROM employees AS e
	JOIN addresses AS a ON e.address_id = a.address_id
	JOIN towns AS t ON a.town_id = t.town_id
	WHERE t.`name` = town_name
	ORDER BY e.first_name, e.last_name, e.employee_id;
END; $$
DELIMITER ;
CALL usp_get_employees_from_town('Sofia')


#5.	Salary Level Function
DELIMITER $$
USE `soft_uni`$$
CREATE FUNCTION `ufn_get_salary_level` (salary DECIMAL(19,2))
RETURNS VARCHAR(10)
RETURN (
	CASE
		WHEN salary < 30000 THEN 'Low'
        WHEN salary <= 50000 THEN 'Average'
        WHEN salary > 50000 THEN 'High'
        END
); $$
DELIMITER ;
SELECT ufn_get_salary_level(43300.00) AS `level`;


#6.	Employees by Salary Level
DELIMITER $$
USE `soft_uni`$$
CREATE PROCEDURE `usp_get_employees_by_salary_level` (salary_level VARCHAR(10))
BEGIN
	SELECT e.first_name, e.last_name FROM employees AS e
    WHERE e.salary < 30000 AND salary_level = 'low'
        OR e.salary BETWEEN 30000 AND 50000 AND salary_level = 'average'
        OR e.salary > 50000 AND salary_level = 'high'
    ORDER BY e.first_name DESC, e.last_name DESC;
END; $$
DELIMITER ;
CALL usp_get_employees_by_salary_level('Low');


#7.	Define Function
DELIMITER $$
CREATE FUNCTION `ufn_is_word_comprised`(set_of_letters VARCHAR(50), word VARCHAR(50)) 
RETURNS BIT
RETURN (SELECT word REGEXP CONCAT('^[',set_of_letters ,']+$')); $$
DELIMITER ;
SELECT ufn_is_word_comprised('oistmiahf', 'Sofia') AS result;


USE bank;

#8.	Find Full Name
DELIMITER $$
CREATE PROCEDURE `usp_get_holders_full_name`()
BEGIN
	SELECT CONCAT(ac.first_name, ' ', ac.last_name) AS full_name FROM account_holders AS ac
    ORDER BY full_name, ac.id;
END; $$
DELIMITER ;
CALL usp_get_holders_full_name();


#9.	People with Balance Higher Than
DELIMITER $$
CREATE PROCEDURE `usp_get_holders_with_balance_higher_than` (num DECIMAL(19,4))
BEGIN
	SELECT ah.first_name, ah.last_name FROM account_holders AS ah
    JOIN (SELECT a.id, a.account_holder_id, SUM(a.balance) AS total_balance FROM accounts AS a
        GROUP BY (a.account_holder_id)
        HAVING `total_balance` > num) 
	AS a ON ah.id = a.account_holder_id
    ORDER BY a.account_holder_id;
END; $$
DELIMITER ;
CALL usp_get_holders_with_balance_higher_than(7000);


#10. Future Value Function
DELIMITER $$
CREATE FUNCTION `ufn_calculate_future_value`(sum DECIMAL(20,4), rate DECIMAL(20,4), years INT)
RETURNS DOUBLE
BEGIN
DECLARE result DOUBLE(20,15);
	SET result := sum * (POW((1 + rate), years));
RETURN result;
END; $$
DELIMITER ;
SELECT ufn_calculate_future_value(1000, 0.1, 5) AS result;


#11.	Calculating Interest
CALL usp_calculate_future_value_for_account(1, 0.1);


#12.	Deposit Money
DELIMITER $$
CREATE PROCEDURE `usp_deposit_money` (account_id INT, money_amount DECIMAL(19,4))
BEGIN
	START TRANSACTION;
		IF(money_amount <= 0) THEN ROLLBACK;
        ELSE 
			UPDATE accounts AS a
			SET balance = balance + money_amount
            WHERE a.id = account_id;
            COMMIT;
		END IF;
END; $$
DELIMITER ;
CALL usp_deposit_money (1, 10);


#13.	Withdraw Money
DELIMITER $$
CREATE PROCEDURE `usp_withdraw_money` (account_id INT, money_amount DECIMAL(19,4))
BEGIN
	START TRANSACTION;
    IF(money_amount <= 0) OR (SELECT balance FROM accounts AS ac WHERE ac.id = account_id) - money_amount < 0
		THEN ROLLBACK;
	ELSE 
		UPDATE accounts AS a
        SET balance = balance - money_amount
        WHERE a.id = account_id;
	COMMIT;
    END IF;
END; $$
DELIMITER ;
CALL usp_withdraw_money(1, 10);


#14.	Money Transfer
DELIMITER $$
CREATE PROCEDURE `usp_transfer_money`(from_account_id INT, to_account_id INT, amount DECIMAL(19,4))
BEGIN
	START TRANSACTION;
		IF(from_account_id = to_account_id) OR (amount <= 0) OR 
			(SELECT a.balance FROM accounts AS a WHERE a.id = from_account_id) - amount < 0 OR 
			(SELECT a.id FROM accounts AS a WHERE a.id = from_account_id) IS NULL OR  
			(SELECT a.id FROM accounts AS a WHERE a.id = to_account_id) IS NULL
        THEN ROLLBACK;
        ELSE 
        UPDATE accounts
        SET balance = balance + amount
        WHERE id = to_account_id;
        UPDATE accounts
        SET balance = balance - amount
        WHERE id = from_account_id;
	COMMIT;
    END IF;
END; $$
DELIMITER ;
CALL usp_transfer_monet(1, 2, 10);


#15.	Log Accounts Trigger
CREATE TABLE `logs` (
	log_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    old_sum DECIMAL(19,4) NOT NULL,
    new_sum DECIMAL(19,4) NOT NULL
);

DELIMITER $$
CREATE TRIGGER tr_log_account_trigger
AFTER UPDATE ON accounts FOR EACH ROW
BEGIN
	IF OLD.balance <> NEW.balance THEN
		INSERT INTO `logs` (account_id, old_sum, new_sum)
		VALUES (OLD.id, OLD.balance, NEW.balance);
	END IF;
END $$


#16.	Emails Trigger
CREATE TABLE notification_emails (
	id INT PRIMARY KEY AUTO_INCREMENT,
    recipient INT NOT NULL,
    `subject` VARCHAR(255) NOT NULL,
    body VARCHAR(255) NOT NULL
);

DELIMITER$$
CREATE TRIGGER tr_notificaion_emails
AFTER INSERT ON `logs`
FOR EACH ROW
BEGIN 
	INSERT INTO notification_emails(recipient, `subject`, body)
    VALUES (NEW.account_id, CONCAT('Balance change for account: ', NEW.account_id),
			CONCAT('On ', DATE(NOW()), 'your balance was changed from ', NEW.old_sum, 'to', NEW.new_sum,'.'));
END; $$
