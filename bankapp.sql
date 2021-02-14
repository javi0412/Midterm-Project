DROP SCHEMA bankapp;
CREATE SCHEMA bankapp;
USE bankapp;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `third_party` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `hashed_key` int NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkipq0epw9t7dv8j9bsmrg3wdg` (`user_id`),
  CONSTRAINT `FKkipq0epw9t7dv8j9bsmrg3wdg` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `account_holder` (
  `date_of_birth` date DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK5tyfveeddp5dqy88r0yl3x5vw` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `admin` (
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK1ja8rua032fgnk9jmq7du3b3a` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `balance_amount` decimal(19,2) DEFAULT NULL,
  `balance_currency` varchar(255) DEFAULT NULL,
  `penalty_fee_amount` decimal(19,2) DEFAULT NULL,
  `penalty_fee_currency` varchar(255) DEFAULT NULL,
  `primary_owner` bigint DEFAULT NULL,
  `secondary_owner` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKes6igfxoxfmlttcmscy411td2` (`primary_owner`),
  KEY `FK9aif9tys7glwp4aajaloekc37` (`secondary_owner`),
  CONSTRAINT `FK9aif9tys7glwp4aajaloekc37` FOREIGN KEY (`secondary_owner`) REFERENCES `account_holder` (`id`),
  CONSTRAINT `FKes6igfxoxfmlttcmscy411td2` FOREIGN KEY (`primary_owner`) REFERENCES `account_holder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `checking` (
  `creation_date` date DEFAULT NULL,
  `minimum_balance` decimal(19,2) DEFAULT NULL,
  `monthly_maintenance_fee` decimal(19,2) DEFAULT NULL,
  `secret_key` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK4pww71v1myyn2iai6qm84t29o` FOREIGN KEY (`id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `credit_card` (
  `creation_date` date DEFAULT NULL,
  `credit_limit` decimal(19,2) DEFAULT NULL,
  `interest_rate` decimal(19,2) DEFAULT NULL,
  `last_access_date` date DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK5nn2ykrc28pst4v0axopldeqv` FOREIGN KEY (`id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `savings` (
  `creation_date` date DEFAULT NULL,
  `interest_rate` decimal(19,2) DEFAULT NULL,
  `last_interest_date` date DEFAULT NULL,
  `minimum_balance` decimal(19,2) DEFAULT NULL,
  `secret_key` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKs02t0s57xrunyqosm96vrttin` FOREIGN KEY (`id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `student_checking` (
  `creation_date` date DEFAULT NULL,
  `secret_key` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKcw2ja1qwe1e6xhuj4e1o9jvy6` FOREIGN KEY (`id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `transaction_amount` decimal(19,2) DEFAULT NULL,
  `transaction_currency` varchar(255) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `transaction_date` datetime(6) DEFAULT NULL,
  `destination_account_id` bigint NOT NULL,
  `origen_account_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKan2cq79w1pqavplm9nbu5aef0` (`destination_account_id`),
  KEY `FK168dn5xbm4mbefw2h49q9vws0` (`origen_account_id`),
  CONSTRAINT `FK168dn5xbm4mbefw2h49q9vws0` FOREIGN KEY (`origen_account_id`) REFERENCES `account` (`id`),
  CONSTRAINT `FKan2cq79w1pqavplm9nbu5aef0` FOREIGN KEY (`destination_account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


SELECT * FROM user;

INSERT INTO user(id,name, password, username) VALUES
('1','Luis','$2a$10$FKWDfJipctc6nQ2GUsejRe9K9W0.DpRG6nsMHIBdxDmQqhGH29ciW', 'username1'); -- passowrd:1234

SELECT * FROM roles;
INSERT INTO roles(id, name, user_id) VALUES
('1','ADMIN', '1');


