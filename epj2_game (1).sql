-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 28, 2021 at 12:20 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `epj2_game`
--
CREATE DATABASE IF NOT EXISTS `epj2_game` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `epj2_game`;

-- --------------------------------------------------------

--
-- Table structure for table `achievement`
--

CREATE TABLE IF NOT EXISTS `achievement` (
  `achievement_id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `score` int(11) NOT NULL DEFAULT 0,
  `time` float(10,2) DEFAULT NULL,
  PRIMARY KEY (`achievement_id`),
  KEY `FK_playerachievement` (`player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `achievement`
--

INSERT INTO `achievement` (`achievement_id`, `player_id`, `score`, `time`) VALUES
(1, 9, 44, 9.00),
(2, 9, 55, 10.00),
(3, 10, 83, 19.00),
(4, 9, 100, 20.00),
(5, 9, 65, 13.00),
(6, 12, 88, 22.00),
(7, 13, 141, 41.00),
(8, 10, 88, 24.00),
(9, 9, 116, 26.00);

-- --------------------------------------------------------

--
-- Table structure for table `player`
--

CREATE TABLE IF NOT EXISTS `player` (
  `player_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `nickname` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `gmail` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `phone_number` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`player_id`),
  UNIQUE KEY `UC_player` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `player`
--

INSERT INTO `player` (`player_id`, `user_name`, `password`, `nickname`, `image`, `gmail`, `phone_number`) VALUES
(9, 'admin', '25f9e794323b453885f5181f1b624d0b', 'adm1n', 'srcImageanh.png', 'admin@gmail.com', '01234567899'),
(10, 'minh123', '25f9e794323b453885f5181f1b624d0b', 'minh123', 'srcImage117799179_717176332182425_4169323277927933169_n.jpg', 'mih@gmail.com', '08656216380'),
(12, 'chingchong', '25f9e794323b453885f5181f1b624d0b', 'china', 'srcImageanh.png', 'china@gmail.com', '001122334455'),
(13, 'minh', '432f45b44c432414d2f97df0e5743818', 'minh', 'srcImage81730090_1750459491751491_6760699628267503616_n (1).jpg', 'minh@gmail.com', '0987654321');

-- --------------------------------------------------------

--
-- Table structure for table `rank`
--

CREATE TABLE IF NOT EXISTS `rank` (
  `rank_id` int(11) NOT NULL AUTO_INCREMENT,
  `player_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `highest_score` int(11) NOT NULL,
  `time` float(10,2) NOT NULL,
  PRIMARY KEY (`rank_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `achievement`
--
ALTER TABLE `achievement`
  ADD CONSTRAINT `FK_playerachievement` FOREIGN KEY (`player_id`) REFERENCES `player` (`player_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
