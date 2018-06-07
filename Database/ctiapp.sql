# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.5.42)
# Database: ctiapp
# Generation Time: 2016-11-02 15:12:47 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table Assignments
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Assignments`;

CREATE TABLE `Assignments` (
  `assignment_id` varchar(45) NOT NULL,
  `module_id` varchar(45) DEFAULT NULL,
  `assignment_number` int(11) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  PRIMARY KEY (`assignment_id`),
  KEY `fk_Assignments_Modules1_idx` (`module_id`),
  CONSTRAINT `fk_Assignments_Modules1` FOREIGN KEY (`module_id`) REFERENCES `Modules` (`module_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Assignments` WRITE;
/*!40000 ALTER TABLE `Assignments` DISABLE KEYS */;

INSERT INTO `Assignments` (`assignment_id`, `module_id`, `assignment_number`, `start_date`, `end_date`)
VALUES
	('ASGN01','C_ITDS221',1,'2016-07-28','2016-11-15'),
	('ASGN02','C_ITII320',1,'2016-06-16','2016-11-04'),
	('ASGN03','C_ITII320',2,'2016-07-15','2016-11-30'),
	('ASGN04','C_ITIP221',1,'2016-07-28','2016-11-15'),
	('ASGN05','C_CORM321',1,'2016-07-15','2016-11-29'),
	('ASGN06','C_ITOS321',1,'2016-08-10','2016-11-16');

/*!40000 ALTER TABLE `Assignments` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Attendance_List
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Attendance_List`;

