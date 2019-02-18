/*
 Navicat MySQL Data Transfer

 Source Server         : tms-测试
 Source Server Type    : MySQL
 Source Server Version : 50616
 Source Host           : snt-daily-pub.mysql.rds.aliyuncs.com:3306
 Source Schema         : snt_sco

 Target Server Type    : MySQL
 Target Server Version : 50616
 File Encoding         : 65001

 Date: 18/02/2019 09:24:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_sco_truck_trace
-- ----------------------------
DROP TABLE IF EXISTS `t_sco_truck_trace`;
CREATE TABLE `t_sco_truck_trace` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `truck_trace_code` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '物流跟踪单号',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `status` int(2) DEFAULT NULL COMMENT '货车跟踪单状态 1初始化 、2运输中 、3已到货',
  `ext_att` varchar(4000) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '扩展字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='货车轨迹';

SET FOREIGN_KEY_CHECKS = 1;
