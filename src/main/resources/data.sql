insert into company (id,name) values (1,'Company #1');
insert into company (id,name) values (2,'Company #2');
insert into company (id,name) values(3,'Company #3');
   INSERT INTO roles (name) VALUES
                    ('USER'),
                    ('ADMIN');
INSERT INTO users (user_name,password,first_name ,second_name,email,locked , role,enabled) VALUES
   /*      ('admin', '$2a$10$e6H1Jgrft/scpmpzbMFO0uqF1gxqop73l5wOlwF30Aem6Tty1nI2G', true),*/
      /*    ('azhwani', '$2a$10$kWWOnNOiToOxcIQ7UJ.cB.XFAflYvMS5BPASR1eqqojc6H9ELWUfC', true),*/
           ('admin', '$2a$12$3dUVETrau2j5xV8JeqJtnuyJWUPPDvpto9HRj5snMqMfpJA9dNEhS','admin', 'admin','admin', false,'ADMIN_ROLE', true);
       /*   ('guest', '$2a$10$SNsPkXTh0ryc82.D2HRJqOcY8sYh/TPnJW8WLqrERWkOq01ViWaCq', true);*/
  /* INSERT INTO users_roles (user_id, role_id) VALUES
          (1, 1),
          (2, 1),
          (3, 2);*/

/*
insert into contact(id,first_name,second_name,email,phonenumber,company_id,comment,stat,created_date,last_modified_date_time)
values (1,'Elso','Lajos','lajos@lajos.com','1234567',1,'1212',False,'1234-12-12','1234-12-24');
insert into contact(id,first_name,second_name,email,phonenumber,company_id,comment,stat,created_date,last_modified_date_time)
values  (2,'Masodik','Lajos','lajos@lajos.com','1234567',1,'1212',False,'1234-12-12','1234-12-24');*/