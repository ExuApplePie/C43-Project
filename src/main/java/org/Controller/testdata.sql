INSERT INTO `assignment2`.`user`
(`userId`, `occupation`, `address`, `DOB`, `SIN`, `name`)
VALUES
(1, 'Software Engineer', '123 Main St, Toronto, ON', '1990-01-01', '123456789', 'Alice'),
(2, 'Doctor', '456 Elm St, Vancouver, BC', '1985-02-15', '234567890', 'Bob'),
(3, 'Teacher', '789 Pine St, Montreal, QC', '1992-03-31', '345678901', 'Charlie'),
(4, 'Lawyer', '321 Oak St, Calgary, AB', '1988-04-30', '456789012', 'Dave'),
(5, 'Accountant', '654 Maple St, Halifax, NS', '1991-05-31', '567890123', 'Eve'),
(6, 'Nurse', '147 Maple St, Ottawa, ON', '1993-06-30', '678901234', 'Frank'),
(7, 'Journalist', '258 Oak St, Edmonton, AB', '1986-07-31', '789012345', 'Grace'),
(8, 'Architect', '369 Pine St, Winnipeg, MB', '1989-08-31', '890123456', 'Helen'),
(9, 'Dentist', '741 Elm St, Quebec City, QC', '1990-09-30', '901234567', 'Ivan'),
(10, 'Electrician', '852 Main St, Hamilton, ON', '1987-10-31', '012345678', 'Jack')
ON DUPLICATE KEY UPDATE userId = VALUES(userId), occupation = VALUES(occupation), address = VALUES(address), DOB = VALUES(DOB), SIN = VALUES(SIN), name = VALUES(name);
@@@
INSERT INTO `assignment2`.`amenity`
(`amenityId`,
 `name`)
VALUES
    (1, 'Wifi'),
    (2, 'Washer'),
    (3, 'Air Conditioning'),
    (4, 'Dedicated Workspace'),
    (5, 'Hair Dryer'),
    (6, 'Kitchen'),
    (7, 'Dryer'),
    (8, 'Heating'),
    (9, 'Tv'),
    (10, 'Iron'),
    (11, 'Pool'),
    (12, 'Free Parking'),
    (13, 'Crib'),
    (14, 'BBQ Grill'),
    (15, 'Indoor Fireplace'),
    (16, 'Hot Tub'),
    (17, 'EV Charger'),
    (18, 'Gym'),
    (19, 'Breakfast'),
    (20, 'Smoking Allowed'),
    (21, 'Beachfront'),
    (22, 'Waterfront'),
    (23, 'Smoke Alarm'),
    (24, 'Carbon Monoxide Alarm')
ON DUPLICATE KEY UPDATE amenityId = VALUES(amenityId), name = VALUES(name);
@@@
INSERT INTO `assignment2`.`listing`
(`listingId`,
 `hostId`,
 `type`,
 `longitude`,
 `latitude`,
 `postalCode`,
 `country`,
 `city`,
 `address`)
VALUES
    (1, 1, 'Apartment', -79.3832, 43.6532, 'M5H 2N2', 'Canada', 'Toronto', '100 Queen St W'),
    (2, 2, 'House', -79.3832, 43.6532, 'M5H 2N2', 'Canada', 'Toronto', '200 Queen St W'),
    (3, 3, 'Condo', -79.3832, 43.6532, 'M5H 2N2', 'Canada', 'Toronto', '300 Queen St W'),
    (4, 4, 'Townhouse', -79.3832, 43.6532, 'M5H 2N2', 'Canada', 'Toronto', '400 Queen St W'),
    (5, 5, 'Loft', -79.3832, 43.6532, 'M5H 2N2', 'Canada', 'Toronto', '500 Queen St W'),
    (6, 6, 'Villa', -79.3832, 43.6532, 'M5H 2N2', 'Canada', 'Montreal', '600 Rue Sherbrooke Ouest'),
    (7, 7, 'Cottage', -79.3832, 43.6532, 'M5H 2N2', 'Canada', 'Montreal', '700 Rue Sherbrooke Ouest'),
    (8, 8, 'Bungalow', -79.3832, 43.6532, 'M5H 2N2', 'Canada', 'Montreal', '800 Rue Sherbrooke Ouest'),
    (9, 9, 'Chalet', -79.3832 ,43.6532 ,'M5H 3C3','Canada','Montreal','900 Rue Sherbrooke Ouest'),
    (10 ,10,'Farm stay' ,-79.3831 ,43.6531 ,'M5H 3C4','Canada','Montreal','1000 Rue Sherbrooke Ouest')
ON DUPLICATE KEY UPDATE listingId = VALUES(listingId), hostId = VALUES(hostId), type = VALUES(type), longitude = VALUES(longitude), latitude = VALUES(latitude), postalCode = VALUES(postalCode), country = VALUES(country), city = VALUES(city), address = VALUES(address);
@@@
INSERT INTO `assignment2`.`availability`
(`listingId`,
 `date`,
 `available`,
 `price`)
