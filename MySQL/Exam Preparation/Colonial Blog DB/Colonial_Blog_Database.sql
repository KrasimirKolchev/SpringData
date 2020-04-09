#-----------Exam Prep: Colonial Blog Database-----------


#1.	Table Design
CREATE SCHEMA colonial_blog_db;
USE colonial_blog_db;

CREATE TABLE users (
	id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL UNIQUE,
    `password` VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL
);

CREATE TABLE categories (
	id INT PRIMARY KEY AUTO_INCREMENT,
    category VARCHAR(30) NOT NULL
);

CREATE TABLE articles (
	id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    content TEXT NOT NULL,
    category_id INT,
    CONSTRAINT fk_article_category_id FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE users_articles (
	user_id INT,
    article_id INT,
    CONSTRAINT fk_users_articles_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_users_articles_article_id FOREIGN KEY (article_id) REFERENCES articles(id)
);

CREATE TABLE comments (
	id INT PRIMARY KEY AUTO_INCREMENT,
    `comment` VARCHAR(255) NOT NULL,
    article_id INT NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT fk_comment_article_id FOREIGN KEY (article_id) REFERENCES articles(id),
    CONSTRAINT fk_comment_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE likes (
	id INT PRIMARY KEY AUTO_INCREMENT,
    article_id INT,
    comment_id INT,
    user_id INT NOT NULL,
    CONSTRAINT fk_likes_article_id FOREIGN KEY (article_id) REFERENCES articles(id),
    CONSTRAINT fk_likes_comment_id FOREIGN KEY (comment_id) REFERENCES comments(id),
    CONSTRAINT fk_likes_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);

#2.	Data Insertion
INSERT INTO likes (article_id, comment_id, user_id)
SELECT (CASE
			WHEN u.id % 2 = 0 THEN LENGTH(u.username)
			ELSE NULL
			END
	) AS article_id,
		(CASE
			WHEN u.id % 2 = 0 THEN NULL
			ELSE LENGTH(u.email)
			END
	) AS comment_id,
	u.id FROM users AS u
WHERE u.id BETWEEN 16 AND 20;
    
INSERT INTO likes (article_id, comment_id, user_id)
SELECT
	IF(u.id % 2 = 0, char_length(u.username), null),
	IF(u.id % 2 = 0, char_length(u.email), null),
	u.id
FROM users AS u
WHERE u.id BETWEEN 16 AND 20;

#3.	Data Update
UPDATE comments 
SET 
    `comment` = (CASE
        WHEN id % 2 = 0 THEN 'Very good article.'
        WHEN id % 3 = 0 THEN 'This is interesting.'
        WHEN id % 5 = 0 THEN 'I definitely will read the article again.'
        WHEN id % 7 = 0 THEN 'The universe is such an amazing thing.'
		ELSE c.`comment`
    END)
WHERE id BETWEEN 1 AND 15;

#4.	Data Deletion
DELETE articles FROM articles
WHERE category_id IS NULL;

#5.	Extract 3 biggest articles
CREATE VIEW v_3_longest_articles AS
SELECT a.id, a.title, CONCAT(SUBSTRING(a.`content`, 1, 20), '...') AS summary FROM articles AS a
ORDER BY LENGTH(a.`content`) DESC LIMIT 3;

SELECT title, summary FROM v_3_longest_articles
ORDER BY id;

#5.	Extract 3 biggest articles
SELECT new_t.title, new_t.summary FROM (
	SELECT a.id, a.title, CONCAT(LEFT(a.content, 20), '...') AS summary FROM articles AS a
	ORDER BY char_length(a.content) DESC
	LIMIT 3) AS new_t
ORDER BY new_t.id;

#6.	Golden Articles
SELECT ua.article_id AS article_id, a.title FROM users_articles AS ua
JOIN articles AS a ON ua.article_id = a.id
WHERE ua.article_id = ua.user_id
ORDER BY ua.article_id;

#7.	Extract categories
SELECT c.category, COUNT(DISTINCT a.id) AS articles, COUNT(l.id) AS likes FROM categories AS c
LEFT JOIN articles AS a ON c.id = a.category_id 
LEFT JOIN likes AS l ON a.id = l.article_id
GROUP BY c.category
ORDER BY likes DESC, articles DESC, c.id;


#8.	Extract the most commented Social article
SELECT a.title, COUNT(c.id) AS comments FROM categories AS c
JOIN articles AS a ON c.id = a.category_id
JOIN comments AS co ON a.id = co.article_id
WHERE c.id = 5
GROUP BY a.title
ORDER BY `comments` DESC LIMIT 1;

#9.	Extract the less liked comments
SELECT CONCAT(SUBSTRING(c.`comment`, 1, 20), '...') AS summary FROM comments AS c
LEFT JOIN likes AS l ON c.id = l.comment_id
WHERE l.comment_id IS NULL
ORDER BY c.id DESC;


#10. Get user’s articles count
USE `colonial_blog_db`;
DROP function IF EXISTS `udf_users_articles_count`;

DELIMITER $$
USE `colonial_blog_db`$$
CREATE FUNCTION `udf_users_articles_count`(username VARCHAR(30)) 
RETURNS INT
DETERMINISTIC
BEGIN
DECLARE count INT;
SET count := (SELECT COUNT(ua.article_id) AS count FROM users AS u
	JOIN users_articles AS ua ON u.id = ua.user_id
	WHERE u.username = username);
RETURN count;
END$$
DELIMITER ;

SELECT u.username, udf_users_articles_count('UnderSinduxrein') AS count
FROM articles AS a
JOIN users_articles ua
ON a.id = ua.article_id
JOIN users u
ON ua.user_id = u.id
WHERE u.username = 'UnderSinduxrein'
GROUP BY u.id;


#11. Like article
USE `colonial_blog_db`;
DROP procedure IF EXISTS `udp_like_article`;

DELIMITER $$
USE `colonial_blog_db`$$
CREATE PROCEDURE `udp_like_article` (username VARCHAR(30), title VARCHAR(30))
BEGIN
	START TRANSACTION;
            
	IF (SELECT u.username FROM users AS u WHERE u.username = username) IS NULL
		THEN 
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'Non-existent user.';
			ROLLBACK;
	END if;
	IF (SELECT a.title FROM articles AS a WHERE a.title = title) IS NULL
		THEN
        SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'Non-existent article.';
			ROLLBACK;
	END if;
    INSERT INTO likes (article_id, user_id) 
    VALUES ((SELECT a.id FROM articles AS a WHERE a.title = title),
			(SELECT u.id FROM users AS u WHERE u.username = username));
	COMMIT;
END$$
DELIMITER ;

CALL udp_like_article('BlaAntigadsa', 'Donnybrook, Victoria');
SELECT a.title, u.username 
FROM articles a 
JOIN likes l
ON a.id = l.article_id
JOIN users u
ON l.user_id = u.id
WHERE u.username = 'BlaAntigadsa' AND a.title = 'Donnybrook, Victoria';
