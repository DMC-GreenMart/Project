create database veggies;
use veggies;


SET SQL_SAFE_UPDATES = 0;
-- for Users   
create table users (
user_id INT Primary Key Auto_Increment ,
username varchar(50) Not null,
email varchar(50) not null unique,
password varchar(200) not null,
tokens varchar(200),
extra varchar(100)
);
-- for Categories 

create table category (
category_id INT Primary Key Auto_Increment ,
category_name varchar(80),
category_image  varchar(120),
extra  varchar(120)
);

-- for products    
create table products (
product_id int primary key Auto_Increment ,
category_id int,
product_name varchar(100) not null,
detail varchar(200) not null,
Price DECIMAL(10, 2) NOT NULL,
image varchar(80) not null,
extras varchar(80) ,
Foreign key (category_id) references category(category_id)
);

drop table products;
-- insert a user
INSERT INTO users (UserName, email, password) VALUES ('tiger', 'tiger@123.com', '1234');
select * from users;

-- insert a Category
INSERT INTO category (category_name) VALUES ('Fresh Vegetable');
select * from category;
update  category set category_image= 'https://rb.gy/7jnxlg' where category_name='Fresh Vegetable';

-- insert a product
INSERT INTO products (category_id,product_name, price, detail , image) VALUES ( 1,'Lady Finger', 45.99, "500gm * 2","https://rb.gy/3qh22s");

select * from products;

-- For Cart


CREATE TABLE cart (
    cart_Id INT PRIMARY KEY auto_increment,
    user_id INT,
    product_id INT,
    quantity INT,
    price DECIMAL(10 , 2 ),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
drop table cart;
INSERT INTO cart (user_id, product_id, quantity , price) VALUES (1, 1, 2, 80);
select * from cart;

-- Join on Cart and Product to fetch Details of
-- cart and prodcuts

SELECT
    cart.cart_id,
    products.product_name,
    products.price AS ProductPrice,
    cart.quantity
FROM
    cart
JOIN
    products ON cart.product_id = products.product_id;

-- for Order table
CREATE TABLE orders (
    order_id INT PRIMARY KEY auto_increment,
    user_id INT,
    order_date DATE ,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(120),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

drop table orders;

CREATE TABLE order_details (
    order_detail_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    quantity INT,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
drop table order_details;

-- Place an Order
INSERT INTO orders (user_id, total_amount , order_date) VALUES (1, 1500.00, current_date());
select * from orders;
-- get name of of customer who order with amt 
SELECT users.username, orders.total_amount
FROM orders
JOIN users ON orders.user_id = users.user_id;

-- total Amount -  do Sum of All Items;

--  move all Items to Cart
-- Retrieve the newly generated order_id
SET @orderId = LAST_INSERT_ID();
select @orderID;
-- Move cart items to order details
INSERT INTO order_details (order_id, product_id, quantity, price)
SELECT @orderId, product_id, quantity, price FROM cart WHERE user_id = 1;

-- Remove items from the cart
DELETE FROM carts WHERE user_id = 1;



