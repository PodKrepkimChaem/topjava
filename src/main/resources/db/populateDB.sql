DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, description, calories)
VALUES (100000, 'tomat', '100');

INSERT INTO meals (user_id, description, calories)
VALUES (100000, 'aple', '120');

INSERT INTO meals (user_id, description, calories)
VALUES (100000, 'citron', '110');

INSERT INTO meals (user_id, description, calories)
VALUES (100001, 'potato', '200');

INSERT INTO meals (user_id, description, calories)
VALUES (100001, 'mango', '120');

INSERT INTO meals (user_id, description, calories)
VALUES (100001, 'cola', '70');
