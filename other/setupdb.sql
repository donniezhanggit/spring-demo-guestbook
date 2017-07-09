-- Database setup script for postgres.
-- We are going to reset db state after some time for our demo example.

BEGIN TRANSACTION;

DELETE FROM comment;
DELETE FROM gbuser;


INSERT INTO gbuser
    (id, version, username, password, email, created, active)
VALUES
    (1, 1, 'admin', 'password', 'admin@spring-gb13.com', '2016-01-01', 't'),
    (2, 1, 'user',  'password', 'user@spring-gb13.com',  '2017-07-04', 't');


INSERT INTO comment
    (id, version, created, message, gbuser_id)
VALUES
    (1, 1, '2015-09-19 14:00:00', 'Welcome to this demo application.', 1),
    (2, 1, '2015-09-19 14:00:01', 'Used technologies: Java 8, Spring 4 (Boot, MVC), Hibernate 5, Thymeleaf 2, Maven 3 and Zenburn.', 1),
    (3, 1, '2015-09-19 14:00:02', 'You can grab source code freely from github under GPL terms.', 1);

COMMIT TRANSACTION;
