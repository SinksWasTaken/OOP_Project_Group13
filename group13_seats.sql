-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: group13
-- ------------------------------------------------------
-- Server version	9.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `seats`
--

DROP TABLE IF EXISTS `seats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seats` (
  `seat_id` int NOT NULL AUTO_INCREMENT,
  `sold` tinyint(1) NOT NULL,
  `hall` int NOT NULL,
  `seat_name` varchar(5) NOT NULL,
  `session_id` int NOT NULL,
  PRIMARY KEY (`seat_id`),
  KEY `seats_ibfk_1` (`session_id`),
  CONSTRAINT `seats_ibfk_1` FOREIGN KEY (`session_id`) REFERENCES `sessions` (`session_id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seats`
--

LOCK TABLES `seats` WRITE;
/*!40000 ALTER TABLE `seats` DISABLE KEYS */;
INSERT INTO `seats` VALUES (1,0,1,'1a',1),(2,0,1,'1b',1),(3,0,1,'1c',1),(4,0,1,'1d',1),(5,0,1,'2a',1),(6,0,1,'2b',1),(7,0,1,'2c',1),(8,0,1,'2d',1),(9,0,1,'3a',1),(10,0,1,'3b',1),(11,0,1,'3c',1),(12,0,1,'3d',1),(13,0,1,'4a',1),(14,0,1,'4b',1),(15,0,1,'4c',1),(16,0,1,'4d',1),(18,0,2,'1',1),(19,0,2,'1',1),(20,0,2,'1',1),(21,0,2,'1',1),(22,0,2,'2',1),(23,0,2,'2',1),(24,0,2,'2',1),(25,0,2,'2',1),(26,0,2,'3',1),(27,0,2,'3',1),(28,0,2,'3',1),(29,0,2,'3',1),(30,0,2,'4',1),(31,0,2,'4',1),(32,0,2,'4',1),(33,0,2,'4',1),(34,0,2,'5',1),(35,0,2,'5',1),(36,0,2,'5',1),(37,0,2,'5',1),(38,0,2,'6',1),(39,0,2,'6',1),(40,0,2,'6',1),(41,0,2,'6',1),(42,0,2,'7',1),(43,0,2,'7',1),(44,0,2,'7',1),(45,0,2,'7',1),(46,0,2,'8',1),(47,0,2,'8',1),(48,0,2,'8',1),(49,0,2,'8',1),(50,0,2,'9',1),(51,0,2,'9',1),(52,0,2,'9',1),(53,0,2,'9',1),(54,0,2,'10',1),(55,0,2,'10',1),(56,0,2,'10',1),(57,0,2,'10',1),(58,0,2,'11',1),(59,0,2,'11',1),(60,0,2,'11',1),(61,0,2,'11',1),(62,0,2,'12',1),(63,0,2,'12',1),(64,0,2,'12',1),(65,0,2,'12',1),(66,0,2,'13',1),(67,0,2,'13',1),(68,0,2,'13',1),(69,0,2,'13',1),(70,0,2,'14',1),(71,0,2,'14',1),(72,0,2,'14',1),(73,0,2,'14',1),(74,0,2,'15',1),(75,0,2,'15',1),(76,0,2,'15',1),(77,0,2,'15',1),(78,0,2,'16',1),(79,0,2,'16',1),(80,0,2,'16',1),(81,0,2,'16',1);
/*!40000 ALTER TABLE `seats` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-12  0:54:25
