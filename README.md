First Test
-- SQL --

CREATE TABLE `houses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `area_size` decimal(10,2) DEFAULT NULL,
  `num_bedrooms` int(11) DEFAULT NULL,
  `num_bathrooms` int(11) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `house_condition` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `year_built` int(11) DEFAULT NULL,
  `parking_spaces` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `image_uri` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
