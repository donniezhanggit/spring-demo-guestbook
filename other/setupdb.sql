-- Database setup script for postgres.
-- We are going to reset db state after some time for our demo example.

BEGIN TRANSACTION;

DELETE FROM comment;

INSERT INTO comment (created, name, message)
       VALUES ('2015-09-19 14:00:00', 'admin', 'Welcome to this demo application.'),
              ('2015-09-19 14:00:01', 'admin', 'Used technologies: Java 7, Spring 4 (Boot, MVC), Hibernate 4, Thymeleaf 2, Maven 3 and Zenburn.'),
              ('2015-09-19 14:00:02', 'admin', 'You can grab source code freely from github under GPL terms.');

COMMIT TRANSACTION;
