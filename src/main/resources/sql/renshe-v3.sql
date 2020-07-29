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

 Date: 22/05/2020 18:03:01
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
  `type` int(1) NULL DEFAULT 1 COMMENT '1 区域群 2 行业群 3 规模群 4大群',
  `es_id` int(11) NULL DEFAULT NULL COMMENT '规模id',
  `industry_id` int(11) NULL DEFAULT NULL COMMENT '行业id',
  PRIMARY KEY (`chat_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dd_chat_at
-- ----------------------------
DROP TABLE IF EXISTS `dd_chat_at`;
CREATE TABLE `dd_chat_at`  (
  `at_id` int(11) NOT NULL AUTO_INCREMENT,
  `msg_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '',
  `msg_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  `create_at` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '消息的时间戳，单位ms',
  `conversation_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '	\r\n群名称',
  `sender_nick` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '发送者昵称',
  `sender_staff_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '发送者在企业内的userid（钉钉userId）（企业内部群有）',
  `flag` int(1) NULL DEFAULT 1 COMMENT '1 有效 0无效',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `record_time` datetime(0) NULL DEFAULT NULL COMMENT '消息回复时间',
  `msg_flag` int(1) NULL DEFAULT 1 COMMENT '1 未回复 2.已回复',
  `is_close` int(1) NULL DEFAULT 1 COMMENT '1开启，2关闭',
  `session_webhook` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '群消息地址',
  `session_webhook_expired_time` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '群消息地址失效时间',
  `receiving_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '接收的用户id',
  `is_read` int(1) NULL DEFAULT NULL COMMENT '1已读 2未读',
  PRIMARY KEY (`at_id`) USING BTREE,
  INDEX `dd_chat_at_receiving_user_id_IDX`(`receiving_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '@机器人之后要存放的' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dd_chat_at_record
-- ----------------------------
DROP TABLE IF EXISTS `dd_chat_at_record`;
CREATE TABLE `dd_chat_at_record`  (
  `record_id` int(11) NOT NULL AUTO_INCREMENT,
  `at_id` int(11) NULL DEFAULT NULL,
  `message_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文本内容',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '发送消息的用户id',
  `flag` int(1) NULL DEFAULT NULL COMMENT '1 有效 0 无效',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `question_or_answer` int(1) NULL DEFAULT NULL COMMENT '1 提问者 2 应答者',
  `chat_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '群名称',
  `is_read` int(1) NULL DEFAULT 2 COMMENT '是否已读 2 未读 1已读',
  PRIMARY KEY (`record_id`) USING BTREE,
  INDEX `chat_name`(`chat_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 449 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
  `file_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文件路径',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
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
  `area_id` int(11) NULL DEFAULT NULL COMMENT '提出问题者所在的区',
  `street_id` int(11) NULL DEFAULT NULL COMMENT '街道id',
  `grid_id` int(11) NULL DEFAULT NULL COMMENT '网格id',
  `is_look` int(1) NULL DEFAULT 0 COMMENT '用户是否已经查看反馈 1 查看 0 未查看',
  PRIMARY KEY (`problem_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '问题表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '反馈表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dd_user
-- ----------------------------
DROP TABLE IF EXISTS `dd_user`;
CREATE TABLE `dd_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '成员名称',
  `company_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '公司名称',
  `credit_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '统一社会信用代码',
  `unit_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '企业在社保系统中的单位编号',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企业在社保系统中的单位编号中的密码',
  `id_card` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '身份证号',
  `position` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '职务',
  `fixed_phone` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '固定电话',
  `dept_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机号码，企业内必须唯一，不可重复。',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '邮箱',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `flag` int(1) NULL DEFAULT 1 COMMENT '1 有效 0 无效',
  `dd_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '钉钉返回的 userId',
  `organize_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '组织代码',
  `city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '市',
  `area` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '区',
  `street` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '街道',
  `position_x` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'X轴坐标',
  `position_y` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Y轴坐标',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '企业详细地址',
  `is_admin` int(1) NULL DEFAULT 0 COMMENT '1 为超级管理员 2为管理员 0 为普通用户',
  `is_confirm` int(1) NULL DEFAULT 0 COMMENT '1 为已经认证，0为未认证',
  `area_id` int(11) NULL DEFAULT NULL COMMENT '区id',
  `street_id` int(11) NULL DEFAULT NULL COMMENT '街道id',
  `es_id` int(11) NULL DEFAULT NULL COMMENT '企业规模id  sys_enterprise_scale',
  `industry_id` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行业id     sys_enterprise_scale',
  `is_on_line` int(1) NULL DEFAULT NULL COMMENT '管理员是否在线 1 在线 0不在线',
  `is_inner_user` int(1) NULL DEFAULT 0 COMMENT '是否是内部人员 1 是 0 否',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `mobile`(`mobile`) USING BTREE,
  INDEX `dd_user_id`(`dd_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dd_user_chat
-- ----------------------------
DROP TABLE IF EXISTS `dd_user_chat`;
CREATE TABLE `dd_user_chat`  (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `chat_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '群id',
  `street_id` int(11) NULL DEFAULT NULL COMMENT '街道id',
  `grid_id` int(11) NULL DEFAULT NULL COMMENT '网格id',
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `chat_id`(`chat_id`) USING BTREE,
  INDEX `street_id`(`street_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin`  (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '管理员名称',
  `mobile` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `flag` int(1) NULL DEFAULT NULL COMMENT '1 有效 0无效',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '对应的用户id',
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理端用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area`  (
  `area_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区的名字',
  PRIMARY KEY (`area_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 370288 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '各个区' ROW_FORMAT = Dynamic;

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
  PRIMARY KEY (`permission_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 156 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '区域权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_enterprise_scale
-- ----------------------------
DROP TABLE IF EXISTS `sys_enterprise_scale`;
CREATE TABLE `sys_enterprise_scale`  (
  `es_id` int(11) NOT NULL AUTO_INCREMENT,
  `scale_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '规模名称',
  `flag` int(1) NULL DEFAULT 1 COMMENT '1 有效 0无效',
  `state` int(1) NOT NULL COMMENT '1 为 规模 2为行业',
  `create_user_id` int(11) NULL DEFAULT NULL COMMENT '创建者id',
  PRIMARY KEY (`es_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '行业与规模群' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '网格员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `job_id` int(11) NOT NULL AUTO_INCREMENT,
  `bean_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'bean 名称',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名称',
  `method_params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法参数',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'cron 表达式',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `job_status` int(1) NULL DEFAULT NULL COMMENT '1 正常 0暂停',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `execute_time` datetime(0) NULL DEFAULT NULL COMMENT '执行时间',
  `notice_id` int(11) NOT NULL COMMENT '发送的消息记录id',
  PRIMARY KEY (`job_id`) USING BTREE,
  INDEX `notice_id`(`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NULL DEFAULT NULL COMMENT '1 通知公告  2. 政策资讯',
  `msg_type` int(1) NULL DEFAULT NULL COMMENT '消息的类型1.Text 2.Image 3.File 4.Link 5.ActionCard 6.BtnActionCard ',
  `msg_content` json NULL COMMENT '消息内容',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `flag` int(1) NULL DEFAULT 1 COMMENT '1 有效 0无效',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '标题',
  `likes` int(11) NULL DEFAULT 0 COMMENT '点赞数',
  `reading_number` int(11) NULL DEFAULT 0 COMMENT '阅读数',
  `create_user_id` int(11) NULL DEFAULT NULL COMMENT '创建者id',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'url',
  `is_chat_msg` int(1) NULL DEFAULT NULL COMMENT '是否为群消息 1 是 0 否',
  `resend_time` datetime(0) NULL DEFAULT NULL COMMENT '重发时间',
  `is_resend` int(1) NULL DEFAULT NULL COMMENT '是否已经完成重新发送 1 已经完成 0 未发送',
  `chat_or_user` int(1) NULL DEFAULT NULL COMMENT '1 群消息 2 个人消息',
  `is_all_read` int(1) NULL DEFAULT 0 COMMENT '1 为全部已读 0为还有未读人员',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 307 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_notice_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice_area`;
CREATE TABLE `sys_notice_area`  (
  `notice_id` int(11) NOT NULL COMMENT '公告id',
  `street_id` int(11) NULL DEFAULT NULL COMMENT '街道id',
  `grid_id` int(11) NULL DEFAULT NULL COMMENT '网格id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `is_all` int(1) NULL DEFAULT 0 COMMENT '1 为全部，0为具体某个区或者街道网格，成员',
  INDEX `notice_id`(`notice_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通知-区域-用户-对应表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_notice_chat
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice_chat`;
CREATE TABLE `sys_notice_chat`  (
  `notice_id` int(11) NULL DEFAULT NULL COMMENT '通知，资讯id',
  `chat_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '群聊id',
  `dd_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id,这里为钉钉的userId',
  `msg_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息id',
  `is_all_read` int(1) NULL DEFAULT 0 COMMENT '消息是否全部已读 1为全部已读，0为还有未读',
  `read_user_list` json NULL COMMENT '已读人员列表',
  `read_num` int(11) NULL DEFAULT NULL COMMENT '已读人数',
  `unread_num` int(11) NULL DEFAULT NULL COMMENT '未读人数',
  INDEX `chat_id`(`chat_id`) USING BTREE,
  INDEX `notice_id`(`notice_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_on_line_count
-- ----------------------------
DROP TABLE IF EXISTS `sys_on_line_count`;
CREATE TABLE `sys_on_line_count`  (
  `on_line_count_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `count_current_date` date NULL DEFAULT NULL COMMENT '当天日期',
  `count_time` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '总计时间',
  PRIMARY KEY (`on_line_count_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员在线总时间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_on_line_time
-- ----------------------------
DROP TABLE IF EXISTS `sys_on_line_time`;
CREATE TABLE `sys_on_line_time`  (
  `on_line_id` int(11) NOT NULL AUTO_INCREMENT,
  `on_line_time` datetime(0) NULL DEFAULT NULL COMMENT '上线时间',
  `off_line_time` datetime(0) NULL DEFAULT NULL COMMENT '下线时间',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '管理员id',
  `on_line_current_date` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '当天日期',
  PRIMARY KEY (`on_line_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40774 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员在线时间段记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_poll_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_poll_admin`;
CREATE TABLE `sys_poll_admin`  (
  `poll_admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `poll_count` int(11) NULL DEFAULT NULL COMMENT '总数',
  `next_num` int(11) NULL DEFAULT NULL COMMENT '轮循到下一个管理员位置',
  `chat_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '群名称',
  `chat_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '群id',
  PRIMARY KEY (`poll_admin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '回复消息时轮询管理员的id' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_poll_chat
-- ----------------------------
DROP TABLE IF EXISTS `sys_poll_chat`;
CREATE TABLE `sys_poll_chat`  (
  `polling_id` int(11) NOT NULL AUTO_INCREMENT,
  `grid_count` int(11) NULL DEFAULT NULL COMMENT '街道下网格的总数',
  `previous_num` int(11) NULL DEFAULT NULL COMMENT '上一次为总数的哪个值',
  `street_id` int(11) NULL DEFAULT NULL COMMENT '街道id',
  PRIMARY KEY (`polling_id`) USING BTREE,
  INDEX `street_id`(`street_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群轮询表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '角色名称',
  `permission` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '角色对应的权限',
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
) ENGINE = MyISAM AUTO_INCREMENT = 37028520 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_info_swap
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_info_swap`;
CREATE TABLE `sys_user_info_swap`  (
  `swap_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '理由',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `company_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '公司名称',
  `credit_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '统一社会信用代码',
  `unit_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '企业在社保系统中的单位编号',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企业在社保系统中的单位编号中的密码',
  `id_card` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '身份证号',
  `position` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '职务',
  `fixed_phone` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '固定电话',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机号码，企业内必须唯一，不可重复。',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '邮箱',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `dd_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '钉钉返回的 userId',
  `organize_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '组织代码',
  `city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '市',
  `area` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '区',
  `street` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '街道',
  `position_x` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'X轴坐标',
  `position_y` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Y轴坐标',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '企业详细地址',
  `area_id` int(11) NULL DEFAULT NULL COMMENT '区id',
  `street_id` int(11) NULL DEFAULT NULL COMMENT '街道id',
  `es_id` int(11) NULL DEFAULT NULL COMMENT '企业规模id  sys_enterprise_scale',
  `industry_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行业id     sys_enterprise_scale',
  `is_pass` int(1) NULL DEFAULT 0 COMMENT '0 未审核  1 同意  2不同意 ',
  `select_chat_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户选择的街道id',
  `before_street_id` int(11) NULL DEFAULT NULL COMMENT '之前的街道id',
  `chat_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '申请加入群名称',
  `current_chat_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '当前群名称',
  `application_time` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `admin_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '管理员回复信息',
  `admin_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核人',
  PRIMARY KEY (`swap_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_swap
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_swap`;
CREATE TABLE `sys_user_swap`  (
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `swap_id` int(11) NULL DEFAULT NULL COMMENT 'swapid'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
