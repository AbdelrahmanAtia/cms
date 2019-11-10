-- ------------------------------------------------------------------------------------------------
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
create table client_order (
	id int primary key auto_increment,
    delivery_date BIGINT(20), 
    tax double,
    subtotal double,
    total_price double,
    status varchar(50),
    ip_address varchar(50),
    client_id int,
    FOREIGN KEY (client_id) REFERENCES client(id)
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