VALUES
    (1, '2023-08-01', 1, 100),
    (1, '2023-08-02', 1, 100),
    (1, '2023-08-03', 0, 100),
    (2, '2023-08-01', 1, 200),
    (2, '2023-08-02', 1, 200),
    (2, '2023-08-03', 0, 200),
    (3, '2023-08-01', 1, 300),
    (3, '2023-08-02', 1, 300),
    (3, '2023-08-03', 0, 300),
    (4, '2023-08-01', 1, 400),
    (4, '2023-08-02', 1, 400),
    (4, '2023-08-03', 0, 400),
    (5, '2023-08-01', 1, 500),
    (5, '2023-08-02', 1, 500),
    (5, '2023-08-03', 0, 500),
    (6, '2023-08-01', 1, 600),
    (6, '2023-08-02', 1, 600),
    (6, '2023-08-03', 0, 600),
    (7, '2023-08-01', 1, 700),
    (7,'2023 -08 -02' ,1 ,700 ),
    (7,'2023 -08 -03' ,0 ,700 ),
    (8,'2023 -08 -01' ,1 ,800 ),
    (8,'2023 -08 -02' ,1 ,800 ),
    (8,'2023 -08 -03' ,0 ,800 ),
    (9,'2023 -08 -01' ,1 ,900 ),
    (9,'2023 -08 -02' ,1 ,900 ),
    (9,'2023 -08 -03' ,0 ,900 ),
    (10,'2023 -08 -01' ,1 ,1000 ),
    (10,'2023 -08 -02' ,1 ,1000 ),
    (10,'2023 -08 -03' ,0 ,1000 )
ON DUPLICATE KEY UPDATE listingId = VALUES(listingId), date = VALUES(date), available = VALUES(available), price = VALUES(price);
@@@
INSERT INTO `assignment2`.`booking`
(`bookingId`,
 `listingId`,
 `guestId`,
 `startDate`,
 `endDate`,
 `score`,
 `comment`,
 `creditcard`,
 `cancelledBy`)
VALUES
    (1, 1, 1, '2023-08-01', '2023-08-05', 5, 'Great stay!', '1234567890123456', -1),
    (2, 2, 2, '2023-08-02', '2023-08-06', 4, 'Nice place', '2345678901234567', -1),
    (3, 3, 3, '2023-08-03', '2023-08-07', 3, 'Okay stay', '3456789012345678', -1),
    (4, 4, 4, '2023-08-04', '2023-08-08', 2, 'Not great', '4567890123456789', -1),
    (5, 5, 5, '2023-08-05', '2023-08-09', 1, 'Terrible!', '5678901234567890', -1),
    (6, 6, 6, '2023-08-06', '2023-08-10', NULL, NULL, '6789012345678901', -1),
    (7, 7, 7, '2023-08-07', '2023-08-11', NULL, NULL, '7890123456789012', -1),
    (8 ,8 ,8 ,'2023 -08 -08' ,'2023 -08 -12' ,NULL ,NULL ,'8901234567890123' ,-1 ),
    (9 ,9 ,9 ,'2023 -08 -09' ,'2023 -08 -13' ,NULL ,NULL ,'9012345678901234' ,-1 ),
    (10 ,10 ,10 ,'2023 -08 -10' ,'2023 -08 -14' ,NULL ,NULL ,'0123456789012345' ,-1 )
ON DUPLICATE KEY UPDATE bookingId = VALUES(bookingId), listingId = VALUES(listingId), guestId = VALUES(guestId), startDate = VALUES(startDate), endDate = VALUES(endDate), score = VALUES(score), comment = VALUES(comment), creditcard = VALUES(creditcard), cancelledBy = VALUES(cancelledBy);
@@@
INSERT INTO `assignment2`.`listingamenity`
(`listingId`,
 `amenityId`,
 `quantity`)
VALUES
    (1, 1, 1),
    (2, 2, 2),
    (3, 3, 3),
    (4, 4, 4),
    (5, 5, 5),
    (6, 6, 6),
    (7, 7, 7),
    (8 ,8 ,8 ),
    (9 ,9 ,9 ),
    (10 ,10 ,10 ),
    (1, 2, 1),
    (1, 3, 1),
    (1, 4, 1),
    (2, 3, 2),
    (2, 4, 2),
    (3, 4, 3),
    (4, 5, 4),
    (5 ,6 ,5 ),
    (6 ,7 ,6 ),
    (7 ,8 ,7 )
ON DUPLICATE KEY UPDATE listingId = VALUES(listingId), amenityId = VALUES(amenityId), quantity = VALUES(quantity);
@@@
INSERT INTO `assignment2`.`rating`
(`renterId`,
 `hostId`,
 `score`,
 `date`,
 `comment`)
VALUES
    (1, 1, 5, '2023-08-01', 'Great guest!'),
    (2, 2, 4, '2023-08-02', 'Nice guest'),
    (3, 3, 3, '2023-08-03', 'Okay guest'),
    (4, 4, 2, '2023-08-04', 'Not great'),
    (5, 5, 1, '2023-08-05', 'Terrible!'),
    (6 ,6 ,5 ,'2023 -08 -06' ,'Great guest !' ),
    (7 ,7 ,4 ,'2023 -08 -07' ,'Nice guest' ),
    (8 ,8 ,3 ,'2023 -08 -08' ,'Okay guest' ),
    (9 ,9 ,2 ,'2023 -08 -09' ,'Not great' ),
    (10 ,10 ,1 ,'2023 -08 -10' ,'Terrible !' )
ON DUPLICATE KEY UPDATE renterId = VALUES(renterId), hostId = VALUES(hostId), score = VALUES(score), date = VALUES(date), comment = VALUES(comment);
