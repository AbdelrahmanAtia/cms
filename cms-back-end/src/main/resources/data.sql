
-- initialize category table
-- ===========================
INSERT INTO category(id, name, description, product_count, image_name) VALUES (1,'Breakfast','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas quis bibendum metus, id varius odio. Sed viverra congue orci, at sodales sem. Mauris sed sagittis lectus.',0,'_init_1_c25bb59b4e8d7bb60f4b02fac891e1f1.jpg');
INSERT INTO category(id, name, description, product_count, image_name) VALUES (2,'Deli Platters','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam tempor diam in neque sollicitudin, ac imperdiet odio fringilla. Phasellus non euismod lacus. ',0,'_init_3_4a1075e97eba5d13ec23bb2f527716b2.jpg');
INSERT INTO category(id, name, description, product_count, image_name) VALUES (3,'Snacks','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam tempor diam in neque sollicitudin, ac imperdiet odio fringilla. Phasellus non euismod lacus.',0,'_init_6_660537c5e401179de0bff09e02edf2c0.jpg');
INSERT INTO category(id, name, description, product_count, image_name) VALUES (4,'Sushi','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam tempor diam in neque sollicitudin, ac imperdiet odio fringilla. Phasellus non euismod lacus.',0,'_init_7_16990f00efbe72e821f4b80ba03f270f.jpg');
INSERT INTO category(id, name, description, product_count, image_name) VALUES (5,'Desserts','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam tempor diam in neque sollicitudin, ac imperdiet odio fringilla. Phasellus non euismod lacus. ',0,'_init_4_8819f35e01c76b431df11343913be193.jpg');
INSERT INTO category(id, name, description, product_count, image_name) VALUES (6,'Drinks','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam tempor diam in neque sollicitudin, ac imperdiet odio fringilla. Phasellus non euismod lacus. ',0,'_init_5_9aa7673e432b7236936475f15188385e.jpeg');


-- initialize user_authority table
-- =================================
insert into user_authority values(1, 'Administrator');
insert into user_authority values(2, 'Editor');


-- initialize user_details table
-- ================================
INSERT INTO user_details(id, email, password, name, phone, active, register_date, authority_id) VALUES (1,'admin@admin.com','pass','Administrator',NULL,1,'12-09-2016, 15:26',1);
INSERT INTO user_details(id, email, password, name, phone, active, register_date, authority_id) VALUES (2,'temp@temp.com','temp','temp',NULL,1,'21-04-2020, 20:17:59',2);


