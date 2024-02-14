CREATE TABLE users(
    id INT PRIMARY KEY AUTO_INCREMENT, 
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    email VARCHAR(50) UNIQUE,
    password VARCHAR(20), 
    mobile CHAR(10));

INSERT INTO users(first_name,last_name,email,password,mobile) 
VALUES("test","test","test@gmail.com","test","1234567890");

CREATE TABLE movies(
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(50),
    image VARCHAR(100),
    release_date DATE
);

INSERT INTO movies(title,image,release_date) VALUES 
("Godzilla -1","godzilla_minus_one.png","2023-11-03"), 
("The Boy And The Heron","the_boy_and_the_heron.png","2023-11-10"),
("The Hunger Games","the_hunger_games.png","2023-11-17"),
("Trolls Band Together","trolls_band_together.png","2023-11-24");

CREATE TABLE reviews(
    id INT PRIMARY KEY AUTO_INCREMENT,
    movieid INT, 
    review TEXT, 
    rating INT, 
    userid INT, 
    FOREIGN KEY(movieid) REFERENCES movies(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(userid) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE(movieid,userid)
);

CREATE TABLE shares(
    reviewid INT,
    userid INT,
    FOREIGN KEY(reviewid) REFERENCES reviews(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(userid) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
    UNIQUE(reviewid,userid)
);