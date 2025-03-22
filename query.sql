-- Désactiver les contraintes de clé étrangère temporairement
SET FOREIGN_KEY_CHECKS = 0;

-- Supprimer les données de toutes les tables sauf users, oauth_users, 'role', 'user_role' et user_profile
TRUNCATE TABLE employee;
TRUNCATE TABLE email_template;
TRUNCATE TABLE customer_login_info;
TRUNCATE TABLE customer;

-- Réactiver les contraintes de clé étrangère
SET FOREIGN_KEY_CHECKS = 1;