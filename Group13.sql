-- MySQL dump 10.13  Distrib 9.1.0, for Win64 (x86_64)
--
-- Host: localhost    Database: Group13
-- ------------------------------------------------------
-- Server version	9.1.0

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
-- Table structure for table `admin_movies`
--

DROP TABLE IF EXISTS `admin_movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_movies` (
  `movie_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `genre` varchar(50) DEFAULT NULL,
  `summary` text,
  `img_path` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`movie_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_movies`
--

LOCK TABLES `admin_movies` WRITE;
/*!40000 ALTER TABLE `admin_movies` DISABLE KEYS */;
INSERT INTO `admin_movies` VALUES (1,'Pirates of the Caribbean 1','Adventure','A thrilling pirate adventure.','piratesOfTheCaribbean 1 poster.webp'),(2,'Pirates of the Caribbean 2','Adventure','Jack Sparrow\'s journey continues.','piratesOfTheCaribbean 2 poster.webp'),(3,'Pirates of the Caribbean 3','Adventure','The search for the fountain of youth.','piratesOfTheCaribbean 3 poster.webp'),(4,'Pirates of the Caribbean 4','Adventure','A new pirate legend begins.','piratesOfTheCaribbean 4 poster.webp'),(5,'Pirates of the Caribbean 5','Adventure','A race against time and curses.','piratesOfTheCaribbean 5 poster.webp'),(6,'Cars','Animation','A story about a race car finding his true calling.','cars poster.webp'),(7,'Cars2','Animation','An action-packed global racing adventure.','cars 2 poster.webp'),(8,'ToyStory','Animation','Toys come to life in this heartwarming tale.','toyStory poster.webp'),(9,'ToyStory2','Animation','The beloved toys return for a new journey.','toyStory2 poster.webp'),(10,'Aladdin','Fantasy','A magical tale of a street rat and a genie.','aladdin.webp'),(11,'Test Movie','Drama','A very interesting movie.','images/testMovie.webp');
/*!40000 ALTER TABLE `admin_movies` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-29  1:06:52
