-- MySQL dump 10.13  Distrib 9.2.0, for Linux (x86_64)
--
-- Host: localhost    Database: tourbooking
-- ------------------------------------------------------
-- Server version	9.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `booking_type` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `paid_at` datetime(6) DEFAULT NULL,
  `status` enum('CANCELED','PAID','PENDING') DEFAULT NULL,
  `total_price` decimal(19,0) DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlnnelfsha11xmo2ndjq66fvro` (`customer_id`),
  CONSTRAINT `FKlnnelfsha11xmo2ndjq66fvro` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (1,NULL,'2025-10-11 18:04:36.283032',NULL,'PENDING',2990000,1);
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `average_rating` double DEFAULT NULL,
  `review_count` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKj7ja2xvrxudhvssosd4nu1o92` (`user_id`),
  CONSTRAINT `FKg09blp5mmjq2s3vgngse5ug4a` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,NULL,NULL,NULL,'Administrator Administrator',NULL,NULL,1,NULL,NULL),(2,NULL,NULL,NULL,'Hung Nghiem',NULL,NULL,2,NULL,NULL);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
INSERT INTO `databasechangelog` VALUES ('00000000000001','jhipster','config/liquibase/changelog/00000000000000_initial_schema.xml','2025-06-24 16:51:46',1,'EXECUTED','9:8ae73e58f4f46b4fcac75f9b9e68f7d6','createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...','',NULL,'4.29.2',NULL,NULL,'0783906134'),('20251011-1','you','config/liquibase/changelog/20251011_added_entity_payment.xml','2025-10-11 17:26:32',2,'EXECUTED','9:fa0636bf463968e13e42ad2bb8fb90d6','createTable tableName=payment; createIndex indexName=ux_payment_order_id, tableName=payment','',NULL,'4.29.2',NULL,NULL,'0203592043');
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangeloglock` (
  `ID` int NOT NULL,
  `LOCKED` tinyint NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,0,NULL,NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotels`
--

