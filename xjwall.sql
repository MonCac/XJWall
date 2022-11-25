/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50560
Source Host           : localhost:3306
Source Database       : xjwall

Target Server Type    : MYSQL
Target Server Version : 50560
File Encoding         : 65001

Date: 2022-11-20 15:44:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for operation
-- ----------------------------
DROP TABLE IF EXISTS `operation`;
CREATE TABLE `operation` (
  `operation_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '操作id',
  `operation_name` varchar(255) DEFAULT NULL COMMENT '操作名',
  PRIMARY KEY (`operation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作表';

-- ----------------------------
-- Records of operation
-- ----------------------------

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `permission_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `permission_name` varchar(255) DEFAULT NULL COMMENT '权限名',
  `operation_id` int(255) DEFAULT NULL COMMENT '操作id',
  PRIMARY KEY (`permission_id`),
  KEY `operation_id` (`operation_id`),
  CONSTRAINT `permission_ibfk_1` FOREIGN KEY (`operation_id`) REFERENCES `operation` (`operation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `post_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '帖子id',
  `type_id` int(255) NOT NULL COMMENT '帖子类型',
  `title` varchar(255) DEFAULT NULL COMMENT '帖子标题',
  `content` varchar(255) NOT NULL COMMENT '内容概述',
  `owner_id` int(255) NOT NULL COMMENT '发帖人id',
  `father_post` int(255) DEFAULT NULL COMMENT '父帖id',
  `likes` int(255) DEFAULT '0' COMMENT '点赞数',
  `collections` int(255) DEFAULT '0' COMMENT '收藏数',
  `views` int(255) DEFAULT '0' COMMENT '浏览次数',
  `anonymous` int(1) NOT NULL DEFAULT '0' COMMENT '是否匿名（匿名为1不匿名为0，默认0）',
  `create_time` datetime DEFAULT NULL,
  `deleted` int(1) DEFAULT '0' COMMENT '逻辑删除，删除为1，默认0',
  PRIMARY KEY (`post_id`),
  KEY `type_id` (`type_id`),
  KEY `owner_id` (`owner_id`),
  KEY `father_post` (`father_post`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `type` (`type_id`),
  CONSTRAINT `post_ibfk_2` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `post_ibfk_3` FOREIGN KEY (`father_post`) REFERENCES `post` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES ('1', '1', '这是一个表白标题', '这是一个表白帖', '3', null, '0', '0', '1', '0', '2022-11-20 15:43:37', '0');
INSERT INTO `post` VALUES ('2', '2', '这是一个吐槽标题', '这是一个吐槽贴', '4', null, '0', '0', '0', '0', '2022-11-20 15:43:38', '0');
INSERT INTO `post` VALUES ('3', '8', '这是一个回复标题', '这是一个回复贴', '5', '1', '0', '0', '0', '0', '2022-11-20 15:43:39', '0');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '系统管理员');
INSERT INTO `role` VALUES ('2', '管理员');
INSERT INTO `role` VALUES ('3', '普通用户');

-- ----------------------------
-- Table structure for role_permission_relation
-- ----------------------------
DROP TABLE IF EXISTS `role_permission_relation`;
CREATE TABLE `role_permission_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表内id',
  `role_id` int(255) NOT NULL COMMENT '角色id',
  `permission_id` int(255) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `role_permission_relation_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `role_permission_relation_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色—权限关系表';

-- ----------------------------
-- Records of role_permission_relation
-- ----------------------------

-- ----------------------------
-- Table structure for sensitive
-- ----------------------------
DROP TABLE IF EXISTS `sensitive`;
CREATE TABLE `sensitive` (
  `word_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '敏感词id',
  `word` varchar(255) NOT NULL COMMENT '敏感词',
  PRIMARY KEY (`word_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sensitive
-- ----------------------------
INSERT INTO `sensitive` VALUES ('1', '只因');
INSERT INTO `sensitive` VALUES ('2', '你干嘛');
INSERT INTO `sensitive` VALUES ('3', '唱跳rap');

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type` (
  `type_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '帖子类型id',
  `type_name` varchar(255) DEFAULT NULL COMMENT '类型名',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='帖子类型表';

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES ('1', '表白');
INSERT INTO `type` VALUES ('2', '吐槽');
INSERT INTO `type` VALUES ('3', '提问');
INSERT INTO `type` VALUES ('4', '求助');
INSERT INTO `type` VALUES ('5', '征友');
INSERT INTO `type` VALUES ('6', '寻物');
INSERT INTO `type` VALUES ('7', '出售');
INSERT INTO `type` VALUES ('8', '回复');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(255) NOT NULL COMMENT '账号名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `role_id` int(255) NOT NULL COMMENT '角色id',
  `heads` varchar(255) DEFAULT NULL COMMENT '用户头像（链接形式）',
  `name` varchar(255) NOT NULL COMMENT '姓名（发帖时显示的名字）',
  `sex` varchar(255) NOT NULL COMMENT '性别',
  `exp` int(11) DEFAULT '0' COMMENT '经验值（等级通过代码换算）',
  `introduction` varchar(255) DEFAULT '' COMMENT '个人简介',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `deleted` int(1) DEFAULT '0' COMMENT '逻辑删除，删除为1，默认0',
  PRIMARY KEY (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '111111', '111111', '1', '', 'admin1', '男', '0', '系统管理员', '2022-11-20 15:26:12', '0');
INSERT INTO `user` VALUES ('2', '222222', '222222', '2', null, 'admin2', '男', '0', '管理员', '2022-11-20 15:27:42', '0');
INSERT INTO `user` VALUES ('3', '333333', '333333', '3', null, '桑磊', '男', '0', '', '2022-11-20 15:28:44', '0');
INSERT INTO `user` VALUES ('4', '444444', '444444', '3', null, '安佰勋', '男', '0', '', '2022-11-20 15:30:29', '0');
INSERT INTO `user` VALUES ('5', '555555', '555555', '3', null, '延明燚', '男', '0', '', '2022-11-20 15:30:56', '0');
INSERT INTO `user` VALUES ('6', '666666', '666666', '3', null, '彭达', '男', '0', '', '2022-11-20 15:31:12', '0');
INSERT INTO `user` VALUES ('7', '777777', '777777', '3', null, '梁泰', '男', '0', '', '2022-11-20 15:31:43', '0');
INSERT INTO `user` VALUES ('8', '888888', '888888', '3', null, '王超凡', '男', '0', '', '2022-11-20 15:32:06', '0');
INSERT INTO `user` VALUES ('9', '999999', '999999', '3', null, '宁家誉', '男', '0', '', '2022-11-20 15:32:25', '0');
INSERT INTO `user` VALUES ('10', '123456', '123456', '3', null, '任皓羽', '男', '0', '', '2022-11-20 15:32:48', '0');
INSERT INTO `user` VALUES ('11', '654321', '654321', '3', null, '初音未来', '女', '0', '', '2022-11-20 15:33:11', '0');

-- ----------------------------
-- Table structure for user_post
-- ----------------------------
DROP TABLE IF EXISTS `user_post`;
CREATE TABLE `user_post` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '表id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `post_id` int(11) NOT NULL COMMENT '帖子id',
  `islike` int(1) DEFAULT '0' COMMENT '是否点赞，是为1，默认0',
  `iscollected` int(1) DEFAULT '0' COMMENT '是否收藏，是为1，默认0',
  `isviewed` int(1) DEFAULT '0' COMMENT '是否浏览，是为1，默认0',
  `deleted` int(1) DEFAULT '0' COMMENT '逻辑删除，删除为1，默认0',
  `isreported` int(1) DEFAULT '0' COMMENT '是否举报，默认为0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `user_post_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `user_post_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_post
-- ----------------------------
INSERT INTO `user_post` VALUES ('1', '5', '1', '0', '0', '1', '0', '1');
