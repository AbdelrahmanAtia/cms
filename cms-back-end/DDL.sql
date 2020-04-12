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

CREATE SEQUENCE cmsapp.client_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE cmsapp.client_order_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE cmsapp.orderline_seq START WITH 1 INCREMENT BY 1;

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
create table cmsapp.client (
	id int primary key,
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
CREATE TABLE cmsapp.client_order (
  id NUMBER PRIMARY KEY NOT NULL,
  delivery_date NUMBER NOT NULL,
  tax NUMBER NOT NULL CHECK (tax > 0),
  subtotal NUMBER NOT NULL CHECK (subtotal > 0),
  total_price NUMBER NOT NULL CHECK (total_price > 0),
  status varchar(255) NOT NULL,
  payment_method varchar(50) NOT NULL,
  ip_address varchar(50) DEFAULT NULL,
  client_id NUMBER NOT NULL,
  created_at NUMBER NOT NULL,
  FOREIGN KEY (client_id) REFERENCES cmsapp.client (id)
);
-- ------------------------------------------------------------------------------------------------
CREATE TABLE cmsapp.order_line (
  id NUMBER PRIMARY KEY NOT NULL,
  quantity NUMBER NOT NULL CHECK (quantity > 0),
  price NUMBER NOT NULL CHECK (price > 0),
  total_price NUMBER NOT NULL CHECK(total_price > 0),
  product_id NUMBER NOT NULL,
  order_id NUMBER NOT NULL,
  FOREIGN KEY (product_id) REFERENCES cmsapp.product(id),
  FOREIGN KEY (order_id)   REFERENCES cmsapp.client_order (id)
);
-- ------------------------------------------------------------------------------------------------
-- triggers

CREATE TRIGGER cmsapp.after_product_insert AFTER INSERT ON cmsapp.product FOR EACH ROW
BEGIN    
	UPDATE cmsapp.category SET product_count = (product_count + 1) where id = :NEW.category_id;
END;
/

CREATE TRIGGER cmsapp.after_product_update AFTER UPDATE ON cmsapp.product FOR EACH ROW
BEGIN
	UPDATE cmsapp.category SET product_count = product_count - 1 WHERE id = :OLD.category_id;
	UPDATE cmsapp.category SET product_count = product_count + 1 WHERE id = :NEW.category_id;
END;
/

CREATE trigger cmsapp.after_product_delete AFTER DELETE ON cmsapp.product FOR EACH ROW
BEGIN
	UPDATE cmsapp.category SET product_count = product_count - 1 WHERE id = :OLD.category_id;
END;
/
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







