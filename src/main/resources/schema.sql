 DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS role_privileges;
 DROP TABLE IF EXISTS users;
 DROP TABLE IF EXISTS roles CASCADE;
 DROP TABLE IF EXISTS contact;
 DROP TABLE IF EXISTS company;
 DROP TABLE IF EXISTS privileges CASCADE;

create table contact
(
    id bigint generated by default as identity (start with 1) primary key,
    first_name varchar(200) not null,
    second_name varchar(200) not null,
    email varchar(200) not null,
    phoneNumber varchar(200) not null,
    company_id bigint,
    comment varchar(400) not null,
    status varchar(400),
    created_date date,
    last_modified date
);
create table company
(
    id   bigint generated by default as identity,
    name varchar(200) not null,
    constraint pk_company primary key (id)
);

CREATE TABLE users (
   id bigint generated by default as identity (start with 1) primary key,
   user_name VARCHAR(250) NOT NULL,
   password VARCHAR(250) NOT NULL,
   email varchar(200) not null
);

CREATE TABLE roles (
   id bigint generated by default as identity (start with 1) primary key,
   name varchar(200) not null
);

CREATE TABLE user_roles (
  user_id bigint NOT NULL,
  role_id bigint NOT NULL,
  FOREIGN KEY(user_id) REFERENCES users(id),
  FOREIGN KEY(role_id) REFERENCES roles(id),
  PRIMARY KEY (user_id)
);

CREATE TABLE privileges(
  id bigint generated by default as identity (start with 1) primary key,
  name varchar(200) not null
);

CREATE TABLE role_privileges(
  id bigint generated by default as identity (start with 1) primary key,
  role_id bigint NOT NULL,
  privileges_id bigint NOT NULL,
  FOREIGN KEY(role_id) REFERENCES roles(id),
  FOREIGN KEY(privileges_id) REFERENCES privileges(id)
);



