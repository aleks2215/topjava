DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id) VALUES
('2020-03-06 09:00:00', 'Завтрак', 600, 100000),
('2020-03-06 13:00:00', 'Обед', 1000, 100000),
('2020-03-06 19:00:00', 'Ужин', 1500, 100000),
('2020-03-07 09:00:00', 'Завтрак', 1000, 100000),
('2020-03-07 13:00:00', 'Обед', 2000, 100000),
('2020-03-08 09:00:00', 'Завтрак', 10000, 100001);