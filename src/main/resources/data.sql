insert into company (id,name) values (1,'Company #1');
insert into company (id,name) values (2,'Company #2');
insert into company (id,name) values(3,'Company #3');

insert into roles (name) values
                    ('USER'),
                    ('ADMIN');

insert into users (user_name,password,email) values
         ('admin', '$2a$10$e6H1Jgrft/scpmpzbMFO0uqF1gxqop73l5wOlwF30Aem6Tty1nI2G','feri@feri.com'),
        ('user', '$2a$10$e6H1Jgrft/scpmpzbMFO0uqF1gxqop73l5wOlwF30Aem6Tty1nI2G','feri@feri.com');

insert into user_roles (user_id, role_id) values
          (1, 2),
          (2, 1);


insert into contact(id,first_name,second_name,email,phonenumber,company_id,comment,stat,created_date,last_modified_date_time)
values (1,'Elso','Lajos','lajos@lajos.com','1234567',1,'1212','ACTIVE','1234-12-12','1234-12-24');
insert into contact(id,first_name,second_name,email,phonenumber,company_id,comment,stat,created_date,last_modified_date_time)
values  (2,'Masodik','Lajos','lajos@lajos.com','1234567',1,'1212','ACTIVE','1234-12-12','1234-12-24');