DROP TABLE IF EXISTS `hotels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotels` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(300) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `name` varchar(150) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `rating` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotels`
--

LOCK TABLES `hotels` WRITE;
/*!40000 ALTER TABLE `hotels` DISABLE KEYS */;
INSERT INTO `hotels` VALUES (2,'123 Lý Thường Kiệt, Đà Nẵng','Đà Nẵng','Cập nhật mô tả mới','Khách sạn Paradise','0987654321','5','https://pix8.agoda.net/hotelImages/568208/0/751dbf960016c483ab024702481b9042.jpeg?ce=0&s=1024x'),(4,'341 Đ. Trần Hưng Đạo, An Hải Bắc, Sơn Trà, Đà Nẵng','Đà Nẵng','Đỗ xe và wifi luôn miễn phí, vì vậy khách có thể giữ liên lạc, đến và đi tùy ý. Nơi đây gần các điểm vui chơi và ăn uống.','Melia Vinpearl Riverfront Đà Nẵng','0912345678','5','https://pix8.agoda.net/hotelImages/4947690/-1/f5dd1db7b2125faf6a1210227979f529.jpg?ce=0&s=1024x'),(5,'07 Trường Sa, Phường Ngũ Hành Sơn, Thành phố Đà Nẵng, Việt Nam, Đà Nẵng, Việt Nam','Đà Nẵng','Với khung cảnh Ngũ Hành Sơn làm nền, Danang Marriott Resort & Spa cung cấp chỗ nghỉ sang trọng bên những bãi cát hoang sơ của Bãi biển Non Nước. Chỗ nghỉ này có 5 hồ bơi ngoài trời, spa đầy đủ dịch vụ và tiện nghi tập thể dục.','Danang Marriott Resort & Spa','968663020','5','https://cf.bstatic.com/xdata/images/hotel/max1024x768/619717249.jpg?k=fe135fce35ed4a83851ae913c57211f67e85e2b099c5130e64131a00f06b896d&o=');
/*!40000 ALTER TABLE `hotels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_authority`
--

DROP TABLE IF EXISTS `jhi_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_authority`
--

LOCK TABLES `jhi_authority` WRITE;
/*!40000 ALTER TABLE `jhi_authority` DISABLE KEYS */;
INSERT INTO `jhi_authority` VALUES ('ROLE_ADMIN'),('ROLE_STAFF'),('ROLE_USER');
/*!40000 ALTER TABLE `jhi_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user`
--

DROP TABLE IF EXISTS `jhi_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(254) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(10) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_login` (`login`),
  UNIQUE KEY `ux_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1076 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user`
--

LOCK TABLES `jhi_user` WRITE;
/*!40000 ALTER TABLE `jhi_user` DISABLE KEYS */;
INSERT INTO `jhi_user` VALUES (1,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','admin@localhost',NULL,_binary '','vi',NULL,NULL,'system',NULL,NULL,'admin','2025-09-30 17:01:07'),(2,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost','',_binary '','vi',NULL,NULL,'system',NULL,NULL,'admin','2025-09-30 17:02:23'),(1050,'billming001@gmail.com','$2a$10$KTxmKfTTFgSYbiy8Xr0wee3TI74jU.95FdNd249eMQAIBWa.a9wIa','nghiêm hùng',NULL,'billming001@gmail.com','https://lh3.googleusercontent.com/a/ACg8ocJiblwUTqvaHvo1SNwWxxvM2pwz0auN04Qx9wBjYbcXbGa2EA=s96-c',_binary '','vi',NULL,NULL,'anonymousUser','2025-06-26 07:47:40',NULL,'anonymousUser','2025-06-26 07:47:40'),(1051,'nghiemviethung9@gmail.com','$2a$10$M5gARC3AWvAMgmCQbuy00eQyEmVrMHlIieb84D34JTNSFfGON90XO','Nghiêm Việt Hùng',NULL,'nghiemviethung9@gmail.com','https://lh3.googleusercontent.com/a/ACg8ocJVpAnDow90yfPBT5VivzUbdhhdZEnPL9vgXtRDd8rzzcBxyA=s96-c',_binary '','vi',NULL,NULL,'anonymousUser','2025-06-26 08:02:34',NULL,'anonymousUser','2025-06-26 08:02:34'),(1052,'user1','$2a$10$oDtjkt/kCESgfQprKrF/R.hFCJ4tKH4L7tydUpTFrJ8MfJFhSUz3q',NULL,NULL,'user1@gmail.com',NULL,_binary '','vi','2KTJEYkA9ZFGwFn03cmt',NULL,'anonymousUser','2025-06-27 08:30:00',NULL,'admin','2025-06-27 14:52:50'),(1053,'user2','$2a$10$vkeHQ0qBlH94BzPl6y20jumu70oSgAsqC1n7ULSuxpAwFMzpOttWO',NULL,NULL,'user2@gmail.com',NULL,_binary '','vi','UwSKmbdEunIdQpZAnJDT',NULL,'anonymousUser','2025-06-27 09:36:41',NULL,'admin','2025-06-27 14:52:50'),(1055,'billming021','$2a$10$9xRzjY7hU2ywYVqrJQOa0uMd8e.nC/1WBZEzvH.ltj4gbNBlU4jei',NULL,NULL,'vhung2002@gmail.com',NULL,_binary '','vi','G2eESfaeqhOr3E8xs65z','uscXk3w14vWqcPhJWbzA','anonymousUser','2025-06-27 09:58:38','2025-06-29 17:16:49','anonymousUser','2025-06-29 17:16:49'),(1056,'staff','$2a$10$6/Y1hniH0SdVMKzpvnuj9O54OiL2hHpRvvZD96/L1rdwUKCpOdtNe',NULL,NULL,'staff@localhost',NULL,_binary '','vi','9EybDn3wAroQec7PummM',NULL,'anonymousUser','2025-06-27 14:15:34',NULL,'anonymousUser','2025-06-27 14:15:34');
/*!40000 ALTER TABLE `jhi_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user_authority`
--

DROP TABLE IF EXISTS `jhi_user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_user_authority` (
  `user_id` bigint NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_authority_name` (`authority_name`),
  CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user_authority`
--

LOCK TABLES `jhi_user_authority` WRITE;
/*!40000 ALTER TABLE `jhi_user_authority` DISABLE KEYS */;
INSERT INTO `jhi_user_authority` VALUES (1,'ROLE_ADMIN'),(1056,'ROLE_STAFF'),(2,'ROLE_USER'),(1050,'ROLE_USER'),(1051,'ROLE_USER'),(1052,'ROLE_USER'),(1053,'ROLE_USER'),(1055,'ROLE_USER');
/*!40000 ALTER TABLE `jhi_user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(10) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'HAN',_binary '','Hà Nội'),(2,'DAL',_binary '','Đà Lạt'),(3,'DAN',_binary '','Đà Nẵng');
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) NOT NULL,
  `paid_at` datetime(6) DEFAULT NULL,
  `status` enum('CANCELED','PAID','PENDING') DEFAULT NULL,
  `target_id` varchar(64) NOT NULL,
  `target_type` varchar(20) NOT NULL,
  `order_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt4dc2r9nbvbujrljv3e23iibt` (`order_id`),
  CONSTRAINT `FKt4dc2r9nbvbujrljv3e23iibt` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `paid_at` datetime(6) DEFAULT NULL,
  `status` enum('CANCELED','OPEN','PAID','PARTIALLY_PAID') DEFAULT NULL,
  `total_amount` decimal(19,2) DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `number_of_people` int DEFAULT NULL,
  `order_date` datetime(6) DEFAULT NULL,
  `payment_status` enum('CANCELLED','PAID','PENDING') DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `total_price` decimal(38,2) DEFAULT NULL,
  `tour_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK624gtjin3po807j3vix093tlf` (`customer_id`),
  CONSTRAINT `FK624gtjin3po807j3vix093tlf` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,NULL,NULL,NULL,NULL,NULL,'vhung2002@gmail.com','Nghiem Viet Hung',2,'2025-12-01 05:52:29.903215',NULL,'0968663020',5980000.00,1),(2,NULL,NULL,NULL,NULL,NULL,'','Hùng',1,'2025-12-08 08:55:36.148812','PAID','0968663020',2990000.00,1),(3,NULL,NULL,NULL,NULL,NULL,'','Hùng',1,'2025-12-08 11:18:01.154350','PAID','0968663020',2990000.00,1),(4,NULL,NULL,NULL,NULL,NULL,'','Hùng',1,'2025-12-08 11:23:03.400779','PAID','0968663020',2990000.00,1),(5,NULL,NULL,NULL,NULL,NULL,'','Hùng',2,'2025-12-08 11:23:55.284728','PAID','0968663020',5980000.00,1),(6,NULL,NULL,NULL,NULL,NULL,'','a',1,'2025-12-08 11:47:52.924119','PAID','a',2990000.00,1),(7,NULL,NULL,NULL,NULL,NULL,'','Hùng',1,'2025-12-09 02:47:07.835975','PAID','0968663020',2990000.00,1),(8,NULL,NULL,NULL,NULL,NULL,'','Hùng',1,'2025-12-09 02:47:49.146488','PAID','0968663020',2990000.00,1),(9,NULL,NULL,NULL,NULL,NULL,'','Hùng',2,'2025-12-09 02:49:07.206661','PAID','0968663020',5980000.00,1),(10,NULL,NULL,NULL,NULL,NULL,'','Hùng',2,'2025-12-09 02:59:41.939191','PAID','0968663020',5980000.00,1),(11,NULL,NULL,NULL,NULL,NULL,'','Hùng',2,'2025-12-09 02:59:45.678268','PAID','0968663020',5980000.00,1),(12,NULL,NULL,NULL,NULL,NULL,'','Hùng',2,'2025-12-09 02:59:47.267077','PAID','0968663020',5980000.00,1),(13,NULL,NULL,NULL,NULL,NULL,'','Hùng',2,'2025-12-09 02:59:52.848426','PAID','0968663020',5980000.00,1),(14,NULL,NULL,NULL,NULL,NULL,'','Hùng',2,'2025-12-09 03:03:36.816634','PAID','0968663020',5980000.00,1),(15,NULL,NULL,NULL,NULL,NULL,'','ga',1,'2025-12-09 03:21:36.997830','PAID','1',2990000.00,1),(16,NULL,NULL,NULL,NULL,NULL,'','ga',1,'2025-12-09 03:33:47.403308','PAID','1',2990000.00,1),(17,NULL,NULL,NULL,NULL,NULL,'','ga',1,'2025-12-09 03:34:04.914489','PAID','1',2990000.00,1),(18,NULL,NULL,NULL,NULL,NULL,'','ga',1,'2025-12-09 03:36:01.881840','PAID','1',2990000.00,1),(19,NULL,NULL,NULL,NULL,NULL,'','ga',1,'2025-12-09 03:39:02.684283','PAID','1',2990000.00,1),(20,NULL,NULL,NULL,NULL,NULL,'','ga',1,'2025-12-09 03:42:10.681442','PAID','1',2990000.00,1),(21,NULL,NULL,NULL,NULL,NULL,'','ga',1,'2025-12-09 03:48:09.489731','PAID','1',2990000.00,1),(22,NULL,NULL,NULL,NULL,NULL,'','ga',1,'2025-12-09 03:48:42.505737','PAID','1',2990000.00,1),(23,NULL,NULL,NULL,NULL,NULL,'','ga',1,'2025-12-09 03:50:11.238194','PAID','1',2990000.00,1),(24,NULL,NULL,NULL,NULL,NULL,'','ga',1,'2025-12-09 03:56:09.930922','PAID','1',2990000.00,1),(25,NULL,NULL,NULL,NULL,NULL,'','ga',1,'2025-12-09 04:00:30.285721','PAID','1',2990000.00,1),(26,NULL,NULL,NULL,NULL,NULL,'','Hùng',1,'2025-12-09 07:50:21.423040','PAID','1',2990000.00,1),(27,NULL,NULL,NULL,NULL,NULL,'','Hùng',1,'2025-12-09 07:53:10.619919','PAID','1',2990000.00,1),(28,NULL,NULL,NULL,NULL,NULL,'','Hùng',1,'2025-12-09 07:54:30.973413','PAID','1',2990000.00,1),(29,NULL,NULL,NULL,NULL,NULL,'vhung2002@gmail.com','H',1,'2025-12-25 03:42:29.902431','PAID','0968663020',3990000.00,2);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `rating` decimal(3,2) DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  `tour_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgce54o0p6uugoc2tev4awewly` (`customer_id`),
  KEY `FKg2ts3ns48cfo72sxpxxjuemjj` (`tour_id`),
  CONSTRAINT `FKg2ts3ns48cfo72sxpxxjuemjj` FOREIGN KEY (`tour_id`) REFERENCES `tours` (`id`),
  CONSTRAINT `FKgce54o0p6uugoc2tev4awewly` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,'Tour như cứt','2025-09-17 16:43:59.254431',4.00,2,1),(2,'a','2026-01-12 04:26:22.141524',5.00,2,2);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(500) DEFAULT NULL,
  `image_url` varchar(300) DEFAULT NULL,
  `price` double NOT NULL,
  `room_type` varchar(100) NOT NULL,
  `hotel_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr1kapilxkn8oyx1gr91g6x8cj` (`hotel_id`),
  CONSTRAINT `FKr1kapilxkn8oyx1gr91g6x8cj` FOREIGN KEY (`hotel_id`) REFERENCES `hotels` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'Phòng tiêu chuẩn 2 giường','https://example.com/standard.jpg',700000,'Standard',NULL),(2,'Phòng cao cấp có ban công','https://example.com/deluxe.jpg',1200000,'Deluxe',NULL),(3,'Phòng tiêu chuẩn 2 giường','https://example.com/standard.jpg',700000,'Standard',2),(4,'Phòng cao cấp có ban công','https://example.com/deluxe.jpg',1200000,'Deluxe',2),(7,'Phòng Loại Sang 1 Giường Lớn (Deluxe Room)','https://pix8.agoda.net/hotelImages/4947690/-1/860733f7993f68ebc7f467e5d9d5fe5c.jpg?ce=0&s=1024x',1663950,'Phòng Loại Sang (Deluxe Room)',4),(8,'Phòng Loại Sang 2 Giường Đơn (Deluxe Room)','https://pix8.agoda.net/hotelImages/4947690/-1/ad8171c9c0390eef1f2dd495ff073440.jpg?ce=0&s=1024x',1663950,'Phòng Loại Sang (Deluxe Room)',4),(9,'Phòng Loại Sang 1 Giường Đơn (Deluxe Room)','https://pix8.agoda.net/hotelImages/4947690/-1/860733f7993f68ebc7f467e5d9d5fe5c.jpg?ce=0&s=1024x',2000000,'Phòng Loại Sang Hướng Biển (Deluxe Room)',4),(10,'Phòng Loại Sang 2 Giường Đơn (Deluxe Room)','https://pix8.agoda.net/hotelImages/4947690/-1/ad8171c9c0390eef1f2dd495ff073440.jpg?ce=0&s=1024x',2000000,'Phòng Loại Sang Hướng Biển (Deluxe Room)',4),(11,'Phòng Loại Sang Hướng Sông 1 Giường Lớn(Deluxe Room)','https://ak-d.tripcdn.com/images/1mc0u12000dtn4dj5B9C7_R_200_100_R5.webp',2000000,'Phòng Loại Sang Hướng Sông (Deluxe Room)',4),(12,'Phòng Sang Trọng Sân Vườn (Có phòng khách, 1 giường lớn, quang cảnh vườn và ban công)','https://cache.marriott.com/is/image/marriotts7prod/mc-dadmr-deluxe-king-garden-01-39858:Wide-Hor?output-quality=70&interpolation=progressive-bilinear&downsize=*:250',3800000,'Phòng Sang Trọng Sân Vườn (Deluxe Garden)',5),(13,'Phòng Sang Trọng Hướng Biển 1 giườn lớn','https://cache.marriott.com/is/image/marriotts7prod/mc-dadmr-deluxe-king-ocean-92773-75550:Wide-Hor?output-quality=70&interpolation=progressive-bilinear&downsize=*:339',3990000,'Phòng Sang Trọng Hướng Biển (Deluce Ocean)',5);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_detail`
--

DROP TABLE IF EXISTS `room_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `room_detail_description` varchar(255) DEFAULT NULL,
  `room_detail_title` varchar(255) DEFAULT NULL,
  `room_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpmgj6lg2cwtdt90qdw076hfej` (`room_id`),
  CONSTRAINT `FKpmgj6lg2cwtdt90qdw076hfej` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_detail`
--

LOCK TABLES `room_detail` WRITE;
/*!40000 ALTER TABLE `room_detail` DISABLE KEYS */;
INSERT INTO `room_detail` VALUES (13,'Tham quan đồi chè, thác Dải Yếm','Ngày 1: Hà Nội - Mộc Châu',3),(14,'Giao lưu văn hoá dân tộc Thái','Ngày 2: Khám phá bản làng',3);
/*!40000 ALTER TABLE `room_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tour_booking_detail`
--

DROP TABLE IF EXISTS `tour_booking_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tour_booking_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `number_of_adult` int DEFAULT NULL,
  `number_of_children` int DEFAULT NULL,
  `booking_id` bigint DEFAULT NULL,
  `tour_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnv6a6wot9h9ig3yipcu4c7huo` (`booking_id`),
  KEY `FKbsgoij44qqrmib9cqxxmfedv3` (`tour_id`),
  CONSTRAINT `FKbsgoij44qqrmib9cqxxmfedv3` FOREIGN KEY (`tour_id`) REFERENCES `tours` (`id`),
  CONSTRAINT `FKnv6a6wot9h9ig3yipcu4c7huo` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tour_booking_detail`
--

LOCK TABLES `tour_booking_detail` WRITE;
/*!40000 ALTER TABLE `tour_booking_detail` DISABLE KEYS */;
INSERT INTO `tour_booking_detail` VALUES (1,1,0,1,1);
/*!40000 ALTER TABLE `tour_booking_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tour_inclusions`
--

DROP TABLE IF EXISTS `tour_inclusions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tour_inclusions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tours_included` int DEFAULT NULL,
  `tours_inclusion_name` varchar(255) DEFAULT NULL,
  `tour_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm6hcyot0tqhm7snoltp3o446r` (`tour_id`),
  CONSTRAINT `FKm6hcyot0tqhm7snoltp3o446r` FOREIGN KEY (`tour_id`) REFERENCES `tours` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tour_inclusions`
