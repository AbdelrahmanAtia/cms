
create database cms_db;
use cms_db;
-- ------------------------------------------------------------------------------------------------
-- drop tables
drop table if exists user_details;
drop table if exists user_authority;
drop table if exists order_line;
drop table if exists client_order;
drop table if exists client;
drop table if exists product;
drop table if exists category;
-- ------------------------------------------------------------------------------------------------
CREATE TABLE category (
	id int primary key auto_increment,
	name varchar(255) unique not null,
	description varchar(255),
	product_count int,
	image_name varchar(255) NOT NULL
);
-- ------------------------------------------------------------------------------------------------
CREATE TABLE product (
  id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  active bit(1) NOT NULL,
  description varchar(1000) DEFAULT NULL,
  image_name varchar(255) not null,
  name varchar(255) NOT NULL,
  price double NOT NULL check (price > 0),
  category_id int(11) DEFAULT NULL,
  FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE SET NULL ON UPDATE CASCADE
);
-- ------------------------------------------------------------------------------------------------
create table client (
	id int primary key auto_increment,
    title varchar(255), 
    name varchar(255) NOT NULL,
    email varchar(255) NOT NULL CHECK (email like '%_@_%'),
    phone varchar(255) NOT NULL,
    company varchar(255),
    address varchar(255),
    city varchar(255),
    state varchar(255),
    zip varchar(255),
    country varchar(255),
    special_instructions varchar(255)
);
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
create table user_authority(
	id int primary key auto_increment,
    name varchar(255)
);
-- ------------------------------------------------------------------------------------------------
create table user_details(
	id int primary key auto_increment,
	email varchar(255) not null unique check(email like '%_@_%'),
    password varchar(255) not null,
    name varchar(255) not null,
    phone varchar(255),
    active bit(1) not null,
    register_date varchar(255) not null,
    authority_id int not null,
    FOREIGN KEY (authority_id) REFERENCES user_authority(id)
);
-- ------------------------------------------------------------------------------------------------
-- triggers
delimiter $$
create trigger after_product_insert after insert on product for each row
begin
	update category set product_count = product_count + 1 where id = new.category_id;
end $$

delimiter $$
create trigger after_product_update after update on product for each row
begin
	update category set product_count = product_count - 1 where id = old.category_id;
	update category set product_count = product_count + 1 where id = new.category_id;
end $$

delimiter $$
create trigger after_product_delete after delete on product for each row
begin
	update category set product_count = product_count - 1 where id = old.category_id;
end $$

delimiter $$
create trigger before_user_details_delete before delete on user_details for each row
begin
	if old.id = 1 then -- Abort when trying to remove this record
		 -- 4500 means a generic sql state  value
		 SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'you can not delete this user'; 
	end if;
end$$

delimiter $$
create trigger before_user_details_update before update on user_details for each row
begin
	if (old.id = 1 and new.authority_id <> 1) then
		 -- 4500 means a generic sql state  value
		 SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'you can not update this user authority'; 
	end if;
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

-- change user password to >> System
 alter user 'root'@'localhost' identified by 'System';
 
 --show triggers in well formatted way 
show triggers \G;

-- dump a certain table to an sql file
mysqldump -u root -p System cms_db category > C:\Users\Abdelrahman_Attya\Desktop\dump.sql
-- ------------------------------------------------------------------------------------------------





