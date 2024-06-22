-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : database
-- Généré le : sam. 22 juin 2024 à 18:19
-- Version du serveur : 8.4.0
-- Version de PHP : 8.2.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `chatop`
--

-- --------------------------------------------------------

--
-- Structure de la table `message`
--

CREATE TABLE `message`
(
    `created_at` DATE NOT NULL,
    `id`         INT  NOT NULL,
    `rental_id`  INT          DEFAULT NULL,
    `updated_at` DATE         DEFAULT NULL,
    `user_id`    INT          DEFAULT NULL,
    `message`    VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_rental_id` (`rental_id`),
    KEY `fk_user_id` (`user_id`),
    CONSTRAINT `fk_rental_id` FOREIGN KEY (`rental_id`) REFERENCES `rental` (`id`),
    CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `message_seq`
--

CREATE TABLE `message_seq`
(
    `next_val` BIGINT DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `message_seq`
--

INSERT INTO `message_seq` (`next_val`)
VALUES (1);

-- --------------------------------------------------------

--
-- Structure de la table `rental`
--

CREATE TABLE `rental`
(
    `created_at`  DATE NOT NULL,
    `id`          INT  NOT NULL,
    `owner_id`    INT            DEFAULT NULL,
    `price`       DECIMAL(38, 2) DEFAULT NULL,
    `surface`     DECIMAL(38, 2) DEFAULT NULL,
    `updated_at`  DATE           DEFAULT NULL,
    `description` TEXT,
    `name`        VARCHAR(255)   DEFAULT NULL,
    `picture`     VARCHAR(255)   DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_owner_id` (`owner_id`),
    CONSTRAINT `fk_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `rental_seq`
--

CREATE TABLE `rental_seq`
(
    `next_val` BIGINT DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `rental_seq`
--

INSERT INTO `rental_seq` (`next_val`)
VALUES (1);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user`
(
    `created_at` DATE NOT NULL,
    `id`         INT  NOT NULL,
    `updated_at` DATE         DEFAULT NULL,
    `email`      VARCHAR(255) DEFAULT NULL,
    `name`       VARCHAR(255) DEFAULT NULL,
    `password`   VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `user_seq`
--

CREATE TABLE `user_seq`
(
    `next_val` BIGINT DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `user_seq`
--

INSERT INTO `user_seq` (`next_val`)
VALUES (1);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;