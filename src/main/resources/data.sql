
DELETE FROM rating_MPA;
DELETE FROM genre;
DELETE FROM films;
DELETE FROM users;
DELETE FROM films_genre;
DELETE FROM likes;
DELETE FROM friends_list;

ALTER TABLE rating_MPA ALTER COLUMN id RESTART WITH 1;
ALTER TABLE genre ALTER COLUMN id RESTART WITH 1;
ALTER TABLE films ALTER COLUMN id RESTART WITH 1;
ALTER TABLE users ALTER COLUMN id RESTART WITH 1;

INSERT INTO rating_MPA (name) VALUES ('G');
INSERT INTO rating_MPA (name) VALUES ('PG');
INSERT INTO rating_MPA (name) VALUES ('PG-13');
INSERT INTO rating_MPA (name) VALUES ('R');
INSERT INTO rating_MPA (name) VALUES ('NC-17');

INSERT INTO genre (name) VALUES ('Комедия');
INSERT INTO genre (name) VALUES ('Драма');
INSERT INTO genre (name) VALUES ('Мультфильм');
INSERT INTO genre (name) VALUES ('Триллер');
INSERT INTO genre (name) VALUES ('Документальный');
INSERT INTO genre (name) VALUES ('Боевик');
