
create database fc;
use fc;

CREATE TABLE `fc`.`pages` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `slug` VARCHAR(45) NOT NULL,
  `content` TEXT NOT NULL,
  `sorting` INT(3) NOT NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE `fc`.`categories` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(60) NOT NULL,
  `slug` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`id`));
  
  CREATE TABLE courses (
    id INT(11) AUTO_INCREMENT NOT NULL,
    name VARCHAR(45) NOT NULL,
    slug VARCHAR(45) NOT NULL,
    description TEXT NOT NULL,
    image VARCHAR(100) NOT NULL,
    price DECIMAL(8, 2) NOT NULL,
    category_id INT(11) NOT NULL,
    duration INT(11) NOT NULL,
    trainer_id INT(11) NOT NULL,
    end_date varchar(45) NOT NULL,
   start_date varchar(45)  NOT NULL,
    PRIMARY KEY (id)
    
);


CREATE TABLE trainers (
    id INT(11) AUTO_INCREMENT NOT NULL,
    name VARCHAR(45) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    centre_id INT(11) NOT NULL,
    PRIMARY KEY (id)
    
);
create table users(
id INT AUTO_INCREMENT NOT NULL,
username varchar(45) NOT NULL,
phone_number varchar(45),
gender varchar(20),
password varchar(255),
dob varchar(12),
city varchar(50),
state varchar(50),
email varchar(45),
street varchar(50),
first_name varchar(50),
last_name varchar(50),

);
CREATE TABLE centres (
    id INT(11) AUTO_INCREMENT NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
);

create table admin(
	id INT AUTO_INCREMENT NOT NULL,
    first_name varchar(50),
    last_name varchar(50),
    email varchar(45),
    password varchar(255)

);

  INSERT INTO pages
VALUES (1, 'Home', 'home', 'home page', 0),
(2, 'Courses', 'courses', 'courses page', 1),
(3, 'Profile', 'profile', 'profile page', 2),
(4, 'Cart', 'cart', 'cart page', 3)
;

  INSERT INTO categories
VALUES (1, 'Yoga', 'yoga'),
(2, 'Aerobics', 'aerobics'),
(3, 'Zumba', 'zumba'),
(4, 'Pilates', 'pilates');

ALTER TABLE `fc`.`trainers` 
ADD INDEX `centre_id_fk_idx` (`centre_id` ASC) VISIBLE;
;
ALTER TABLE `fc`.`trainers` 
ADD CONSTRAINT `centre_id_fk`
  FOREIGN KEY (`centre_id`)
  REFERENCES `fc`.`centres` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;






select * from pages;
