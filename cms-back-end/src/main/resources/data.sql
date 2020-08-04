-- initialize user_authority table
insert into user_authority values(1, 'Administrator');
insert into user_authority values(2, 'Editor');


-- initialize user_details table
INSERT INTO user_details(id, email, password, name, phone, active, register_date, authority_id) VALUES (1,'admin@admin.com','pass','Administrator',NULL,1,'12-09-2016, 15:26',1),(2,'temp@temp.com','temp','temp',NULL,1,'21-04-2020, 20:17:59',2);

-- initialize category table
INSERT INTO category(id, name, description, product_count, image_name) VALUES (1,'Breakfast','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas quis bibendum metus, id varius odio. Sed viverra congue orci, at sodales sem. Mauris sed sagittis lectus.',0,'15904378616241519277513.jpg'),(2,'Deli Platters','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam tempor diam in neque sollicitudin, ac imperdiet odio fringilla. Phasellus non euismod lacus. ',0,'15904378711261664683266.jpg'),(3,'Snacks','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam tempor diam in neque sollicitudin, ac imperdiet odio fringilla. Phasellus non euismod lacus.',0,'1587954313471420473752.jpg'),(4,'Sushi','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam tempor diam in neque sollicitudin, ac imperdiet odio fringilla. Phasellus non euismod lacus.',0,'1587954347204941049124.jpg'),(5,'Desserts','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam tempor diam in neque sollicitudin, ac imperdiet odio fringilla. Phasellus non euismod lacus. ',0,'1587954393821761285343.jpg'),(6,'Drinks','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam tempor diam in neque sollicitudin, ac imperdiet odio fringilla. Phasellus non euismod lacus. ',0,'15879545126611502298569.jpg');