CREATE TABLE `Attendance_List` (
  `att_list_id` int(45) NOT NULL AUTO_INCREMENT,
  `module_id` varchar(45) DEFAULT NULL,
  `student_id` varchar(45) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`att_list_id`),
  KEY `fk_Attendace_List_Modules1_idx` (`module_id`),
  KEY `fk_Attendace_List_Students1_idx` (`student_id`),
  CONSTRAINT `fk_Attendace_List_Modules1` FOREIGN KEY (`module_id`) REFERENCES `modules` (`module_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Attendace_List_Students1` FOREIGN KEY (`student_id`) REFERENCES `Students` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;

LOCK TABLES `Attendance_List` WRITE;
/*!40000 ALTER TABLE `Attendance_List` DISABLE KEYS */;

INSERT INTO `Attendance_List` (`att_list_id`, `module_id`, `student_id`, `date`)
VALUES
	(1,'C_ITDS221','MB2015-0615','2016-10-10'),
	(2,'C_ITSP200','MB2015-0615','2016-10-11'),
	(3,'C_ITDS221','MB2014-0603','2016-10-10'),
	(18,'C_ITEC301','MB2014-0203','2016-10-11'),
	(19,'C_ITEC301','MB2015-0615','2016-10-11'),
	(25,'C_ITDS221','MB2014-0203','2016-10-10'),
	(26,'C_ITJA321','MB2015-0615','2016-10-19'),
	(27,'C_ITSP300','MB2015-0615','2016-10-20'),
	(28,'C_ITJA321','MB2014-0603','2016-10-20'),
	(30,'C_ITJA321','MB2015-0615','2016-10-20'),
	(31,'C_ITEC301','MB2015-0615','2016-11-01'),
	(32,'C_ITSP200','MB2015-0615','2016-11-01');

/*!40000 ALTER TABLE `Attendance_List` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Class_Sessions
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Class_Sessions`;

CREATE TABLE `Class_Sessions` (
  `session_id` int(11) NOT NULL AUTO_INCREMENT,
  `start_time` time DEFAULT '00:00:00',
  `end_time` time DEFAULT '00:00:00',
  `period_no` int(11) DEFAULT NULL,
  `day` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`session_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;

LOCK TABLES `Class_Sessions` WRITE;
/*!40000 ALTER TABLE `Class_Sessions` DISABLE KEYS */;

INSERT INTO `Class_Sessions` (`session_id`, `start_time`, `end_time`, `period_no`, `day`)
VALUES
	(1,'08:00:00','09:00:00',1,'Monday'),
	(2,'09:00:00','10:00:00',2,'Monday'),
	(3,'10:00:00','11:00:00',3,'Monday'),
	(4,'11:00:00','12:00:00',4,'Monday'),
	(5,'12:30:00','13:30:00',5,'Monday'),
	(6,'13:30:00','14:30:00',6,'Monday'),
	(7,'14:30:00','15:30:00',7,'Monday'),
	(8,'15:30:00','16:30:00',8,'Monday'),
	(9,'08:00:00','09:00:00',1,'Tuesday'),
	(10,'09:00:00','10:00:00',2,'Tuesday'),
	(11,'10:00:00','11:00:00',3,'Tuesday'),
	(12,'11:00:00','12:00:00',4,'Tuesday'),
	(13,'12:30:00','13:30:00',5,'Tuesday'),
	(14,'13:30:00','14:30:00',6,'Tuesday'),
	(15,'14:30:00','15:30:00',7,'Tuesday'),
	(16,'15:30:00','16:30:00',8,'Tuesday'),
	(17,'08:00:00','09:00:00',1,'Wednesday'),
	(18,'09:00:00','10:00:00',2,'Wednesday'),
	(19,'10:00:00','11:00:00',3,'Wednesday'),
	(20,'11:00:00','12:00:00',4,'Wednesday'),
	(21,'12:30:00','13:30:00',5,'Wednesday'),
	(22,'13:30:00','14:30:00',6,'Wednesday'),
	(23,'14:30:00','15:30:00',7,'Wednesday'),
	(24,'15:30:00','16:30:00',8,'Wednesday'),
	(25,'08:00:00','09:00:00',1,'Thursday'),
	(26,'09:00:00','10:00:00',2,'Thursday'),
	(27,'10:00:00','11:00:00',3,'Thursday'),
	(28,'11:00:00','12:00:00',4,'Thursday'),
	(29,'12:30:00','13:30:00',5,'Thursday'),
	(30,'13:30:00','14:30:00',6,'Thursday'),
	(31,'14:30:00','15:30:00',7,'Thursday'),
	(32,'15:30:00','16:30:00',8,'Thursday'),
	(33,'08:00:00','09:00:00',1,'Friday'),
	(34,'09:00:00','10:00:00',2,'Friday'),
	(35,'10:00:00','11:00:00',3,'Friday'),
	(36,'11:00:00','12:00:00',4,'Friday'),
	(37,'12:30:00','13:30:00',5,'Friday'),
	(38,'13:30:00','14:30:00',6,'Friday'),
	(39,'14:30:00','15:30:00',7,'Friday'),
	(40,'15:30:00','16:30:00',8,'Friday');

/*!40000 ALTER TABLE `Class_Sessions` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Class_Timetable
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Class_Timetable`;

CREATE TABLE `Class_Timetable` (
  `c_timetable_id` int(11) NOT NULL AUTO_INCREMENT,
  `module_id` varchar(45) DEFAULT NULL,
  `room_no` varchar(45) DEFAULT NULL,
  `session_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`c_timetable_id`),
  KEY `fk_Class_Timetable_Modules1_idx` (`module_id`),
  KEY `fk_Class_Timetable_Class_Sessions1_idx` (`session_id`),
  CONSTRAINT `fk_Class_Timetable_Class_Sessions1` FOREIGN KEY (`session_id`) REFERENCES `Class_Sessions` (`session_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Class_Timetable_Modules1` FOREIGN KEY (`module_id`) REFERENCES `modules` (`module_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;

LOCK TABLES `Class_Timetable` WRITE;
/*!40000 ALTER TABLE `Class_Timetable` DISABLE KEYS */;

INSERT INTO `Class_Timetable` (`c_timetable_id`, `module_id`, `room_no`, `session_id`)
VALUES
	(1,'C_ITDS221','PC2',3),
	(2,'C_ITDS221','PC2',4),
	(3,'C_ITCO221','PC1',7),
	(4,'C_ITCO221','PC1',8),
	(5,'C_ITIP221','2H',11),
	(6,'C_ITIP221','2H',12),
	(7,'C_ITSP200','PC1',13),
	(8,'C_ITSP200','PC1',14),
	(9,'C_ITDB221','2B',17),
	(10,'C_ITDB221','2B',18),
	(11,'C_ITIP221','2H',19),
	(12,'C_ITIP221','2H',20),
	(13,'C_ITDS221','2F',21),
	(14,'C_ITDS221','2F',22),
	(15,'C_ITDB221','PC2',27),
	(16,'C_ITDB221','PC2',28),
	(17,'C_ITSP200','PC2',29),
	(18,'C_ITSP200','PC2',30),
	(19,'C_ITCO221','PC1',31),
	(20,'C_ITCO221','PC1',32),
	(21,'C_ITOS321','2B',1),
	(22,'C_ITOS321','2B',2),
	(23,'C_ITEC301','PC1',3),
	(24,'C_ITEC301','PC1',4),
	(25,'C_ITII320','2A',5),
	(26,'C_ITII320','2A',6),
	(27,'C_ITOS321','PC1',9),
	(28,'C_ITOS321','PC1',10),
	(29,'C_ITEC301','PC1',11),
	(30,'C_ITEC301','PC1',12),
	(31,'C_ITJA321','PC1',17),
	(32,'C_ITJA321','PC1',18),
	(33,'C_ITII320','2B',19),
	(34,'C_ITII320','2B',20),
	(35,'C_ITSP300','PC1',21),
	(36,'C_ITSP300','PC1',22),
	(37,'C_ITSP300','2B',25),
	(38,'C_ITSP300','2B',26),
	(39,'C_ITJA321','PC1',27),
	(40,'C_ITJA321','PC1',28);

/*!40000 ALTER TABLE `Class_Timetable` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Departments
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Departments`;

CREATE TABLE `Departments` (
  `department_id` varchar(45) NOT NULL,
  `department_name` varchar(45) DEFAULT NULL,
  `coordinator` varchar(45) DEFAULT NULL,
  `department_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Departments` WRITE;
/*!40000 ALTER TABLE `Departments` DISABLE KEYS */;

INSERT INTO `Departments` (`department_id`, `department_name`, `coordinator`, `department_type`)
VALUES
	('DEPT01','SRC','Tom Johnson','Administration'),
	('DEPT02','Program Manager','Ioline Redfern','Academics'),
	('DEPT03','Football Team','John Smith','Sports'),
	('DEPT04','Library','Belinda Peters','Academics');

/*!40000 ALTER TABLE `Departments` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Event_Responses
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Event_Responses`;

CREATE TABLE `Event_Responses` (
  `response_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` varchar(45) DEFAULT NULL,
  `student_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`response_id`),
  KEY `fk_Event_Responses_Events1_idx` (`event_id`),
  KEY `fk_Event_Responses_Students1_idx` (`student_id`),
  CONSTRAINT `fk_Event_Responses_Events1` FOREIGN KEY (`event_id`) REFERENCES `Events` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Event_Responses_Students1` FOREIGN KEY (`student_id`) REFERENCES `Students` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

LOCK TABLES `Event_Responses` WRITE;
/*!40000 ALTER TABLE `Event_Responses` DISABLE KEYS */;

INSERT INTO `Event_Responses` (`response_id`, `event_id`, `student_id`)
VALUES
	(6,'EVT2','MB2015-0615'),
	(9,'EVT3','MB2015-0615'),
	(10,'EVT2','MB2014-0603');

/*!40000 ALTER TABLE `Event_Responses` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Events
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Events`;

CREATE TABLE `Events` (
  `event_id` varchar(45) NOT NULL,
  `event_name` varchar(45) DEFAULT NULL,
  `department_id` varchar(45) DEFAULT NULL,
  `venue` varchar(45) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL,
  `details` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `fk_Events_Departments1_idx` (`department_id`),
  CONSTRAINT `fk_Events_Departments1` FOREIGN KEY (`department_id`) REFERENCES `Departments` (`department_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Events` WRITE;
/*!40000 ALTER TABLE `Events` DISABLE KEYS */;

INSERT INTO `Events` (`event_id`, `event_name`, `department_id`, `venue`, `date`, `time`, `details`)
VALUES
	('EVT1','Halloween Party','DEPT01','Scotty\'s Pub','2016-10-29','20:00:00','Halloween party at Scotty\'s Pub. All students are encouraged to attend. Ticket: R50.'),
	('EVT2','Exams Meeting','DEPT02','Room 2A','2016-10-05','08:00:00','Important meeting regarding exam rooms.'),
	('EVT3','Football Team Meeting','DEPT03','Recreation Room (3rd Floor)','2016-09-18','12:00:00','Team selection for next year.'),
	('EVT4','Referencing Workshop','DEPT04','Room1A','2016-09-21','09:00:00','Referencing Workshop for Harvard Referencing.');

/*!40000 ALTER TABLE `Events` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Exam_Timetable
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Exam_Timetable`;

CREATE TABLE `Exam_Timetable` (
  `exam_id` int(11) NOT NULL,
  `module_id` varchar(45) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  `venue` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`exam_id`),
  KEY `fk_Exam_Timetable_Modules1_idx` (`module_id`),
  CONSTRAINT `fk_Exam_Timetable_Modules1` FOREIGN KEY (`module_id`) REFERENCES `modules` (`module_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Exam_Timetable` WRITE;
/*!40000 ALTER TABLE `Exam_Timetable` DISABLE KEYS */;

INSERT INTO `Exam_Timetable` (`exam_id`, `module_id`, `date`, `start_time`, `end_time`, `venue`)
VALUES
	(1,'C_ITDS221','2016-11-22','09:00:00','12:00:00','Room 1A'),
	(2,'C_ITDB221','2016-11-22','12:00:00','15:00:00','Room 1A'),
	(3,'C_ITCO221','2016-11-23','09:00:00','12:00:00','Room 2A'),
	(4,'C_ITIP221','2016-11-24','12:00:00','15:00:00','Room 2A'),
	(5,'C_COMA101','2016-11-22','09:00:00','12:00:00','Room 1C');

/*!40000 ALTER TABLE `Exam_Timetable` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Lecturers
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Lecturers`;

CREATE TABLE `Lecturers` (
  `lecturer_id` varchar(45) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `middle_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `primary_phone` int(11) DEFAULT NULL,
  `secondary_phone` int(11) DEFAULT NULL,
  `apartment_unit` varchar(45) DEFAULT NULL,
  `street_number_name` varchar(100) DEFAULT NULL,
  `suburb` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `postal_code` int(11) DEFAULT NULL,
  `year_started` int(11) DEFAULT NULL,
  `app_password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`lecturer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Lecturers` WRITE;
/*!40000 ALTER TABLE `Lecturers` DISABLE KEYS */;

INSERT INTO `Lecturers` (`lecturer_id`, `first_name`, `middle_name`, `last_name`, `primary_phone`, `secondary_phone`, `apartment_unit`, `street_number_name`, `suburb`, `city`, `postal_code`, `year_started`, `app_password`)
VALUES
	('LEC2000_001','Ndai',NULL,'Mapaso',836893737,NULL,NULL,'12 Main Rd','Claremont','Cape Town',7300,2000,'pass'),
	('LEC2001_891','Jakob','','Veneman',845646997,731247283,'','42 Tanner Ave','Constantia','Cape Town',7806,2001,'pass'),
	('LEC2002_173','Melissa','Ivy','Evans',853370295,844655051,'','274 Eden Rd','Retreat','Cape Town',7965,2002,'pass'),
	('LEC2005_647','Andrew',NULL,'Hill',657657865,NULL,NULL,'56 Durban Rd','Constantia','Cape Town',7654,2005,'pass'),
	('LEC2006_734','Zakira','','Mohamed',738096403,NULL,'','167 2nd Ave','Athlone','Cape Town',7760,2006,'pass'),
	('LEC2008_030','Patricia',NULL,'Harper',747543273,216856384,'301','27 Chester St','Gardens','Cape Town',8000,2008,'pass'),
	('LEC2010_056','John','Kevin','Smith',735754356,NULL,NULL,'15 Main Rd','Newlands','Cape Town',7400,2010,'pass'),
	('LEC2010_276','David','','Johnson',826509724,NULL,'22','26 Crown St','Milnerton','Cape Town',7435,2010,'pass'),
	('LEC2015_480','Gina','Sue','Kloosterhuis',841812087,NULL,'','12 Dale St','Lansdowne','Cape Town',7779,2015,'pass');

/*!40000 ALTER TABLE `Lecturers` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Marks
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Marks`;

CREATE TABLE `Marks` (
  `mark_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(45) DEFAULT NULL,
  `module_id` varchar(45) DEFAULT NULL,
  `test1_mark` int(11) DEFAULT NULL COMMENT '	',
  `test2_mark` int(11) DEFAULT NULL,
  `assignment_mark` int(11) DEFAULT NULL,
  `project_mark` int(11) DEFAULT NULL,
  `exam_mark` int(11) DEFAULT NULL,
  PRIMARY KEY (`mark_id`),
  KEY `fk_Marks_Students1_idx` (`student_id`),
  KEY `fk_Marks_Modules1_idx` (`module_id`),
  CONSTRAINT `fk_Marks_Modules1` FOREIGN KEY (`module_id`) REFERENCES `modules` (`module_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Marks_Students1` FOREIGN KEY (`student_id`) REFERENCES `Students` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

LOCK TABLES `Marks` WRITE;
/*!40000 ALTER TABLE `Marks` DISABLE KEYS */;

INSERT INTO `Marks` (`mark_id`, `student_id`, `module_id`, `test1_mark`, `test2_mark`, `assignment_mark`, `project_mark`, `exam_mark`)
VALUES
	(1,'MB2015-0615','C_ITEC301',70,75,80,85,90),
	(2,'MB2015-0615','C_ITDS221',60,65,70,NULL,80),
	(3,'MB2015-0615','C_ITII320',NULL,NULL,NULL,85,NULL),
	(4,'MB2015-0615','C_COBU211',65,80,77,NULL,62),
	(5,'MB2015-0615','C_ITCO111',83,67,87,NULL,70),
	(6,'MB2015-0615','C_ITCO221',99,62,100,NULL,94),
	(7,'MB2015-0615','C_ITCS111',85,55,85,NULL,82),
	(8,'MB2015-0615','C_ITDB211',84,59,93,NULL,98),
	(9,'MB2015-0615','C_ITDB221',84,52,56,NULL,65),
	(10,'MB2015-0615','C_ITDB310',73,70,89,NULL,64),
	(11,'MB2015-0615','C_ITHC121',86,100,91,NULL,99),
	(12,'MB2015-0615','C_ITIP111',68,75,86,NULL,83),
	(13,'MB2015-0615','C_ITIP211',80,78,90,NULL,52),
	(14,'MB2015-0615','C_ITIP221',60,98,58,NULL,74),
	(15,'MB2015-0615','C_ITJA211',100,61,57,NULL,51),
	(16,'MB2015-0615','C_ITJA321',67,52,93,NULL,85),
	(17,'MB2015-0615','C_ITMA111',63,92,57,NULL,69),
	(18,'MB2015-0615','C_ITNT121',85,91,96,NULL,54),
	(19,'MB2015-0615','C_ITNT211',85,71,74,NULL,87),
	(20,'MB2015-0615','C_ITOO121',81,82,71,NULL,85),
	(21,'MB2015-0615','C_ITOO311',92,83,73,NULL,56),
	(22,'MB2015-0615','C_ITOS321',91,92,55,NULL,78),
	(23,'MB2015-0615','C_ITPP111',89,100,71,NULL,94),
	(24,'MB2015-0615','C_ITSP100',NULL,NULL,NULL,91,NULL),
	(25,'MB2015-0615','C_ITSP200',NULL,NULL,NULL,50,NULL),
	(26,'MB2015-0615','C_ITSP300',NULL,NULL,NULL,65,NULL),
	(27,'MB2015-0615','C_ITSS321',88,96,71,NULL,71);

/*!40000 ALTER TABLE `Marks` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Modules
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Modules`;

CREATE TABLE `Modules` (
  `module_id` varchar(45) NOT NULL DEFAULT '',
  `module_name` varchar(45) DEFAULT NULL,
  `module_length` int(11) DEFAULT NULL,
  `program_id` varchar(45) NOT NULL,
  `lecturer_id` varchar(45) DEFAULT NULL,
  `nqf_level` int(11) DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `program_level` int(11) DEFAULT NULL,
  PRIMARY KEY (`module_id`),
  KEY `fk_Modules_Lecturers1_idx` (`lecturer_id`),
  KEY `fk_Modules_Programs1_idx` (`program_id`),
  CONSTRAINT `fk_Modules_Lecturers1` FOREIGN KEY (`lecturer_id`) REFERENCES `Lecturers` (`lecturer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Modules_Programs1` FOREIGN KEY (`program_id`) REFERENCES `Programs` (`program_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Modules` WRITE;
/*!40000 ALTER TABLE `Modules` DISABLE KEYS */;

INSERT INTO `Modules` (`module_id`, `module_name`, `module_length`, `program_id`, `lecturer_id`, `nqf_level`, `cost`, `program_level`)
VALUES
	('C_COAC111','Accounting 1A',1,'PR_CO01','LEC2001_891',6,5500,1),
	('C_COAC121','Accounting 1B',1,'PR_CO01','LEC2006_734',6,5700,1),
	('C_COAC211','Accounting 2A',1,'PR_CO01','LEC2010_056',6,6000,2),
	('C_COAC221','Accounting 2B',1,'PR_CO01','LEC2015_480',6,6200,2),
	('C_COBM111','Business Management 1A',1,'PR_CO01','LEC2010_056',6,6000,1),
	('C_COBM121','Business Management 1B',1,'PR_CO01','LEC2002_173',6,6200,1),
	('C_COBM211','Business Management 2A',1,'PR_CO01','LEC2001_891',6,5500,2),
	('C_COBM221','Business Management 2B',1,'PR_CO01','LEC2002_173',7,5700,2),
	('C_COBM311','Business Management 3A',1,'PR_CO01','LEC2010_056',7,6200,3),
	('C_COBM321','Business Management 3B',1,'PR_CO01','LEC2006_734',7,5500,3),
	('C_COBU211','Introduction to Business Management',1,'PR_IT01','LEC2001_891',6,6000,2),
	('C_COEC111','Economics 1A',1,'PR_CO01','LEC2010_056',6,5500,1),
	('C_COEC121','Economics 1B',1,'PR_CO01','LEC2006_734',6,5700,1),
	('C_COEP221','Entrepreneurship (Voluntary Elective)',1,'PR_CO01','LEC2002_173',7,6000,2),
	('C_COFM311','Financial Management',1,'PR_CO01','LEC2001_891',7,5700,3),
	('C_COHR111','Human Resource Management 1A',1,'PR_CO01','LEC2001_891',6,6000,1),
	('C_COHR121','Human Resource Management 1B',1,'PR_CO01','LEC2002_173',6,6200,1),
	('C_COHR211','Human Resource Management 2A',1,'PR_CO01','LEC2006_734',7,6000,2),
	('C_COHR221','Human Resource Management 2B',1,'PR_CO01','LEC2010_276',7,6200,2),
	('C_COHR3111','Human Resource Management 3A',1,'PR_CO01','LEC2010_276',7,6000,3),
	('C_COHR321','Human Resource Management 3B',1,'PR_CO01','LEC2010_056',7,6200,3),
	('C_COMA101','Marketing 1A',1,'PR_CO01','LEC2008_030',6,6200,1),
	('C_COMA121','Marketing 1B',1,'PR_CO01','LEC2010_276',6,5700,1),
	('C_COMA211','Marketing 2A',1,'PR_CO01','LEC2001_891',7,5500,2),
	('C_COMA311','Marketing 3A',1,'PR_CO01','LEC2015_480',7,5500,3),
	('C_COMA321','Marketing 3B',1,'PR_CO01','LEC2006_734',7,5700,3),
	('C_COOB121','Organisations and Behaviour',1,'PR_CO01','LEC2015_480',6,6000,1),
	('C_COPR121','Public Relations and Communication Management',1,'PR_CO01','LEC2006_734',6,6200,1),
	('C_CORM311','Research Methodology 3A',1,'PR_CO01','LEC2002_173',7,6000,3),
	('C_CORM321','Research Methodology 3B',1,'PR_CO01','LEC2001_891',7,6200,3),
	('C_ITCO111','Computer Systems',1,'PR_IT01','LEC2010_276',6,6100,1),
	('C_ITCO221','Internet Server Management',1,'PR_IT01','LEC2010_056',7,6200,2),
	('C_ITCS111','Computer Skills Development',1,'PR_IT01','LEC2005_647',6,6200,1),
	('C_ITDB211','Database Design Concepts',1,'PR_IT01','LEC2005_647',7,6300,2),
	('C_ITDB221','Data Analysis and Design',1,'PR_IT01','LEC2010_276',7,6600,2),
	('C_ITDB310','Advanced Database Systems',1,'PR_IT01','LEC2008_030',7,6350,3),
	('C_ITDS221','Data Structures and Algorithms',1,'PR_IT01','LEC2005_647',7,6600,2),
	('C_ITEC301','Internet Programming and E-Commerce',2,'PR_IT01','LEC2000_001',7,6200,3),
	('C_ITHC121','Human Computer Interaction',1,'PR_IT01','LEC2008_030',7,6500,1),
	('C_ITII320','Information Systems Strategic Design',1,'PR_IT01','LEC2008_030',6,6300,3),
	('C_ITIP111','Introduction To Programming',1,'PR_IT01','LEC2008_030',6,6600,1),
	('C_ITIP211','Systems Analysis and Design',1,'PR_IT01','LEC2001_891',7,6300,2),
	('C_ITIP221','Project Management',1,'PR_IT01','LEC2010_056',6,6600,2),
	('C_ITJA211','Programming in Java',1,'PR_IT01','LEC2000_001',6,6550,2),
	('C_ITJA321','Java and Distributed Systems',1,'PR_IT01','LEC2000_001',7,6600,3),
	('C_ITMA111','Mathematics for Computer Science',1,'PR_IT01','LEC2002_173',6,6000,1),
	('C_ITNT121','Networking Technologies',1,'PR_IT01','LEC2015_480',6,6300,1),
	('C_ITNT211','Networking Infrastructure',1,'PR_IT01','LEC2002_173',7,6100,2),
	('C_ITOO121','Object Oriented Programming',1,'PR_IT01','LEC2000_001',7,6200,1),
	('C_ITOO311','Object Oriented System Analysis and Design',1,'PR_IT01','LEC2010_056',7,6000,3),
	('C_ITOS321','Operating Systems',1,'PR_IT01','LEC2005_647',6,6300,3),
	('C_ITPP111','Procedural Programming',1,'PR_IT01','LEC2008_030',6,6100,1),
	('C_ITSP100','Software Development Project 1',1,'PR_IT01','LEC2000_001',6,6500,1),
	('C_ITSP200','Software Development Project 2',1,'PR_IT01','LEC2000_001',6,6000,2),
	('C_ITSP300','Software Development Project 3',2,'PR_IT01','LEC2008_030',7,6200,3),
	('C_ITSS321','Social Practices and Security',1,'PR_IT01','LEC2010_276',7,6150,3);

/*!40000 ALTER TABLE `Modules` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table News
# ------------------------------------------------------------

DROP TABLE IF EXISTS `News`;

CREATE TABLE `News` (
  `news_id` int(11) NOT NULL AUTO_INCREMENT,
  `department_id` varchar(45) DEFAULT NULL,
  `message` varchar(300) DEFAULT NULL,
  `date_posted` date DEFAULT NULL,
  `time_posted` time DEFAULT NULL,
  PRIMARY KEY (`news_id`),
  KEY `fk_News_Departments1_idx` (`department_id`),
  CONSTRAINT `fk_News_Departments1` FOREIGN KEY (`department_id`) REFERENCES `Departments` (`department_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

LOCK TABLES `News` WRITE;
/*!40000 ALTER TABLE `News` DISABLE KEYS */;

INSERT INTO `News` (`news_id`, `department_id`, `message`, `date_posted`, `time_posted`)
VALUES
	(1,'DEPT01','Please contact 0216783648 for more information.','2016-07-10','09:00:00'),
	(2,'DEPT02','Marks have been posted on MyLMS.','2016-07-22','09:00:00'),
	(3,'DEPT03','There is a football meetings on the first floor on Thursday. The meeting will take place in Room 2A. Every member must be present.','2016-08-30','11:15:00'),
	(4,'DEPT04','Please return any books borrowed.','2016-09-01','15:46:00'),
	(5,'DEPT02','Contact Lameez to settle payments.','2016-09-01','15:47:00'),
	(6,'DEPT02','You can collect your timetables from Megan.','2016-09-01','14:28:00'),
	(7,'DEPT01','Please see your Class Representatives for more information.','2016-08-05','10:27:00'),
	(8,'DEPT04','Your Internet Programming and E-Commerce textbook can be picked from the Library.','2016-07-27','14:08:00');

/*!40000 ALTER TABLE `News` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Payments
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Payments`;

CREATE TABLE `Payments` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(45) DEFAULT NULL,
  `payment_amount` double DEFAULT NULL,
  `payment_date` date DEFAULT NULL,
  PRIMARY KEY (`payment_id`),
  KEY `fk_Finances_Students1_idx` (`student_id`),
  CONSTRAINT `fk_Finances_Students1` FOREIGN KEY (`student_id`) REFERENCES `Students` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

LOCK TABLES `Payments` WRITE;
/*!40000 ALTER TABLE `Payments` DISABLE KEYS */;

INSERT INTO `Payments` (`payment_id`, `student_id`, `payment_amount`, `payment_date`)
VALUES
	(1,'MB2015-0615',30000,'2016-02-02'),
	(2,'MB2015-0615',30000,'2016-05-17');

/*!40000 ALTER TABLE `Payments` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Programs
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Programs`;

CREATE TABLE `Programs` (
  `program_id` varchar(45) NOT NULL,
  `program_name` varchar(45) DEFAULT NULL,
  `length` double DEFAULT NULL,
  `nqf_level` int(11) DEFAULT NULL,
  PRIMARY KEY (`program_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Programs` WRITE;
/*!40000 ALTER TABLE `Programs` DISABLE KEYS */;

INSERT INTO `Programs` (`program_id`, `program_name`, `length`, `nqf_level`)
VALUES
	('PR_CO01','BCom',6,7),
	('PR_IT01','BSc in IT',6,7);

/*!40000 ALTER TABLE `Programs` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Query
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Query`;

CREATE TABLE `Query` (
  `query_id` int(11) NOT NULL AUTO_INCREMENT,
  `department_id` varchar(45) DEFAULT NULL,
  `student_id` varchar(45) DEFAULT NULL,
  `message` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`query_id`),
  KEY `fk_Incoming_Support_Departments1_idx` (`department_id`),
  KEY `fk_Incoming_Support_Students1_idx` (`student_id`),
  CONSTRAINT `fk_Incoming_Support_Departments1` FOREIGN KEY (`department_id`) REFERENCES `Departments` (`department_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Incoming_Support_Students1` FOREIGN KEY (`student_id`) REFERENCES `Students` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

LOCK TABLES `Query` WRITE;
/*!40000 ALTER TABLE `Query` DISABLE KEYS */;

INSERT INTO `Query` (`query_id`, `department_id`, `student_id`, `message`)
VALUES
	(1,'DEPT02','MB2015-0615','hello'),
	(2,'DEPT01','MB2014-0603','hi'),
	(3,'DEPT02','MB2015-0615','hello 1'),
	(5,'DEPT01','MB2014-0603','Hello Philip, see you tomorrow at 10.');

/*!40000 ALTER TABLE `Query` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Query_Feedback
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Query_Feedback`;

CREATE TABLE `Query_Feedback` (
  `feedback_id` int(11) NOT NULL AUTO_INCREMENT,
  `department_id` varchar(45) DEFAULT NULL,
  `student_id` varchar(45) DEFAULT NULL,
  `message` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`feedback_id`),
  KEY `fk_Outgoing_Support_Departments1_idx` (`department_id`),
  KEY `fk_Outgoing_Support_Students1_idx` (`student_id`),
  CONSTRAINT `fk_Outgoing_Support_Departments1` FOREIGN KEY (`department_id`) REFERENCES `Departments` (`department_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Outgoing_Support_Students1` FOREIGN KEY (`student_id`) REFERENCES `Students` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

LOCK TABLES `Query_Feedback` WRITE;
/*!40000 ALTER TABLE `Query_Feedback` DISABLE KEYS */;

INSERT INTO `Query_Feedback` (`feedback_id`, `department_id`, `student_id`, `message`)
VALUES
	(1,'DEPT04','MB2015-0615','Library timings are 08:00 to 17:00'),
	(2,'DEPT02','MB2015-0615','Please see you Student Advisor to sign a new contract'),
	(3,'DEPT01','MB2014-0603','Please see your SRC rep.'),
	(4,'DEPT01','MB2014-0603','See you tomorrow');

/*!40000 ALTER TABLE `Query_Feedback` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Students
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Students`;

CREATE TABLE `Students` (
  `student_id` varchar(45) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `middle_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `primary_phone` varchar(45) DEFAULT NULL,
  `secondary_phone` varchar(45) DEFAULT NULL,
  `apartment_unit` varchar(45) DEFAULT NULL,
  `street_number_name` varchar(100) DEFAULT NULL,
  `suburb` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `postal_code` int(11) DEFAULT NULL,
  `year_started` varchar(45) DEFAULT NULL,
  `app_password` varchar(45) DEFAULT NULL,
  `program_id` varchar(45) NOT NULL,
  `current_program_level` int(11) DEFAULT NULL,
  PRIMARY KEY (`student_id`,`program_id`),
  KEY `fk_Students_Programs1_idx` (`program_id`),
  CONSTRAINT `fk_Students_Programs1` FOREIGN KEY (`program_id`) REFERENCES `Programs` (`program_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Students` WRITE;
/*!40000 ALTER TABLE `Students` DISABLE KEYS */;

INSERT INTO `Students` (`student_id`, `first_name`, `middle_name`, `last_name`, `primary_phone`, `secondary_phone`, `apartment_unit`, `street_number_name`, `suburb`, `city`, `postal_code`, `year_started`, `app_password`, `program_id`, `current_program_level`)
VALUES
	('MB2013-0503','Linda','Ana','Moba','0747268392',NULL,'12','120 Main Rd','Claremont','Cape Town',7400,'2014','pass','PR_IT01',2),
	('MB2014-0203','Dave','Kevin','Brown','0682738293',NULL,NULL,'15 Ottery Rd','Ottery','Cape Town',7165,'2014','pass','PR_IT01',1),
	('MB2014-0603','Mohamed',NULL,'Khan','0846863827',NULL,'101','127 Main rd','Claremont','Cape Town',7400,'2014','pass','PR_IT01',2),
	('MB2015-0444','Leigh','','Kroutz','0825374822',NULL,NULL,'18 Lansdowne Rd,','Lansdowne','Cape Town',7880,'2015','pass','PR_IT01',2),
	('MB2015-0615','Danyal',NULL,'Dharani','0725215889',NULL,'301','21 Lower Nursery Rd','Rosebank','Cape Town',7700,'2015','pass','PR_IT01',3);

/*!40000 ALTER TABLE `Students` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Test_Timetable
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Test_Timetable`;

CREATE TABLE `Test_Timetable` (
  `test_id` int(45) NOT NULL AUTO_INCREMENT,
  `module_id` varchar(45) NOT NULL,
  `date` date DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  `venue` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`test_id`),
  KEY `fk_Test_Timetable_Modules_idx` (`module_id`),
  CONSTRAINT `fk_Test_Timetable_Modules` FOREIGN KEY (`module_id`) REFERENCES `modules` (`module_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

LOCK TABLES `Test_Timetable` WRITE;
/*!40000 ALTER TABLE `Test_Timetable` DISABLE KEYS */;

INSERT INTO `Test_Timetable` (`test_id`, `module_id`, `date`, `start_time`, `end_time`, `venue`)
VALUES
	(1,'C_ITDS221','2016-10-02','09:00:00','12:00:00','Room 1A'),
	(2,'C_ITDB221','2016-10-02','12:00:00','15:00:00','Room 1A'),
	(3,'C_ITCO221','2016-10-03','09:00:00','12:00:00','Room 2A'),
	(4,'C_ITIP221','2016-10-04','12:00:00','15:00:00','Room 2A'),
	(5,'C_COMA101','2016-10-02','09:00:00','12:00:00','Room 1C');

/*!40000 ALTER TABLE `Test_Timetable` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Year_Planner
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Year_Planner`;

CREATE TABLE `Year_Planner` (
  `year_event_id` int(11) NOT NULL AUTO_INCREMENT,
  `year_event_type` varchar(45) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `details` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`year_event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=latin1;

LOCK TABLES `Year_Planner` WRITE;
/*!40000 ALTER TABLE `Year_Planner` DISABLE KEYS */;

INSERT INTO `Year_Planner` (`year_event_id`, `year_event_type`, `date`, `details`)
VALUES
	(1,'Yellow','2016-01-01','Public holiday'),
	(2,'Normal','2016-01-04','All staff start'),
	(3,'Normal','2016-01-05','IS Start : Returning Students'),
	(4,'Normal','2016-01-11','IS Start : New Students'),
	(5,'Normal','2016-01-15','S2/2015  Initial exam remark applications close (MGI)'),
	(6,'Pale Red','2016-01-18','2015 Supp/Deferred Exam Starts'),
	(7,'Pale Red','2016-01-25','Enrollment starts   \nCTI resit Exam HND students'),
	(8,'Pale Red','2016-01-26','CTI resit Exam HND students'),
	(9,'Pale Red','2016-01-27','CTI resit Exam HND students'),
	(10,'Pale Red','2016-01-28','CTI resit Exam HND students'),
	(11,'Pale Red','2016-01-29','2015 Supp/Def Exam End  \nCTI resit Exam HND students'),
	(12,'Grey','2016-01-30','Open Day'),
	(13,'Normal','2016-02-01','New lecturer induction; \nCTI resit Exam HND students'),
	(14,'Normal','2016-02-02','New lecturer induction'),
	(15,'Normal','2016-02-03','New lecturer induction'),
	(16,'Normal','2016-02-04','New lecturer induction'),
	(17,'Normal','2016-02-05','New lecturer induction'),
	(18,'Grey','2016-02-06','End of enrollment for FP & First Year students'),
	(19,'Orange','2016-02-08','S1 Start Date for FP & First Year Students'),
	(20,'Normal','2016-02-09','Exam Committee'),
	(21,'Red','2016-02-11','S2 2015 Supp results published '),
	(22,'Normal','2016-02-12','Academic progress report S2 2015'),
	(23,'Grey','2016-02-13','End of enrollment for returning students; Post Grad Start Date'),
	(24,'Orange','2016-02-15','S1 Start Date for Returning Students'),
	(25,'Pale Red','2016-02-18','Special exams '),
	(26,'Normal','2016-02-23','CTI HND Resit Exam Results Publish'),
	(27,'Normal','2016-02-26','Senate'),
	(28,'Grey','2016-02-27','Enrollment ends'),
	(29,'Normal','2016-03-04','Exam TT loaded on LMS'),
	(30,'Grey','2016-03-05','Close of exceptional enrolment cases'),
	(31,'Normal','2016-03-07','BUSSE Survey starts'),
	(32,'Normal','2016-03-10','Finalise the 2015 graduates'),
	(33,'Green','2016-03-17','Foundation Programme Awards Ceremony (MC)'),
	(34,'Yellow','2016-03-21','Public holiday'),
	(35,'Light Green','2016-03-22','Semester Break'),
	(36,'Light Green','2016-03-23','Semester Break'),
	(37,'Light Green','2016-03-24','Semester Break'),
	(38,'Yellow','2016-03-25','Public holiday'),
	(39,'Yellow','2016-03-28','Public holiday'),
	(40,'Orange','2016-03-29','Classes resume'),
	(41,'Normal','2016-03-31','2017 Booklist due'),
	(42,'Normal','2016-04-01','S1 Student numbers;\nBUSSE Survey ends'),
	(43,'Grey','2016-04-02','Open Day'),
	(44,'Normal','2016-04-04','MGI Test Week 1 S1'),
	(45,'Normal','2016-04-05','MGI Test Week 1 S1'),
	(46,'Normal','2016-04-06','MGI Test Week 1 S1'),
	(47,'Normal','2016-04-07','MGI Test Week 1 S1'),
	(48,'Green','2016-04-08','MGI Test Week 1 S1   \nGraduation Ceremony MC'),
	(49,'Orange','2016-04-11','MGI Classes resume\n'),
	(50,'Green','2016-04-15','Graduation Ceremony DU\nGraduation Ceremony PTA'),
	(51,'Normal','2016-04-18','Exam papers to externals'),
	(52,'Green','2016-04-19','Graduation Ceremony EL'),
	(53,'Normal','2016-04-20','S2 2016 study material for copy editing'),
	(54,'Green','2016-04-21','Graduation Ceremony BE\nGraduation Ceremony BL & RB'),
	(55,'Green','2016-04-26','Graduation Ceremony CT\nGraduation Ceremony PE'),
	(56,'Yellow','2016-04-27','Public holiday'),
	(57,'Light Green','2016-04-28','Semester Break'),
	(58,'Green','2016-04-29','Graduation Ceremony PS\nGraduation Ceremony VDB'),
	(59,'Grey','2016-04-30','Open Day'),
	(60,'Yellow','2016-05-01','Public holiday'),
	(61,'Yellow','2016-05-02','Public holiday'),
	(62,'Orange','2016-05-03','Classes resume\nAudit Visits start'),
	(63,'Green','2016-05-06','Graduation Ceremony NS'),
	(64,'Grey','2016-05-08','Exam papers back from externals'),
	(65,'Normal','2016-05-09','CTI Test Week S1'),
	(66,'Normal','2016-05-10','CTI Test Week S1'),
	(67,'Normal','2016-05-11','CTI Test Week S1'),
	(68,'Normal','2016-05-12','CTI Test Week S1'),
	(69,'Normal','2016-05-13','CTI Test Week S1'),
	(70,'Normal','2016-05-16','MGI Test Week 2 S1'),
	(71,'Normal','2016-05-17','MGI Test Week 2 S1'),
	(72,'Normal','2016-05-18','MGI Test Week 2 S1'),
	(73,'Normal','2016-05-19','MGI Test Week 2 S1'),
	(74,'Normal','2016-05-20','Exam scopes uploaded\nMGI Test Week 2 S1\n'),
	(75,'Normal','2016-05-23','Exam papers to printers\nMGI Deferred Tests start'),
	(76,'Normal','2016-05-27','MGI Deferred Tests end'),
	(77,'Grey','2016-05-28','Open Day'),
	(78,'Normal','2016-05-30','All printing for S2 2016 to printers'),
	(79,'Normal','2016-05-31','Audit visits end'),
	(80,'Normal','2016-06-01','All S1 marks captured'),
	(81,'Normal','2016-06-03','HEQCIS Submission'),
	(82,'Normal','2016-06-06','Exam papers uploaded to LMS\n'),
	(83,'Red','2016-06-07','S1 DPs published'),
	(84,'Orange','2016-06-10','Classes end'),
	(85,'Pink','2016-06-24','S1 Exam end; \nSenate'),
	(86,'Grey','2016-06-25','Open Day'),
	(87,'Purple','2016-07-01','Intended Official launch of PIHE'),
	(88,'Normal','2016-07-04','S2 MO on LMS'),
	(89,'Purple','2016-07-06','MGI Supp & Def Exams Start'),
	(91,'Purple','2016-07-08','IS classes end'),
	(92,'Purple','2016-07-11','CTI Exam Committee; \nNew lecturer induction'),
	(93,'Purple','2016-07-12','CTI Initial Result Publ.; Lecturer induction'),
	(94,'Purple','2016-07-13','MGI Supp & Def Exams end;  Lecturer induction'),
	(95,'Normal','2016-07-15','CTI Supp. Exam start'),
	(96,'Normal','2016-07-18','MGI Exam Committee\nS2 MO on LMS'),
	(97,'Normal','2016-07-19','Lecturer contact detail list'),
	(98,'Normal','2016-07-21','AOM;\nS2 2016 Assign. on LMS'),
	(99,'Red','2016-07-22','MGI S1 Final results pub.;\nCTI Supp Exam ends'),
	(100,'Orange','2016-07-25','S2 Classes start'),
	(101,'Normal','2016-07-27','CTI Exam Committee'),
	(102,'Normal','2016-07-28','\nABM'),
	(103,'Normal','2016-07-29','CTI Supp result publication'),
	(104,'Grey','2016-07-30','Open Day'),
	(105,'Normal','2016-08-01','TA nominations open(TBC)\nS2 student numbers'),
	(106,'Normal','2016-08-02','Special Exam & Remark Application close'),
	(108,'Normal','2016-08-04','Academic progress report S1 2016'),
	(109,'Normal','2016-08-05','\nLast day S2 enrollment'),
	(110,'Pale Red','2016-08-08','Special Exams;\nNo Classes'),
	(111,'Yellow','2016-08-09','Public holiday'),
	(112,'Normal','2016-08-10','Class visits start;\nSASSE / LSSE Survey starts'),
	(113,'Normal','2016-08-11','CEC\nS2 class reps'),
	(114,'Normal','2016-08-12','TA nominations close'),
	(115,'Normal','2016-08-15','ECM\nExam TT loaded on LMS'),
	(116,'Normal','2016-08-16','RCM'),
	(117,'Normal','2016-08-17','RQAA'),
	(118,'Normal','2016-08-18','AOM'),
	(119,'Normal','2016-08-22','MGI Test Week 1/S2'),
	(120,'Normal','2016-08-23','MGI Test Week 1/S2'),
	(121,'Normal','2016-08-24','MGI Test Week 1/S2'),
	(122,'Normal','2016-08-25','ABM\nMGI Test Week 1/S2'),
	(123,'Normal','2016-08-26','MGI Test Week 1/S2;\nSASSE / LSSE Survey ends'),
	(124,'Grey','2016-08-27','Open Day'),
	(125,'Orange','2016-08-29','Classes resume                  Lecturer reviews start\n'),
	(126,'Normal','2016-08-31','Class visits end'),
	(127,'Normal','2016-09-01','Lecturer reviews end\n2017 ac. planners due'),
	(128,'Orange','2016-09-02','Lecturer reviews start'),
	(129,'Light Green','2016-09-05','Mid semester break start\nSubmit TA e-portfolio (TBC)'),
	(130,'Light Green','2016-09-06','AM & AAM Workshop'),
	(131,'Light Green','2016-09-07','AM & AAM Workshop'),
	(132,'Light Green','2016-09-08','AM & AAM Workshop'),
	(133,'Light Green','2016-09-09','Mid semester break end\n'),
	(134,'Orange','2016-09-12','Classes resume\nExam papers to externals'),
	(135,'Normal','2016-09-23','WIL Indaba'),
	(136,'Yellow','2016-09-24','Public Holiday'),
	(137,'Normal','2016-09-26','TA class visits (TBC)'),
	(138,'Normal','2016-09-27','TA class visits (TBC)'),
	(139,'Normal','2016-09-28','TA class visits (TBC); S1 2017 Study material copy editing'),
	(140,'Normal','2016-09-29','AOM\nTA class visits (TBC)'),
	(141,'Normal','2016-09-30','Senate;  Exam papers back from externals'),
	(142,'Grey','2016-10-01','Open Day'),
	(143,'Normal','2016-10-03','Test Week'),
	(144,'Normal','2016-10-04','Test Week'),
	(145,'Normal','2016-10-05','Test Week\nExam papers to printers'),
	(146,'Normal','2016-10-06','ABM\nTest Week'),
	(147,'Normal','2016-10-07','Exam scopes uploaded\nTest Week'),
	(148,'Normal','2016-10-10','ECM\nMGI Deferred Tests start'),
	(149,'Normal','2016-10-11','RCM'),
	(150,'Normal','2016-10-13','CEC'),
	(151,'Normal','2016-10-14','MGI Deferred Tests end'),
	(152,'Normal','2016-10-19','All S2 marks captured (MGI)'),
	(153,'Normal','2016-10-21','IT Project day'),
	(154,'Normal','2016-10-24','Exam papers uploaded to LMS'),
	(155,'Red','2016-10-25','MGI S2 DPs published\nResearch Indaba'),
	(156,'Normal','2016-10-26','Research Indaba'),
	(157,'Normal','2016-10-27','Research Indaba\nTeaching award'),
	(158,'Orange','2016-10-28','MGI Classes end;  \nCTI Marks captured'),
	(159,'Grey','2016-10-29','Open Day'),
	(160,'Pink','2016-10-31','MGI S2 exams start'),
	(161,'Pink','2016-11-01','All printing for S1 2017 to printers'),
	(162,'Pink','2016-11-02','CTI S2 DPs Published'),
	(163,'Pink','2016-11-03','AOM'),
	(164,'Pink','2016-11-04','CTI Classes end'),
	(165,'Pink','2016-11-07','CTI Exam start'),
	(166,'Pink','2016-11-10','ABM'),
	(167,'Pink','2016-11-18','All S2 exams end'),
	(168,'Grey','2016-11-19','Open Day'),
	(169,'Normal','2016-11-25','Senate'),
	(170,'Grey','2016-11-26','Open Day'),
	(171,'Normal','2016-11-29','S1  2017 Study Guides uploaded on LMS'),
	(172,'Normal','2016-12-02','HEQCIS Submission'),
	(173,'Normal','2016-12-08','Exam Committee'),
	(174,'Red','2016-12-12','S2 Initial exam results published '),
	(175,'Normal','2016-12-15','Close for 2016');

/*!40000 ALTER TABLE `Year_Planner` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
