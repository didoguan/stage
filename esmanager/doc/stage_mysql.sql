/*
 Navicat Premium Data Transfer

 Source Server         : deepspc
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 139.159.191.121:3306
 Source Schema         : ec_test

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 23/08/2021 14:41:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ec_cost_center
-- ----------------------------
DROP TABLE IF EXISTS `ec_cost_center`;
CREATE TABLE `ec_cost_center`  (
  `cost_center_id` bigint NOT NULL,
  `order_no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `cost_content` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `cost_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '01-房租\r\n02-水电煤费\r\n03-网络费\r\n04-人工费\r\n05-交通费\r\n06-接待费\r\n07-办公耗材费\r\n08-采购费\r\n09-运输费\r\n10-冲抵',
  `cost_amount` decimal(8, 2) NULL DEFAULT NULL,
  `cost_date` date NULL DEFAULT NULL,
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creator_id` bigint NULL DEFAULT NULL,
  `creator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `updator_id` bigint NULL DEFAULT NULL,
  `updator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`cost_center_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ec_cost_center
-- ----------------------------
INSERT INTO `ec_cost_center` VALUES (1415843679940993026, NULL, '采购品革皮具样品', '08', 1480.60, '2021-07-05', '', 1412682819735134210, '关志伟', '2021-07-16 09:20:13', NULL, NULL, NULL);
INSERT INTO `ec_cost_center` VALUES (1415844166148907010, NULL, '淘宝店铺装修', '08', 59.70, '2021-07-12', '', 1412682819735134210, '关志伟', '2021-07-16 09:22:09', NULL, NULL, NULL);
INSERT INTO `ec_cost_center` VALUES (1415844419468091393, NULL, '摄影背景布', '08', 28.00, '2021-07-06', '', 1412682819735134210, '关志伟', '2021-07-16 09:23:09', NULL, NULL, NULL);
INSERT INTO `ec_cost_center` VALUES (1417304198249992194, NULL, '淘宝保险保证金年缴', '11', 30.00, '2021-07-16', '淘宝店铺保险保证金费用，按年缴纳每年30。', 1412682819735134210, '关志伟', '2021-07-20 10:03:48', NULL, NULL, NULL);
INSERT INTO `ec_cost_center` VALUES (1417395765790920706, NULL, '购买单号网代理发货权', '08', 100.00, '2021-07-20', '在单号网http://www.danhw.com/购买快递单号代理权，用于店铺发送礼品包裹刷单', 1412682819735134210, '关志伟', '2021-07-20 16:07:39', NULL, NULL, NULL);
INSERT INTO `ec_cost_center` VALUES (1418481947710869505, NULL, '箱包样品退货邮费', '09', 60.00, '2021-07-18', '退回泽泽箱包样品邮费9.7KG', 1412682819735134210, '关志伟', '2021-07-23 16:03:45', NULL, NULL, NULL);
INSERT INTO `ec_cost_center` VALUES (1423471710671007745, NULL, '淘宝直通车推广费用', '12', 210.00, '2021-07-30', '', 1412682819735134210, '关志伟', '2021-08-06 10:31:17', NULL, NULL, NULL);
INSERT INTO `ec_cost_center` VALUES (1424641836254498817, NULL, '高士利箱包样品退货邮费', '09', 12.00, '2021-08-09', '', 1412682819735134210, '关志伟', '2021-08-09 16:00:57', 1412682819735134210, '关志伟', '2021-08-09 16:01:11');
INSERT INTO `ec_cost_center` VALUES (1424918956948082690, NULL, '淘宝商品刷单费用', '12', 32.00, '2021-08-09', '刷了4单，单笔佣金8元', 1412682819735134210, '关志伟', '2021-08-10 10:22:08', NULL, NULL, NULL);
INSERT INTO `ec_cost_center` VALUES (1425283081188470785, NULL, '淘宝商品刷单费用', '12', 26.00, '2021-08-10', '一共刷了4单，其中2单费用16元，每单8元。另两单10元，每单5元。', 1412682819735134210, '关志伟', '2021-08-11 10:29:02', NULL, NULL, NULL);
INSERT INTO `ec_cost_center` VALUES (1425692225368285185, NULL, '淘宝商品刷单费用', '12', 25.00, '2021-08-11', '一共刷了5单，每单5元', 1412682819735134210, '关志伟', '2021-08-12 13:34:49', NULL, NULL, NULL);
INSERT INTO `ec_cost_center` VALUES (1426088322167287809, NULL, '淘宝商品刷单费用', '12', 30.00, '2021-08-12', '一共刷了6单，每单5元', 1412682819735134210, '关志伟', '2021-08-13 15:48:46', NULL, NULL, NULL);
INSERT INTO `ec_cost_center` VALUES (1426418834094260226, NULL, '淘宝直通车充值', '12', 610.00, '2021-08-14', '', 1412682819735134210, '关志伟', '2021-08-14 13:42:06', NULL, NULL, NULL);
INSERT INTO `ec_cost_center` VALUES (1426419044023369729, NULL, '淘宝商品刷单费用', '12', 20.00, '2021-08-14', '刷了4单，每单5元', 1412682819735134210, '关志伟', '2021-08-14 13:42:56', 1412682819735134210, '关志伟', '2021-08-14 16:11:53');

-- ----------------------------
-- Table structure for ec_goods_attachment
-- ----------------------------
DROP TABLE IF EXISTS `ec_goods_attachment`;
CREATE TABLE `ec_goods_attachment`  (
  `goods_attachment_id` bigint NOT NULL,
  `goods_id` bigint NULL DEFAULT NULL,
  `original_file_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `new_file_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `file_category` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '01-图片\r\n02-视频\r\n03-work\r\n04-excel\r\n05-pdf',
  `file_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '01-条形码\r\n02-颜色\r\n03-说明书\r\n04-简介\r\n05-主图\r\n06-详情',
  `file_size` int NULL DEFAULT NULL,
  `file_path` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creator_id` bigint NULL DEFAULT NULL,
  `creator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `updator_id` bigint NULL DEFAULT NULL,
  `updator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`goods_attachment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ec_goods_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for ec_goods_info
-- ----------------------------
DROP TABLE IF EXISTS `ec_goods_info`;
CREATE TABLE `ec_goods_info`  (
  `goods_id` bigint NOT NULL,
  `goods_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `goods_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `goods_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '00-淘汰产品\r\n01-常规产品\r\n02-季节产品',
  `category_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `category_code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `brand_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `brand_code` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creator_id` bigint NULL DEFAULT NULL,
  `creator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `updator_id` bigint NULL DEFAULT NULL,
  `updator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ec_goods_info
-- ----------------------------
INSERT INTO `ec_goods_info` VALUES (1412701946239623170, '单肩斜跨手提真皮时尚女包2018款', NULL, '01', '箱包', '26', NULL, NULL, 1412682819735134210, '关志伟', '2021-07-07 17:16:05', 1412682819735134210, '关志伟', '2021-07-07 17:59:05');
INSERT INTO `ec_goods_info` VALUES (1412713321808646146, '单肩贝壳斜跨牛皮女包2021款', '9812-D', '01', '箱包', '26', NULL, NULL, 1412682819735134210, '关志伟', '2021-07-07 18:01:18', 1412682819735134210, '关志伟', '2021-07-16 18:00:38');

-- ----------------------------
-- Table structure for ec_goods_property
-- ----------------------------
DROP TABLE IF EXISTS `ec_goods_property`;
CREATE TABLE `ec_goods_property`  (
  `property_id` bigint NOT NULL,
  `property_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `category_code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `category_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `multiple_choice` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sort` int NULL DEFAULT NULL,
  PRIMARY KEY (`property_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ec_goods_property
-- ----------------------------
INSERT INTO `ec_goods_property` VALUES (1412669125412261889, '款式', '26', '箱包', 'Y', 1);
INSERT INTO `ec_goods_property` VALUES (1412669444871426050, '皮质特征', '26', '箱包', 'N', 2);
INSERT INTO `ec_goods_property` VALUES (1412669561791844354, '里料', '26', '箱包', 'N', 3);

-- ----------------------------
-- Table structure for ec_goods_property_info
-- ----------------------------
DROP TABLE IF EXISTS `ec_goods_property_info`;
CREATE TABLE `ec_goods_property_info`  (
  `goods_id` bigint NOT NULL,
  `property_id` bigint NOT NULL,
  `property_value_id` bigint NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ec_goods_property_info
-- ----------------------------
INSERT INTO `ec_goods_property_info` VALUES (1412701946239623170, 1412669125412261889, 1412696680374837250);
INSERT INTO `ec_goods_property_info` VALUES (1412701946239623170, 1412669125412261889, 1412696680374837252);
INSERT INTO `ec_goods_property_info` VALUES (1412701946239623170, 1412669125412261889, 1412696680374837254);
INSERT INTO `ec_goods_property_info` VALUES (1412701946239623170, 1412669444871426050, 1412669444938534914);
INSERT INTO `ec_goods_property_info` VALUES (1412701946239623170, 1412669561791844354, 1412669561846370305);
INSERT INTO `ec_goods_property_info` VALUES (1412713321808646146, 1412669125412261889, 1412696680374837250);
INSERT INTO `ec_goods_property_info` VALUES (1412713321808646146, 1412669444871426050, 1412669444938534914);
INSERT INTO `ec_goods_property_info` VALUES (1412713321808646146, 1412669561791844354, 1412669561846370305);

-- ----------------------------
-- Table structure for ec_goods_property_value
-- ----------------------------
DROP TABLE IF EXISTS `ec_goods_property_value`;
CREATE TABLE `ec_goods_property_value`  (
  `property_value_id` bigint NOT NULL,
  `property_id` bigint NULL DEFAULT NULL,
  `property_value_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `category_code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `category_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sort` int NULL DEFAULT NULL,
  PRIMARY KEY (`property_value_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ec_goods_property_value
-- ----------------------------
INSERT INTO `ec_goods_property_value` VALUES (1412669444938534914, 1412669444871426050, '头层牛皮', '26', '箱包', 1);
INSERT INTO `ec_goods_property_value` VALUES (1412669444938534915, 1412669444871426050, '二层牛皮', '26', '箱包', 2);
INSERT INTO `ec_goods_property_value` VALUES (1412669561846370305, 1412669561791844354, '真皮', '26', '箱包', 1);
INSERT INTO `ec_goods_property_value` VALUES (1412669561846370306, 1412669561791844354, 'PU', '26', '箱包', 2);
INSERT INTO `ec_goods_property_value` VALUES (1412696680374837250, 1412669125412261889, '单肩包', '26', '箱包', 1);
INSERT INTO `ec_goods_property_value` VALUES (1412696680374837251, 1412669125412261889, '双肩包', '26', '箱包', 2);
INSERT INTO `ec_goods_property_value` VALUES (1412696680374837252, 1412669125412261889, '斜跨包', '26', '箱包', 3);
INSERT INTO `ec_goods_property_value` VALUES (1412696680374837253, 1412669125412261889, '托特包', '26', '箱包', 4);
INSERT INTO `ec_goods_property_value` VALUES (1412696680374837254, 1412669125412261889, '小方包', '26', '箱包', 5);
INSERT INTO `ec_goods_property_value` VALUES (1412696680374837255, 1412669125412261889, '手提包', '26', '箱包', 6);
INSERT INTO `ec_goods_property_value` VALUES (1412696680374837256, 1412669125412261889, '旅行包', '26', '箱包', 7);

-- ----------------------------
-- Table structure for ec_goods_sku
-- ----------------------------
DROP TABLE IF EXISTS `ec_goods_sku`;
CREATE TABLE `ec_goods_sku`  (
  `goods_sku_id` bigint NOT NULL,
  `goods_id` bigint NULL DEFAULT NULL,
  `sku` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `color_pic_id` bigint NULL DEFAULT NULL,
  `barcode_pic_id` bigint NULL DEFAULT NULL,
  `barcode_value` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`goods_sku_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ec_goods_sku
-- ----------------------------

-- ----------------------------
-- Table structure for ec_purchase_order
-- ----------------------------
DROP TABLE IF EXISTS `ec_purchase_order`;
CREATE TABLE `ec_purchase_order`  (
  `purchase_order_id` bigint NOT NULL,
  `purchase_order_no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `supplier_id` bigint NULL DEFAULT NULL,
  `supplier_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `purchase_quantity` decimal(8, 2) NULL DEFAULT NULL,
  `arrive_total_quantity` decimal(8, 2) NULL DEFAULT NULL,
  `total_amount` decimal(8, 2) NULL DEFAULT NULL,
  `pay_account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pay_date` date NULL DEFAULT NULL,
  `order_status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '00-作废\r\n01-待付款\r\n02-待收货\r\n03-收货中\r\n04-已收货',
  `purchaser_id` bigint NULL DEFAULT NULL,
  `purchaser_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `purchase_date` date NULL DEFAULT NULL,
  `expect_arrive_date` date NULL DEFAULT NULL,
  `actual_arrive_date` date NULL DEFAULT NULL,
  `express_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '多个单号用逗号隔开',
  `express_way` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '01-公路\r\n02-铁路\r\n03-船运\r\n04-空运',
  `express_comp` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creator_id` bigint NULL DEFAULT NULL,
  `creator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `updator_id` bigint NULL DEFAULT NULL,
  `updator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`purchase_order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ec_purchase_order
-- ----------------------------
INSERT INTO `ec_purchase_order` VALUES (1392028489994846210, 'PO20210511-0002', 1343498425531682817, '测试', 40.00, 40.00, 820.00, '5187180012404392', '2021-05-10', '04', 1387703311789842434, '李四', '2021-05-10', '2021-05-13', '2021-05-11', NULL, NULL, NULL, '补填采购', 1, '超级管理员', '2021-05-11 16:07:09', 1, '超级管理员', '2021-05-11 17:01:08', NULL);
INSERT INTO `ec_purchase_order` VALUES (1392655634672689153, 'PO20210513-0003', 1343498425531682817, '测试', 30.00, 14.00, 645.00, '5187180012404392', '2021-05-13', '03', 1387703311789842434, '李四', '2021-05-13', '2021-05-17', NULL, 'YT5456561864784', NULL, NULL, '', 1, '超级管理员', '2021-05-13 09:39:12', 1, '超级管理员', '2021-05-13 15:17:21', NULL);
INSERT INTO `ec_purchase_order` VALUES (1394207188169056257, 'PO20210517-0004', 1343498425531682817, '测试', 10.00, 4.00, 270.00, '6225887577442938', '2021-05-17', '03', 1387703311789842434, '李四', '2021-05-17', '2021-05-20', NULL, '', NULL, NULL, '', 1, '超级管理员', '2021-05-17 16:24:31', 1, '超级管理员', '2021-05-17 16:24:31', NULL);

-- ----------------------------
-- Table structure for ec_purchase_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `ec_purchase_order_detail`;
CREATE TABLE `ec_purchase_order_detail`  (
  `order_detail_id` bigint NOT NULL,
  `purchase_order_id` bigint NULL DEFAULT NULL,
  `sku` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `category_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `category_code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `goods_unit` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `detail_quantity` decimal(8, 2) NULL DEFAULT NULL,
  `single_price` decimal(8, 2) NULL DEFAULT NULL,
  `arrive_quantity` decimal(8, 2) NULL DEFAULT NULL,
  `stock_entry` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Y-是 N-否',
  `location_no` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`order_detail_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ec_purchase_order_detail
-- ----------------------------
INSERT INTO `ec_purchase_order_detail` VALUES (1392028490091315201, 1392028489994846210, '1388059006137532418', '箱包', '26', '个', 20.00, 24.00, 20.00, 'Y', '', '');
INSERT INTO `ec_purchase_order_detail` VALUES (1392028490091315202, 1392028489994846210, '1388059006187864066', '箱包', '26', '个', 20.00, 17.00, 20.00, 'Y', '', '');
INSERT INTO `ec_purchase_order_detail` VALUES (1392655635318611969, 1392655634672689153, '1388059006187864066', '箱包', '26', '个', 15.00, 20.00, 8.00, 'Y', '', '');
INSERT INTO `ec_purchase_order_detail` VALUES (1392655635318611970, 1392655634672689153, '1388059209041182721', '箱包', '26', '个', 15.00, 23.00, 6.00, 'Y', '', '');
INSERT INTO `ec_purchase_order_detail` VALUES (1394207188244553729, 1394207188169056257, '1394205821937442818', '箱包', '26', '个', 10.00, 27.00, 4.00, 'Y', '', '');

-- ----------------------------
-- Table structure for ec_stock_detail
-- ----------------------------
DROP TABLE IF EXISTS `ec_stock_detail`;
CREATE TABLE `ec_stock_detail`  (
  `stock_detail_id` bigint NOT NULL,
  `order_no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `relate_id` bigint NULL DEFAULT NULL,
  `sku` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `category_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `category_code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `operation_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '01-出库\r\n02-入库\r\n03-买家退回\r\n04-退回供应商\r\n05-损坏',
  `goods_unit` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `quantity` decimal(8, 2) NULL DEFAULT NULL,
  `single_price` decimal(8, 2) NULL DEFAULT NULL,
  `creator_id` bigint NULL DEFAULT NULL,
  `creator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`stock_detail_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ec_stock_detail
-- ----------------------------
INSERT INTO `ec_stock_detail` VALUES (1392042073990684673, 'PO20210511-0002', 1392028489994846210, '1388059006187864066', '箱包', '26', '02', '个', 20.00, 17.00, 1, '超级管理员', '2021-05-11 17:01:08');
INSERT INTO `ec_stock_detail` VALUES (1392042073990684674, 'PO20210511-0002', 1392028489994846210, '1388059006137532418', '箱包', '26', '02', '个', 20.00, 24.00, 1, '超级管理员', '2021-05-11 17:01:08');
INSERT INTO `ec_stock_detail` VALUES (1392655916580249602, 'PO20210513-0003', 1392655634672689153, '1388059209041182721', '箱包', '26', '02', '个', 6.00, 23.00, 1, '超级管理员', '2021-05-13 09:40:19');
INSERT INTO `ec_stock_detail` VALUES (1392655916580249603, 'PO20210513-0003', 1392655634672689153, '1388059006187864066', '箱包', '26', '02', '个', 8.00, 20.00, 1, '超级管理员', '2021-05-13 09:40:19');
INSERT INTO `ec_stock_detail` VALUES (1394207188458463233, 'PO20210517-0004', 1394207188169056257, '1394205821937442818', '箱包', '26', '02', '个', 4.00, 27.00, 1, '超级管理员', '2021-05-17 16:24:32');

-- ----------------------------
-- Table structure for ec_supplier_info
-- ----------------------------
DROP TABLE IF EXISTS `ec_supplier_info`;
CREATE TABLE `ec_supplier_info`  (
  `supplier_id` bigint NOT NULL,
  `supplier_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `supplier_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `company_contacts` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `contact_number` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `contact_address` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ali_url` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `start_level` int NULL DEFAULT NULL,
  `blacklist` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'N-否\r\nY-是',
  `supplier_status` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '01-正常\r\n02-冻结',
  `invoice_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `bank_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `bank_account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tax_number` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `return_address` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '退货地址',
  `remark` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creator_id` bigint NULL DEFAULT NULL,
  `creator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `updator_id` bigint NULL DEFAULT NULL,
  `updator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`supplier_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ec_supplier_info
-- ----------------------------
INSERT INTO `ec_supplier_info` VALUES (1412663468126638081, '广州品革皮具有限公司', 'GZ0001', '徐海霞', '13246469683', '广州市花都区狮岭镇金狮大道金辉花园', 'https://pingepiju.1688.com', 1, 'N', '01', '', '', '', '', NULL, '商品图片资源在QQ群：290344200', 1, '超级管理员', '2021-07-07 14:43:11', 1412682819735134210, '关志伟', '2021-07-21 13:55:50');
INSERT INTO `ec_supplier_info` VALUES (1412664488433029122, '保定白沟新城延梵箱包厂', 'BD0002', '李悦', '13472250102', '河北保定白沟新城和道国际辅料城三街172号', 'https://shop999k556q0s120.1688.com/', 1, 'N', '02', '', '', '', '', NULL, NULL, 1, '超级管理员', '2021-07-07 14:47:15', 1412682819735134210, '关志伟', '2021-08-23 14:07:20');
INSERT INTO `ec_supplier_info` VALUES (1416961472623067137, '广州市柏淘皮具有限公司', 'GZ0002', '王海鸿', '17688456534', '广州市白云区 嘉禾街大岭南工业区A栋501', 'https://paste666.1688.com', 1, 'N', '01', '', '', '', '', NULL, '商品图片资源下载地址：https://pan.baidu.com/s/1RsZRmz3fko6bTYjF4cfRVw 提取码: hxbj', 1412682819735134210, '关志伟', '2021-07-19 11:21:56', 1412682819735134210, '关志伟', '2021-07-21 13:54:32');
INSERT INTO `ec_supplier_info` VALUES (1418142102513885185, '深圳市韩衣舍贸易有限公司', 'GZ0003', '赖美源', '15917196637', '广州市花都区 狮领镇新民四队', 'https://shop736t6nu854622.1688.com', 1, 'N', '01', '', '', '', '', NULL, '商品图片由1274571209提供', 1412682819735134210, '关志伟', '2021-07-22 17:33:20', NULL, NULL, NULL);
INSERT INTO `ec_supplier_info` VALUES (1423470757737721857, ' 广州强士利皮具贸易有限公司', 'GZ0004', '王强', '13247310788', '广州市白云区嘉禾街望岗西岭工业园路10号A401', 'https://wqiang102.1688.com', 1, 'N', '01', '', '', '', '', '刘明兰,13247310788,广东省 广州市 白云区 广州市白云区嘉禾望岗西岭工业园10号', '商品图片和视频获取方式QQ群110950166，部分文件的密码wqiang', 1412682819735134210, '关志伟', '2021-08-06 10:27:30', 1412682819735134210, '关志伟', '2021-08-09 11:50:30');

-- ----------------------------
-- Table structure for ec_trade_account
-- ----------------------------
DROP TABLE IF EXISTS `ec_trade_account`;
CREATE TABLE `ec_trade_account`  (
  `account_id` bigint NOT NULL,
  `account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `account_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '01-微信\r\n02-支付宝\r\n03-网银',
  `account_status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '01-正常\r\n02-停用',
  `public_private` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'public\r\nprivate',
  `bank_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creator_id` bigint NULL DEFAULT NULL,
  `creator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `updator_id` bigint NULL DEFAULT NULL,
  `updator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`account_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ec_trade_account
-- ----------------------------
INSERT INTO `ec_trade_account` VALUES (1390129150116618242, '6225887577442938', '02', '01', 'private', '招商银行桂华六路支行', '绑定了微信及支付宝', 1, '超级管理员', '2021-05-06 10:19:51', 1, '超级管理员', '2021-05-06 10:21:15');
INSERT INTO `ec_trade_account` VALUES (1390130225825570818, '5187180012404392', '01', '01', 'private', '招商银行桂华六路支行', '绑定了微信及支付宝', 1, '超级管理员', '2021-05-06 10:24:08', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_attachment
-- ----------------------------
DROP TABLE IF EXISTS `sys_attachment`;
CREATE TABLE `sys_attachment`  (
  `attachment_id` bigint NOT NULL,
  `relate_id` bigint NULL DEFAULT NULL,
  `original_file_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `new_file_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `file_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'doc,xls,pdf',
  `file_catalog` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `file_size` int NULL DEFAULT NULL,
  `file_path` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `system_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creator_id` bigint NULL DEFAULT NULL,
  `creator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`attachment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_attachment
-- ----------------------------
INSERT INTO `sys_attachment` VALUES (1391958022785179650, 1391691991348994050, 'QQ截图20210412134113.png', '1391958022785179650.png', '.png', NULL, 76670, '/attachment/cost/1391958022785179650.png', NULL, NULL, NULL, NULL);
INSERT INTO `sys_attachment` VALUES (1391958022785179651, 1391691991348994050, '头像1.jpg', '1391958022785179651.jpg', '.jpg', NULL, 9529, '/attachment/cost/1391958022785179651.jpg', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint NOT NULL,
  `pid` bigint NULL DEFAULT NULL,
  `pids` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `dept_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `dept_code` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sort` int NULL DEFAULT NULL,
  `system_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creator_id` bigint NULL DEFAULT NULL,
  `creator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `updator_id` bigint NULL DEFAULT NULL,
  `updator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1286216691673337856, 0, '[0],', '佛山德朗司科技有限公司', 'dls', '佛山德朗司科技有限公司', 1, NULL, 1, '超级管理员', '2020-12-14 13:58:32', NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (1286216691673337857, 1286216691673337856, '[0],[1286216691673337856],', '总经办', 'manage', '总经办', 1, NULL, 1, '超级管理员', '2020-12-14 13:58:35', NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (1339832896199626754, 1286216691673337856, '[0],[1286216691673337856]', '人事行政部', 'administration', '人事行政部', 2, NULL, 1, '超级管理员', '2020-12-18 15:20:30', NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (1339834652186939394, 1286216691673337856, '[0],[1286216691673337856],', '销售部', 'sales', '销售部', 3, NULL, 1, '超级管理员', '2020-12-18 15:27:29', NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (1339834652186939395, 1339834652186939394, '[0],[1286216691673337856],[1339834652186939394]', '销售一部', 'sales_1', '销售一部', 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (1387702540352479234, 1286216691673337856, '[0],[1286216691673337856],', '采购部', 'purchase', '采购部', 5, NULL, 1, '超级管理员', '2021-04-29 17:37:23', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `dict_id` bigint NOT NULL,
  `parent_id` bigint NULL DEFAULT NULL,
  `parent_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `text` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sort` int NULL DEFAULT NULL,
  `system_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creator_id` bigint NULL DEFAULT NULL,
  `creator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `updator_id` bigint NULL DEFAULT NULL,
  `updator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1286856817449558017, 0, '0', '职位', 'position', '职位', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1286856817583775745, 1286856817449558017, 'position', '主管', '04', '主管', 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1288764724000755713, 1286856817449558017, 'position', '行政专员', '05', '行政专员', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1288764724097224705, 1286856817449558017, 'position', '跟单', '12', '跟单', 12, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1288764724386631681, 1286856817449558017, 'position', '仓管', '11', '仓管', 11, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1288764724449546242, 1286856817449558017, 'position', '会计', '06', '会计', 6, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1288764724512460802, 1286856817449558017, 'position', '客服专员', '08', '客服专员', 8, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1288764724579569665, 1286856817449558017, 'position', '出纳', '07', '出纳', 7, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1290526411380916225, 1286856817449558017, 'position', '采购专员', '10', '采购专员', 10, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1295553668432846850, 1286856817449558017, 'position', '副总经理', '02', '副总经理', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1295553668508344322, 1286856817449558017, 'position', '总经理', '01', '总经理', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1298886162326495234, 1286856817449558017, 'position', '经理助理', '03', '经理助理', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1303937261379006466, 1286856817449558017, 'position', '产品开发专员', '09', '产品开发专员', 9, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1339495994707476481, 0, '0', '用户状态', 'user_status', '用户状态', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1339495994707476482, 1339495994707476481, 'user_status', '正常', '01', '正常', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1339495994707476483, 1339495994707476481, 'user_status', '冻结', '02', '冻结', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1339495994707476484, 1339495994707476481, 'user_status', '离职', '03', '离职', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1339495994707476485, 1339495994707476481, 'user_status', '停职', '04', '停职', 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1339495994707476486, 0, '0', '权限类型', 'permission_type', '权限类型', 3, NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-05-15 11:04:09');
INSERT INTO `sys_dict` VALUES (1383235412761112578, 0, '0', '商品类型', 'goods_type', '商品类型', 4, NULL, 1, '超级管理员', '2021-04-17 09:46:36', 1, '超级管理员', '2021-04-17 09:47:00');
INSERT INTO `sys_dict` VALUES (1383235513218887682, 1383235412761112578, 'goods_type', '常规产品', '01', NULL, 1, NULL, 1, '超级管理员', '2021-04-17 09:47:00', 1, '超级管理员', '2021-04-17 09:47:00');
INSERT INTO `sys_dict` VALUES (1383235513218887683, 1383235412761112578, 'goods_type', '季节产品', '02', NULL, 2, NULL, 1, '超级管理员', '2021-04-17 09:47:00', 1, '超级管理员', '2021-04-17 09:47:00');
INSERT INTO `sys_dict` VALUES (1383235513218887684, 1383235412761112578, 'goods_type', '淘汰产品', '00', NULL, 99, NULL, 1, '超级管理员', '2021-04-17 09:47:00', 1, '超级管理员', '2021-04-17 09:47:00');
INSERT INTO `sys_dict` VALUES (1387582814561259522, 0, '0', '支付方式', 'pay_way', '支付方式', 5, NULL, 1, '超级管理员', '2021-04-29 09:41:38', 1, '超级管理员', '2021-05-06 10:03:24');
INSERT INTO `sys_dict` VALUES (1387583368268107777, 0, '0', '订单状态', 'order_status', '订单状态', 6, NULL, 1, '超级管理员', '2021-04-29 09:43:50', 1, '超级管理员', '2021-05-03 16:47:16');
INSERT INTO `sys_dict` VALUES (1387583734032388098, 0, '0', '快递方式', 'express_way', '快递方式', 7, NULL, 1, '超级管理员', '2021-04-29 09:45:17', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1387583734133051394, 1387583734032388098, 'express_way', '公路', '01', NULL, 1, NULL, 1, '超级管理员', '2021-04-29 09:45:17', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1387583734133051395, 1387583734032388098, 'express_way', '铁路', '02', NULL, 2, NULL, 1, '超级管理员', '2021-04-29 09:45:17', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1387583734133051396, 1387583734032388098, 'express_way', '船运', '03', NULL, 3, NULL, 1, '超级管理员', '2021-04-29 09:45:17', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1387583734133051397, 1387583734032388098, 'express_way', '空运', '04', NULL, 4, NULL, 1, '超级管理员', '2021-04-29 09:45:17', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1387584507730481153, 0, '0', '快递公司', 'express_comp', '快递公司', 8, NULL, 1, '超级管理员', '2021-04-29 09:48:21', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1387584507826950145, 1387584507730481153, 'express_comp', '顺丰', '01', NULL, 1, NULL, 1, '超级管理员', '2021-04-29 09:48:21', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1387584507826950146, 1387584507730481153, 'express_comp', '中通', '02', NULL, 2, NULL, 1, '超级管理员', '2021-04-29 09:48:21', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1387584507826950147, 1387584507730481153, 'express_comp', '圆通', '03', NULL, 3, NULL, 1, '超级管理员', '2021-04-29 09:48:21', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1387584507826950148, 1387584507730481153, 'express_comp', '韵达', '04', NULL, 4, NULL, 1, '超级管理员', '2021-04-29 09:48:21', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1387584507826950149, 1387584507730481153, 'express_comp', '申通', '05', NULL, 5, NULL, 1, '超级管理员', '2021-04-29 09:48:21', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1387584507826950150, 1387584507730481153, 'express_comp', '百世', '06', NULL, 6, NULL, 1, '超级管理员', '2021-04-29 09:48:21', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1387584507826950151, 1387584507730481153, 'express_comp', '天天', '07', NULL, 7, NULL, 1, '超级管理员', '2021-04-29 09:48:21', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1387584507826950152, 1387584507730481153, 'express_comp', 'EMS', '08', NULL, 8, NULL, 1, '超级管理员', '2021-04-29 09:48:21', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1389139482520686593, 1387583368268107777, 'order_status', '待付款', '01', NULL, 1, NULL, 1, '超级管理员', '2021-05-03 16:47:16', 1, '超级管理员', '2021-05-03 16:47:16');
INSERT INTO `sys_dict` VALUES (1389139482520686594, 1387583368268107777, 'order_status', '待收货', '02', NULL, 2, NULL, 1, '超级管理员', '2021-05-03 16:47:16', 1, '超级管理员', '2021-05-03 16:47:16');
INSERT INTO `sys_dict` VALUES (1389139482520686595, 1387583368268107777, 'order_status', '收货中', '03', NULL, 3, NULL, 1, '超级管理员', '2021-05-03 16:47:16', 1, '超级管理员', '2021-05-03 16:47:16');
INSERT INTO `sys_dict` VALUES (1389139482520686596, 1387583368268107777, 'order_status', '作废', '00', NULL, 5, NULL, 1, '超级管理员', '2021-05-03 16:47:16', 1, '超级管理员', '2021-05-03 16:47:16');
INSERT INTO `sys_dict` VALUES (1389139482520686597, 1387583368268107777, 'order_status', '已收货', '04', NULL, 4, NULL, 1, '超级管理员', '2021-05-03 16:47:16', 1, '超级管理员', '2021-05-03 16:47:16');
INSERT INTO `sys_dict` VALUES (1389772818624569346, 0, '0', '操作类型', 'operation_type', '库存管理中的单据操作类型', 10, NULL, 1, '超级管理员', '2021-05-05 10:43:55', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1389772819287269378, 1389772818624569346, 'operation_type', '出库', '01', NULL, 1, NULL, 1, '超级管理员', '2021-05-05 10:43:56', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1389772819287269379, 1389772818624569346, 'operation_type', '入库', '02', NULL, 2, NULL, 1, '超级管理员', '2021-05-05 10:43:56', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1389772819287269380, 1389772818624569346, 'operation_type', '买家退回', '03', NULL, 3, NULL, 1, '超级管理员', '2021-05-05 10:43:56', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1389772819287269381, 1389772818624569346, 'operation_type', '退回供应商', '04', NULL, 4, NULL, 1, '超级管理员', '2021-05-05 10:43:56', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1389772819287269382, 1389772818624569346, 'operation_type', '损坏', '05', NULL, 5, NULL, 1, '超级管理员', '2021-05-05 10:43:56', NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1390125010346958850, 1387582814561259522, 'pay_way', '信用卡', '01', NULL, 1, NULL, 1, '超级管理员', '2021-05-06 10:03:24', 1, '超级管理员', '2021-05-06 10:03:24');
INSERT INTO `sys_dict` VALUES (1390125010346958851, 1387582814561259522, 'pay_way', '储蓄卡', '02', NULL, 2, NULL, 1, '超级管理员', '2021-05-06 10:03:24', 1, '超级管理员', '2021-05-06 10:03:24');
INSERT INTO `sys_dict` VALUES (1391685743266353153, 0, '0', '成本类型', 'cost_type', '成本中心的成本类型', 11, NULL, 1, '超级管理员', '2021-05-10 17:25:12', 1412682819735134210, '关志伟', '2021-08-06 10:28:51');
INSERT INTO `sys_dict` VALUES (1393401787802497025, 1339495994707476486, 'permission_type', '菜单', '01', '菜单', 1, NULL, 1, '超级管理员', '2021-05-15 11:04:09', 1, '超级管理员', '2021-05-15 11:04:09');
INSERT INTO `sys_dict` VALUES (1393401787802497026, 1339495994707476486, 'permission_type', '数据', '02', '数据', 2, NULL, 1, '超级管理员', '2021-05-15 11:04:09', 1, '超级管理员', '2021-05-15 11:04:09');
INSERT INTO `sys_dict` VALUES (1393401787802497027, 1339495994707476486, 'permission_type', '语句', '03', NULL, 3, NULL, 1, '超级管理员', '2021-05-15 11:04:09', 1, '超级管理员', '2021-05-15 11:04:09');
INSERT INTO `sys_dict` VALUES (1423471096457129985, 1391685743266353153, 'cost_type', '房租', '01', NULL, 1, NULL, 1412682819735134210, '关志伟', '2021-08-06 10:28:51', 1412682819735134210, '关志伟', '2021-08-06 10:28:51');
INSERT INTO `sys_dict` VALUES (1423471096457129986, 1391685743266353153, 'cost_type', '水电煤费', '02', NULL, 2, NULL, 1412682819735134210, '关志伟', '2021-08-06 10:28:51', 1412682819735134210, '关志伟', '2021-08-06 10:28:51');
INSERT INTO `sys_dict` VALUES (1423471096457129987, 1391685743266353153, 'cost_type', '网络费', '03', NULL, 3, NULL, 1412682819735134210, '关志伟', '2021-08-06 10:28:51', 1412682819735134210, '关志伟', '2021-08-06 10:28:51');
INSERT INTO `sys_dict` VALUES (1423471096457129988, 1391685743266353153, 'cost_type', '人工费', '04', NULL, 4, NULL, 1412682819735134210, '关志伟', '2021-08-06 10:28:51', 1412682819735134210, '关志伟', '2021-08-06 10:28:51');
INSERT INTO `sys_dict` VALUES (1423471096457129989, 1391685743266353153, 'cost_type', '交通费', '05', NULL, 5, NULL, 1412682819735134210, '关志伟', '2021-08-06 10:28:51', 1412682819735134210, '关志伟', '2021-08-06 10:28:51');
INSERT INTO `sys_dict` VALUES (1423471096457129990, 1391685743266353153, 'cost_type', '接待费', '06', NULL, 6, NULL, 1412682819735134210, '关志伟', '2021-08-06 10:28:51', 1412682819735134210, '关志伟', '2021-08-06 10:28:51');
INSERT INTO `sys_dict` VALUES (1423471096457129991, 1391685743266353153, 'cost_type', '办公耗材费', '07', NULL, 7, NULL, 1412682819735134210, '关志伟', '2021-08-06 10:28:51', 1412682819735134210, '关志伟', '2021-08-06 10:28:51');
INSERT INTO `sys_dict` VALUES (1423471096457129992, 1391685743266353153, 'cost_type', '采购费', '08', NULL, 8, NULL, 1412682819735134210, '关志伟', '2021-08-06 10:28:51', 1412682819735134210, '关志伟', '2021-08-06 10:28:51');
INSERT INTO `sys_dict` VALUES (1423471096457129993, 1391685743266353153, 'cost_type', '运输费', '09', NULL, 9, NULL, 1412682819735134210, '关志伟', '2021-08-06 10:28:51', 1412682819735134210, '关志伟', '2021-08-06 10:28:51');
INSERT INTO `sys_dict` VALUES (1423471096457129994, 1391685743266353153, 'cost_type', '退回费用', '10', NULL, 10, NULL, 1412682819735134210, '关志伟', '2021-08-06 10:28:51', 1412682819735134210, '关志伟', '2021-08-06 10:28:51');
INSERT INTO `sys_dict` VALUES (1423471096457129995, 1391685743266353153, 'cost_type', '保险费-其他', '11', NULL, 11, NULL, 1412682819735134210, '关志伟', '2021-08-06 10:28:51', 1412682819735134210, '关志伟', '2021-08-06 10:28:51');
INSERT INTO `sys_dict` VALUES (1423471096457129996, 1391685743266353153, 'cost_type', '广告费', '12', NULL, 12, NULL, 1412682819735134210, '关志伟', '2021-08-06 10:28:51', 1412682819735134210, '关志伟', '2021-08-06 10:28:51');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pcode` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pcodes` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `icon` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `url` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sort` int NULL DEFAULT NULL,
  `levels` int NULL DEFAULT NULL,
  `menu_flag` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `new_page_flag` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `open_flag` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `system_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creator_id` bigint NULL DEFAULT NULL,
  `creator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `updator_id` bigint NULL DEFAULT NULL,
  `updator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1338364322918240258, '系统管理', 'system', '0', '[0],', 'layui-icon-set', '#', 1, 1, 'Y', 'ENABLE', NULL, NULL, '1', NULL, 1, '超级管理员', '2020-12-14 14:24:31', 1, '超级管理员', '2020-12-28 17:23:22');
INSERT INTO `sys_menu` VALUES (1338364763810918402, '用户管理', 'user_mgr', 'system', '[0],[system],', NULL, '/user', 1, 2, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2020-12-14 14:24:31', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1338365061744865282, '添加修改用户', 'add_modify_user', 'user_mgr', '[0],[system],[user_mgr],', '', '/user/addModifyPage', 1, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2020-12-14 14:24:31', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1338365332084490241, '删除用户', 'del_user', 'user_mgr', '[0],[system],[user_mgr],', NULL, '/user/deleteUser', 2, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2020-12-14 14:24:31', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1338365614306738177, '重置密码', 'reset_password', 'user_mgr', '[0],[system],[user_mgr],', NULL, '/user/resetPassword', 3, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2020-12-14 14:24:31', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1338365966187819010, '分配角色', 'user_roleset', 'user_mgr', '[0],[system],[user_mgr],', NULL, '/user/roleAssign', 4, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2020-12-14 14:24:31', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1338366156621746177, '菜单管理', 'menu_mgr', 'system', '[0],[system],', NULL, '/menu', 2, 2, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2020-12-14 14:24:31', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1338366429943656450, '添加修改菜单', 'add_modify_menu', 'menu_mgr', '[0],[system],[menu_mgr],', '', '/menu/addModifyPage', 1, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2020-12-14 14:24:31', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1338366643974717442, '删除菜单', 'del_menu', 'menu_mgr', '[0],[system],[menu_mgr],', NULL, '/menu/deleteMenu', 2, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2020-12-14 14:24:31', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1338366909318991874, '部门管理', 'dept_mgr', 'system', '[0],[system],', NULL, '/dept', 3, 2, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2020-12-14 14:24:31', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1338367055939264514, '添加修改部门', 'add_modify_dept', 'dept_mgr', '[0],[system],[dept_mgr],', '', '/dept/addModifyPage', 1, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2020-12-14 14:24:31', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1338367332172029953, '删除部门', 'del_dept', 'dept_mgr', '[0],[system],[dept_mgr],', NULL, '/dept/deleteDept', 2, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2020-12-14 14:24:31', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1338683513311645698, '行政管理', 'administrative', '0', '[0],', 'layui-icon-user', '#', 3, 1, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-05-06 09:40:22');
INSERT INTO `sys_menu` VALUES (1338690541694328834, '考勤记录', 'attendance_record', 'administrative', '[0],[administrative],', '', '#', 1, 2, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1338696374339608577, '考勤统计', 'attendance_statistics', 'administrative', '[0],[administrative],', '', '#', 2, 2, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1340138305288507394, '角色管理', 'role_mgr', 'system', '[0],[system],', '', '/role', 4, 2, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1340138691818786818, '添加修改角色', 'add_modify_role', 'role_mgr', '[0],[system],[role_mgr],', '', '/role/addModifyPage', 1, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1340138920248971266, '删除角色', 'del_role', 'role_mgr', '[0],[system],[role_mgr],', '', '/role/deleteRole', 2, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1340139268745302017, '用户分配', 'role_user_assign', 'role_mgr', '[0],[system],[role_mgr],', '', '/role/userAssign', 3, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1340139484403830786, '权限分配', 'role_permission_assign', 'role_mgr', '[0],[system],[role_mgr],', '', '/role/permissionAssign', 4, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1340139726855573506, '权限管理', 'permission_mgr', 'system', '[0],[system],', '', '/permission', 5, 2, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1340140131555577858, '添加修改权限', 'add_modify_permission', 'permission_mgr', '[0],[system],[permission_mgr],', '', '/permission/addModifyPage', 1, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1340140428952702977, '删除权限', 'del_permission', 'permission_mgr', '[0],[system],[permission_mgr],', '', '/permission/deletePermission', 2, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1340140744100122626, '用户分配', 'permission_user_assign', 'permission_mgr', '[0],[system],[permission_mgr],', '', '/permission/userAssign', 3, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1342363929080000514, '字典管理', 'dict_mgr', 'system', '[0],[system],', '', '/dict', 6, 2, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2020-12-25 14:57:55', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1342364183791693826, '添加修改字典', 'add_modify_dict', 'dict_mgr', '[0],[system],[dict_mgr],', '', '/dict/addModifyPage', 1, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2020-12-25 14:58:56', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1342364342239916034, '删除字典', 'delete_dict', 'dict_mgr', '[0],[system],[dict_mgr],', '', '/dict/deleteDict', 2, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2020-12-25 14:59:34', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1343487378246721537, '采购管理', 'purchase_mgr', '0', '[0],', 'layui-icon-cart', '#', 4, 1, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2020-12-28 17:22:06', 1, '超级管理员', '2021-05-06 09:40:27');
INSERT INTO `sys_menu` VALUES (1343487854509940738, '供应商管理', 'supplier_mgr', 'purchase_mgr', '[0],[purchase_mgr],', '', '/purchase/supplier', 1, 2, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2020-12-28 17:24:00', 1, '超级管理员', '2021-05-15 10:50:16');
INSERT INTO `sys_menu` VALUES (1343488123939446786, '添加修改供应商', 'add_modify_supplier', 'supplier_mgr', '[0],[purchase_mgr],[supplier_mgr],', '', '/purchase/addModifySupplierPage', 2, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2020-12-28 17:25:04', 1, '超级管理员', '2021-05-15 10:40:03');
INSERT INTO `sys_menu` VALUES (1343488293808758786, '删除供应商', 'delete_supplier', 'supplier_mgr', '[0],[purchase_mgr],[supplier_mgr],', '', '/supplier/deleteSupplier', 3, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2020-12-28 17:25:45', 1, '超级管理员', '2021-05-15 10:40:13');
INSERT INTO `sys_menu` VALUES (1383254088600010754, '商品管理', 'goods_mgr', '0', '[0],', 'layui-icon-tree', '#', 5, 1, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-04-17 11:00:49', 1, '超级管理员', '2021-05-06 09:40:33');
INSERT INTO `sys_menu` VALUES (1383254440137211905, '商品属性', 'goods_properties', 'goods_mgr', '[0],[goods_mgr],', '', '/goods/properties', 1, 2, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-04-17 11:02:13', 1, '超级管理员', '2021-04-17 11:14:04');
INSERT INTO `sys_menu` VALUES (1383255318265085954, '添加修改商品属性', 'goods_property_add_modify', 'goods_properties', '[0],[goods_mgr],[goods_properties],', '', '/goods/saveUpdateProperties', 1, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-04-17 11:05:42', 1, '超级管理员', '2021-04-17 11:14:25');
INSERT INTO `sys_menu` VALUES (1383256321618100225, '删除商品属性', 'goods_property_delete', 'goods_properties', '[0],[goods_mgr],[goods_properties],', '', '/goods/deleteProperties', 2, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-04-17 11:09:41', 1, '超级管理员', '2021-04-17 11:14:43');
INSERT INTO `sys_menu` VALUES (1383257764592893953, '商品信息', 'goods_info', 'goods_mgr', '[0],[goods_mgr],', '', '/goods', 2, 2, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-04-17 11:15:25', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1383258091941543938, '添加修改商品信息', 'goods_info_add_modify', 'goods_info', '[0],[goods_mgr],[goods_info],', '', '/goods/addModifyGoodsPage', 1, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-04-17 11:16:43', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1383258580041089026, '删除商品', 'goods_info_delete', 'goods_info', '[0],[goods_mgr],[goods_info],', '', '/goods/deleteGoods', 2, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-04-17 11:18:40', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1387347768902545409, '采购订单', 'purchase_order', 'purchase_mgr', '[0],[purchase_mgr],', '', '/purchase', 2, 2, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-04-28 18:07:38', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1387348237062369281, '添加修改采购单', 'add_modify_purchase_order', 'purchase_order', '[0],[purchase_mgr],[purchase_order],', '', '/purchase/addModifyPurchaseOrderPage', 1, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-04-28 18:09:30', 1, '超级管理员', '2021-04-28 18:09:51');
INSERT INTO `sys_menu` VALUES (1387348620526612482, '删除采购单', 'delete_purchase_order', 'purchase_order', '[0],[purchase_mgr],[purchase_order],', '', '/purchase/deletePurchaseOrder', 2, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-04-28 18:11:01', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1389770326092304386, '库存管理', 'stock_mgr', '0', '[0],', 'layui-icon-templeate-1', '#', 6, 1, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-05-05 10:34:01', 1, '超级管理员', '2021-05-06 09:40:40');
INSERT INTO `sys_menu` VALUES (1389770521735614466, '库存明细', 'stock_details', 'stock_mgr', '[0],[stock_mgr],', '', '/stock/details', 1, 2, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-05-05 10:34:48', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1389770761888878594, '库存汇总', 'stock_summary', 'stock_mgr', '[0],[stock_mgr],', '', '/stock/summary', 2, 2, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-05-05 10:35:45', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1390119161012543489, '基本设置', 'base_conf', '0', '[0],', 'layui-icon-tabs', '#', 2, 1, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-05-06 09:40:10', 1, '超级管理员', '2021-05-06 09:47:18');
INSERT INTO `sys_menu` VALUES (1390119559823745025, '交易账号管理', 'trade_account', 'base_conf', '[0],[base_conf],', 'layui-icon-star-fill', '/finance/tradeAccounts', 1, 2, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-05-06 09:41:45', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1390120131926810625, '添加修改账号', 'trade_account_add_modify', 'trade_account', '[0],[base_conf],[trade_account],', '', '/finance/addModifyTradeAccount', 1, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-05-06 09:44:01', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1390120700741541890, '删除账号', 'trade_account_delete', 'trade_account', '[0],[base_conf],[trade_account],', '', '/finance/deleteTradeAccount', 2, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-05-06 09:46:17', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1391682333318172674, '财务管理', 'finance_mgr', '0', '[0],', 'layui-icon-dollar', '#', 7, 1, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-05-10 17:11:39', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1391682594723975170, '成本中心', 'cost_center', 'finance_mgr', '[0],[finance_mgr],', '', '/cost/costCenter', 1, 2, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-05-10 17:12:41', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1391683281151184897, '添加修改成本', 'cost_center_add_modify', 'cost_center', '[0],[finance_mgr],[cost_center],', '', '/cost/addModifyCostCenter', 1, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-05-10 17:15:25', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1391683448688463874, '删除成本', 'cost_center_delete', 'cost_center', '[0],[finance_mgr],[cost_center],', '', '/cost/deleteCostCenter', 2, 3, 'N', 'ENABLE', NULL, NULL, NULL, NULL, 1, '超级管理员', '2021-05-10 17:16:05', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `permission_id` bigint NULL DEFAULT NULL,
  `permission_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `permission_type` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '01-菜单，02-数据',
  `content` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限内容，可以是编码，可以是url，可以是语句',
  `data_url` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '如果是语句则对应访问的页面地址',
  `system_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creator_id` bigint NULL DEFAULT NULL,
  `creator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `updator_id` bigint NULL DEFAULT NULL,
  `updator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1338363991836672002, '所有菜单查询及操作', '01', '', '#', NULL, 1, '超级管理员', '2020-12-14 14:07:13', 1, '超级管理员', '2020-12-19 17:19:05');
INSERT INTO `sys_permission` VALUES (1387349445831421953, '查看采购订单所有记录', '02', 'PurchaseOrderCheckAll', '/purchase', NULL, 1, '超级管理员', '2021-04-28 18:14:18', 1, '超级管理员', '2021-05-15 11:42:38');
INSERT INTO `sys_permission` VALUES (1387704032752951298, '采购普通权限', '01', 'PurchaseNormal', '#', NULL, 1, '超级管理员', '2021-04-29 17:43:18', 1, '超级管理员', '2021-05-05 10:37:15');
INSERT INTO `sys_permission` VALUES (1393401518465265665, '查看所有供应商', '02', 'SupplierCheckAll', '/purchase/supplier', NULL, 1, '超级管理员', '2021-05-15 11:03:05', 1, '超级管理员', '2021-05-15 11:41:59');

-- ----------------------------
-- Table structure for sys_permission_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_resource`;
CREATE TABLE `sys_permission_resource`  (
  `permission_id` bigint NOT NULL,
  `resource_id` bigint NOT NULL COMMENT '菜单资源或其它资源标识',
  PRIMARY KEY (`permission_id`, `resource_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission_resource
-- ----------------------------
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1338364322918240258);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1338364763810918402);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1338365061744865282);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1338365332084490241);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1338365614306738177);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1338365966187819010);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1338366156621746177);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1338366429943656450);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1338366643974717442);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1338366909318991874);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1338367055939264514);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1338367332172029953);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1338683513311645698);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1338690541694328834);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1338696374339608577);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1340138305288507394);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1340138691818786818);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1340138920248971266);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1340139268745302017);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1340139484403830786);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1340139726855573506);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1340140131555577858);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1340140428952702977);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1340140744100122626);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1342363929080000514);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1342364183791693826);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1342364342239916034);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1343487378246721537);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1343487854509940738);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1343488123939446786);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1343488293808758786);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1383254088600010754);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1383254440137211905);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1383255318265085954);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1383256321618100225);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1383257764592893953);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1383258091941543938);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1383258580041089026);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1387347768902545409);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1387348237062369281);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1387348620526612482);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1389770326092304386);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1389770521735614466);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1389770761888878594);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1390119161012543489);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1390119559823745025);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1390120131926810625);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1390120700741541890);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1391682333318172674);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1391682594723975170);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1391683281151184897);
INSERT INTO `sys_permission_resource` VALUES (1338363991836672002, 1391683448688463874);
INSERT INTO `sys_permission_resource` VALUES (1387704032752951298, 1343487378246721537);
INSERT INTO `sys_permission_resource` VALUES (1387704032752951298, 1343487854509940738);
INSERT INTO `sys_permission_resource` VALUES (1387704032752951298, 1343488293808758786);
INSERT INTO `sys_permission_resource` VALUES (1387704032752951298, 1387347768902545409);
INSERT INTO `sys_permission_resource` VALUES (1387704032752951298, 1387348237062369281);
INSERT INTO `sys_permission_resource` VALUES (1387704032752951298, 1387348620526612482);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint NOT NULL,
  `role_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role_code` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `system_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creator_id` bigint NULL DEFAULT NULL,
  `creator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `updator_id` bigint NULL DEFAULT NULL,
  `updator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1338363692514332674, '超级管理员', 'administrator', '超级管理员', NULL, 1, '超级管理员', '2020-12-14 14:03:10', NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (1340149901830176770, '行政主管', 'administration_leader', '行政主管角色', NULL, 1, '超级管理员', '2020-12-19 12:20:10', NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (1387703570939109377, '商品采购', 'goods_purchase', '商品采购', NULL, 1, '超级管理员', '2021-04-29 17:41:28', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `role_id` bigint NOT NULL,
  `permission_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1338363692514332674, 1338363991836672002);
INSERT INTO `sys_role_permission` VALUES (1338363692514332674, 1387349445831421953);
INSERT INTO `sys_role_permission` VALUES (1387703570939109377, 1387349445831421953);
INSERT INTO `sys_role_permission` VALUES (1387703570939109377, 1387704032752951298);
INSERT INTO `sys_role_permission` VALUES (1387703570939109377, 1393401518465265665);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL,
  `user_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `salt` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `dept_id` bigint NULL DEFAULT NULL,
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gender` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `contact_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `id_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `join_date` date NULL DEFAULT NULL,
  `desert_date` date NULL DEFAULT NULL,
  `official_date` date NULL DEFAULT NULL,
  `work_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `marriage` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_status` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `position` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `contact_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `emergency_contact_person` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `emergency_contact_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `avatar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `system_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creator_id` bigint NULL DEFAULT NULL,
  `creator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `updator_id` bigint NULL DEFAULT NULL,
  `updator_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '超级管理员', 'admin', 'f85793fcbac8b481bdd8c51b23ee7e41', 'qaqyw', 'administrator', 1286216691673337856, '佛山德朗司科技有限公司', 'M', '00000000000', NULL, '00000000000', '2020-01-01', NULL, '2020-01-01', '佛山市南海区', 'married', '01', '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (1412682819735134210, '关志伟', 'gzw', '6e63c21e9cf4d1cecc260534acac07cf', 'f0qra', '00001', 1286216691673337857, '总经办', 'M', '13602291009', 'didoguan@163.com', '441203198401250916', NULL, NULL, NULL, '', 'married', '01', '01', '', '', '', NULL, NULL, 1, '超级管理员', '2021-07-07 16:00:05', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_user_access
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_access`;
CREATE TABLE `sys_user_access`  (
  `user_id` bigint NOT NULL,
  `access_id` bigint NOT NULL COMMENT '角色标识或权限标识',
  PRIMARY KEY (`user_id`, `access_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_access
-- ----------------------------
INSERT INTO `sys_user_access` VALUES (1, 1338363692514332674);
INSERT INTO `sys_user_access` VALUES (1387703311789842434, 1387703570939109377);
INSERT INTO `sys_user_access` VALUES (1412682819735134210, 1338363692514332674);

-- ----------------------------
-- Table structure for ws_user
-- ----------------------------
DROP TABLE IF EXISTS `ws_user`;
CREATE TABLE `ws_user`  (
  `user_id` bigint NOT NULL,
  `user_account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `dept_id` bigint NULL DEFAULT NULL,
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `salt` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ws_user
-- ----------------------------
INSERT INTO `ws_user` VALUES (1371728323106615297, 'user1', 'aba48525c14b53051e5c5c62a8a0245b', '用户1', NULL, '一科', '01', '1qtr');
INSERT INTO `ws_user` VALUES (1371728919192739842, 'user2', '53730ec0d618c23edef4e5516b313364', '用户2', NULL, '二科', '01', 'es28');
INSERT INTO `ws_user` VALUES (1371729136499585026, 'user3', '6f6c02795fc2ae226ee85bd9fdb799a5', '用户3', NULL, '一科', '02', '3ums');

SET FOREIGN_KEY_CHECKS = 1;