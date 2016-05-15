-- Database setup script for h2.

INSERT INTO gbuser
    (id, username, password, email, created, active)
VALUES
    (1, 'user', 'password', 'user@spring-gb13.com', '2016-01-01', 1);

INSERT INTO comment
    (created, name, message, gbuser_id)
VALUES
    ('2015-09-19 14:00:00', 'admin', 'Welcome to this demo application.', 1),
    ('2015-09-19 14:00:01', 'admin', 'Used technologies: Java 7, Spring 4 (Boot, MVC), Hibernate 4, Thymeleaf 2, Maven 3 and Zenburn.', 1),
    ('2015-09-19 14:00:02', 'admin', 'You can grab source code freely from github under GPL terms.', 1);
