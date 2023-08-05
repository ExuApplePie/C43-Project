CREATE TABLE `user` (
  `userId` int NOT NULL,
  `occupation` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `DOB` varchar(45) DEFAULT NULL,
  `SIN` int NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `SIN_UNIQUE` (`SIN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
@@@
