# va faceti prima data un database

CREATE TABLE `users` (
     `id` int NOT NULL AUTO_INCREMENT,
     `first_name` varchar(45) NOT NULL,
     `last_name` varchar(45) NOT NULL,
     `email` varchar(255) DEFAULT NULL,
     `password_hash` varchar(255) NOT NULL,
     `account_locked` bit(1) NOT NULL,
     `enabled` bit(1) NOT NULL,
     `role` int NOT NULL,
     PRIMARY KEY (`id`),
     UNIQUE KEY `users_pk` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `companies` (
     `id` int NOT NULL AUTO_INCREMENT,
     `name` varchar(45) NOT NULL,
     `telefon_number` varchar(15) NOT NULL,
     `address` varchar(45) NOT NULL,
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `contracts` (
     `id` int NOT NULL AUTO_INCREMENT,
     `user_id` int NOT NULL,
     `company_id` int NOT NULL,
     `signing_date` date DEFAULT NULL,
     `previous_year_vacation_days` int DEFAULT '0',
     `actual_year_vacation_days` int DEFAULT '0',
     PRIMARY KEY (`id`),
     UNIQUE KEY `contracts_pk` (`user_id`),
     KEY `FK_contract_user_idx` (`user_id`),
     KEY `FK_contract_company_idx` (`company_id`),
     CONSTRAINT `FK_contract_company` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`),
     CONSTRAINT `FK_contract_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `vacation_request` (
    `id` int NOT NULL AUTO_INCREMENT,
    `employee_id` int NOT NULL,
    `administrator_id` int NOT NULL,
    `requested_date` datetime NOT NULL,
    `start_date` date NOT NULL,
    `end_date` date NOT NULL,
    `status` int NOT NULL,
    `description` varchar(256) NOT NULL,
    `pdf_path` varchar(256),
    PRIMARY KEY (`id`),
    KEY `user_id_idx` (`employee_id`),
    KEY `administrator_id_idx` (`administrator_id`),
    CONSTRAINT `FK_administrator_id_vac_req` FOREIGN KEY (`administrator_id`) REFERENCES `users` (`id`),
    CONSTRAINT `FK_user_id_vac_req` FOREIGN KEY (`employee_id`) REFERENCES `users` (`id`)
);

CREATE TABLE `working_days` (
        `id` int NOT NULL AUTO_INCREMENT,
        `date` date NOT NULL,
        `start_date` datetime NOT NULL,
        `end_date` datetime NOT NULL,
        `description` varchar(256) NOT NULL,
        `employee_id` int NOT NULL,
        PRIMARY KEY (`id`),
        KEY `employee_working_day_id_FK_idx` (`employee_id`),
        CONSTRAINT `employee_working_day_id_FK` FOREIGN KEY (`employee_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `days_off` (
    `id` int(10) unsigned zerofill NOT NULL,
    `date` date NOT NULL,
    `name` varchar(45) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `date_UNIQUE` (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



drop table days_off;
drop table contracts;
drop table companies;
drop table vacation_request;
drop table working_days;
drop table users;