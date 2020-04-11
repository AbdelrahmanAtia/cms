-- these commands are executed by the database administrator

-- drop tables
drop table if exists user_details;
drop table if exists user_authority;
drop table if exists order_line;
drop table if exists client_order;
drop table if exists client;
drop table if exists product;
drop table if exists category;
-- ------------------------------------------------------------------------------------------------
-- sequences
-- -----------
CREATE SEQUENCE cmsapp.user_seq START WITH 2 INCREMENT BY 1;
CREATE SEQUENCE cmsapp.category_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE cmsapp.product_seq START WITH 1 INCREMENT BY 1;
-- ------------------------------------------------------------------------------------------------
create table cmsapp.user_authority(
	id int primary key,
    name varchar(255)
);
-- ------------------------------------------------------------------------------------------------
create table cmsapp.user_details(
	id int primary key,
	email varchar(255) not null unique check(email like '%_@_%'),
    password char(60) not null,
    name varchar(255) not null,
    phone varchar(255),
    active NUMBER(1) not null,
    register_date varchar(255) not null,
    authority_id int not null,
    FOREIGN KEY (authority_id) REFERENCES cmsapp.user_authority(id)
);
-- ------------------------------------------------------------------------------------------------

CREATE TABLE cmsapp.category (
	id int primary key,
	name varchar(255) not null,
	description varchar(255),
	product_count int,
	IMAGE_NAME VARCHAR2(255 BYTE) NOT NULL
);
-- ------------------------------------------------------------------------------------------------
CREATE TABLE cmsapp.product (
  id number PRIMARY KEY NOT NULL,
  active number(1) NOT NULL,
  description varchar(1000) DEFAULT NULL,
  name varchar(255) NOT NULL,
  price number NOT NULL check (price > 0),
  category_id number DEFAULT NULL,
  IMAGE_NAME VARCHAR2(255 BYTE) NOT NULL, 
  FOREIGN KEY (category_id) REFERENCES cmsapp.category (id) ON DELETE SET NULL
);
-- ------------------------------------------------------------------------------------------------
create table client (
	id int primary key auto_increment,
    title varchar(255), 
    name varchar(255),
    email varchar(255),
    phone varchar(255),
    company varchar(255),
    address varchar(255),
    city varchar(255),
    state varchar(255),
    zip varchar(255),
    country varchar(255),
    special_instructions varchar(255)
);

ALTER TABLE client CHANGE COLUMN name name VARCHAR(255) NOT NULL;
ALTER TABLE client CHANGE COLUMN phone phone VARCHAR(255) NOT NULL;
ALTER TABLE client CHANGE COLUMN email email VARCHAR(255) NOT NULL;
ALTER TABLE client ADD CHECK (email like '%_@_%');
ALTER TABLE client_order CHANGE COLUMN status status ENUM ('PENDING','CONFIRMED','CANCELLED') NOT NULL;
-- ------------------------------------------------------------------------------------------------
CREATE TABLE client_order (
  id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  delivery_date bigint(20) NOT NULL,
  tax double NOT NULL CHECK (tax > 0),
  subtotal double NOT NULL CHECK (subtotal > 0),
  total_price double NOT NULL CHECK (total_price > 0),
  status enum('PENDING','CONFIRMED','CANCELLED') NOT NULL,
  payment_method varchar(50) NOT NULL,
  ip_address varchar(50) DEFAULT NULL,
  client_id int(11) NOT NULL,
  created_at bigint(20) NOT NULL,
  FOREIGN KEY (client_id) REFERENCES client (id)
);
-- ------------------------------------------------------------------------------------------------
CREATE TABLE order_line (
  id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  quantity int(11) NOT NULL CHECK (quantity > 0),
  price double NOT NULL CHECK (price > 0),
  total_price double NOT NULL CHECK(total_price > 0),
  product_id int(11) NOT NULL,
  order_id int(11) NOT NULL,
  FOREIGN KEY (product_id) REFERENCES product(id),
  FOREIGN KEY (order_id)   REFERENCES client_order (id)
);
-- ------------------------------------------------------------------------------------------------


-- ------------------------------------------------------------------------------------------------
-- triggers
delimiter $$
create trigger after_product_insert after insert on product for each row
begin
	update category set product_count = product_count + 1 where id = new.category_id;
end$$

delimiter $$
create trigger after_product_update after update on product for each row
begin
	update category set product_count = product_count - 1 where id = old.category_id;
	update category set product_count = product_count + 1 where id = new.category_id;
end$$

delimiter $$
create trigger after_product_delete after delete on product for each row
begin
	update category set product_count = product_count - 1 where id = old.category_id;
end$$
delimiter ;
-- ------------------------------------------------------------------------------------------------
-- reset data base tables
delete  from order_line where id > 0;
delete  from client_order where id > 0;
delete  from client where id > 0;
delete from product where id > 0;
delete from category where id > 0;
delete from user_details where id > 0;
-- ------------------------------------------------------------------------------------------------
-- important queries

-- show query used to create a table
SHOW CREATE TABLE table_name;

-- disable foreign key checks
SET foreign_key_checks = 0;

-- enable foreign key checks
SET foreign_key_checks = 1;

-- add check constrain that field > 0
ALTER TABLE table_name ADD CHECK (col_name > 0);







