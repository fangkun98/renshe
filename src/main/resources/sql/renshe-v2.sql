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

 Date: 08/05/2020 16:47:19
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
  `type` int(1) NULL DEFAULT 1 COMMENT '1 区域群 2 行业群 3 规模群',
  `es_id` int(11) NULL DEFAULT NULL COMMENT '规模id',
  `industry_id` int(11) NULL DEFAULT NULL COMMENT '行业id',
  PRIMARY KEY (`chat_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dd_chat
-- ----------------------------
INSERT INTO `dd_chat` VALUES ('chat0409412ba356849b9c960153f5419538', '临时群2', '105455511233021495', '[\"105455511233021495\", \"202565540023349933\", \"105455511233021495\"]', 0, 0, 0, 0, 0, 0, '2020-04-23 09:49:01', 1, NULL, '', 3, 1, NULL);
INSERT INTO `dd_chat` VALUES ('chat1b8d3e6f3778d934b82c69088c8f6641', '中山路街道2', '105455511233021495', '[\"105455511233021495\"]', 0, 0, 0, 1, 0, 0, '2020-04-20 18:47:21', 1, NULL, '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chat1de80b4699adafe7ffab2d22d7cfacac', '行业临时群', '105455511233021495', '[\"0008\", \"105455511233021495\"]', 0, 0, 0, 0, 0, 0, '2020-04-23 09:27:08', 1, NULL, '', 3, 1, NULL);
INSERT INTO `dd_chat` VALUES ('chat33cf6c61ab8fdbde9792ac98093b26d7', '黑龙江2', '202565540023349933', '[\"202565540023349933\"]', 0, 0, 0, 0, 0, 0, '2020-05-08 15:59:31', 1, '[\"202565540023349933\"]', '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chat345cb63e150d01a1aa0203a9216036b0', '中山路街道办事处网格', '105455511233021495', '[\"105455511233021495\"]', 0, 0, 0, 0, 0, 0, '2020-04-22 21:07:47', 1, NULL, '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chat370576f552e4669482281bc9df017d73', '中国14亿人沸腾！', '202565540023349933', '[\"105455511233021495\", \"202565540023349933\", \"2656524308682418\", \"105455511233021495\"]', 0, 0, 0, 0, 0, 0, '2020-04-24 09:24:14', 1, '[\"105455511233021495\"]', '', 2, 1, NULL);
INSERT INTO `dd_chat` VALUES ('chat500df87add6821424f802de94d6d9926', '敦化路网格2', '181931274124193877', '[\"181931274124193877\"]', 0, 0, 0, 0, 0, 0, '2020-04-23 02:44:11', 1, NULL, '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chat5109b0c1c4f9a3a81a559c5b747be40b', '1688软件园1', '186623261929468455', '[\"186623261929468455\"]', 0, 0, 0, 0, 0, 0, '2020-05-07 22:05:44', 1, '[\"186623261929468455\"]', '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chat554ee8f2f84d43c27470151dc6de9d04', '副食行业临时群', '105455511233021495', '[\"105455511233021495\", \"202565540023349933\", \"105455511233021495\"]', 0, 0, 0, 0, 0, 0, '2020-04-23 00:50:53', 1, NULL, '', 3, 1, NULL);
INSERT INTO `dd_chat` VALUES ('chat5e73cf24c81060b28f14cb3d17d318de', '八大关网格', '2656524308682418', '[\"2656524308682418\"]', 0, 0, 0, 1, 0, 0, '2020-04-22 11:06:42', 1, NULL, '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chat85bdde909001233307d21e25bd100e49', '香港中路街道网格2', '152746241238825728', '[\"202565540023349933\"]', 0, 0, 0, 1, 0, 0, '2020-04-21 14:44:10', 1, NULL, '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chat99bd593817cf805ad6031ac4da90b4be', '八大关街道2', '105455511233021495', '[\"105455511233021495\"]', 0, 0, 0, 1, 0, 0, '2020-04-20 18:48:12', 1, NULL, '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chat9b906c63d9c0425c26abc649e766059f', '中山路街道3', '105455511233021495', '[\"105455511233021495\"]', 0, 0, 0, 1, 0, 0, '2020-04-21 10:41:16', 1, NULL, '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chat9c40b4153aea1e5bdb009f16393e4053', '中山路街道1', '105455511233021495', '[\"105455511233021495\"]', 0, 0, 0, 0, 0, 0, '2020-04-20 18:42:28', 1, NULL, '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chatb476147c9bde3fca09874da4d7291c40', '台东路网格1', '152746241238825728', '[\"152746241238825728\"]', 0, 0, 0, 1, 0, 0, '2020-04-17 13:57:13', 1, NULL, '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chatbd58500df6f13d92a1b378225b744b5c', '上王埠村北', '2656524308682418', '[\"2656524308682418\"]', 0, 0, 0, 0, 0, 0, '2020-04-24 15:05:51', 1, NULL, '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chatc1950fae21b9d2cfdb703e029c4ec067', '上王埠网格', '105455511233021495', '[\"2656524308682418\"]', 0, 0, 0, 0, 0, 0, '2020-04-23 16:42:08', 1, NULL, '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chatc367fef1061b5f780a9368d2e01dfb65', '新增网格一', '105455511233021495', '[\"105455511233021495\"]', 0, 0, 0, 0, 0, 0, '2020-04-22 20:00:42', 1, NULL, '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chatcb6583881987ee4074a7109c0ec5fe49', '敦化路路网格1', '105455511233021495', '[\"105455511233021495\", \"152746241238825728\", \"2656524308682418\"]', 0, 0, 0, 1, 0, 0, '2020-04-17 13:57:46', 1, NULL, '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chatccf26d92c8c4a91d77c65495ce603ac8', '这是某条路上的一个名字较长的网格', '105455511233021495', '[\"105455511233021495\"]', 0, 0, 0, 1, 0, 0, '2020-04-22 11:13:40', 1, NULL, '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chatdad38ef6d4f58360788c54926bb14f97', '黑龙江1', '202565540023349933', '[\"202565540023349933\"]', 0, 0, 0, 0, 0, 0, '2020-05-08 15:59:09', 1, '[\"202565540023349933\"]', '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chatdb2d94cddade0098079436a5550380a0', '1688软件园', '186623261929468455', '[\"186623261929468455\"]', 0, 0, 0, 0, 0, 0, '2020-05-07 22:05:31', 1, '[\"186623261929468455\"]', '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chatdf8543aa644b73ebfb16c25eec83a8e6', '香港中路街道网格3', '105455511233021495', '[\"105455511233021495\"]', 0, 0, 0, 1, 0, 0, '2020-04-21 14:57:41', 1, NULL, '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chatefcede9dd31814c29a03f91024fae5bc', '中山路街道1', '105455511233021495', '[\"105455511233021495\"]', 0, 0, 0, 1, 0, 0, '2020-04-20 18:42:19', 1, NULL, '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chatf76e916b2b0f67bc6d90b4aa7b64aa79', '香港中路街道网格1', '186623261929468455', '[\"105455511233021495\", \"202565540023349933\"]', 0, 0, 0, 0, 0, 0, '2020-04-16 16:24:22', 1, NULL, '', 1, NULL, NULL);
INSERT INTO `dd_chat` VALUES ('chatff21cf2ca3a7c6d16dbabb6ec33e326d', '测试发送消息群', '202565540023349933', '[\"202565540023349933\", \"105455511233021495\"]', 0, 0, 0, 0, 0, 0, '2020-05-06 16:28:45', 1, '[\"202565540023349933\"]', '', 2, NULL, NULL);

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
  PRIMARY KEY (`at_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '@机器人之后要存放的' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dd_chat_at
-- ----------------------------
INSERT INTO `dd_chat_at` VALUES (39, 'msgKApXzRVp8Ae+2PI5WuV2Uw==', '  Game of rights  ', '1588744273655', '中国14亿人沸腾！', '孔毅松', '202565540023349933', 1, '2020-05-06 13:51:15', '2020-05-06 13:51:40', 2, 2, 'https://oapi.dingtalk.com/robot/sendBySession?session=0c00bd045a943c53b9c6a582f771a173', '1588749673709', '1');
INSERT INTO `dd_chat_at` VALUES (40, 'msgOZDXVzkdioUeyvUjp8npPQ==', ' 22222', '1588744314259', '中国14亿人沸腾！', '范恒昆', '105455511233021495', 1, '2020-05-06 13:51:55', '2020-05-06 13:53:55', 1, 2, 'https://oapi.dingtalk.com/robot/sendBySession?session=c0d2363250d6e8d2d400fea842ae4c0f', '1588749714302', '1');
INSERT INTO `dd_chat_at` VALUES (41, 'msgn6sdT/stVlZ+JVLVfP7mMg==', ' 冰与火之歌', '1588819270179', '中国14亿人沸腾！', '孔毅松', '202565540023349933', 1, '2020-05-07 10:41:12', '2020-05-07 10:51:12', 2, 2, 'https://oapi.dingtalk.com/robot/sendBySession?session=f89619525f953934f0883615684aa8bc', '1588838347409', '1');
INSERT INTO `dd_chat_at` VALUES (42, 'msgfZZ5eXj2/BIrJ82KjIyEKQ==', ' 1111', '1588832991755', '中国14亿人沸腾！', '孔毅松', '202565540023349933', 1, '2020-05-07 14:29:52', '2020-05-07 14:29:58', 1, 2, 'https://oapi.dingtalk.com/robot/sendBySession?session=c801686dd77ad71b49dc7fec0e578641', '1588859593469', '3');
INSERT INTO `dd_chat_at` VALUES (43, 'msgfKv7kXfSnm2qoxwZQTcASw==', ' 111', '1588833024911', '中国14亿人沸腾！', '范恒昆', '105455511233021495', 1, '2020-05-07 14:30:25', '2020-05-07 15:30:25', 2, 2, 'https://oapi.dingtalk.com/robot/sendBySession?session=de9c860a795670089b0680ba56624aa2', '1588838514541', '1');
INSERT INTO `dd_chat_at` VALUES (44, 'msgfVOC+QjXpY+Fqxhw6xbdrQ==', ' 啊实打实的', '1588853714061', '中国14亿人沸腾！', '范恒昆', '105455511233021495', 1, '2020-05-07 20:15:15', '2020-05-07 20:15:27', 2, 2, 'https://oapi.dingtalk.com/robot/sendBySession?session=600891b2580d5d84be5cd1c6bd465678', '1588859114117', '1');
INSERT INTO `dd_chat_at` VALUES (45, 'msgZ3N6FB7Wa0To8rywiQl0fg==', ' 111', '1588854363539', '中国14亿人沸腾！', '孔毅松', '202565540023349933', 1, '2020-05-07 20:26:04', '2020-05-07 20:26:38', 2, 2, 'https://oapi.dingtalk.com/robot/sendBySession?session=c801686dd77ad71b49dc7fec0e578641', '1588859763589', '1');
INSERT INTO `dd_chat_at` VALUES (46, 'msgjtYlKiISvwZudsSgCeK/hQ==', ' 555', '1588855631464', '中国14亿人沸腾！', '范恒昆', '105455511233021495', 1, '2020-05-07 20:47:14', NULL, 1, 2, 'https://oapi.dingtalk.com/robot/sendBySession?session=600891b2580d5d84be5cd1c6bd465678', '1588861031509', '1');
INSERT INTO `dd_chat_at` VALUES (47, 'msgRJTgXb/NK7iD23w0UMb14Q==', ' 111', '1588856409375', '中国14亿人沸腾！', '孔毅松', '202565540023349933', 1, '2020-05-07 21:00:10', '2020-05-07 21:00:52', 2, 1, 'https://oapi.dingtalk.com/robot/sendBySession?session=c801686dd77ad71b49dc7fec0e578641', '1588862104214', '1');
INSERT INTO `dd_chat_at` VALUES (48, 'msg0AFRMRDdcvAtr5XzVU0bhA==', ' 反反复复烦烦烦', '1588856745328', '中国14亿人沸腾！', '范恒昆', '105455511233021495', 1, '2020-05-07 21:05:46', '2020-05-07 21:06:37', 2, 1, 'https://oapi.dingtalk.com/robot/sendBySession?session=f306e27791f36fce6a6905bad47ac96a', '1588928777202', '1');
INSERT INTO `dd_chat_at` VALUES (49, 'msgkrMI3hHCg1sUOoSI4+0Dcw==', ' 你好', '1588860542469', '1688软件园1', '张恩松', '181931274124193877', 1, '2020-05-07 22:09:03', '2020-05-07 22:09:17', 2, 2, 'https://oapi.dingtalk.com/robot/sendBySession?session=1c6b1c33c84e1dfed8da29a78c8bf780', '1588866567140', '65');
INSERT INTO `dd_chat_at` VALUES (50, 'msghf894j+rx96PIX/+Aw3cNA==', ' 你好啊', '1588860758249', '1688软件园', '张恩松', '181931274124193877', 1, '2020-05-07 22:12:39', '2020-05-07 22:19:39', 2, 2, 'https://oapi.dingtalk.com/robot/sendBySession?session=cab86b2d63340240c97dcbaeb527fd77', '1588866582829', '65');
INSERT INTO `dd_chat_at` VALUES (51, 'msgKSPRxMOz+Pk26lrx9hjAcg==', ' 啦啦啦', '1588861460009', '1688软件园', '张瀚文', '0008', 1, '2020-05-07 22:24:21', '2020-05-07 22:24:39', 2, 2, 'https://oapi.dingtalk.com/robot/sendBySession?session=49500364a9f1fd5c30c1c249b267d70c', '1588910319089', '65');
INSERT INTO `dd_chat_at` VALUES (52, 'msgjMfw8haVYgRDuGI7yZpULw==', ' 你好啊', '1588901958663', '1688软件园1', '王绍辉', '186623261929468455', 1, '2020-05-08 09:39:19', NULL, 1, 2, 'https://oapi.dingtalk.com/robot/sendBySession?session=219cbfae6df0a3448afe9ff3735de96f', '1588907597003', '65');
INSERT INTO `dd_chat_at` VALUES (53, 'msgmxHBC6Kiqa0pMtC0wno/Tg==', ' 你好', '1588902061192', '1688软件园', '王绍辉', '186623261929468455', 1, '2020-05-08 09:41:02', NULL, 1, 2, 'https://oapi.dingtalk.com/robot/sendBySession?session=566fc3dff853bf51469a1d7fe4f8bbda', '1588907461235', '65');
INSERT INTO `dd_chat_at` VALUES (54, 'msg7TL+i0i/RtUsRQDHO3vvhA==', ' 问题', '1588905150805', '1688软件园', '张瀚文', '0008', 1, '2020-05-08 10:32:31', NULL, 1, 1, 'https://oapi.dingtalk.com/robot/sendBySession?session=49500364a9f1fd5c30c1c249b267d70c', '1588910550864', '65');

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
  `chat_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '群名称',
  PRIMARY KEY (`record_id`) USING BTREE,
  INDEX `chat_name`(`chat_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 159 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dd_chat_at_record
-- ----------------------------
INSERT INTO `dd_chat_at_record` VALUES (38, 39, '  Game of rights  ', 3, 1, '2020-05-06 13:51:15', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (39, 39, '1111', 1, 1, '2020-05-06 13:51:38', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (40, 40, ' 22222', 1, 1, '2020-05-06 13:51:55', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (41, 39, '33333', 1, 1, '2020-05-06 13:52:01', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (42, 39, '33333', 1, 1, '2020-05-06 13:52:19', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (43, 40, 'good', 1, 1, '2020-05-06 13:55:17', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (44, 40, ' 大萨达所', 1, 1, '2020-05-06 13:55:55', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (45, 40, '123123', 1, 1, '2020-05-06 13:56:05', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (46, 40, '111', 1, 1, '2020-05-06 13:57:09', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (47, 39, '发送成功你得再给我推一下', 1, 1, '2020-05-06 13:57:50', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (48, 39, ' 冰与火之歌', 3, 1, '2020-05-06 13:58:11', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (49, 39, ' 111', 3, 1, '2020-05-06 14:17:38', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (50, 40, '我试试', 1, 1, '2020-05-06 14:18:14', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (51, 40, ' 顶顶顶顶', 1, 1, '2020-05-06 14:19:52', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (52, 40, '我再看看', 1, 1, '2020-05-06 14:22:02', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (53, 39, ' 111', 3, 1, '2020-05-06 14:42:08', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (54, 40, '看看', 1, 1, '2020-05-06 14:42:45', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (55, 39, '好的', 1, 1, '2020-05-06 14:43:09', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (56, 40, '是吗', 1, 1, '2020-05-06 15:21:45', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (57, 39, ' 111', 3, 1, '2020-05-06 15:23:19', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (58, 40, ' 顶顶顶顶', 1, 1, '2020-05-06 15:24:20', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (59, 40, ' 王企鹅群翁群翁', 1, 1, '2020-05-06 15:24:56', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (60, 40, ' 啊实打实大', 1, 1, '2020-05-06 15:25:31', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (61, 40, ' 啊飒飒的', 1, 1, '2020-05-06 15:28:22', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (62, 39, ' 123', 3, 1, '2020-05-06 15:34:59', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (63, 40, '是吗', 1, 1, '2020-05-06 15:37:26', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (64, 40, '是的', 1, 1, '2020-05-06 15:53:04', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (65, 39, ' 111', 3, 1, '2020-05-06 15:53:32', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (66, 39, '好的', 1, 1, '2020-05-06 15:54:05', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (67, 40, ' 2323', 1, 1, '2020-05-06 15:57:06', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (68, 40, '蔷薇蔷薇', 1, 1, '2020-05-06 15:57:13', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (69, 40, ' 4444', 1, 1, '2020-05-06 15:57:26', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (70, 40, '5555', 1, 1, '2020-05-06 16:03:14', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (71, 40, '4545453', 1, 1, '2020-05-06 16:04:48', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (72, 39, '678', 1, 1, '2020-05-06 16:05:30', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (73, 40, ' 111', 1, 1, '2020-05-06 16:05:50', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (74, 40, '111 ', 1, 1, '2020-05-06 16:11:41', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (75, 40, 'haluo', 1, 1, '2020-05-06 16:53:34', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (76, 40, ' 驱蚊器翁群翁群无', 1, 1, '2020-05-06 16:53:50', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (77, 40, ' 123213', 1, 1, '2020-05-06 16:54:05', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (78, 40, ' 是吗', 1, 1, '2020-05-06 17:00:31', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (79, 40, ' 是是是', 1, 1, '2020-05-06 17:00:46', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (80, 40, '那就\n当做\n是吧\n', 1, 1, '2020-05-06 17:01:06', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (81, 40, 'f\n f\n  fff\n', 1, 1, '2020-05-06 17:04:43', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (82, 40, ' 哦是吗', 1, 1, '2020-05-06 17:05:09', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (83, 39, '我\n觉得\n差不多\n可以用了', 1, 1, '2020-05-06 17:08:37', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (84, 39, '来聊五块钱的', 1, 1, '2020-05-06 17:09:08', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (85, 40, ' [加油]', 1, 1, '2020-05-06 17:11:07', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (86, 40, '[加油]', 1, 1, '2020-05-06 17:11:32', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (87, 40, ' 不在线', 1, 1, '2020-05-06 17:23:00', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (88, 40, '哈哈哈哈\n', 1, 1, '2020-05-06 18:10:14', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (89, 40, ' 啊实打实大师', 1, 1, '2020-05-06 18:10:28', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (90, 40, ' 实打实大师', 1, 1, '2020-05-06 18:10:46', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (91, 41, ' 冰与火之歌', 3, 1, '2020-05-07 10:41:12', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (92, 40, ' 大幅度发', 1, 1, '2020-05-07 10:46:19', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (93, 40, ' 顶顶顶顶', 1, 1, '2020-05-07 10:47:14', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (94, 40, ' 帆帆帆帆', 1, 1, '2020-05-07 11:29:47', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (95, 40, ' 啊实打实', 1, 1, '2020-05-07 11:30:09', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (96, 40, ' 就这？', 1, 1, '2020-05-07 13:10:00', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (97, 40, ' 撒大苏打', 1, 1, '2020-05-07 13:10:46', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (98, 40, ' 啊实打实的', 1, 1, '2020-05-07 13:20:22', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (99, 40, ' 咔咔咔咔咔咔', 1, 1, '2020-05-07 13:41:06', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (100, 40, ' 哒哒哒哒哒哒', 1, 1, '2020-05-07 13:41:24', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (101, 40, ' 按时打算', 1, 1, '2020-05-07 13:46:51', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (102, 40, ' 呐呐呐呐', 1, 1, '2020-05-07 13:48:24', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (103, 40, ' 所得到的', 1, 1, '2020-05-07 14:10:41', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (104, 41, ' 123', 3, 1, '2020-05-07 14:28:26', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (105, 41, ' 123', 3, 1, '2020-05-07 14:29:08', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (106, 41, '呐呐呐', 1, 1, '2020-05-07 14:29:18', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (107, 42, ' 1111', 3, 1, '2020-05-07 14:29:52', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (108, 43, ' 111', 1, 1, '2020-05-07 14:30:25', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (109, 43, '看不见', 1, 1, '2020-05-07 14:31:37', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (110, 43, ' 好的', 1, 1, '2020-05-07 14:31:55', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (111, 44, ' 啊实打实的', 1, 1, '2020-05-07 20:15:15', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (112, 44, '犯得上发生', 1, 1, '2020-05-07 20:15:26', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (113, 44, '的\n d d\n\n', 1, 1, '2020-05-07 20:15:41', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (114, 42, ' 111', 3, 1, '2020-05-07 20:23:14', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (115, 45, ' 111', 3, 1, '2020-05-07 20:26:04', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (116, 45, '如何规范和', 1, 1, '2020-05-07 20:26:38', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (117, 46, ' 555', 1, 1, '2020-05-07 20:47:14', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (118, 47, ' 111', 3, 1, '2020-05-07 21:00:10', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (119, 47, 'hh ', 1, 1, '2020-05-07 21:00:52', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (120, 47, 'hhhj ', 1, 1, '2020-05-07 21:00:57', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (121, 47, 'g ', 1, 1, '2020-05-07 21:01:10', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (122, 47, ' 111', 3, 1, '2020-05-07 21:05:05', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (123, 48, ' 反反复复烦烦烦', 1, 1, '2020-05-07 21:05:46', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (124, 48, ' 11111111', 1, 1, '2020-05-07 21:06:26', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (125, 48, '123213', 1, 1, '2020-05-07 21:06:37', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (126, 48, ' 11111123232', 1, 1, '2020-05-07 21:06:50', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (127, 48, 'okjmjkojmkml', 1, 1, '2020-05-07 21:06:58', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (128, 48, 'kjhkjk', 1, 1, '2020-05-07 21:07:14', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (129, 48, ' 1111111', 1, 1, '2020-05-07 21:15:17', 1, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (130, 48, '大是大非阿萨德饭 \n', 1, 1, '2020-05-07 21:15:24', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (131, 48, '啊啊\n', 1, 1, '2020-05-07 21:16:00', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (132, 48, '啊', 1, 1, '2020-05-07 21:16:04', 2, '中国14亿人沸腾！');
INSERT INTO `dd_chat_at_record` VALUES (133, 49, ' 你好', 41, 1, '2020-05-07 22:09:03', 1, '1688软件园1');
INSERT INTO `dd_chat_at_record` VALUES (134, 49, '你好', 65, 1, '2020-05-07 22:09:17', 2, '1688软件园1');
INSERT INTO `dd_chat_at_record` VALUES (135, 49, ' 请问什么时候可以复工', 41, 1, '2020-05-07 22:09:46', 1, '1688软件园1');
INSERT INTO `dd_chat_at_record` VALUES (136, 49, '  你好', 41, 1, '2020-05-07 22:10:28', 1, '1688软件园1');
INSERT INTO `dd_chat_at_record` VALUES (137, 49, '  二分法', 41, 1, '2020-05-07 22:11:00', 1, '1688软件园1');
INSERT INTO `dd_chat_at_record` VALUES (138, 50, ' 你好啊', 41, 1, '2020-05-07 22:12:39', 1, '1688软件园');
INSERT INTO `dd_chat_at_record` VALUES (139, 49, ' 你好', 41, 1, '2020-05-07 22:19:06', 1, '1688软件园1');
INSERT INTO `dd_chat_at_record` VALUES (140, 49, ' 大家好', 41, 1, '2020-05-07 22:19:20', 1, '1688软件园1');
INSERT INTO `dd_chat_at_record` VALUES (141, 49, '给覆盖热负荷', 65, 1, '2020-05-07 22:19:21', 2, '1688软件园1');
INSERT INTO `dd_chat_at_record` VALUES (142, 49, ' 嗯', 41, 1, '2020-05-07 22:19:28', 1, '1688软件园1');
INSERT INTO `dd_chat_at_record` VALUES (143, 50, '1233132', 65, 1, '2020-05-07 22:19:39', 2, '1688软件园');
INSERT INTO `dd_chat_at_record` VALUES (144, 50, ' 你好', 41, 1, '2020-05-07 22:19:43', 1, '1688软件园');
INSERT INTO `dd_chat_at_record` VALUES (145, 51, ' 啦啦啦', 56, 1, '2020-05-07 22:24:21', 1, '1688软件园');
INSERT INTO `dd_chat_at_record` VALUES (146, 51, '通过', 65, 1, '2020-05-07 22:24:39', 2, '1688软件园');
INSERT INTO `dd_chat_at_record` VALUES (147, 51, '@ 还可以么', 56, 1, '2020-05-07 22:24:53', 1, '1688软件园');
INSERT INTO `dd_chat_at_record` VALUES (148, 51, '可以\n', 65, 1, '2020-05-07 22:25:04', 2, '1688软件园');
INSERT INTO `dd_chat_at_record` VALUES (149, 51, '老总  结下加班费', 65, 1, '2020-05-07 22:25:17', 2, '1688软件园');
INSERT INTO `dd_chat_at_record` VALUES (150, 51, '我们溜了', 65, 1, '2020-05-07 22:25:22', 2, '1688软件园');
INSERT INTO `dd_chat_at_record` VALUES (151, 51, ' 测试', 56, 1, '2020-05-08 09:37:21', 1, '1688软件园');
INSERT INTO `dd_chat_at_record` VALUES (152, 52, ' 你好啊', 65, 1, '2020-05-08 09:39:19', 1, '1688软件园1');
INSERT INTO `dd_chat_at_record` VALUES (153, 53, ' 你好', 65, 1, '2020-05-08 09:41:02', 1, '1688软件园');
INSERT INTO `dd_chat_at_record` VALUES (154, 52, ' 1111', 65, 1, '2020-05-08 09:43:17', 1, '1688软件园1');
INSERT INTO `dd_chat_at_record` VALUES (155, 51, ' 测试消息', 56, 1, '2020-05-08 10:28:39', 1, '1688软件园');
INSERT INTO `dd_chat_at_record` VALUES (156, 51, '人社消息回复', 65, 1, '2020-05-08 10:29:05', 2, '1688软件园');
INSERT INTO `dd_chat_at_record` VALUES (157, 54, ' 问题', 56, 1, '2020-05-08 10:32:31', 1, '1688软件园');
INSERT INTO `dd_chat_at_record` VALUES (158, 48, ' 撒大苏打', 1, 1, '2020-05-08 15:36:18', 1, '中国14亿人沸腾！');

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
  PRIMARY KEY (`problem_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '问题表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dd_problem
-- ----------------------------
INSERT INTO `dd_problem` VALUES (35, 41, 0, '2020-04-24 02:04:39', '请问复工需要做哪些准备，什么条件下才能复工。', 1, 1, 2, 17);
INSERT INTO `dd_problem` VALUES (36, 41, 1, '2020-04-12 03:03:35', '请问什么条件下可以申请复工', 1, 1, 2, 17);
INSERT INTO `dd_problem` VALUES (37, 51, 1, '2020-04-10 09:23:55', '开工时间', 1, 1, 1, 8);
INSERT INTO `dd_problem` VALUES (38, 56, 1, '2020-05-24 10:35:20', '人社政策相关', 1, 1, 2, 8);
INSERT INTO `dd_problem` VALUES (39, 56, 1, '2020-05-08 10:20:55', '人社政策', 1, 1, 2, 8);

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
-- Records of dd_role
-- ----------------------------
INSERT INTO `dd_role` VALUES ('1226590701', '管理员', '1227024234', '2020-04-10 15:07:13', 1);

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
-- Records of dd_role_group
-- ----------------------------
INSERT INTO `dd_role_group` VALUES ('1227024234', '2020-04-10 15:03:20', 1, '角色管理测试');

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
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '反馈表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dd_solve
-- ----------------------------
INSERT INTO `dd_solve` VALUES (1, 1, '2020-04-03 16:09:24', '已经反馈', 2, 1);
INSERT INTO `dd_solve` VALUES (2, 22, '2020-04-08 15:16:34', '我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈我也不知道怎么反馈', 1, 1);
INSERT INTO `dd_solve` VALUES (3, 22, '2020-04-08 15:24:18', '11', 1, 1);
INSERT INTO `dd_solve` VALUES (4, 19, '2020-04-08 15:39:47', '一二一二一二呀', 1, 1);
INSERT INTO `dd_solve` VALUES (5, 15, '2020-04-08 15:40:26', '好好好，都可以', 1, 1);
INSERT INTO `dd_solve` VALUES (6, 23, '2020-04-08 15:54:36', '1021212121', 1, 1);
INSERT INTO `dd_solve` VALUES (7, 23, '2020-04-08 15:54:58', '465465435463', 1, 1);
INSERT INTO `dd_solve` VALUES (8, 24, '2020-04-08 15:56:47', '这就是我的反馈', 1, 1);
INSERT INTO `dd_solve` VALUES (9, 24, '2020-04-08 15:57:29', '这是我进行的第二次反馈', 1, 1);
INSERT INTO `dd_solve` VALUES (10, 25, '2020-04-08 15:59:28', '给你空当接龙发卡机圣诞快乐家雷克萨的积分了卡时间的分类可接受的六块腹肌拉斯肯德基发拉开手机的弗兰克就沙龙的积分', 1, 1);
INSERT INTO `dd_solve` VALUES (11, 25, '2020-04-08 15:59:58', '撒旦法师打发撒旦法撒旦法', 1, 1);
INSERT INTO `dd_solve` VALUES (12, 25, '2020-04-08 16:00:06', '这是我的第三次反馈', 1, 1);
INSERT INTO `dd_solve` VALUES (13, 26, '2020-04-08 16:01:45', '这是第一条反馈', 1, 1);
INSERT INTO `dd_solve` VALUES (14, 26, '2020-04-08 16:01:52', '这是第二条反馈', 1, 1);
INSERT INTO `dd_solve` VALUES (15, 27, '2020-04-15 11:40:26', '回答', 1, 1);
INSERT INTO `dd_solve` VALUES (16, 17, '2020-04-21 16:42:14', '我反馈你了', 1, 1);
INSERT INTO `dd_solve` VALUES (17, 25, '2020-04-21 16:55:33', '这是我的反馈', 1, 1);
INSERT INTO `dd_solve` VALUES (18, 36, '2020-04-23 03:03:53', '需要提交申请', 1, 1);
INSERT INTO `dd_solve` VALUES (19, 36, '2020-04-23 03:06:55', '具体请电话联系', 1, 1);
INSERT INTO `dd_solve` VALUES (20, 37, '2020-04-23 09:56:55', '2020年5月', 1, 1);
INSERT INTO `dd_solve` VALUES (21, 37, '2020-04-23 09:57:27', '跟进', 1, 1);
INSERT INTO `dd_solve` VALUES (22, 38, '2020-04-23 10:35:42', '反馈信息', 1, 1);
INSERT INTO `dd_solve` VALUES (23, 38, '2020-04-29 14:57:21', '11', NULL, 1);
INSERT INTO `dd_solve` VALUES (24, 38, '2020-04-29 14:58:45', '11', NULL, 1);
INSERT INTO `dd_solve` VALUES (25, 39, '2020-05-08 10:51:41', '反馈', NULL, 1);

-- ----------------------------
-- Table structure for dd_user
-- ----------------------------
DROP TABLE IF EXISTS `dd_user`;
CREATE TABLE `dd_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '成员名称',
  `company_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '公司名称',
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
  `industry_id` int(11) NULL DEFAULT NULL COMMENT '行业id     sys_enterprise_scale',
  `is_on_line` int(1) NULL DEFAULT NULL COMMENT '管理员是否在线 1 在线 0不在线',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `mobile`(`mobile`) USING BTREE,
  INDEX `dd_user_id`(`dd_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 79 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dd_user
-- ----------------------------
INSERT INTO `dd_user` VALUES (1, '范恒坤', '山东光云科技有限公司', '15964298660', '', '', '2020-04-14 14:39:09', 1, '105455511233021495', '', '', '市南区', '香港中路街道', '36.0633930800', '120.3509330800', '山东省青岛市李沧区中意1688广场产业园', 1, 1, 1, 1, 1, 22, 1);
INSERT INTO `dd_user` VALUES (3, '孔毅松', '山东易成新能有限公司', '17316294167', '', '', '2020-04-14 14:39:09', 1, '202565540023349933', '', '', '市南区', '香港中路街道', '36.0628380100', '120.3371143300', '山东省青岛市李沧区中意1688广场产业园', 1, 1, 1, 1, 19, 24, 0);
INSERT INTO `dd_user` VALUES (29, '马晓宇', '山东智慧能源有限公司', '15589582912', '', '1491742594@qq.com', '2020-04-18 14:26:05', 1, '152746241238825728', '', '', '市北区', '敦化路街道', '36.0641562800', '120.3129959100', '山东省青岛市李沧区中意1688广场产业园', 0, 1, 2, 5, 23, 25, 0);
INSERT INTO `dd_user` VALUES (41, '张明宇', '山东光大科技有限公司', '15688462389', '', '', '2020-04-23 01:36:56', 1, '181931274124193877', '', '', '市南区', '中山路街道', '36.0633930800', '120.3509330800', '山东省青岛市李沧区中意1688广场产业园', 1, 1, 1, 2, 27, 24, 0);
INSERT INTO `dd_user` VALUES (56, '张瀚文', '壹当伍', '13964259469', '', '', '2020-04-23 10:32:29', 1, '0008', '', '', '市南区', '中山路街道', '36.0641562800', '120.3129959100', '李沧', 0, 1, 1, 2, 23, 24, 0);
INSERT INTO `dd_user` VALUES (65, '王绍辉', '一汽大众4S店', '15153222915', '', '', '2020-05-07 21:20:02', 1, '186623261929468455', '', '', '市南区', '香港中路街道', '36.065284', '120.400428', '青岛市香港中路', 1, 1, 1, 1, 19, 22, 0);
INSERT INTO `dd_user` VALUES (78, '刘磊', '1', '18506431326', '', '', '2020-05-08 16:11:16', 1, '2656524308682418', '', '', '李沧区', '黑龙江中路街道', '', '', '1', 0, 1, 4, 7, 1, 3, NULL);

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
-- Records of dd_user_chat
-- ----------------------------
INSERT INTO `dd_user_chat` VALUES (3, 'chatf76e916b2b0f67bc6d90b4aa7b64aa79', 1, NULL);
INSERT INTO `dd_user_chat` VALUES (29, 'chatcb6583881987ee4074a7109c0ec5fe49', 5, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chat85bdde909001233307d21e25bd100e49', 1, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chatdf8543aa644b73ebfb16c25eec83a8e6', 1, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chatccf26d92c8c4a91d77c65495ce603ac8', 4, NULL);
INSERT INTO `dd_user_chat` VALUES (29, 'chat45621130ffb2adf4ca728de1c831c717', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (29, 'chat45621130ffb2adf4ca728de1c831c717', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chateb9a7384c92eee2056cdc6d492f71697', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chateb9a7384c92eee2056cdc6d492f71697', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chateb9a7384c92eee2056cdc6d492f71697', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chateb9a7384c92eee2056cdc6d492f71697', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chatcacbf436843226f320c1e5dad0746f98', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (29, 'chatcacbf436843226f320c1e5dad0746f98', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chatcacbf436843226f320c1e5dad0746f98', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (29, 'chatcacbf436843226f320c1e5dad0746f98', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chat0b6594893dd72008eb75b30888a01f98', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chat0b6594893dd72008eb75b30888a01f98', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chat7a7f6b9838efc2789f4293dfbaa63824', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (29, 'chat7a7f6b9838efc2789f4293dfbaa63824', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chatf52834abebed106d7cc941fc8405d8bc', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chatf52834abebed106d7cc941fc8405d8bc', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (29, 'chatf52834abebed106d7cc941fc8405d8bc', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chatf52834abebed106d7cc941fc8405d8bc', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chatc367fef1061b5f780a9368d2e01dfb65', 4, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chateb9a7384c92eee2056cdc6d492f71697', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chateb9a7384c92eee2056cdc6d492f71697', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chata099cdb84220d766a0a9d8241506a3d3', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chat345cb63e150d01a1aa0203a9216036b0', 2, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chat0b6594893dd72008eb75b30888a01f98', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chat0b6594893dd72008eb75b30888a01f98', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chat91f3b8d1071ac587941176b0489f2a8b', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chat91f3b8d1071ac587941176b0489f2a8b', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chat91f3b8d1071ac587941176b0489f2a8b', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chatfb858df2628555eaf33255529b6d3a6a', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chatf7f68a248b75e9176bdf99c5e88ac510', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chatf7f68a248b75e9176bdf99c5e88ac510', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chat6c8429cf9fe08a3701576df7fa1c8894', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chat6c8429cf9fe08a3701576df7fa1c8894', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (29, 'chat6c8429cf9fe08a3701576df7fa1c8894', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chat6c8429cf9fe08a3701576df7fa1c8894', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chatc9495b7e28985f535507f44985f4e116', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chatc9495b7e28985f535507f44985f4e116', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chatc9495b7e28985f535507f44985f4e116', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chat554ee8f2f84d43c27470151dc6de9d04', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chat554ee8f2f84d43c27470151dc6de9d04', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chat554ee8f2f84d43c27470151dc6de9d04', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (29, 'chat554ee8f2f84d43c27470151dc6de9d04', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (29, 'chat554ee8f2f84d43c27470151dc6de9d04', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (41, 'chatdf8543aa644b73ebfb16c25eec83a8e6', 1, NULL);
INSERT INTO `dd_user_chat` VALUES (41, 'chatefcede9dd31814c29a03f91024fae5bc', 2, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chat1de80b4699adafe7ffab2d22d7cfacac', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chat0409412ba356849b9c960153f5419538', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chat0409412ba356849b9c960153f5419538', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chat0409412ba356849b9c960153f5419538', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (56, 'chatf76e916b2b0f67bc6d90b4aa7b64aa79', 1, NULL);
INSERT INTO `dd_user_chat` VALUES (56, 'chat9b906c63d9c0425c26abc649e766059f', 2, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chat370576f552e4669482281bc9df017d73', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chat370576f552e4669482281bc9df017d73', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chat370576f552e4669482281bc9df017d73', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chatff21cf2ca3a7c6d16dbabb6ec33e326d', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (1, 'chatff21cf2ca3a7c6d16dbabb6ec33e326d', NULL, NULL);
INSERT INTO `dd_user_chat` VALUES (64, 'chatc1950fae21b9d2cfdb703e029c4ec067', 6, NULL);
INSERT INTO `dd_user_chat` VALUES (65, 'chatf76e916b2b0f67bc6d90b4aa7b64aa79', 1, NULL);
INSERT INTO `dd_user_chat` VALUES (65, 'chat9cb7b5f1080b99a678a2ca78c21e2138', 6, NULL);
INSERT INTO `dd_user_chat` VALUES (65, 'chatafd849647d441637e292a4a23844dc25', 6, NULL);
INSERT INTO `dd_user_chat` VALUES (65, 'chatdb2d94cddade0098079436a5550380a0', 6, NULL);
INSERT INTO `dd_user_chat` VALUES (65, 'chat5109b0c1c4f9a3a81a559c5b747be40b', 6, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chat252b4bbe4f9ade717daf6a4144323480', 7, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chat85cb060dafd385ea96be3a29e279b437', 7, NULL);
INSERT INTO `dd_user_chat` VALUES (66, 'chat252b4bbe4f9ade717daf6a4144323480', 7, NULL);
INSERT INTO `dd_user_chat` VALUES (67, 'chat85cb060dafd385ea96be3a29e279b437', 7, NULL);
INSERT INTO `dd_user_chat` VALUES (68, 'chat252b4bbe4f9ade717daf6a4144323480', 7, NULL);
INSERT INTO `dd_user_chat` VALUES (69, 'chat252b4bbe4f9ade717daf6a4144323480', 7, NULL);
INSERT INTO `dd_user_chat` VALUES (70, 'chat85cb060dafd385ea96be3a29e279b437', 7, NULL);
INSERT INTO `dd_user_chat` VALUES (71, 'chat252b4bbe4f9ade717daf6a4144323480', 7, NULL);
INSERT INTO `dd_user_chat` VALUES (72, 'chat252b4bbe4f9ade717daf6a4144323480', 7, NULL);
INSERT INTO `dd_user_chat` VALUES (73, 'chat252b4bbe4f9ade717daf6a4144323480', 7, NULL);
INSERT INTO `dd_user_chat` VALUES (74, 'chat252b4bbe4f9ade717daf6a4144323480', 7, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chatdad38ef6d4f58360788c54926bb14f97', 7, NULL);
INSERT INTO `dd_user_chat` VALUES (3, 'chat33cf6c61ab8fdbde9792ac98093b26d7', 7, NULL);
INSERT INTO `dd_user_chat` VALUES (75, 'chatdad38ef6d4f58360788c54926bb14f97', 7, NULL);
INSERT INTO `dd_user_chat` VALUES (76, 'chatdad38ef6d4f58360788c54926bb14f97', 7, NULL);
INSERT INTO `dd_user_chat` VALUES (77, 'chat33cf6c61ab8fdbde9792ac98093b26d7', 7, NULL);
INSERT INTO `dd_user_chat` VALUES (78, 'chatdad38ef6d4f58360788c54926bb14f97', 7, NULL);

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
-- Records of sys_area
-- ----------------------------
INSERT INTO `sys_area` VALUES (1, '市南区');
INSERT INTO `sys_area` VALUES (2, '市北区');
INSERT INTO `sys_area` VALUES (4, '李沧区');
INSERT INTO `sys_area` VALUES (5, '崂山区');
INSERT INTO `sys_area` VALUES (6, '城阳区');
INSERT INTO `sys_area` VALUES (7, '黄岛区');

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
) ENGINE = InnoDB AUTO_INCREMENT = 88 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '区域权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_area_permissions
-- ----------------------------
INSERT INTO `sys_area_permissions` VALUES (1, 1, NULL, 2, 1, NULL);
INSERT INTO `sys_area_permissions` VALUES (12, 1, 1, 5, 1, 1);
INSERT INTO `sys_area_permissions` VALUES (22, 2, 5, 4, 1, NULL);
INSERT INTO `sys_area_permissions` VALUES (23, 2, 4, 5, 3, 5);
INSERT INTO `sys_area_permissions` VALUES (27, 1, 1, 5, 1, 6);
INSERT INTO `sys_area_permissions` VALUES (28, 1, 1, 5, 1, 7);
INSERT INTO `sys_area_permissions` VALUES (29, 1, 1, 5, 65, 8);
INSERT INTO `sys_area_permissions` VALUES (30, 2, 4, 5, 22, 9);
INSERT INTO `sys_area_permissions` VALUES (31, 2, 5, 5, 1, 10);
INSERT INTO `sys_area_permissions` VALUES (32, 1, 2, 5, 1, 11);
INSERT INTO `sys_area_permissions` VALUES (33, 1, 3, 5, 1, 12);
INSERT INTO `sys_area_permissions` VALUES (34, 1, 2, 5, 1, 13);
INSERT INTO `sys_area_permissions` VALUES (35, 1, 3, 5, 1, 14);
INSERT INTO `sys_area_permissions` VALUES (36, 1, 2, 5, 1, 15);
INSERT INTO `sys_area_permissions` VALUES (37, 1, 1, 5, 29, 16);
INSERT INTO `sys_area_permissions` VALUES (38, 1, 1, 5, 1, 17);
INSERT INTO `sys_area_permissions` VALUES (52, 1, 3, 5, 35, 18);
INSERT INTO `sys_area_permissions` VALUES (53, 2, 4, 5, 1, 19);
INSERT INTO `sys_area_permissions` VALUES (56, 2, 4, 5, 1, 20);
INSERT INTO `sys_area_permissions` VALUES (57, 1, 2, 5, 1, 21);
INSERT INTO `sys_area_permissions` VALUES (59, 1, 1, 4, 3, NULL);
INSERT INTO `sys_area_permissions` VALUES (63, 2, 5, 5, 41, 22);
INSERT INTO `sys_area_permissions` VALUES (64, 5, NULL, 3, 62, NULL);
INSERT INTO `sys_area_permissions` VALUES (65, 4, 6, 5, 1, 23);
INSERT INTO `sys_area_permissions` VALUES (66, 4, 6, 5, 63, 24);
INSERT INTO `sys_area_permissions` VALUES (67, 1, NULL, 3, 3, NULL);
INSERT INTO `sys_area_permissions` VALUES (68, 1, 1, 4, 1, NULL);
INSERT INTO `sys_area_permissions` VALUES (69, 4, NULL, 3, 3, NULL);
INSERT INTO `sys_area_permissions` VALUES (70, 1, 2, 4, 41, NULL);
INSERT INTO `sys_area_permissions` VALUES (72, 4, 6, 5, 65, 26);
INSERT INTO `sys_area_permissions` VALUES (80, 4, 6, 5, 65, 34);
INSERT INTO `sys_area_permissions` VALUES (81, 4, 6, 5, 65, 35);
INSERT INTO `sys_area_permissions` VALUES (82, 4, 6, 5, 65, 36);
INSERT INTO `sys_area_permissions` VALUES (83, 4, 7, 5, 3, 37);
INSERT INTO `sys_area_permissions` VALUES (84, 4, 7, 5, 3, 38);
INSERT INTO `sys_area_permissions` VALUES (85, 4, 7, 4, 3, NULL);
INSERT INTO `sys_area_permissions` VALUES (86, 4, 7, 5, 3, 39);
INSERT INTO `sys_area_permissions` VALUES (87, 4, 7, 5, 3, 40);

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
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '行业与规模群' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_enterprise_scale
-- ----------------------------
INSERT INTO `sys_enterprise_scale` VALUES (1, '1~10人', 1, 1, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (2, '10~20人', 0, 1, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (3, '制造业', 1, 2, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (4, '高新技术业', 0, 2, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (5, '软件开发', 0, 2, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (6, '20~50人', 0, 1, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (7, '电子商务', 0, 2, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (8, '30~40人', 0, 1, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (9, '30~40人', 0, 1, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (10, '30~40', 0, 1, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (11, '30~40', 0, 1, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (12, '40~50人', 0, 1, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (13, '100~999人大型企业', 0, 1, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (14, '副食品', 0, 2, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (15, '1~5人', 0, 1, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (16, '999人以上超大型企业', 0, 1, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (17, '500人以上', 0, 1, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (18, '100人以上', 0, 1, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (19, '100~999人', 1, 1, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (20, '10~99人', 0, 1, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (21, '副食业', 0, 2, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (22, '汽车销售行业', 1, 2, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (23, '1000人以上超大型企业', 1, 1, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (24, '副食产品制造业', 1, 2, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (25, '餐饮行业', 1, 2, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (26, '0人公司', 0, 1, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (27, '10000人企业', 1, 1, NULL);
INSERT INTO `sys_enterprise_scale` VALUES (28, '手工业', 1, 2, 1);
INSERT INTO `sys_enterprise_scale` VALUES (29, '10~100人', 1, 1, 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '网格员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_grid
-- ----------------------------
INSERT INTO `sys_grid` VALUES (8, 1, '香港中路街道网格1', 'chatf76e916b2b0f67bc6d90b4aa7b64aa79', 1);
INSERT INTO `sys_grid` VALUES (9, 4, '台东路网格1', 'chatb476147c9bde3fca09874da4d7291c40', 1);
INSERT INTO `sys_grid` VALUES (10, 5, '敦化路路网格1', 'chatcb6583881987ee4074a7109c0ec5fe49', 1);
INSERT INTO `sys_grid` VALUES (11, 2, '中山路街道1', 'chatefcede9dd31814c29a03f91024fae5bc', 1);
INSERT INTO `sys_grid` VALUES (12, 3, '中山路街道1', 'chat9c40b4153aea1e5bdb009f16393e4053', 1);
INSERT INTO `sys_grid` VALUES (13, 2, '中山路街道2', 'chat1b8d3e6f3778d934b82c69088c8f6641', 1);
INSERT INTO `sys_grid` VALUES (14, 3, '八大关街道2', 'chat99bd593817cf805ad6031ac4da90b4be', 1);
INSERT INTO `sys_grid` VALUES (15, 2, '中山路街道3', 'chat9b906c63d9c0425c26abc649e766059f', 1);
INSERT INTO `sys_grid` VALUES (16, 1, '香港中路街道网格2', 'chat85bdde909001233307d21e25bd100e49', 1);
INSERT INTO `sys_grid` VALUES (17, 1, '香港中路街道网格3', 'chatdf8543aa644b73ebfb16c25eec83a8e6', 1);
INSERT INTO `sys_grid` VALUES (18, 3, '八大关网格', 'chat5e73cf24c81060b28f14cb3d17d318de', 1);
INSERT INTO `sys_grid` VALUES (19, 4, '这是某条路上的一个名字较长的网格', 'chatccf26d92c8c4a91d77c65495ce603ac8', 1);
INSERT INTO `sys_grid` VALUES (20, 4, '新增网格一', 'chatc367fef1061b5f780a9368d2e01dfb65', 1);
INSERT INTO `sys_grid` VALUES (21, 2, '中山路街道办事处网格', 'chat345cb63e150d01a1aa0203a9216036b0', 1);
INSERT INTO `sys_grid` VALUES (22, 5, '敦化路网格2', 'chat500df87add6821424f802de94d6d9926', 1);
INSERT INTO `sys_grid` VALUES (23, 6, '上王埠网格', 'chatc1950fae21b9d2cfdb703e029c4ec067', 1);
INSERT INTO `sys_grid` VALUES (24, 6, '上王埠村北', 'chatbd58500df6f13d92a1b378225b744b5c', 1);
INSERT INTO `sys_grid` VALUES (35, 6, '1688软件园', 'chatdb2d94cddade0098079436a5550380a0', 1);
INSERT INTO `sys_grid` VALUES (36, 6, '1688软件园1', 'chat5109b0c1c4f9a3a81a559c5b747be40b', 1);
INSERT INTO `sys_grid` VALUES (39, 7, '黑龙江1', 'chatdad38ef6d4f58360788c54926bb14f97', 1);
INSERT INTO `sys_grid` VALUES (40, 7, '黑龙江2', 'chat33cf6c61ab8fdbde9792ac98093b26d7', 1);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT,
  `msg_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '钉钉返回的消息id',
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
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 218 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公告表' ROW_FORMAT = Dynamic;

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
  `notice_id` int(255) NULL DEFAULT NULL COMMENT '通知，资讯id',
  `chat_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '群聊id',
  `dd_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id,这里为钉钉的userId',
  `msg_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息id',
  INDEX `chat_id`(`chat_id`) USING BTREE
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
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员在线总时间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_on_line_count
-- ----------------------------
INSERT INTO `sys_on_line_count` VALUES (2, 3, '2020-05-07', '252');
INSERT INTO `sys_on_line_count` VALUES (3, 1, '2020-05-07', '572');
INSERT INTO `sys_on_line_count` VALUES (4, 1, '2020-05-06', '100');
INSERT INTO `sys_on_line_count` VALUES (5, 3, '2020-05-07', '130');
INSERT INTO `sys_on_line_count` VALUES (6, 3, '2020-05-07', '0');
INSERT INTO `sys_on_line_count` VALUES (7, 65, '2020-05-07', '27');
INSERT INTO `sys_on_line_count` VALUES (8, 65, '2020-05-08', '354');
INSERT INTO `sys_on_line_count` VALUES (9, 1, '2020-05-08', '400');
INSERT INTO `sys_on_line_count` VALUES (10, 1, '2020-05-08', '0');

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
) ENGINE = InnoDB AUTO_INCREMENT = 108 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员在线时间段记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_on_line_time
-- ----------------------------
INSERT INTO `sys_on_line_time` VALUES (7, '2020-05-07 10:19:59', '2020-05-07 10:20:11', 10, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (8, '2020-05-07 10:20:11', '2020-05-07 10:20:24', 10, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (9, '2020-05-07 10:20:24', '2020-05-07 10:20:51', 1, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (10, '2020-05-07 10:20:51', '2020-05-07 10:21:10', 1, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (11, '2020-05-07 10:21:10', '2020-05-07 10:21:22', 10, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (12, '2020-05-07 10:21:22', '2020-05-07 14:31:08', 10, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (13, '2020-05-07 14:28:30', '2020-05-07 14:43:14', 1, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (14, '2020-05-07 14:31:02', '2020-05-07 14:31:23', 10, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (16, '2020-05-07 14:31:23', '2020-05-07 14:32:22', 20, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (17, '2020-05-07 14:31:27', '2020-05-07 15:24:45', 1, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (19, '2020-05-07 15:23:25', '2020-05-07 15:36:39', 1, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (20, '2020-05-07 15:36:37', '2020-05-07 20:14:50', 1, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (21, '2020-05-07 20:14:39', '2020-05-07 20:14:53', 1, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (22, '2020-05-07 20:14:51', '2020-05-07 20:15:02', 1, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (23, '2020-05-07 20:15:00', '2020-05-07 20:15:58', 1, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (24, '2020-05-07 20:15:17', '2020-05-07 21:07:51', 1, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (25, '2020-05-07 20:22:35', '2020-05-07 21:08:24', 1, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (26, '2020-05-07 20:23:30', '2020-05-07 21:29:07', 1, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (27, '2020-05-07 20:51:25', '2020-05-07 21:42:05', 1, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (28, '2020-05-07 20:59:03', NULL, 1, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (29, '2020-05-07 20:59:14', NULL, 10, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (30, '2020-05-07 21:00:24', NULL, 1, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (31, '2020-05-07 21:08:29', NULL, 1, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (32, '2020-05-07 21:14:50', NULL, 1, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (33, '2020-05-07 21:40:45', NULL, 1, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (34, '2020-05-07 21:42:22', '2020-05-07 21:51:11', 65, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (35, '2020-05-07 22:02:00', '2020-05-07 22:04:05', 65, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (36, '2020-05-07 22:09:09', '2020-05-07 22:12:43', 65, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (37, '2020-05-07 22:12:43', '2020-05-07 22:20:59', 65, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (38, '2020-05-07 22:18:49', '2020-05-07 22:25:39', 65, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (39, '2020-05-07 22:21:02', NULL, 65, '2020-05-07');
INSERT INTO `sys_on_line_time` VALUES (40, '2020-05-08 09:01:57', '2020-05-08 09:37:43', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (41, '2020-05-08 09:39:41', '2020-05-08 09:42:10', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (42, '2020-05-08 09:42:08', '2020-05-08 10:01:07', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (43, '2020-05-08 09:42:22', '2020-05-08 10:01:43', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (44, '2020-05-08 09:43:06', '2020-05-08 10:10:20', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (45, '2020-05-08 09:44:32', '2020-05-08 10:25:51', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (46, '2020-05-08 09:55:33', '2020-05-08 10:29:01', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (47, '2020-05-08 10:02:27', '2020-05-08 10:29:06', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (48, '2020-05-08 10:26:14', '2020-05-08 10:42:15', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (49, '2020-05-08 10:29:02', '2020-05-08 10:55:49', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (50, '2020-05-08 10:29:19', '2020-05-08 10:56:27', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (51, '2020-05-08 10:55:46', '2020-05-08 10:56:34', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (52, '2020-05-08 10:56:05', '2020-05-08 11:11:34', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (53, '2020-05-08 10:56:33', '2020-05-08 11:18:22', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (54, '2020-05-08 10:56:41', '2020-05-08 13:08:22', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (55, '2020-05-08 11:31:19', '2020-05-08 13:10:26', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (56, '2020-05-08 15:33:42', '2020-05-08 15:33:48', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (57, '2020-05-08 15:33:42', '2020-05-08 15:34:25', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (58, '2020-05-08 15:34:19', '2020-05-08 15:35:34', 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (59, '2020-05-08 15:34:19', NULL, 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (60, '2020-05-08 15:34:52', NULL, 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (61, '2020-05-08 15:34:52', NULL, 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (62, '2020-05-08 15:35:02', NULL, 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (63, '2020-05-08 15:35:02', NULL, 65, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (64, '2020-05-08 15:35:35', '2020-05-08 15:35:42', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (65, '2020-05-08 15:35:35', '2020-05-08 15:35:58', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (66, '2020-05-08 15:35:43', '2020-05-08 15:56:40', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (67, '2020-05-08 15:35:44', '2020-05-08 15:58:07', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (68, '2020-05-08 15:35:59', '2020-05-08 15:59:51', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (69, '2020-05-08 15:49:19', '2020-05-08 16:04:47', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (70, '2020-05-08 15:56:41', '2020-05-08 16:04:59', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (71, '2020-05-08 15:58:08', '2020-05-08 16:04:59', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (72, '2020-05-08 15:59:52', '2020-05-08 16:07:34', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (73, '2020-05-08 16:04:48', '2020-05-08 16:08:05', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (74, '2020-05-08 16:04:48', '2020-05-08 16:08:49', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (75, '2020-05-08 16:05:00', '2020-05-08 16:08:57', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (76, '2020-05-08 16:05:00', '2020-05-08 16:14:19', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (77, '2020-05-08 16:07:58', '2020-05-08 16:16:19', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (78, '2020-05-08 16:07:58', '2020-05-08 16:17:23', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (79, '2020-05-08 16:08:06', '2020-05-08 16:29:16', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (80, '2020-05-08 16:08:06', '2020-05-08 16:29:55', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (81, '2020-05-08 16:08:50', '2020-05-08 16:30:35', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (82, '2020-05-08 16:08:50', '2020-05-08 16:34:45', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (83, '2020-05-08 16:08:58', '2020-05-08 16:34:52', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (84, '2020-05-08 16:08:58', '2020-05-08 16:35:23', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (85, '2020-05-08 16:14:20', '2020-05-08 16:37:20', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (86, '2020-05-08 16:14:20', '2020-05-08 16:38:42', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (87, '2020-05-08 16:16:20', '2020-05-08 16:45:44', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (88, '2020-05-08 16:16:20', '2020-05-08 16:46:58', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (89, '2020-05-08 16:29:17', '2020-05-08 16:47:18', 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (90, '2020-05-08 16:29:17', NULL, 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (91, '2020-05-08 16:29:56', NULL, 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (92, '2020-05-08 16:30:25', NULL, 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (93, '2020-05-08 16:30:25', NULL, 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (94, '2020-05-08 16:30:35', NULL, 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (95, '2020-05-08 16:34:46', NULL, 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (96, '2020-05-08 16:34:47', NULL, 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (97, '2020-05-08 16:34:53', NULL, 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (98, '2020-05-08 16:35:24', NULL, 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (99, '2020-05-08 16:37:21', NULL, 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (100, '2020-05-08 16:37:22', NULL, 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (101, '2020-05-08 16:37:57', NULL, 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (102, '2020-05-08 16:37:57', NULL, 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (103, '2020-05-08 16:38:43', NULL, 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (104, '2020-05-08 16:45:45', NULL, 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (105, '2020-05-08 16:46:59', NULL, 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (106, '2020-05-08 16:47:19', NULL, 1, '2020-05-08');
INSERT INTO `sys_on_line_time` VALUES (107, '2020-05-08 16:47:19', NULL, 1, '2020-05-08');

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
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '回复消息时轮询管理员的id' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_poll_admin
-- ----------------------------
INSERT INTO `sys_poll_admin` VALUES (10, 1, 1, '测试发送消息群', 'chatff21cf2ca3a7c6d16dbabb6ec33e326d');
INSERT INTO `sys_poll_admin` VALUES (11, 1, 1, '1688产业园1', 'chat9cb7b5f1080b99a678a2ca78c21e2138');
INSERT INTO `sys_poll_admin` VALUES (19, 1, 1, '1688-002', 'chatafd849647d441637e292a4a23844dc25');
INSERT INTO `sys_poll_admin` VALUES (20, 1, 1, '1688软件园', 'chatdb2d94cddade0098079436a5550380a0');
INSERT INTO `sys_poll_admin` VALUES (21, 1, 1, '1688软件园1', 'chat5109b0c1c4f9a3a81a559c5b747be40b');
INSERT INTO `sys_poll_admin` VALUES (22, 1, 1, '黑龙江中路网格1', 'chat252b4bbe4f9ade717daf6a4144323480');
INSERT INTO `sys_poll_admin` VALUES (23, 1, 1, '黑龙江中路网格2', 'chat85cb060dafd385ea96be3a29e279b437');
INSERT INTO `sys_poll_admin` VALUES (24, 1, 1, '黑龙江1', 'chatdad38ef6d4f58360788c54926bb14f97');
INSERT INTO `sys_poll_admin` VALUES (25, 1, 1, '黑龙江2', 'chat33cf6c61ab8fdbde9792ac98093b26d7');

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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群轮询表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_poll_chat
-- ----------------------------
INSERT INTO `sys_poll_chat` VALUES (1, 1, 1, 6);
INSERT INTO `sys_poll_chat` VALUES (2, 2, 1, 7);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '角色名称',
  `permission` json NULL COMMENT '角色对应的权限',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'super', '[\"Dashbord\", \"grid-manage\", \"manager\", \"grid-info\", \"chat-manage\", \"chat-manage-1\", \"chat-manage-2\", \"chat-watch\", \"chat-watch-2\", \"tags\", \"send-msg\", \"send-msg1\", \"send-msg2\", \"answer-qus\", \"data-list\", \"compony\", \"person\", \"question\", \"notice\", \"chat\", \"permission\", \"permission-1\", \"permission-2\", \"permission-manager\"]');
INSERT INTO `sys_role` VALUES (2, '市管理员', '[\"Dashbord\", \"grid-manage\", \"manager\", \"grid-info\", \"chat-manage\", \"chat-manage-1\", \"chat-manage-2\", \"chat-watch\", \"chat-watch-2\", \"tags\", \"send-msg\", \"send-msg1\", \"send-msg2\", \"answer-qus\", \"data-list\", \"compony\", \"person\", \"question\", \"notice\", \"chat\"]');
INSERT INTO `sys_role` VALUES (3, '区管理员', '[\"Dashbord\", \"permission\", \"permission-1\", \"permission-2\", \"permission-manager\", \"grid-manage\", \"manager\", \"grid-info\", \"chat-manage\", \"chat-manage-1\", \"chat-manage-2\", \"chat-watch\", \"chat-watch-2\", \"tags\", \"send-msg\", \"send-msg1\", \"send-msg2\", \"answer-qus\", \"data-list\", \"compony\", \"person\", \"question\", \"notice\", \"chat\"]');
INSERT INTO `sys_role` VALUES (4, '街道管理员', '[\"grid-manage\", \"manager\", \"grid-info\", \"chat-manage\", \"chat-manage-1\", \"chat-manage-2\", \"chat-watch\", \"chat-watch-2\", \"tags\", \"send-msg\", \"send-msg1\", \"send-msg2\", \"answer-qus\", \"data-list\", \"compony\", \"person\", \"question\", \"notice\", \"chat\"]');
INSERT INTO `sys_role` VALUES (5, '网格管理员', '[\"Dashbord\", \"grid-manage\", \"manager\", \"grid-info\", \"chat-manage\", \"chat-manage-1\", \"chat-manage-2\", \"chat-watch\", \"chat-watch-2\", \"tags\", \"send-msg\", \"send-msg1\", \"send-msg2\", \"answer-qus\", \"data-list\", \"compony\", \"person\", \"question\", \"notice\", \"chat\"]');

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
) ENGINE = MyISAM AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_street
-- ----------------------------
INSERT INTO `sys_street` VALUES (1, '香港中路街道', 1);
INSERT INTO `sys_street` VALUES (2, '中山路街道', 1);
INSERT INTO `sys_street` VALUES (3, '八大关街道', 1);
INSERT INTO `sys_street` VALUES (4, '台东街道', 2);
INSERT INTO `sys_street` VALUES (5, '敦化路街道', 2);
INSERT INTO `sys_street` VALUES (6, '李村街道', 4);
INSERT INTO `sys_street` VALUES (7, '黑龙江中路街道', 4);

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
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 3);
INSERT INTO `sys_user_role` VALUES (22, 5);
INSERT INTO `sys_user_role` VALUES (29, 4);
INSERT INTO `sys_user_role` VALUES (29, 4);
INSERT INTO `sys_user_role` VALUES (35, 4);
INSERT INTO `sys_user_role` VALUES (3, 4);
INSERT INTO `sys_user_role` VALUES (35, 5);
INSERT INTO `sys_user_role` VALUES (3, 4);
INSERT INTO `sys_user_role` VALUES (3, 4);
INSERT INTO `sys_user_role` VALUES (3, 4);
INSERT INTO `sys_user_role` VALUES (3, 3);
INSERT INTO `sys_user_role` VALUES (3, 3);
INSERT INTO `sys_user_role` VALUES (3, 3);
INSERT INTO `sys_user_role` VALUES (62, 3);
INSERT INTO `sys_user_role` VALUES (63, 5);
INSERT INTO `sys_user_role` VALUES (3, 3);
INSERT INTO `sys_user_role` VALUES (1, 4);
INSERT INTO `sys_user_role` VALUES (41, 4);
INSERT INTO `sys_user_role` VALUES (65, 1);
INSERT INTO `sys_user_role` VALUES (65, 5);
INSERT INTO `sys_user_role` VALUES (65, 5);
INSERT INTO `sys_user_role` VALUES (65, 5);
INSERT INTO `sys_user_role` VALUES (65, 5);
INSERT INTO `sys_user_role` VALUES (3, 5);
INSERT INTO `sys_user_role` VALUES (3, 5);
INSERT INTO `sys_user_role` VALUES (3, 4);
INSERT INTO `sys_user_role` VALUES (3, 5);
INSERT INTO `sys_user_role` VALUES (3, 5);

SET FOREIGN_KEY_CHECKS = 1;
