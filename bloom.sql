-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jun 06, 2023 at 11:27 AM
-- Server version: 8.0.21
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bloom`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
CREATE TABLE IF NOT EXISTS `cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  `qty` int NOT NULL,
  `status` varchar(10) NOT NULL,
  `created_dttm` datetime NOT NULL,
  `updt_dttm` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`id`, `user_id`, `product_id`, `qty`, `status`, `created_dttm`, `updt_dttm`) VALUES
(1, 1, 3, 3, 'ACTIVE', '2023-06-05 16:33:59', '2023-06-06 16:49:46'),
(2, 1, 2, 1, 'INACTIVE', '2023-06-05 17:38:42', '2023-06-06 16:44:18'),
(11, 1, 1, 1, 'INACTIVE', '2023-06-06 15:39:56', '2023-06-06 16:40:40'),
(12, 1, 2, 1, 'INACTIVE', '2023-06-06 16:44:09', '2023-06-06 16:44:18');

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `category`) VALUES
(1, 'Plants'),
(2, 'Vase'),
(3, 'Plant Care Products');

-- --------------------------------------------------------

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
CREATE TABLE IF NOT EXISTS `images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `img_type_id` int NOT NULL,
  `source_id` int NOT NULL,
  `img_path` varchar(255) DEFAULT NULL,
  `created_dttm` datetime NOT NULL,
  `updt_dttm` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `img_type_id` (`img_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `images`
--

INSERT INTO `images` (`id`, `img_type_id`, `source_id`, `img_path`, `created_dttm`, `updt_dttm`) VALUES
(1, 1, 1, NULL, '2023-06-01 06:28:39', NULL),
(2, 2, 1, 'products/rose.png', '2023-06-01 06:29:34', NULL),
(3, 2, 2, 'products/jasmin.png', '2023-06-01 06:36:49', NULL),
(4, 2, 3, 'products/lily.png', '2023-06-01 06:37:18', NULL),
(5, 2, 4, 'products/alovera.png', '2023-06-01 06:38:21', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `image_type`
--

DROP TABLE IF EXISTS `image_type`;
CREATE TABLE IF NOT EXISTS `image_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `image_type` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `image_type`
--

INSERT INTO `image_type` (`id`, `image_type`) VALUES
(1, 'user'),
(2, 'product');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `category_id` int NOT NULL,
  `status` varchar(10) NOT NULL,
  `created_dttm` datetime NOT NULL,
  `updt_dttm` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `name`, `price`, `category_id`, `status`, `created_dttm`, `updt_dttm`) VALUES
(1, 'Rose', 289, 1, 'ACTIVE', '2023-06-01 06:04:32', NULL),
(2, 'Jasminum sambac, Mogra, Arabian Jasmine', 299, 1, 'ACTIVE', '2023-06-01 06:07:25', NULL),
(3, 'Peace Lily, Spathiphyllum', 169, 1, 'ACTIVE', '2023-06-01 06:08:22', NULL),
(4, 'Aloe vera', 259, 1, 'ACTIVE', '2023-06-01 06:09:10', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `role`) VALUES
(1, 'Customer'),
(2, 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `dob` date DEFAULT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `role_id` int NOT NULL,
  `password` varchar(20) NOT NULL,
  `created_dttm` datetime NOT NULL,
  `updt_dttm` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `name`, `email`, `phone`, `dob`, `gender`, `role_id`, `password`, `created_dttm`, `updt_dttm`) VALUES
(1, 'ashukla69', 'Arpit Shukla', 'shuklaarpit00007@gmail.com', '9936027455', '2000-05-06', 'M', 1, 'Arpit@123', '2023-05-31 03:28:43', NULL);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `images`
--
ALTER TABLE `images`
  ADD CONSTRAINT `images_ibfk_1` FOREIGN KEY (`img_type_id`) REFERENCES `image_type` (`id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
