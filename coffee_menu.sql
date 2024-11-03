/*
 Navicat Premium Data Transfer

 Source Server         : 本地服务器
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : coffee_menu

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 01/11/2024 13:30:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of categories
-- ----------------------------
INSERT INTO `categories` VALUES (1, 'Espresso');
INSERT INTO `categories` VALUES (2, 'Americano');
INSERT INTO `categories` VALUES (3, 'Latte');
INSERT INTO `categories` VALUES (4, 'Cappuccino');
INSERT INTO `categories` VALUES (5, 'Mocha');
INSERT INTO `categories` VALUES (6, 'Macchiato');
INSERT INTO `categories` VALUES (7, 'Flat White');
INSERT INTO `categories` VALUES (8, 'Affogato');
INSERT INTO `categories` VALUES (9, 'Cold Brew');
INSERT INTO `categories` VALUES (10, 'Iced Coffee');
INSERT INTO `categories` VALUES (14, 'CC');
INSERT INTO `categories` VALUES (15, 'PP');
INSERT INTO `categories` VALUES (16, 'LLLLL');

-- ----------------------------
-- Table structure for coffees
-- ----------------------------
DROP TABLE IF EXISTS `coffees`;
CREATE TABLE `coffees`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `price` decimal(10, 2) NOT NULL,
  `category_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category_id`(`category_id`) USING BTREE,
  CONSTRAINT `coffees_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coffees
-- ----------------------------
INSERT INTO `coffees` VALUES (1, 'Espresso', 'https://ellianoscoffee.lunchbox.io/menu_image/item_201_pos,400,300', 2.30, 14);
INSERT INTO `coffees` VALUES (2, 'Double Espresso', 'https://ellianoscoffee.lunchbox.io/menu_image/item_202_pos,400,300', 6.00, 1);
INSERT INTO `coffees` VALUES (3, 'Ristretto', 'https://ellianoscoffee.lunchbox.io/menu_image/item_203_pos,400,300', 2.75, 1);
INSERT INTO `coffees` VALUES (4, 'Espresso Con Panna', 'https://ellianoscoffee.lunchbox.io/menu_image/item_204_pos,400,300', 3.25, 1);
INSERT INTO `coffees` VALUES (5, 'Americano', 'https://ellianoscoffee.lunchbox.io/menu_image/item_205_pos,400,300', 2.00, 2);
INSERT INTO `coffees` VALUES (6, 'Iced Americano', 'https://ellianoscoffee.lunchbox.io/menu_image/item_206_pos,400,300', 2.50, 2);
INSERT INTO `coffees` VALUES (7, 'Americano Misto', 'https://ellianoscoffee.lunchbox.io/menu_image/item_207_pos,400,300', 2.75, 2);
INSERT INTO `coffees` VALUES (8, 'Vanilla Americano', 'https://ellianoscoffee.lunchbox.io/menu_image/item_208_pos,400,300', 3.00, 2);
INSERT INTO `coffees` VALUES (9, 'Latte', 'https://ellianoscoffee.lunchbox.io/menu_image/item_209_pos,400,300', 3.50, 3);
INSERT INTO `coffees` VALUES (10, 'Vanilla Latte', 'https://ellianoscoffee.lunchbox.io/menu_image/item_210_pos,400,300', 4.00, 3);
INSERT INTO `coffees` VALUES (11, 'Caramel Latte', 'https://ellianoscoffee.lunchbox.io/menu_image/item_211_pos,400,300', 4.25, 3);
INSERT INTO `coffees` VALUES (12, 'Hazelnut Latte', 'https://ellianoscoffee.lunchbox.io/menu_image/item_212_pos,400,300', 4.50, 3);
INSERT INTO `coffees` VALUES (13, 'Cappuccino', 'https://ellianoscoffee.lunchbox.io/menu_image/item_213_pos,400,300', 3.75, 4);
INSERT INTO `coffees` VALUES (14, 'Iced Cappuccino', 'https://ellianoscoffee.lunchbox.io/menu_image/item_214_pos,400,300', 4.00, 4);
INSERT INTO `coffees` VALUES (15, 'Flavored Cappuccino', 'https://ellianoscoffee.lunchbox.io/menu_image/item_215_pos,400,300', 4.25, 4);
INSERT INTO `coffees` VALUES (16, 'Cappuccino with Almond Milk', 'https://ellianoscoffee.lunchbox.io/menu_image/item_216_pos,400,300', 4.50, 4);
INSERT INTO `coffees` VALUES (17, 'Mocha', 'https://ellianoscoffee.lunchbox.io/menu_image/item_217_pos,400,300', 4.00, 5);
INSERT INTO `coffees` VALUES (18, 'White Chocolate Mocha', 'https://ellianoscoffee.lunchbox.io/menu_image/item_218_pos,400,300', 4.50, 5);
INSERT INTO `coffees` VALUES (19, 'Mint Mocha', 'https://ellianoscoffee.lunchbox.io/menu_image/item_219_pos,400,300', 4.75, 5);
INSERT INTO `coffees` VALUES (20, 'Mocha Frappuccino', 'https://ellianoscoffee.lunchbox.io/menu_image/item_220_pos,400,300', 5.00, 5);
INSERT INTO `coffees` VALUES (21, 'Macchiato', 'https://ellianoscoffee.lunchbox.io/menu_image/item_221_pos,400,300', 3.25, 6);
INSERT INTO `coffees` VALUES (22, 'Caramel Macchiato', 'https://ellianoscoffee.lunchbox.io/menu_image/item_222_pos,400,300', 4.00, 6);
INSERT INTO `coffees` VALUES (23, 'Iced Macchiato', 'https://ellianoscoffee.lunchbox.io/menu_image/item_223_pos,400,300', 4.25, 6);
INSERT INTO `coffees` VALUES (24, 'Almond Milk Macchiato', 'https://ellianoscoffee.lunchbox.io/menu_image/item_224_pos,400,300', 4.50, 6);
INSERT INTO `coffees` VALUES (25, 'Flat White', 'https://ellianoscoffee.lunchbox.io/menu_image/item_225_pos,400,300', 3.75, 7);
INSERT INTO `coffees` VALUES (26, 'Iced Flat White', 'https://ellianoscoffee.lunchbox.io/menu_image/item_226_pos,400,300', 4.00, 7);
INSERT INTO `coffees` VALUES (27, 'Vanilla Flat White', 'https://ellianoscoffee.lunchbox.io/menu_image/item_227_pos,400,300', 4.25, 7);
INSERT INTO `coffees` VALUES (28, 'Hazelnut Flat White', 'https://ellianoscoffee.lunchbox.io/menu_image/item_228_pos,400,300', 4.50, 7);
INSERT INTO `coffees` VALUES (29, 'Affogato', 'https://ellianoscoffee.lunchbox.io/menu_image/item_229_pos,400,300', 5.00, 8);
INSERT INTO `coffees` VALUES (30, 'Affogato with Chocolate', 'https://ellianoscoffee.lunchbox.io/menu_image/item_230_pos,400,300', 5.50, 8);
INSERT INTO `coffees` VALUES (31, 'Affogato with Almonds', 'https://ellianoscoffee.lunchbox.io/menu_image/item_231_pos,400,300', 5.75, 8);
INSERT INTO `coffees` VALUES (32, 'Iced Affogato', 'https://ellianoscoffee.lunchbox.io/menu_image/item_232_pos,400,300', 6.00, 8);
INSERT INTO `coffees` VALUES (33, 'Cold Brew', 'https://ellianoscoffee.lunchbox.io/menu_image/item_233_pos,400,300', 3.00, 9);
INSERT INTO `coffees` VALUES (34, 'Nitro Cold Brew', 'https://ellianoscoffee.lunchbox.io/menu_image/item_234_pos,400,300', 3.50, 9);
INSERT INTO `coffees` VALUES (35, 'Vanilla Cold Brew', 'https://ellianoscoffee.lunchbox.io/menu_image/item_235_pos,400,300', 3.75, 9);
INSERT INTO `coffees` VALUES (36, 'Cold Brew with Milk', 'https://ellianoscoffee.lunchbox.io/menu_image/item_236_pos,400,300', 4.00, 9);
INSERT INTO `coffees` VALUES (39, 'Iced Caramel Coffee', 'https://ellianoscoffee.lunchbox.io/menu_image/item_239_pos,400,300', 3.50, 10);

-- ----------------------------
-- Table structure for orderitems
-- ----------------------------
DROP TABLE IF EXISTS `orderitems`;
CREATE TABLE `orderitems`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NULL DEFAULT NULL,
  `coffee_id` int(11) NULL DEFAULT NULL,
  `size` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE,
  INDEX `coffee_id`(`coffee_id`) USING BTREE,
  CONSTRAINT `orderitems_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `orderitems_ibfk_2` FOREIGN KEY (`coffee_id`) REFERENCES `coffees` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `time` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `order_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `product_details` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, '2024/09/30', '08:23:15', '34 Princes Street, Auckland CBD, Auckland 1010 New Zealand', '22.50', 1, 'Pending', 'Double Espresso - $5 (x3)');
INSERT INTO `orders` VALUES (2, '2024/10/01', '09:47:22', 'Massey University East Precinct Albany Expressway, SH17, Albany, Auckland 0632 New Zealand', '18.00', 2, 'Completed', 'Latte - $4.50 (x2), Espresso - $3 (x1)');
INSERT INTO `orders` VALUES (3, '2024/10/15', '10:15:45', '340 Riverhead Road, Riverhead 0892 New Zealand', '15.50', 3, 'Pending', 'Flat White - $5 (x2)');
INSERT INTO `orders` VALUES (4, '2024/09/29', '11:52:33', '30 Ashley Avenue, Long Bay, Auckland 0630 New Zealand', '25.75', 4, 'Completed', 'Cappuccino - $4 (x3), Cold Brew - $4.75 (x1)');
INSERT INTO `orders` VALUES (5, '2024/10/05', '09:37:18', 'Ground Arthur Nathan Building, 36 - 38 Galway Street, Britomart, Auckland 1010 New Zealand', '12.50', 5, 'Pending', 'Affogato - $5 (x1), Espresso - $3 (x2)');
INSERT INTO `orders` VALUES (6, '2024/10/12', '10:46:59', '10-14 Lorne Street, Auckland CBD, Auckland 1010 New Zealand', '30.00', 6, 'Completed', 'Mocha - $6 (x3), Latte - $4.50 (x2)');
INSERT INTO `orders` VALUES (7, '2024/09/30', '12:03:27', 'Level 2/238 Broadway, Newmarket, Auckland 1023 New Zealand', '22.25', 7, 'Pending', 'Cappuccino - $4 (x1), Ristretto - $4.75 (x2)');
INSERT INTO `orders` VALUES (8, '2024/10/10', '13:29:54', 'Level 23, Huawei Centre, 120 Albert Street, Auckland 1010 New Zealand', '17.00', 8, 'Completed', 'Flat White - $5 (x2), Espresso - $3 (x1)');
INSERT INTO `orders` VALUES (9, '2024/09/28', '14:10:11', '70 Parkway Drive, Mairangi Bay, Auckland 0632 New Zealand', '19.50', 9, 'Pending', 'Cold Brew - $4.75 (x2), Americano - $3.50 (x1)');
INSERT INTO `orders` VALUES (10, '2024/10/20', '15:45:36', 'Convention Centre Level 5/88 Federal Street, Auckland CBD, Auckland 1010 New Zealand', '21.00', 10, 'Completed', 'Latte - $4.50 (x2), Espresso Con Panna - $5.25 (x1)');
INSERT INTO `orders` VALUES (11, '2024/09/29', '16:28:40', '34 Princes Street, Auckland CBD, Auckland 1010 New Zealand', '27.00', 1, 'Pending', 'Ristretto - $4.75 (x4)');
INSERT INTO `orders` VALUES (12, '2024/10/15', '17:12:22', 'Massey University East Precinct Albany Expressway, SH17, Albany, Auckland 0632 New Zealand', '23.50', 2, 'Completed', 'Cappuccino - $4 (x3), Mocha - $6 (x1)');
INSERT INTO `orders` VALUES (13, '2024/10/03', '18:05:15', '340 Riverhead Road, Riverhead 0892 New Zealand', '16.25', 3, 'Pending', 'Affogato - $5 (x1), Flat White - $5 (x1)');
INSERT INTO `orders` VALUES (14, '2024/09/28', '19:45:58', '30 Ashley Avenue, Long Bay, Auckland 0630 New Zealand', '20.00', 4, 'Completed', 'Espresso - $3 (x2), Americano - $3.50 (x1)');
INSERT INTO `orders` VALUES (15, '2024/10/25', '20:32:11', 'Ground Arthur Nathan Building, 36 - 38 Galway Street, Britomart, Auckland 1010 New Zealand', '24.00', 5, 'Pending', 'Cold Brew - $4.75 (x3)');
INSERT INTO `orders` VALUES (16, '2024/09/30', '21:10:44', '10-14 Lorne Street, Auckland CBD, Auckland 1010 New Zealand', '28.50', 6, 'Completed', 'Latte - $4.50 (x2), Ristretto - $4.75 (x3)');
INSERT INTO `orders` VALUES (17, '2024/10/10', '22:15:36', 'Level 2/238 Broadway, Newmarket, Auckland 1023 New Zealand', '19.00', 7, 'Pending', 'Double Espresso - $5 (x1), Mocha - $6 (x1)');
INSERT INTO `orders` VALUES (18, '2024/09/29', '22:58:22', 'Level 23, Huawei Centre, 120 Albert Street, Auckland 1010 New Zealand', '20.50', 8, 'Completed', 'Espresso Con Panna - $5.25 (x1), Cold Brew - $4.75 (x2)');
INSERT INTO `orders` VALUES (19, '2024/10/05', '23:42:17', '70 Parkway Drive, Mairangi Bay, Auckland 0632 New Zealand', '14.00', 9, 'Pending', 'Flat White - $5 (x1), Americano - $3.50 (x1)');
INSERT INTO `orders` VALUES (20, '2024/10/20', '23:15:34', 'Convention Centre Level 5/88 Federal Street, Auckland CBD, Auckland 1010 New Zealand', '22.75', 10, 'Completed', 'Cappuccino - $4 (x2), Ristretto - $4.75 (x1)');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `gender` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1000 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1001, 'OliviaDavis', '123456', 'OliviaDavis', 'olivia.davis@example.com', '0212345791', 'Female', 'Massey University East Precinct Albany Expressway, SH17, Albany, Auckland 0632 New Zealand');
INSERT INTO `users` VALUES (1000, 'EthanMiller', '123456', 'EthanMiller', 'ethan.miller@example.com', '0212345790', 'Male', '34 Princes Street, Auckland CBD, Auckland 1010 New Zealand');
INSERT INTO `users` VALUES (999, 'Admin', '123456', 'Admin', '1@qq.com', '0', '99', '0');
INSERT INTO `users` VALUES (1002, 'LucasMartinez', '123456', 'LucasMartinez', 'lucas.martinez@example.com', '0212345792', 'Male', '340 Riverhead Road, Riverhead 0892 New Zealand');
INSERT INTO `users` VALUES (1003, 'AvaLopez', '123456', 'AvaLopez', 'ava.lopez@example.com', '0212345793', 'Female', '30 Ashley Avenue, Long Bay, Auckland 0630 New Zealand');
INSERT INTO `users` VALUES (1004, 'NoahClark', '123456', 'NoahClark', 'noah.clark@example.com', '0212345794', 'Male', 'Ground Arthur Nathan Building, 36 - 38 Galway Street, Britomart, Auckland 1010 New Zealand');
INSERT INTO `users` VALUES (1005, 'MiaRodriguez', '123456', 'MiaRodriguez', 'mia.rodriguez@example.com', '0212345795', 'Female', '10-14 Lorne Street, Auckland CBD, Auckland 1010 New Zealand');
INSERT INTO `users` VALUES (1006, 'LiamWalker', '123456', 'LiamWalker', 'liam.walker@example.com', '0212345796', 'Male', 'Level 2/238 Broadway, Newmarket, Auckland 1023 New Zealand');
INSERT INTO `users` VALUES (1007, 'SophiaHall', '123456', 'SophiaHall', 'sophia.hall@example.com', '0212345797', 'Female', 'Level 23, Huawei Centre, 120 Albert Street, Auckland 1010 New Zealand');
INSERT INTO `users` VALUES (1008, 'JacksonLee', '123456', 'JacksonLee', 'jackson.lee@example.com', '0212345798', 'Male', '70 Parkway Drive, Mairangi Bay, Auckland 0632 New Zealand');
INSERT INTO `users` VALUES (1009, 'IsabellaTaylor', '123456', 'IsabellaTaylor', 'isabella.taylor@example.com', '0212345799', 'Female', 'Convention Centre Level 5/88 Federal Street, Auckland CBD, Auckland 1010 New Zealand');

SET FOREIGN_KEY_CHECKS = 1;
