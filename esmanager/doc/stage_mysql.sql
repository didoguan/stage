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

 Date: 26/12/2020 16:13:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
INSERT INTO `sys_dict` VALUES (1339495994707476486, 0, '0', '权限类型', 'permission_type', '权限类型', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1339495994707476487, 1339495994707476486, 'permission_type', '菜单', '01', '菜单', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (1339495994707476488, 1339495994707476486, 'permission_type', '数据', '02', '数据', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

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
INSERT INTO `sys_menu` VALUES (1338364322918240258, '系统管理', 'system', '0', '[0],', 'layui-icon layui-icon-username', '#', 1, 1, 'Y', 'ENABLE', NULL, NULL, '1', NULL, 1, '超级管理员', '2020-12-14 14:24:31', NULL, NULL, NULL);
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
INSERT INTO `sys_menu` VALUES (1338683513311645698, '行政管理', 'administrative', '0', '[0],', 'layui-icon-user', '#', 2, 1, 'Y', 'ENABLE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
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
INSERT INTO `sys_user` VALUES (1339762528067784705, '张三', 'zhangshan', '88aa46519981adb6ca283c04a6397a77', '8o399', '00002', 1339834652186939395, '销售一部', 'F', '13800138000', '', '441132199807213352', '2020-12-18', NULL, NULL, '', 'unmarried', '01', '04', '', '', '', NULL, NULL, 1, '超级管理员', '2020-12-18 10:40:53', NULL, NULL, NULL);

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

SET FOREIGN_KEY_CHECKS = 1;
