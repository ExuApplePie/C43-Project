CREATE TABLE IF NOT EXISTS `amenity` (
   `amenityId` int NOT NULL AUTO_INCREMENT,
   `name` varchar(45) NOT NULL,
   PRIMARY KEY (`amenityId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
@@@
CREATE TABLE IF NOT EXISTS `user` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `occupation` varchar(90) NOT NULL,
  `address` varchar(90) NOT NULL,
  `DOB` date NOT NULL,
  `SIN` int NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `SIN_UNIQUE` (`SIN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
@@@
CREATE TABLE IF NOT EXISTS `listing` (
  `listingId` int NOT NULL AUTO_INCREMENT,
  `hostId` int NOT NULL,
  `type` varchar(45) NOT NULL,
  `longitude` float NOT NULL,
  `latitude` float NOT NULL,
  `postalCode` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`listingId`),
  KEY `listingHID_idx` (`hostId`),
  CONSTRAINT `listingHID` FOREIGN KEY (`hostId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
@@@
CREATE TABLE IF NOT EXISTS `availability` (
  `listingId` int NOT NULL,
  `date` date NOT NULL,
  `available` tinyint NOT NULL,
  `price` int NOT NULL,
  PRIMARY KEY (`listingId`,`date`),
  CONSTRAINT `availabilityLID` FOREIGN KEY (`listingId`) REFERENCES `listing` (`listingId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
@@@
CREATE TABLE IF NOT EXISTS `booking` (
  `bookingId` int NOT NULL AUTO_INCREMENT,
  `listingId` int NOT NULL,
  `guestId` int NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `score` int DEFAULT '-1',
  `comment` text,
  `creditcard` varchar(45) NOT NULL,
  `cancelledBy` int NOT NULL DEFAULT '-1',
  PRIMARY KEY (`bookingId`),
  KEY `bookingLID_idx` (`listingId`),
  KEY `bookingGID_idx` (`guestId`),
  CONSTRAINT `bookingGID` FOREIGN KEY (`guestId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bookingLID` FOREIGN KEY (`listingId`) REFERENCES `listing` (`listingId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
@@@
CREATE TABLE IF NOT EXISTS `listingamenity` (
  `listingId` int NOT NULL,
  `amenityId` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`listingId`,`amenityId`),
  KEY `listingamenityAID_idx` (`amenityId`),
  CONSTRAINT `listingamenityAID` FOREIGN KEY (`amenityId`) REFERENCES `amenity` (`amenityId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `listingamenityLID` FOREIGN KEY (`listingId`) REFERENCES `listing` (`listingId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
@@@
CREATE TABLE IF NOT EXISTS `rating` (
  `renterId` int NOT NULL,
  `hostId` int NOT NULL,
  `score` int NOT NULL,
  `date` date NOT NULL,
  `comment` text,
  PRIMARY KEY (`renterId`,`hostId`),
  KEY `ratingHID_idx` (`hostId`),
  CONSTRAINT `ratingHID` FOREIGN KEY (`hostId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ratingRID` FOREIGN KEY (`renterId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;