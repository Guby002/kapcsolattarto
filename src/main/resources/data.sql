/*insert into company (id,name) values (1,'Company #1');
insert into company (id,name) values (2,'Company #2');
insert into company (id,name) values(3,'Company #3');

insert into roles (name) values
                    ('ADMIN'),
                    ('USER');

insert into users (user_name,password,email) values ('admin', '$2a$10$NZdoGG8sjbRqiqX8/zq0BeOG8KnmhlBBQfhU5yyiwG0QkwvSyQHNm','feri@feri.com');

insert into user_roles (user_id, role_id) values (1, 1);


insert into contact(id,first_name,second_name,email,phonenumber,company_id,comment,status,created_date,last_modified)
values (1,'Elso','Lajos','lajos@lajos.com','1234567',1,'1212','ACTIVE','1234-12-12','1234-12-24');
insert into contact(id,first_name,second_name,email,phonenumber,company_id,comment,status,created_date,last_modified)
values  (2,'Masodik','Lajos','lajos@lajos.com','1234567',1,'1212','ACTIVE','1234-12-12','1234-12-24');

insert into privileges(name) values
    ('DELETE'),
    ('LIST'),
    ('CREATE'),
    ('MODIFY'),
    ('GET_USER_DATA');

insert into role_privileges (role_id, privileges_id) values (1,1),(1,2),(1,3),(1,4),(1,5),
                                                            (2,2);*/