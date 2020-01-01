use cms;
-- ------------------------------------------------------------------------------------------------
CREATE TABLE category (
	id int primary key auto_increment,
	name varchar(255),
	description varchar(255),
	product_count int,
	image longblob
);
-- ------------------------------------------------------------------------------------------------
CREATE TABLE product (
	id int primary key auto_increment,
	active bit(1),
	description varchar(255),
	image longblob,
	name varchar(255),
	price double,
	category_id int,
	FOREIGN KEY (category_id) REFERENCES category(id)  ON DELETE SET NULL ON UPDATE CASCADE
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
-- ------------------------------------------------------------------------------------------------
create table client_order (
	id int primary key auto_increment,
    delivery_date BIGINT(20), 
    tax double,
    subtotal double,
    total_price double,
    status varchar(50),
    payment_method varchar(50),
    ip_address varchar(50),
    client_id int,
    FOREIGN KEY (client_id) REFERENCES client(id)
);
-- ------------------------------------------------------------------------------------------------
create table order_line (
	id int primary key auto_increment,
    quantity int, 
    price double,
    total_price double,
    product_id int,
    order_id int,
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (order_id) REFERENCES client_order(id)
);
-- ------------------------------------------------------------------------------------------------
create table user_authority(
	id int primary key auto_increment,
    name varchar(255)
);
-- ------------------------------------------------------------------------------------------------
create table user_details(
	id int primary key auto_increment,
	email varchar(255),
    password char(60),
    name varchar(255),
    phone varchar(255),
    active bit(1),
    register_date varchar(255),
    authority_id int,
    FOREIGN KEY (authority_id) REFERENCES user_authority(id)
);
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
-- ------------------------------------------------------------------------------------------------
-- reset data base tables
delete  from order_line where id > 0;
delete  from client_order where id > 0;
delete  from client where id > 0;
delete from product where id > 0;
delete from category where id > 0;
delete from user_details where id > 0;
-- ------------------------------------------------------------------------------------------------


