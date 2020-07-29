/*
 Navicat Premium Data Transfer

 Source Server         : 121.40.232.47
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 121.40.232.47:3306
 Source Schema         : renshe

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 15/04/2020 16:08:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dd_chat
-- ----------------------------
DROP TABLE IF EXISTS `dd_chat`;
CREATE TABLE `dd_chat`  (
  `chat_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '群名称',
  `owner` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '群主userId，员工唯一标识ID；必须为该会话useridlist的成员之一',
  `user_id_list` json NULL COMMENT '群成员列表，每次最多支持40人，群人数上限为1000',
  `show_history_type` int(1) NULL DEFAULT 0 COMMENT '新成员是否可查看聊天历史消息（新成员入群是否可查看最近100条聊天记录），\r\n\r\n0代表否；1代表是；不传默认为否',
  `search_able` int(1) NULL DEFAULT 0 COMMENT '群可搜索，0-默认，不可搜索，1-可搜索',
  `validation_type` int(1) NULL DEFAULT 0 COMMENT '入群验证，0：不入群验证（默认） 1：入群验证',
  `mention_all_authority` int(1) NULL DEFAULT 0 COMMENT '@ all 权限，0-默认，所有人，1-仅群主可@ all',
  `chat_banned_type` int(1) NULL DEFAULT 0 COMMENT '群禁言，0-默认，不禁言，1-全员禁言',
  `management_type` int(1) NULL DEFAULT 0 COMMENT '管理类型，0-默认，所有人可管理，1-仅群主可管理',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `flag` int(1) NULL DEFAULT 1 COMMENT '1有效 0无效',
  `manager_user` json NULL COMMENT '管理员id',
  `position` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '群所在的区',
  PRIMARY KEY (`chat_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dd_dept
-- ----------------------------
DROP TABLE IF EXISTS `dd_dept`;
CREATE TABLE `dd_dept`  (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `parent_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父部门id，根部门id为1',
  `dept_order` int(11) NULL DEFAULT NULL COMMENT '在父部门中的排序值，order值小的排序靠前',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `flag` int(1) NULL DEFAULT NULL COMMENT '1有效 0 无效',
  `dd_dept_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '钉钉返回的 部门id',
  `chat_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门对应的群id',
  `position` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门所在地区',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dd_message
-- ----------------------------
DROP TABLE IF EXISTS `dd_message`;
CREATE TABLE `dd_message`  (
  `msg_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息id',
  `chat_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '群id',
  `content` json NULL COMMENT '消息内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `msg_type` int(2) NULL DEFAULT NULL COMMENT '1.Text 2.Image 3.File 4.Link 5.ActionCard 6.BtnActionCard ',
  `content_type` int(2) NULL DEFAULT NULL COMMENT '内容类型， 1 通知消息 2 群消息',
  `mediaId` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '钉钉媒体id',
  `flag` int(1) NULL DEFAULT 1 COMMENT '1 有效 0 无效',
  `file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文件路径',
  PRIMARY KEY (`msg_id`) USING BTREE,
  INDEX `chat_id`(`chat_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dd_problem
-- ----------------------------
DROP TABLE IF EXISTS `dd_problem`;
CREATE TABLE `dd_problem`  (
  `problem_id` int(11) NOT NULL AUTO_INCREMENT,
  `problem_user_id` int(11) NULL DEFAULT NULL COMMENT '提出问题的用户id',
  `is_solve` int(1) NULL DEFAULT 0 COMMENT '1 已反馈 0 未反馈',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `problem_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '提出问题内容',
  `flag` int(1) NULL DEFAULT 1 COMMENT '1 有效 0 无效',
  PRIMARY KEY (`problem_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '问题表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dd_role
-- ----------------------------
DROP TABLE IF EXISTS `dd_role`;
CREATE TABLE `dd_role`  (
  `role_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色id',
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `group_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色组id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `flag` int(1) NULL DEFAULT NULL COMMENT '1 有效 0 无效',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dd_role_group
-- ----------------------------
DROP TABLE IF EXISTS `dd_role_group`;
CREATE TABLE `dd_role_group`  (
  `group_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色组id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `flag` int(1) NULL DEFAULT 1 COMMENT '1 有效 0 无效',
  `group_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '角色名称',
  PRIMARY KEY (`group_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dd_solve
-- ----------------------------
DROP TABLE IF EXISTS `dd_solve`;
CREATE TABLE `dd_solve`  (
  `solve_id` int(11) NOT NULL AUTO_INCREMENT,
  `problem_id` int(11) NULL DEFAULT NULL COMMENT '问题 id',
  `solve_time` datetime(0) NULL DEFAULT NULL COMMENT '反馈时间',
  `solve_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '反馈内容',
  `solve_user_id` int(11) NULL DEFAULT NULL COMMENT '反馈问题的人员',
  `flag` int(1) NULL DEFAULT 1 COMMENT '1 有效 0 无效',
  PRIMARY KEY (`solve_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '反馈表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dd_user
-- ----------------------------
DROP TABLE IF EXISTS `dd_user`;
CREATE TABLE `dd_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '成员名称',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机号码，企业内必须唯一，不可重复。',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '邮箱',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `flag` int(1) NULL DEFAULT 1 COMMENT '1 有效 0 无效',
  `dd_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '钉钉返回的 userId',
  `organize_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '组织代码',
  `city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '市',
  `area` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '区',
  `street` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '街道',
  `position_x` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'X轴坐标',
  `position_y` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Y轴坐标',
  `is_admin` int(1) NULL DEFAULT 0 COMMENT '1 为超级管理员 2为管理员 0 为普通用户',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `dd_user_id`(`dd_user_id`) USING BTREE,
  INDEX `mobile`(`mobile`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area`  (
  `area_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区的名字',
  PRIMARY KEY (`area_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '各个区' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_area_permissions
-- ----------------------------
DROP TABLE IF EXISTS `sys_area_permissions`;
CREATE TABLE `sys_area_permissions`  (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT,
  `area_id` int(11) NULL DEFAULT NULL COMMENT '区id',
  `street_id` int(11) NULL DEFAULT NULL COMMENT '街道id',
  `admin_type` int(1) NOT NULL COMMENT ' 1 super 2 市管理员 3 区管理员 4 街道管理员 5. 网格管理员',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `grid_id` int(11) NULL DEFAULT NULL COMMENT '网格id',
  `flag` int(1) NULL DEFAULT 1 COMMENT '1 有效 0 无效',
  PRIMARY KEY (`permission_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '区域权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_grid
-- ----------------------------
DROP TABLE IF EXISTS `sys_grid`;
CREATE TABLE `sys_grid`  (
  `grid_id` int(11) NOT NULL AUTO_INCREMENT,
  `street_id` int(11) NULL DEFAULT NULL COMMENT '街道id',
  `grid_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '网格名称',
  `chat_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '对应的群id',
  `flag` int(1) NULL DEFAULT 1 COMMENT ' 1 有效 0无效',
  PRIMARY KEY (`grid_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '网格员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '角色名称',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_street
-- ----------------------------
DROP TABLE IF EXISTS `sys_street`;
CREATE TABLE `sys_street`  (
  `street_id` int(11) NOT NULL AUTO_INCREMENT,
  `street_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '街道名称',
  `area_id` int(11) NULL DEFAULT NULL COMMENT '区id',
  PRIMARY KEY (`street_id`) USING BTREE,
  INDEX `area_id`(`area_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `flag` int(1) NULL DEFAULT 1 COMMENT '1 有效 0 无效',
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色中间表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
