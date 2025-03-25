-- Désactiver les contraintes de clé étrangère temporairement
SET FOREIGN_KEY_CHECKS = 0;

-- Supprimer les données de toutes les tables sauf users, oauth_users, 'role', 'user_role' et user_profile
TRUNCATE TABLE employee;
TRUNCATE TABLE email_template;
TRUNCATE TABLE customer_login_info;
TRUNCATE TABLE customer;

-- Réactiver les contraintes de clé étrangère
SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE `table_temp_1` (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `Index` VARCHAR(255) ,
    `Customer Id` VARCHAR(255) ,
    `First Name` VARCHAR(255) ,
    `Last Name` VARCHAR(255) ,
    `Company` VARCHAR(255) ,
    `City` VARCHAR(255) ,
    `Country` VARCHAR(255) ,
    `Phone 1` VARCHAR(255) ,
    `Phone 2` VARCHAR(255) ,
    `Email` VARCHAR(255) ,
    `Subscription Date` VARCHAR(255) ,
    `Website` VARCHAR(255) 
);

INSERT INTO table_temp_1 (Index, Customer Id, First Name, Last Name, Company, City, Country, Phone 1, Phone 2, Email, Subscription Date, Website) 
VALUES 
('1', 'DD37Cf93aecA6Dc', 'Sheryl', 'Baxter', 'Rasmussen Group', 'East Leonard', 'Chile', '229.077.5154', '397.884.0519x718', 'zunigavanessa@smith.info', '2020-08-24', 'http://www.stephenson.com/');