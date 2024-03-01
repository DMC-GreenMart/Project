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

ALTER TABLE users
CHANGE COLUMN extra role VARCHAR(10);

ALTER TABLE users
ALTER COLUMN role SET DEFAULT 'User';
select * from users;
delete from users where user_id = 10;

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
delete from category where category_id = 3;
update  category set category_image='https://rb.gy/7jnxlg' where category_name='Fresh Vegetable';

-- insert a product
INSERT INTO products (category_id,product_name, price, detail , image) VALUES ( 1,'Lady Finger', 45.99, "500gm * 2","https://rb.gy/3qh22s");
INSERT INTO products (category_id,product_name, price, detail , image) VALUES ( 1,' Tomato', 30, "250gm * 2","https://shorturl.at/stBO8");
select * from products;

-- For Cart


CREATE TABLE cart (
    cart_Id INT PRIMARY KEY auto_increment,
    user_id INT,
    product_id INT,
    quantity INT,
    price DECIMAL(10, 2),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
drop table cart;
INSERT INTO cart (user_id, product_id, quantity , price) VALUES (1, 1, 2, 80);
INSERT INTO cart (user_id, product_id, quantity , price) VALUES (1, 2, 4, 120);


select * from cart;	

-- for incrementing product
UPDATE cart
        SET quantity = quantity + 1,
            price = price + ?
        WHERE cart_id= ? AND product_id = ?;
 
 
 -- for Decrementing product
UPDATE cart
        SET quantity = quantity - 1,
            price = price - ?
        WHERE cart_id= ? AND product_id = ? and quantity >0;
 
 
-- Join on Cart and Product to fetch Details of
-- cart and prodcuts

SELECT
    cart.cart_id,
    products.product_name,
    products.price AS ProductPrice,
    products.image,
    cart.quantity
FROM
    cart
JOIN
    products ON cart.product_id = products.product_id;
select * from cart;

-- for Order table

CREATE TABLE orders (
    order_id INT PRIMARY KEY auto_increment,
    user_id INT,
    order_date DATE ,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(120),
    address_id int ,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ,
    FOREIGN KEY (address_id) REFERENCES addresses(address_id)
);
select * from orders;
drop table orders;
delete from orders;


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
select * from order_details;
delete from order_details;
CREATE TABLE addresses (
    address_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    street VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    zip_code VARCHAR(20),

    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

INSERT INTO addresses (user_id, street, city, state, zip_code)
VALUES (1, 'Hinjewadi', 'Pune', 'Maharashtra', '411057');

select * from addresses where user_id = 1;
   
-- steps to required -- start on Place Order


-- before placing order add a address Whole with pinCde

INSERT INTO addresses (user_id, street, city, state, zip_code)
VALUES (1, 'Hinjewadi', 'Pune', 'Maharashtra', '411057');
 
 
-- Place an Order
INSERT INTO orders (user_id, total_amount , order_date ,address_id) VALUES (1, 1500.00, current_date(),1);
select * from orders;
delete from orders;
-- get name of of customer who-s order with amount and  Address
SELECT users.username As Name, orders.total_amount ,addresses.street , addresses.state 
FROM orders
JOIN users ON orders.user_id = users.user_id
JOIN 
    addresses ON orders.address_id = addresses.address_id;

-- total Amount -  do Sum of All Items;

--  move all Items to Cart


-- Retrieve the newly generated order_id
SET @orderId = LAST_INSERT_ID();
select @orderID;

-- Move cart items to order details
INSERT INTO order_details (order_id, product_id, quantity, price)
SELECT @orderId, product_id, quantity, price FROM cart WHERE user_id = 1;
select * from order_details;

-- Remove items from the cart
DELETE FROM cart WHERE user_id = 1;

-- end of place Order

-- joins in orders Detail -- Products --Order to get (my Orders )
-- my orders
SELECT
    o.order_id,
    o.order_date,
    p.product_name,
    p.price,
    od.quantity,
    (p.price * od.quantity) AS total_price
FROM
    order_details od
JOIN
    orders o ON od.order_id = o.order_id
JOIN
    products p ON od.product_id = p.product_id;

-- genrate bill

SELECT
    o.order_id,
    u.username,
    
    o.order_date,
    od.product_id,
    p.product_name,
    od.quantity,
    p.price,
    (od.quantity * p.price) AS total_price
FROM
    orders o
JOIN
    users u ON o.user_id = u.user_id
JOIN
    order_details od ON o.order_id = od.order_id
JOIN
    products p ON od.product_id = p.product_id;
    
    
    -- If the orders table has an address_id that corresponds to an address table, and you want to generate a bill that includes
    -- information from both the orders and address tables, you can modify the query accordingly. Here's an example:



SELECT
    o.order_id,
    u.username,
    a.street,
    a.state,
    o.order_date,
    od.product_id,
    p.product_name,
    od.quantity,
    p.price,
    (od.quantity * p.price) AS total_price
FROM
    orders o
JOIN
    users u ON o.user_id = u.user_id
JOIN
    addresses a ON o.address_id = a.address_id
JOIN
    order_details od ON o.order_id = od.order_id
JOIN

    products p ON od.product_id = p.product_id;
    
    // 
    -- --  --- -- -- - - -  - - generate by id
    SELECT
    orders.order_id,
    orders.order_date,
     products.product_name,
    order_details.quantity,
    order_details.price AS unit_price,
      addresses.street AS delivery_street,
    addresses.city AS delivery_city
FROM
    orders
JOIN
    order_details ON orders.order_id = order_details.order_id
JOIN
    addresses ON orders.address_id = addresses.address_id
    JOIN
    products ON order_details.product_id = products.product_id
WHERE
    orders.order_id = 8;

-- get product name and category join 
SELECT
    p.product_id,
    p.category_id,
    c.category_name,
    p.product_name,
    p.detail,
    p.Price,
    p.image,
    p.extras
FROM
    products p
JOIN
    categories c ON p.category_id = c.category_id;

select * from orders;


-- my Order details based on User Id

SELECT
  o.order_id,
  o.order_date,
  p.product_name,
  p.price,
  od.quantity,
  (p.price * od.quantity) AS total_price
FROM
  order_details od
JOIN
  orders o ON od.order_id = o.order_id
JOIN
  products p ON od.product_id = p.product_id
WHERE
  o.user_id = ?  -- Replace ? with the user ID you want to filter by
ORDER BY
  o.order_id DESC;



