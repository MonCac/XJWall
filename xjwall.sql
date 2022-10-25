/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50560
Source Host           : localhost:3306
Source Database       : xjwall

Target Server Type    : MYSQL
Target Server Version : 50560
File Encoding         : 65001

Date: 2022-10-25 00:40:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for operation
-- ----------------------------
DROP TABLE IF EXISTS `operation`;
CREATE TABLE `operation` (
  `operation_id` varchar(255) NOT NULL COMMENT '操作id',
  `operation_name` varchar(255) DEFAULT NULL COMMENT '操作名',
  PRIMARY KEY (`operation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作表';

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `permission_id` varchar(255) NOT NULL COMMENT '权限id',
  `permission_name` varchar(255) DEFAULT NULL COMMENT '权限名',
  `operation_id` varchar(255) DEFAULT NULL COMMENT '操作id',
  PRIMARY KEY (`permission_id`),
  KEY `operation_id` (`operation_id`),
  CONSTRAINT `permission_ibfk_1` FOREIGN KEY (`operation_id`) REFERENCES `operation` (`operation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `post_id` varchar(255) NOT NULL COMMENT '帖子id',
  `type_id` varchar(255) NOT NULL COMMENT '帖子类型',
  `title` varchar(255) DEFAULT NULL COMMENT '帖子标题',
  `content` varchar(255) NOT NULL COMMENT '内容概述',
  `owner_id` varchar(255) DEFAULT NULL COMMENT '发帖人id',
  `father_post` varchar(255) DEFAULT NULL COMMENT '父帖id',
  `create_time` datetime NOT NULL,
  `likes` int(11) DEFAULT '0' COMMENT '点赞数',
  PRIMARY KEY (`post_id`),
  KEY `type_id` (`type_id`),
  KEY `father_post` (`father_post`),
  KEY `owner_id` (`owner_id`),
  CONSTRAINT `post_ibfk_3` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `type` (`type_id`),
  CONSTRAINT `post_ibfk_2` FOREIGN KEY (`father_post`) REFERENCES `post` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` varchar(255) NOT NULL COMMENT '角色id',
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for role_permission_relation
-- ----------------------------
DROP TABLE IF EXISTS `role_permission_relation`;
CREATE TABLE `role_permission_relation` (
  `role_id` varchar(255) NOT NULL COMMENT '角色id',
  `permission_id` varchar(255) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `role_permission_relation_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`),
  CONSTRAINT `role_permission_relation_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色—权限关系表';

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type` (
  `type_id` varchar(255) NOT NULL COMMENT '帖子类型id',
  `type_name` varchar(255) DEFAULT NULL COMMENT '类型名',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='帖子类型表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` varchar(255) NOT NULL COMMENT '用户id',
  `user_name` varchar(255) NOT NULL COMMENT '账号名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `role_id` varchar(255) DEFAULT NULL COMMENT '角色id',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名（发帖时显示的名字）',
  `sex` varchar(255) DEFAULT NULL COMMENT '性别',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `deleted` int(255) DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
