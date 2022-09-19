/*
 Navicat Premium Data Transfer

 Source Server         : 10.11.9.23
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : 10.11.9.23:3306
 Source Schema         : platform

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 19/09/2022 16:34:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dsmp_cate
-- ----------------------------
DROP TABLE IF EXISTS `dsmp_cate`;
CREATE TABLE `dsmp_cate`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `cate_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目录名称',
  `cate_level` int NULL DEFAULT NULL COMMENT '目录级别',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除（0 否 1是）',
  `parent_id` int NULL DEFAULT NULL COMMENT '上一级目录ID',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目录描述',
  `dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '归属部门',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dsmp_cate
-- ----------------------------
INSERT INTO `dsmp_cate` VALUES (1, '电池溯源事业部数据表目录', 1, '2022-09-16 09:52:34', '2022-09-16 09:52:38', 0, 0, NULL, NULL, NULL);
INSERT INTO `dsmp_cate` VALUES (2, '电池溯源基础表目录', 2, '2022-09-15 09:52:51', '2022-09-16 09:52:44', 0, 1, NULL, NULL, NULL);
INSERT INTO `dsmp_cate` VALUES (3, 'X计划事业部数据表目录', 1, '2022-09-04 09:52:55', '2022-09-16 09:52:42', 0, 0, NULL, NULL, NULL);
INSERT INTO `dsmp_cate` VALUES (4, '重庆创新事业部数据表目录', 1, '2022-09-13 09:52:58', '2022-09-16 09:52:46', 0, 0, NULL, NULL, NULL);
INSERT INTO `dsmp_cate` VALUES (5, '电池溯源主题表目录', 2, '2022-09-16 10:31:27', '2022-09-16 10:31:30', 0, 1, NULL, NULL, NULL);
INSERT INTO `dsmp_cate` VALUES (6, '测试1', 3, NULL, NULL, 0, 2, NULL, NULL, NULL);
INSERT INTO `dsmp_cate` VALUES (7, '测试2', 3, NULL, NULL, 0, 2, NULL, NULL, NULL);
INSERT INTO `dsmp_cate` VALUES (8, '数据智能事业部数据表目录', 1, '2022-09-16 13:52:46', '2022-09-16 13:52:49', 0, 0, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
