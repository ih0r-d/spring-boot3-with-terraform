CREATE TABLE if not exists users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(50)  NOT NULL,
    email    VARCHAR(100) NOT NULL
);



INSERT INTO users(username, email)
SELECT
    'user' || generate_series(1, 10),
    'user' || generate_series(1, 10) || '@example.com';