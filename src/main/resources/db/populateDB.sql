DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

insert into meals (datetime, description, calories, user_id) VALUES ('2015-05-31 18:00', 'Ужин пользователя', 510, 100000);
insert into meals (datetime, description, calories, user_id) VALUES ('2015-05-31 15:00', 'Обед пользователя', 1000, 100000);
insert into meals (datetime, description, calories, user_id) VALUES ('2015-05-31 10:00', 'Завтрак пользователя', 500, 100000);
insert into meals (datetime, description, calories, user_id) VALUES ('2015-05-30 18:00', 'Ужин пользователя', 500, 100000);
insert into meals (datetime, description, calories, user_id) VALUES ('2015-05-30 15:00', 'Обед пользователя', 1000, 100000);
insert into meals (datetime, description, calories, user_id) VALUES ('2015-05-30 10:00', 'Завтрак пользователя', 500, 100000);

insert into meals (datetime, description, calories, user_id) VALUES ('2015-05-31 18:00', 'Ужин админа', 510, 100001);
insert into meals (datetime, description, calories, user_id) VALUES ('2015-05-31 15:00', 'Обед админа', 1000, 100001);
insert into meals (datetime, description, calories, user_id) VALUES ('2015-05-31 10:00', 'Завтрак админа', 500, 100001);
insert into meals (datetime, description, calories, user_id) VALUES ('2015-05-30 18:00', 'Ужин админа', 500, 100001);
insert into meals (datetime, description, calories, user_id) VALUES ('2015-05-30 15:00', 'Обед админа', 1000, 100001);
insert into meals (datetime, description, calories, user_id) VALUES ('2015-05-30 10:00', 'Завтрак админа', 500, 100001);