--

LOCK TABLES `tour_inclusions` WRITE;
/*!40000 ALTER TABLE `tour_inclusions` DISABLE KEYS */;
INSERT INTO `tour_inclusions` VALUES (13,1,'Xe giường nằm khứ hồi',5),(14,0,'Khách sạn 3 sao',5),(15,1,'Ăn sáng',5),(19,1,'Vé máy bay khứ hồi',7),(20,1,'Khách sạn 4 sao',7),(21,1,'Ăn buffet',7),(22,1,'Xe giường nằm',8),(23,0,'Khách sạn 2 sao',8),(24,1,'Ăn uống theo lịch trình',8),(25,1,'Xe đưa đón',9),(26,0,'Khách sạn 3 sao',9),(27,1,'Ăn đặc sản miền Tây',9),(28,1,'Xe đưa đón',10),(29,1,'Tàu tham quan',10),(30,1,'Khách sạn 4 sao',10),(31,1,'Xe du lịch',11),(32,0,'Khách sạn 2 sao',11),(33,1,'Ăn uống theo chương trình',11),(43,0,'Vé máy bay khứ hồi',1),(44,1,'Ăn uống theo chương trình',1),(45,1,'Khách sạn 5 sao chuẩn quốc tế',1),(46,1,'Khách sạn 5 sao',16),(47,0,'Vé vui chơi',16),(51,1,'Vé máy bay khứ hồi',2),(52,0,'Khách sạn 3 sao',2),(53,1,'Ăn hải sản',2),(54,1,'Vé máy bay khứ hồi',3),(55,1,'Khách sạn 4 sao',3),(56,0,'Ăn buffet sáng',3),(57,1,'Xe đưa đón',4),(58,0,'Khách sạn 3 sao',4),(59,1,'Ăn đặc sản',4),(60,1,'Vé máy bay khứ hồi',6),(61,1,'Khách sạn 4 sao',6),(62,1,'Ăn hải sản',6);
/*!40000 ALTER TABLE `tour_inclusions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tours`
--

DROP TABLE IF EXISTS `tours`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tours` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `average_rating` decimal(38,2) DEFAULT NULL,
  `departures` varchar(300) NOT NULL,
  `description` varchar(300) NOT NULL,
  `destination` varchar(300) NOT NULL,
  `end_date` date DEFAULT NULL,
  `image_url` varchar(300) NOT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `review_count` int DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `tour_name` varchar(300) NOT NULL,
  `transportation` varchar(300) NOT NULL,
  `created_by_id` bigint DEFAULT NULL,
  `view_count` bigint DEFAULT NULL,
  `season` enum('AUTUMN','SPRING','SUMMER','WINTER') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKrcar42uv4mtlkxim53kjua0t1` (`tour_name`),
  KEY `FKsvotu4pe04agef1kh7dmi7vc9` (`created_by_id`),
  CONSTRAINT `FKsvotu4pe04agef1kh7dmi7vc9` FOREIGN KEY (`created_by_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tours`
--

LOCK TABLES `tours` WRITE;
/*!40000 ALTER TABLE `tours` DISABLE KEYS */;
INSERT INTO `tours` VALUES (1,4.00,'Hà Nội','Khám phá Đà Lạt thơ mộng','Đà Lạt','2025-09-23','https://hoanghamobile.com/tin-tuc/wp-content/uploads/2024/07/anh-da-lat.jpg',2990000.00,1,'2025-09-20','Tour Đà Lạt 3N2Đ','Máy bay + Xe',1,87,'WINTER'),(2,5.00,'TP.HCM','Khám phá thiên đường biển đảo','Phú Quốc','2026-01-23','https://media.gettyimages.com/id/1453456835/photo/paradise-beach-b%C3%A3i-t%E1%BA%AFm-sao-beach-phu-quoc-vietnam.jpg?s=612x612&w=0&k=20&c=Buaw3T5Sd5u_18KmnoK2n5wBKwD2luehgYuzgtCCjcg=',3990000.00,1,'2026-01-13','Tour Phú Quốc 4N3Đ','Máy bay + Xe ô tô',1,9,'SPRING'),(3,0.00,'Hà Nội','Hành trình di sản miền Trung','Huế','2025-09-17','https://images.unsplash.com/photo-1705823637026-92c0ef6d6222?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8SHUlRTElQkElQkZ8ZW58MHx8MHx8fDA%3D',2590000.00,0,'2025-09-15','Tour Huế 3N2Đ','Máy bay + Xe',1,3,'WINTER'),(4,0.00,'Đà Nẵng','Phố cổ lung linh về đêm','Hội An','2025-11-02','https://plus.unsplash.com/premium_photo-1690960644375-6f2399a08ebc?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8SCVFMSVCQiU5OWklMjBBbnxlbnwwfHwwfHx8MA%3D%3D',1990000.00,0,'2025-11-01','Tour Hội An 2N1Đ','Xe',1,1,'WINTER'),(5,0.00,'Hà Nội','Trải nghiệm núi rừng Tây Bắc','Sapa','2025-12-22','https://images.unsplash.com/photo-1570366583862-f91883984fde?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8U2ElMjBwYXxlbnwwfHwwfHx8MA%3D%3D',2890000.00,0,'2025-12-20','Tour Sapa 3N2Đ','Xe giường nằm',1,0,'AUTUMN'),(6,0.00,'TP.HCM','Biển xanh cát trắng nắng vàng','Nha Trang','2025-10-13','https://images.unsplash.com/photo-1687025846473-9bd391575faa?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8bmhhJTIwdHJhbmclMjBiZWFjaHxlbnwwfHwwfHx8MA%3D%3D',3590000.00,0,'2025-10-10','Tour Nha Trang 4N3Đ','Máy bay + Xe',1,1,'SPRING'),(7,0.00,'Hà Nội','Khám phá thành phố biển đáng sống','Đà Nẵng','2025-10-17','https://images.unsplash.com/photo-1559592413-7cec4d0cae2b?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8JUM0JTkxJUMzJUEwJTIwbiVFMSVCQSVCNW5nfGVufDB8fDB8fHww',3190000.00,0,'2025-10-15','Tour Đà Nẵng 3N2Đ','Máy bay + Xe',1,0,'AUTUMN'),(8,0.00,'Hà Nội','Vùng cao nguyên đá hùng vĩ','Hà Giang','2025-11-08','https://plus.unsplash.com/premium_photo-1692731797466-298784587496?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8SCVDMyVBMCUyMEdpYW5nfGVufDB8fDB8fHww',3490000.00,0,'2025-11-05','Tour Hà Giang 4N3Đ','Xe du lịch',1,0,'AUTUMN'),(9,0.00,'TP.HCM','Trải nghiệm chợ nổi miền Tây','Cần Thơ','2025-09-26','https://images.unsplash.com/photo-1683617638309-b1fc59c8c7b3?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8QyVFMSVCQSVBN24lMjBUaCVDNiVBMXxlbnwwfHwwfHx8MA%3D%3D',1790000.00,0,'2025-09-25','Tour Cần Thơ 2N1Đ','Xe',1,0,'AUTUMN'),(10,0.00,'Hà Nội','Kỳ quan thiên nhiên thế giới','Hạ Long','2025-10-22','https://images.unsplash.com/photo-1643029891412-92f9a81a8c16?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8SCVFMSVCQSVBMSUyMExvbmd8ZW58MHx8MHx8fDA%3D',3290000.00,0,'2025-10-20','Tour Hạ Long 3N2Đ','Xe + Tàu',1,0,'AUTUMN'),(11,0.00,'Hà Nội','Thảo nguyên xanh mát','Mộc Châu','2025-10-01','https://media.istockphoto.com/id/2207814107/photo/green-tea-field-in-moc-chau-vietnam-a-view-from-the-buckwheats-flower-field.webp?a=1&b=1&s=612x612&w=0&k=20&c=oGC0aLwRqATtbPNgINYzxgrxgT16I_JBEPhoYfFdjec=',1990000.00,0,'2025-09-30','Tour Mộc Châu 2N1Đ','Xe',1,0,'AUTUMN'),(16,0.00,'Hà Nội','Tour biển hè','Cửa Lò','2025-10-13','https://hoanghamobile.com/tin-tuc/wp-content/uploads/2024/07/anh-da-lat.jpg',1999000.00,0,'2025-10-11','Tour Biển Cửa Lò','Xe ô tô',NULL,0,'AUTUMN');
/*!40000 ALTER TABLE `tours` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tours_detail`
--

DROP TABLE IF EXISTS `tours_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tours_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `additional_info` text,
  `booking_guide` text,
  `cancellation_policy` text,
  `children_policy` text,
  `overview` text,
  `payment` text,
  `terms_notes` text,
  `tour_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKrdyt389u1jxa3djhb6162wxl0` (`tour_id`),
  CONSTRAINT `FKohmy8vesdk6ojbsaa2ivu8lcw` FOREIGN KEY (`tour_id`) REFERENCES `tours` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tours_detail`
--

LOCK TABLES `tours_detail` WRITE;
/*!40000 ALTER TABLE `tours_detail` DISABLE KEYS */;
INSERT INTO `tours_detail` VALUES (1,'Liên hệ hotline nếu cần hỗ trợ','Đặt cọc 50% trước khi khởi hành','Hủy trước 7 ngày hoàn 80%','Trẻ em dưới 5 tuổi miễn phí','Hành trình tham quan thành phố ngàn hoa','Thanh toán tiền mặt hoặc chuyển khoản','Mang theo giấy tờ tùy thân',1),(2,'Có xe đưa đón tại sân bay','Đặt cọc 40% trước khi khởi hành','Hủy trước 10 ngày hoàn 90%','Trẻ em dưới 6 tuổi miễn phí','Tận hưởng kỳ nghỉ tại đảo ngọc','Thanh toán chuyển khoản','Mang theo đồ bơi và kem chống nắng',2),(3,'Có hướng dẫn viên thuyết minh','Thanh toán đủ trước ngày đi 5 ngày','Hủy trước 7 ngày hoàn 70%','Trẻ em dưới 5 tuổi miễn phí','Tham quan cố đô Huế và các di tích','Tiền mặt hoặc thẻ','Mang theo giấy tờ tùy thân',3),(4,'Có thuyền thả hoa đăng','Đặt cọc 30%','Hủy trước 5 ngày hoàn 50%','Trẻ em dưới 4 tuổi miễn phí','Khám phá phố cổ Hội An','Thanh toán online','Mang theo mũ nón',4),(5,'Có tour trekking','Đặt cọc 50%','Hủy trước 3 ngày không hoàn','Trẻ em dưới 6 tuổi giảm 50%','Tham quan Fansipan và bản Cát Cát','Thanh toán tiền mặt','Mang áo ấm',5),(6,'Có xe đưa đón miễn phí','Đặt cọc 40%','Hủy trước 7 ngày hoàn 60%','Trẻ em dưới 5 tuổi miễn phí','Khám phá Vinpearl Land và Hòn Mun','Thanh toán online','Chuẩn bị đồ bơi',6),(7,'Có xe đưa đón sân bay','Đặt cọc 30%','Hủy trước 5 ngày hoàn 70%','Trẻ em dưới 6 tuổi miễn phí','Tham quan Bà Nà Hills, Cầu Vàng','Tiền mặt hoặc chuyển khoản','Mang theo kem chống nắng',7),(8,'Có tour xe máy cho khách trẻ','Đặt cọc 50%','Hủy trước 7 ngày hoàn 80%','Trẻ em dưới 5 tuổi miễn phí','Khám phá Đồng Văn, Mã Pí Lèng','Tiền mặt','Mang theo áo khoác ấm',8),(9,'Có chương trình giao lưu đờn ca tài tử','Thanh toán trước 100%','Hủy trước 3 ngày hoàn 50%','Trẻ em dưới 5 tuổi miễn phí','Tham quan chợ nổi Cái Răng, vườn trái cây','Tiền mặt hoặc online','Mang theo mũ, nón',9),(10,'Có trải nghiệm chèo kayak','Đặt cọc 40%','Hủy trước 7 ngày hoàn 75%','Trẻ em dưới 6 tuổi miễn phí','Tham quan Vịnh Hạ Long, hang Sửng Sốt','Tiền mặt hoặc chuyển khoản','Chuẩn bị đồ bơi',10),(11,'Có chụp ảnh check-in miễn phí','Đặt cọc 30%','Hủy trước 3 ngày hoàn 60%','Trẻ em dưới 5 tuổi miễn phí','Tham quan đồi chè, thác Dải Yếm','Tiền mặt','Mang theo áo khoác nhẹ',11),(14,'đa','bla','bla','Trẻ em dưới 1m3 được miễn phí','Tour biển hè','','đâ',16);
/*!40000 ALTER TABLE `tours_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tours_itinerary`
--

DROP TABLE IF EXISTS `tours_itinerary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tours_itinerary` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tours_itinerary_description` text,
  `tours_itinerary_title` varchar(255) DEFAULT NULL,
  `tour_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqwvofl9qj5k8aeoknrny6012j` (`tour_id`),
  CONSTRAINT `FKqwvofl9qj5k8aeoknrny6012j` FOREIGN KEY (`tour_id`) REFERENCES `tours` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tours_itinerary`
--

LOCK TABLES `tours_itinerary` WRITE;
/*!40000 ALTER TABLE `tours_itinerary` DISABLE KEYS */;
INSERT INTO `tours_itinerary` VALUES (8,'Xe giường nằm đến Sapa','Ngày 1: Hà Nội - Sapa',5),(9,'Đi cáp treo, tham quan đỉnh Fansipan','Ngày 2: Khám phá Fansipan',5),(12,'Đến Đà Nẵng, tham quan Cầu Rồng','Ngày 1: Hà Nội - Đà Nẵng',7),(13,'Đi cáp treo, tham quan Cầu Vàng','Ngày 2: Bà Nà Hills',7),(14,'Di chuyển bằng xe, nghỉ đêm tại TP Hà Giang','Ngày 1: Hà Nội - Hà Giang',8),(15,'Tham quan Đồng Văn, đèo Mã Pí Lèng','Ngày 2: Cao nguyên đá',8),(16,'Khám phá vườn trái cây, thưởng thức đặc sản','Ngày 1: TP.HCM - Cần Thơ',9),(17,'Đi thuyền tham quan chợ nổi','Ngày 2: Chợ nổi Cái Răng',9),(18,'Đi xe đến Hạ Long, tham quan Sun World','Ngày 1: Hà Nội - Hạ Long',10),(19,'Đi tàu thăm Vịnh, tham quan hang Sửng Sốt','Ngày 2: Du ngoạn Vịnh',10),(20,'Tham quan đồi chè, thác Dải Yếm','Ngày 1: Hà Nội - Mộc Châu',11),(21,'Giao lưu văn hoá dân tộc Thái','Ngày 2: Khám phá bản làng',11),(28,'Bay từ Hà Nội đến Đà Lạt, tham quan quảng trường Lâm Viên','Ngày 1: Hà Nội - Đà Lạt',1),(29,'Tham quan thác Datanla, Thiền viện Trúc Lâm','Ngày 2: Khám phá ngoại ô',1),(30,'bơi','Ngày 1 qua cửa lò',16),(33,'Bay đến Phú Quốc, tham quan chợ đêm','Ngày 1: TP.HCM - Phú Quốc',2),(34,'Tham quan Hòn Thơm, lặn ngắm san hô','Ngày 2: Khám phá biển đảo',2),(35,'Bay đến Huế, tham quan Kinh thành','Ngày 1: Hà Nội - Huế',3),(36,'Lăng Minh Mạng, Lăng Tự Đức','Ngày 2: Tham quan lăng tẩm',3),(37,'Tham quan phố cổ, ăn cao lầu','Ngày 1: Đà Nẵng - Hội An',4),(38,'Đến Nha Trang, tắm biển Trần Phú','Ngày 1: TP.HCM - Nha Trang',6),(39,'Đi cano ra đảo Hòn Mun','Ngày 2: Khám phá đảo',6);
/*!40000 ALTER TABLE `tours_itinerary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tours_seasons`
--

DROP TABLE IF EXISTS `tours_seasons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tours_seasons` (
  `tour_id` bigint NOT NULL,
  `season` enum('AUTUMN','SPRING','SUMMER','WINTER') DEFAULT NULL,
  KEY `FK8fcs7d23q9hojnaub4jg321qw` (`tour_id`),
  CONSTRAINT `FK8fcs7d23q9hojnaub4jg321qw` FOREIGN KEY (`tour_id`) REFERENCES `tours` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tours_seasons`
--

LOCK TABLES `tours_seasons` WRITE;
/*!40000 ALTER TABLE `tours_seasons` DISABLE KEYS */;
/*!40000 ALTER TABLE `tours_seasons` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-19  2:53:49
