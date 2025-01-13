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
  `col` int NOT NULL,
  `row` varchar(5) NOT NULL,
  `session_id` int NOT NULL,
  PRIMARY KEY (`seat_id`),
  KEY `seats_ibfk_1` (`session_id`),
  CONSTRAINT `seats_ibfk_1` FOREIGN KEY (`session_id`) REFERENCES `sessions` (`session_id`)
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seats`
--

LOCK TABLES `seats` WRITE;
/*!40000 ALTER TABLE `seats` DISABLE KEYS */;
INSERT INTO `seats` VALUES (82,1,1,1,'a',1),(83,1,1,2,'a',1),(84,1,1,3,'a',1),(85,0,1,4,'a',1),(86,0,1,5,'a',1),(87,1,1,6,'a',1),(88,1,1,7,'a',1),(89,1,1,8,'a',1),(90,0,1,1,'b',1),(91,0,1,2,'b',1),(92,0,1,3,'b',1),(93,1,1,4,'b',1),(94,1,1,5,'b',1),(95,0,1,6,'b',1),(96,0,1,7,'b',1),(97,0,1,8,'b',1),(98,1,2,1,'a',2),(99,1,2,2,'a',2),(100,1,2,3,'a',2),(101,0,2,4,'a',2),(102,0,2,5,'a',2),(103,0,2,6,'a',2),(104,1,2,7,'a',2),(105,0,2,8,'a',2),(106,1,2,1,'b',2),(107,0,2,2,'b',2),(108,1,2,3,'b',2),(109,0,2,4,'b',2),(110,1,2,5,'b',2),(111,0,2,6,'b',2),(112,0,2,7,'b',2),(113,0,2,8,'b',2),(114,0,2,1,'b',2),(115,0,2,2,'b',2),(116,0,2,3,'b',2),(117,0,2,4,'b',2),(118,0,2,5,'b',2),(119,0,2,6,'b',2),(120,0,2,7,'b',2),(121,0,2,8,'b',2),(122,0,2,1,'b',2),(123,0,2,2,'b',2),(124,0,2,3,'b',2),(125,0,2,4,'b',2),(126,0,2,5,'b',2),(127,0,2,6,'b',2),(128,0,2,7,'b',2),(129,0,2,8,'b',2),(130,0,2,1,'b',2),(131,0,2,2,'b',2),(132,0,2,3,'b',2),(133,0,2,4,'b',2),(134,0,2,5,'b',2),(135,0,2,6,'b',2),(136,0,2,7,'b',2),(137,0,2,8,'b',2),(138,0,2,1,'b',2),(139,0,2,2,'b',2),(140,0,2,3,'b',2),(141,0,2,4,'b',2),(142,0,2,5,'b',2),(143,0,2,6,'b',2),(144,0,2,7,'b',2),(145,0,2,8,'b',2),(146,0,1,1,'a',3),(147,0,1,2,'a',3),(148,0,1,3,'a',3),(149,0,1,4,'a',3),(150,0,1,5,'a',3),(151,0,1,6,'a',3),(152,0,1,7,'a',3),(153,0,1,8,'a',3),(154,0,1,1,'b',3),(155,0,1,2,'b',3),(156,0,1,3,'b',3),(157,0,1,4,'b',3),(158,0,1,5,'b',3),(159,0,1,6,'b',3),(160,0,1,7,'b',3),(161,0,1,8,'b',3);
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

-- Dump completed on 2025-01-13 13:02:37
