/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : gaming

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2016-11-28 17:33:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `advertising`
-- ----------------------------
DROP TABLE IF EXISTS `advertising`;
CREATE TABLE `advertising` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of advertising
-- ----------------------------
INSERT INTO `advertising` VALUES ('1', '2016-07-12 18:03:19', '/file/20160712/04814e150aa64a16a1a3112a5438f2ed.jpg', '测试广告位1', '1', '2016-07-12 18:04:21', 'localhost:9016/#/recommend/advertising/update1');
INSERT INTO `advertising` VALUES ('2', '2016-07-12 18:04:38', '/file/20160712/ab47e4e45b4f10ff42c83a0752a4bdb5.jpg', '测试啊啊', '1', '2016-07-12 18:04:38', 'http://localhost:9016/#/recommend/advertising/update');
INSERT INTO `advertising` VALUES ('3', '2016-07-25 15:39:40', '/file/20160725/ba45c8f60456a672e003a875e469d0eb.jpg', '还是测试', '1', '2016-07-25 15:39:40', 'http://localhost:9016/#/recommend/advertising/update');
INSERT INTO `advertising` VALUES ('4', '2016-07-25 15:39:57', '/file/20160725/9d377b10ce778c4938b3c7e2c63a229a.jpg', '继续测试', '1', '2016-07-25 15:39:57', 'dd');
INSERT INTO `advertising` VALUES ('5', '2016-07-25 15:40:15', '/file/20160725/fafa5efeaf3cbe3b23b2748d13e629a1.jpg', '再来', '1', '2016-07-25 15:40:15', '12121');
INSERT INTO `advertising` VALUES ('6', '2016-07-25 15:40:28', '/file/20160725/8969288f4245120e7c3870287cce0ff3.jpg', '仔仔来', '1', '2016-07-25 15:40:28', '444');

-- ----------------------------
-- Table structure for `anchor_zone`
-- ----------------------------
DROP TABLE IF EXISTS `anchor_zone`;
CREATE TABLE `anchor_zone` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `background_image` varchar(255) DEFAULT NULL,
  `ico_image` varchar(255) DEFAULT NULL,
  `introduction` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `name_first_letter` varchar(255) DEFAULT NULL,
  `name_spell` varchar(255) DEFAULT NULL,
  `other_username` varchar(255) DEFAULT NULL,
  `platform_id` bigint(20) DEFAULT NULL,
  `platform_name` varchar(255) DEFAULT NULL,
  `recommend_image` varchar(255) DEFAULT NULL,
  `seo_description` varchar(255) DEFAULT NULL,
  `seo_keywords` varchar(255) DEFAULT NULL,
  `seo_title` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `visit_count` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of anchor_zone
-- ----------------------------
INSERT INTO `anchor_zone` VALUES ('1', '2016-07-13 17:00:19', '1', '2016-07-18 13:41:59', '/file/20160713/ab47e4e45b4f10ff42c83a0752a4bdb5.jpg', '/file/20160713/e6eeb358e49e3cf4865a9739e15dbbb2.jpg', '<p>士大夫似的<br/></p>', '主播1', 'z', 'zhubo', '测试用户', '3', '熊猫TV', '/file/20160713/ae1181ad939906394f1addf941d2db0f.jpg', '测试', '测试', '测试', '默默人', '0');
INSERT INTO `anchor_zone` VALUES ('2', '2016-07-13 17:13:37', '1', '2016-07-13 17:13:37', '/file/20160713/ae1181ad939906394f1addf941d2db0f.jpg', '/file/20160713/e6eeb358e49e3cf4865a9739e15dbbb2.jpg', '<p>werwew<br/></p>', '测试珠宝盒', 'sdf', 'sdfsd', 'werwe', '2', '斗鱼', '/file/20160713/ab47e4e45b4f10ff42c83a0752a4bdb5.jpg', 'wer', 'wer', 'wer', 'wer', '0');
INSERT INTO `anchor_zone` VALUES ('3', '2016-07-14 10:37:25', '1', '2016-07-14 10:46:05', '/file/20160714/ab47e4e45b4f10ff42c83a0752a4bdb5.jpg', '/file/20160714/ae1181ad939906394f1addf941d2db0f.jpg', '<p>sdfs<br/></p>', 'ces', 'a', 'ce', 'sdf', '2', '斗鱼', '/file/20160714/b353a842cf095414a57fda3bf90737dc.jpg', 'ss', 'ss', 'ss', 'sdf', '20');

-- ----------------------------
-- Table structure for `anchor_zone_honor`
-- ----------------------------
DROP TABLE IF EXISTS `anchor_zone_honor`;
CREATE TABLE `anchor_zone_honor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `anchor_zone_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `honor_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of anchor_zone_honor
-- ----------------------------
INSERT INTO `anchor_zone_honor` VALUES ('25', '3', '2016-07-14 10:46:05', '2016-07-20 06:30:00', 'ces', '1', '2016-07-14 10:46:05');
INSERT INTO `anchor_zone_honor` VALUES ('26', '3', '2016-07-14 10:46:05', '2016-07-05 01:05:00', 'dds', '1', '2016-07-14 10:46:05');
INSERT INTO `anchor_zone_honor` VALUES ('28', '1', '2016-07-18 13:41:59', '2016-07-08 11:35:00', '测试3', '1', '2016-07-18 13:41:59');

-- ----------------------------
-- Table structure for `anchor_zone_match_zone_mapping`
-- ----------------------------
DROP TABLE IF EXISTS `anchor_zone_match_zone_mapping`;
CREATE TABLE `anchor_zone_match_zone_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `anchor_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `match_zone_id` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `anchor_zone_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of anchor_zone_match_zone_mapping
-- ----------------------------
INSERT INTO `anchor_zone_match_zone_mapping` VALUES ('11', null, '2016-07-14 10:46:05', '6', '1', '2016-07-14 10:46:05', '3');
INSERT INTO `anchor_zone_match_zone_mapping` VALUES ('12', null, '2016-07-18 13:41:59', '5', '1', '2016-07-18 13:41:59', '1');

-- ----------------------------
-- Table structure for `anchor_zone_platform`
-- ----------------------------
DROP TABLE IF EXISTS `anchor_zone_platform`;
CREATE TABLE `anchor_zone_platform` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of anchor_zone_platform
-- ----------------------------
INSERT INTO `anchor_zone_platform` VALUES ('1', '2016-07-13 16:52:57', '虎牙视频', '1', '2016-07-13 16:53:00');
INSERT INTO `anchor_zone_platform` VALUES ('2', '2016-07-13 16:53:34', '斗鱼', '1', '2016-07-13 16:53:45');
INSERT INTO `anchor_zone_platform` VALUES ('3', '2016-07-13 16:53:52', '熊猫TV', '1', '2016-07-13 16:54:04');

-- ----------------------------
-- Table structure for `info`
-- ----------------------------
DROP TABLE IF EXISTS `info`;
CREATE TABLE `info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `info_type` varchar(255) DEFAULT NULL,
  `info_video_type` varchar(255) DEFAULT NULL,
  `info_zone_type` varchar(255) DEFAULT NULL,
  `publish_time` datetime DEFAULT NULL,
  `seo_description` varchar(255) DEFAULT NULL,
  `seo_keywords` varchar(255) DEFAULT NULL,
  `seo_title` varchar(255) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `sub_title` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `title_image` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `verify` int(11) DEFAULT NULL,
  `visit_count` int(11) DEFAULT NULL,
  `zone_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of info
-- ----------------------------
INSERT INTO `info` VALUES ('1', 'sdf', '<p>sdfsdfsfd<br/></p>', '2016-07-18 18:10:20', 'news', null, 'anchorZone', '2016-07-25 16:47:18', 'sdf', 'sdf', 'sf', 'sdf', '1', 'sdfs', '哦也 啊啊', 'cesss', '/file/20160718/ae1181ad939906394f1addf941d2db0f.jpg', '2016-07-21 13:50:07', '1', '0', '2');
INSERT INTO `info` VALUES ('2', '没有', '<p>水水水水<br/></p>', '2016-07-21 13:41:03', 'news', null, 'matchZone', '2016-07-25 16:47:18', null, null, null, '自己', '1', '小标题', '资讯内容 自选', '在测试', '/file/20160721/04814e150aa64a16a1a3112a5438f2ed.jpg', '2016-07-21 13:49:52', '1', '10', '6');
INSERT INTO `info` VALUES ('3', '订单', '', '2016-07-21 14:08:07', 'video', 'anchorVideo', 'trade', '2016-07-25 16:47:18', null, null, null, '啊啊', '1', '测试行业', null, '测试行业', '/file/20160721/ae1181ad939906394f1addf941d2db0f.jpg', '2016-07-21 14:08:07', '1', '0', null);
INSERT INTO `info` VALUES ('4', '水电费', '<p>水电费水电费<br/></p>', '2016-07-25 16:47:18', 'news', null, 'matchZone', '2016-07-25 16:47:18', null, null, null, '胜多负少', '1', '测试咨询哦', null, '测试咨询哦', '/file/20160725/2b04df3ecc1d94afddff082d139c6f15.jpg', '2016-07-25 16:47:18', '1', '0', '10');
INSERT INTO `info` VALUES ('5', '213', '<p>123123<br/></p>', '2016-07-25 16:47:56', 'video', 'matchVideo', 'matchZone', '2016-07-25 16:47:56', null, null, null, '水电费水电费', '1', '123123', '1231', '水电费水电费', '/file/20160725/076e3caed758a1c18c91a0e9cae3368f.jpg', '2016-07-25 16:47:56', '1', '0', '10');
INSERT INTO `info` VALUES ('6', 'qwe', '<p>qweqwe<br/></p>', '2016-07-27 09:53:51', 'video', 'matchVideo', 'matchZone', '2016-07-27 09:53:51', 'qw', 'qwe', 'qwe', 'qwe', '1', 'qwe', 'qwe', 'qweqwe', '/file/20160727/fafa5efeaf3cbe3b23b2748d13e629a1.jpg', '2016-07-27 09:53:51', '1', '0', '12');
INSERT INTO `info` VALUES ('7', '123', '<p>qwe<br/></p>', '2016-07-27 09:54:20', 'video', 'matchVideo', 'matchZone', '2016-07-27 09:54:20', 'qwe', 'qwe', 'qw', '123', '1', 'qweqw', '123', 'sdffd', '/file/20160727/8969288f4245120e7c3870287cce0ff3.jpg', '2016-07-27 09:54:20', '1', '20', '14');
INSERT INTO `info` VALUES ('8', 'sdf', '<p>sdfsdf<br/></p>', '2016-07-27 09:54:40', 'video', 'matchVideo', 'matchZone', '2016-07-27 09:54:40', null, null, null, 'sfd', '1', 'sdfsdf', 'sdf', 'sdfsdf', '/file/20160727/8969288f4245120e7c3870287cce0ff3.jpg', '2016-07-27 09:55:42', '1', '0', '13');
INSERT INTO `info` VALUES ('9', 'xvc', '<p>xvc<br/></p>', '2016-07-27 09:55:13', 'video', 'matchVideo', 'matchZone', '2016-07-27 09:55:13', null, null, null, 'xcvxcv', '1', 'xcvcv', 'xcv', 'xvxcv', '/file/20160727/fafa5efeaf3cbe3b23b2748d13e629a1.jpg', '2016-07-27 09:55:36', '1', '0', '15');
INSERT INTO `info` VALUES ('10', '123', '<p>胜多负少<br/></p>', '2016-08-04 15:06:02', 'video', 'playerVideo', 'trade', '2016-08-04 15:06:02', null, null, null, '1231', '1', '123213', '123', '啥都是否', '/file/20160804/fafa5efeaf3cbe3b23b2748d13e629a1.jpg', '2016-08-04 15:06:02', '1', '0', null);
INSERT INTO `info` VALUES ('11', '112', '<p>1212<br/></p>', '2016-11-19 15:28:13', 'news', null, 'trade', '2016-11-19 15:28:13', null, null, null, '1', '1', '12', '1212', '112', null, '2016-11-19 15:28:13', '1', '0', null);
INSERT INTO `info` VALUES ('12', 'sdfsd', '', '2016-11-19 15:28:37', 'news', null, 'trade', '2016-11-19 15:28:37', null, null, null, 'sdfsdf', '1', 'sdfsdf', null, 'sdfsdf', '/file/20161119/e8a77009b60f6eb331260b3289a0dd20.jpg', '2016-11-19 15:28:37', '1', '0', null);
INSERT INTO `info` VALUES ('13', 'fgdfgd', '<p>dfgdfg<br/></p>', '2016-11-19 15:29:55', 'news', null, 'trade', '2016-11-19 15:29:55', null, null, null, 'dfgd', '1', 'dfgdfg', 'dfg', 'vbfddfgdf', null, '2016-11-19 15:29:55', '1', '0', null);
INSERT INTO `info` VALUES ('14', 'qweqwe', '<p>qweqw<br/></p>', '2016-11-19 15:32:38', 'news', null, 'trade', '2016-11-19 15:32:38', null, null, null, 'qweqw', '1', 'qweqwe', null, 'qweqweqw', '/file/20161119/ae1181ad939906394f1addf941d2db0f.jpg', '2016-11-19 15:32:38', '1', '0', null);

-- ----------------------------
-- Table structure for `match_team`
-- ----------------------------
DROP TABLE IF EXISTS `match_team`;
CREATE TABLE `match_team` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `ico_image` varchar(255) DEFAULT NULL,
  `if_star_team` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `set_up_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of match_team
-- ----------------------------
INSERT INTO `match_team` VALUES ('1', '2016-07-07 17:19:42', null, null, '无敌战队', '2016-07-07 17:19:55', '1', '2016-07-07 17:19:58');
INSERT INTO `match_team` VALUES ('2', '2016-07-07 17:20:08', '/file/20160712/e6eeb358e49e3cf4865a9739e15dbbb2.jpg', '', '也是战队', '2016-07-07 17:20:23', '1', '2016-07-12 15:22:52');
INSERT INTO `match_team` VALUES ('3', '2016-07-12 15:36:24', '/file/20160712/ae1181ad939906394f1addf941d2db0f.jpg', '', '测试的', '2016-07-06 10:30:00', '1', '2016-07-12 15:36:24');

-- ----------------------------
-- Table structure for `match_team_mapping`
-- ----------------------------
DROP TABLE IF EXISTS `match_team_mapping`;
CREATE TABLE `match_team_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `match_team_id` bigint(20) DEFAULT NULL,
  `match_zone_id` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of match_team_mapping
-- ----------------------------
INSERT INTO `match_team_mapping` VALUES ('1', '2016-07-12 15:21:20', '2', '6', '1', '2016-07-12 15:22:52');
INSERT INTO `match_team_mapping` VALUES ('3', '2016-07-12 15:22:29', '2', '5', '1', '2016-07-12 15:22:52');
INSERT INTO `match_team_mapping` VALUES ('4', '2016-07-12 15:36:24', '3', '5', '1', '2016-07-12 15:36:24');
INSERT INTO `match_team_mapping` VALUES ('5', '2016-07-12 15:36:24', '3', '1', '1', '2016-07-12 15:36:24');

-- ----------------------------
-- Table structure for `match_zone`
-- ----------------------------
DROP TABLE IF EXISTS `match_zone`;
CREATE TABLE `match_zone` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `background_image` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `host_party` varchar(255) DEFAULT NULL,
  `ico_image` varchar(255) DEFAULT NULL,
  `if_anchor_match` bit(1) DEFAULT NULL,
  `if_start` bit(1) DEFAULT NULL,
  `introduction` varchar(255) DEFAULT NULL,
  `match_status` varchar(255) DEFAULT NULL,
  `match_type` varchar(255) DEFAULT NULL,
  `match_zone_area_id` bigint(20) DEFAULT NULL,
  `match_zone_area_name` varchar(255) DEFAULT NULL,
  `match_zone_year_id` bigint(20) DEFAULT NULL,
  `match_zone_year_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `name_first_letter` varchar(255) DEFAULT NULL,
  `name_spell` varchar(255) DEFAULT NULL,
  `seo_description` varchar(255) DEFAULT NULL,
  `seo_keywords` varchar(255) DEFAULT NULL,
  `seo_title` varchar(255) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `visit_count` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of match_zone
-- ----------------------------
INSERT INTO `match_zone` VALUES ('1', '/file/20160630/ae1181ad939906394f1addf941d2db0f.jpg', '2016-06-30 15:53:06', '2016-06-10 07:35:00', '厦门这边', '/file/20160630/30ce2ef2f53e8634da01eb706d720cdb.jpg', '', '', '<p>测试街按揭啊啊 事实上<br/></p>', 'START', '线上比赛', '3', '北京', '4', '2015', '专区一', 'z', 'zhuangquyi1', '测试seo描述', '关键字1 关键字2 关键字3', '测试seo标题', '2016-06-09 10:30:00', '1', '2016-06-30 17:43:51', '20');
INSERT INTO `match_zone` VALUES ('2', null, '2016-07-08 14:50:51', null, null, null, '', '', '', null, null, null, null, null, null, null, null, null, null, null, null, null, '0', '2016-07-08 14:50:51', null);
INSERT INTO `match_zone` VALUES ('3', null, '2016-07-08 14:52:02', null, null, null, '', '', '', null, null, null, null, null, null, null, null, null, null, null, null, null, '0', '2016-07-08 14:52:02', null);
INSERT INTO `match_zone` VALUES ('4', null, '2016-07-08 17:24:53', null, null, null, '', '', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', '2016-07-08 17:24:53', null);
INSERT INTO `match_zone` VALUES ('5', '/file/20160708/ae1181ad939906394f1addf941d2db0f.jpg', '2016-07-08 17:36:07', '2016-07-02 07:15:00', '2131', '/file/20160708/ae1181ad939906394f1addf941d2db0f.jpg', '', '', '<p>水电费是否<br/></p>', 'START', '水电费', '3', '北京', '5', '2016', '专区二', 'z', 'zhuanquer', '水电费', '水电费', '水电费', '2016-07-01 07:15:00', '1', '2016-07-22 17:07:10', '10');
INSERT INTO `match_zone` VALUES ('6', '/file/20160708/ae1181ad939906394f1addf941d2db0f.jpg', '2016-07-08 18:05:06', '2016-07-15 15:55:00', '21', '/file/20160708/e6eeb358e49e3cf4865a9739e15dbbb2.jpg', '', '', '<p>水电费水电费<br/></p>', 'START', '测试', '2', '福建', '5', '2016', '专区三', 'z', 'zhuanqusan', '水电费', '水电费', '水电费', '2016-07-07 10:30:00', '1', '2016-07-22 17:06:59', '10');
INSERT INTO `match_zone` VALUES ('10', null, '2016-07-12 15:57:16', '2016-06-30 06:15:00', '是', null, '', '', '<p>上算得上是<br/></p>', 'DOING', '才', '2', '福建', '5', '2016', '测试专区名称', 'z', '哈哈', null, null, null, '2016-07-20 10:10:00', '1', '2016-07-12 15:57:16', '0');
INSERT INTO `match_zone` VALUES ('11', '/file/20160722/ae1181ad939906394f1addf941d2db0f.jpg', '2016-07-22 17:06:36', '2016-07-08 11:55:00', 'sdf', '/file/20160722/e8a77009b60f6eb331260b3289a0dd20.jpg', '', '', '<p>sdf<br/></p>', 'START', 'sdf', '3', '北京', '5', '2016', '测试专区赛事2', '21', '12', null, null, null, '2016-07-07 02:10:00', '1', '2016-07-22 17:06:36', '20');
INSERT INTO `match_zone` VALUES ('12', null, '2016-07-27 09:50:40', '2016-07-22 15:15:00', 'ces', null, '', '', '<p>sdf<br/></p>', 'START', 'meiyou', '1', '全国', '5', '2016', '炉石传说', 'l', 'lushichuanshuo', null, null, null, '2016-07-05 05:05:00', '1', '2016-07-27 10:00:22', '0');
INSERT INTO `match_zone` VALUES ('13', '/file/20160727/fafa5efeaf3cbe3b23b2748d13e629a1.jpg', '2016-07-27 09:51:40', '2016-07-08 11:35:00', 'qweq', '/file/20160727/5a44c7ba5bbe4ec867233d67e4806848.jpg', '', '', '', 'DOING', 'sadas', '1', '全国', '5', '2016', '虚荣', 'x', 'xurong', null, null, null, '2016-07-05 05:05:00', '1', '2016-07-27 10:00:17', '0');
INSERT INTO `match_zone` VALUES ('14', null, '2016-07-27 09:52:18', '2016-07-14 14:50:00', 'sd', null, '', '', '', 'DOING', 'dsd', '2', '福建', '5', '2016', '全民枪战', 'q', 'quanmingqiangzhan', null, null, null, '2016-07-05 05:05:00', '1', '2016-07-27 10:00:11', '0');
INSERT INTO `match_zone` VALUES ('15', '/file/20160727/fafa5efeaf3cbe3b23b2748d13e629a1.jpg', '2016-07-27 09:52:55', '2016-07-01 11:35:00', 'sdf', '/file/20160727/fafa5efeaf3cbe3b23b2748d13e629a1.jpg', '', '', '', 'START', 'sdf', '2', '福建', '5', '2016', '自由之战', 'z', 'ziyouzhizhan', null, null, null, '2016-07-06 10:30:00', '1', '2016-07-27 10:00:05', '0');

-- ----------------------------
-- Table structure for `match_zone_area`
-- ----------------------------
DROP TABLE IF EXISTS `match_zone_area`;
CREATE TABLE `match_zone_area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `area_name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of match_zone_area
-- ----------------------------
INSERT INTO `match_zone_area` VALUES ('1', '全国', '2016-06-30 16:54:03', '1', '2016-06-30 16:54:05');
INSERT INTO `match_zone_area` VALUES ('2', '福建', '2016-06-30 16:54:14', '1', '2016-06-30 16:54:17');
INSERT INTO `match_zone_area` VALUES ('3', '北京', '2016-06-30 16:54:34', '1', '2016-06-30 16:54:39');

-- ----------------------------
-- Table structure for `match_zone_bonus`
-- ----------------------------
DROP TABLE IF EXISTS `match_zone_bonus`;
CREATE TABLE `match_zone_bonus` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bonus_fee` double DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `match_zone_id` bigint(20) DEFAULT NULL,
  `ranking` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of match_zone_bonus
-- ----------------------------
INSERT INTO `match_zone_bonus` VALUES ('1', '5000', '2016-07-08 17:24:53', null, '一等奖', '1', '2016-07-08 17:24:53');
INSERT INTO `match_zone_bonus` VALUES ('4', '300', '2016-07-08 18:06:01', null, '第五名', '1', '2016-07-08 18:06:01');
INSERT INTO `match_zone_bonus` VALUES ('5', '200', '2016-07-08 18:06:01', null, '第六名', '1', '2016-07-08 18:06:01');
INSERT INTO `match_zone_bonus` VALUES ('23', '300', '2016-07-22 17:06:59', '6', '民称3', '1', '2016-07-22 17:06:59');
INSERT INTO `match_zone_bonus` VALUES ('24', '200', '2016-07-22 17:06:59', '6', '沙发沙发', '1', '2016-07-22 17:06:59');
INSERT INTO `match_zone_bonus` VALUES ('25', '123', '2016-07-22 17:06:59', '6', '1231', '1', '2016-07-22 17:06:59');
INSERT INTO `match_zone_bonus` VALUES ('26', '3510', '2016-07-22 17:07:10', '5', '第一名', '1', '2016-07-22 17:07:10');

-- ----------------------------
-- Table structure for `match_zone_calendar`
-- ----------------------------
DROP TABLE IF EXISTS `match_zone_calendar`;
CREATE TABLE `match_zone_calendar` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `match_team_one_id` bigint(20) DEFAULT NULL,
  `match_team_two_id` bigint(20) DEFAULT NULL,
  `match_zone_id` bigint(20) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `match_team_one_name` varchar(255) DEFAULT NULL,
  `match_team_two_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of match_zone_calendar
-- ----------------------------
INSERT INTO `match_zone_calendar` VALUES ('1', '2016-07-08 17:24:53', '2', '1', null, '2016-07-01 03:15:00', '1', '2016-07-08 17:24:53', null, null);
INSERT INTO `match_zone_calendar` VALUES ('3', '2016-07-08 18:06:01', '2', '1', null, '2016-07-02 11:35:00', '1', '2016-07-08 18:06:01', '也是战队', '无敌战队');
INSERT INTO `match_zone_calendar` VALUES ('11', '2016-07-22 17:06:36', '2', '3', '11', '2016-07-07 11:15:00', '1', '2016-07-22 17:06:36', '也是战队', '测试的');
INSERT INTO `match_zone_calendar` VALUES ('12', '2016-07-22 17:06:36', '1', '2', '11', '2016-07-05 09:25:00', '1', '2016-07-22 17:06:36', '无敌战队', '也是战队');
INSERT INTO `match_zone_calendar` VALUES ('13', '2016-07-22 17:06:59', '2', '1', '6', '2016-07-04 05:05:00', '1', '2016-07-22 17:06:59', '也是战队', '无敌战队');
INSERT INTO `match_zone_calendar` VALUES ('14', '2016-07-22 17:07:10', '3', '3', '5', null, '1', '2016-07-22 17:07:10', '测试的', '测试的');

-- ----------------------------
-- Table structure for `match_zone_year`
-- ----------------------------
DROP TABLE IF EXISTS `match_zone_year`;
CREATE TABLE `match_zone_year` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `year_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of match_zone_year
-- ----------------------------
INSERT INTO `match_zone_year` VALUES ('1', '2016-06-30 16:52:36', '1', '2016-06-30 16:52:39', '2012');
INSERT INTO `match_zone_year` VALUES ('2', '2016-06-30 16:52:58', '1', '2016-06-30 16:52:56', '2013');
INSERT INTO `match_zone_year` VALUES ('3', '2016-06-30 16:53:06', '1', '2016-06-30 16:53:11', '2014');
INSERT INTO `match_zone_year` VALUES ('4', '2016-06-30 16:53:21', '1', '2016-06-30 16:53:23', '2015');
INSERT INTO `match_zone_year` VALUES ('5', '2016-06-30 16:53:33', '1', '2016-06-30 16:53:35', '2016');

-- ----------------------------
-- Table structure for `picture`
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `picture_zone_type` varchar(100) DEFAULT NULL,
  `publish_time` varchar(255) DEFAULT NULL,
  `seo_description` varchar(255) DEFAULT NULL,
  `seo_keywords` varchar(255) DEFAULT NULL,
  `seo_title` varchar(255) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `verify` int(11) DEFAULT NULL,
  `visit` int(11) DEFAULT NULL,
  `zone_id` int(11) DEFAULT NULL,
  `visit_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of picture
-- ----------------------------
INSERT INTO `picture` VALUES ('1', '2016-07-15 17:12:19', '额', '/file/20160715/e8a77009b60f6eb331260b3289a0dd20.jpg', null, null, null, null, null, null, '1', '是', null, null, '2016-07-15 17:12:19', '1', null, null, null);
INSERT INTO `picture` VALUES ('3', '2016-07-18 11:25:08', '测试美图', '/file/20160718/30ce2ef2f53e8634da01eb706d720cdb.jpg', null, null, null, null, null, '琵琶网', '1', '测试 美女 氧气', null, null, '2016-07-18 11:40:48', '1', null, null, '20');
INSERT INTO `picture` VALUES ('4', '2016-07-18 15:55:16', '测', '/file/20160718/30ce2ef2f53e8634da01eb706d720cdb.jpg', 'matchZone', null, null, null, null, '琵琶网', '1', '测试', null, null, '2016-07-18 15:55:55', '1', null, '6', '0');

-- ----------------------------
-- Table structure for `picture_detail`
-- ----------------------------
DROP TABLE IF EXISTS `picture_detail`;
CREATE TABLE `picture_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `picture_id` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of picture_detail
-- ----------------------------
INSERT INTO `picture_detail` VALUES ('1', '2016-07-15 17:12:19', '始发地', '/file/20160715/04814e150aa64a16a1a3112a5438f2ed.jpg', '1', '1', '2016-07-15 17:12:19', '1');
INSERT INTO `picture_detail` VALUES ('2', '2016-07-15 17:12:19', '胜多负少', '/file/20160715/b353a842cf095414a57fda3bf90737dc.jpg', '1', '1', '2016-07-15 17:12:19', '2');
INSERT INTO `picture_detail` VALUES ('19', '2016-07-18 11:40:48', '订单', '/file/20160718/ae1181ad939906394f1addf941d2db0f.jpg', '3', '1', '2016-07-18 11:40:48', '1');
INSERT INTO `picture_detail` VALUES ('20', '2016-07-18 11:40:48', '搜索', '/file/20160718/b353a842cf095414a57fda3bf90737dc.jpg', '3', '1', '2016-07-18 11:40:48', '2');

-- ----------------------------
-- Table structure for `recommend`
-- ----------------------------
DROP TABLE IF EXISTS `recommend`;
CREATE TABLE `recommend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `recommend_type` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `recommend_local` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recommend
-- ----------------------------
INSERT INTO `recommend` VALUES ('1', '2016-07-12 10:58:19', '首页banner', 'matchZone', '1', '2016-07-26 17:26:34', 'index');
INSERT INTO `recommend` VALUES ('2', '2016-07-12 10:58:54', '首页头部', 'matchZone', '1', '2016-07-26 17:26:40', null);
INSERT INTO `recommend` VALUES ('3', '2016-07-12 10:59:14', '栏目页头部', 'matchZone', '1', '2016-07-12 10:59:32', null);
INSERT INTO `recommend` VALUES ('4', '2016-07-12 10:59:38', '栏目页底部1', 'matchZone', '1', '2016-07-12 17:03:30', null);
INSERT INTO `recommend` VALUES ('5', '2016-07-12 17:04:13', '测试啊啊啊11', 'news', '1', '2016-07-12 17:04:31', null);
INSERT INTO `recommend` VALUES ('6', '2016-07-13 16:47:07', '主播推荐位1', 'anchorZone', '1', '2016-07-13 16:47:07', 'index');
INSERT INTO `recommend` VALUES ('7', '2016-07-13 16:47:21', '主播推荐位2', 'anchorZone', '1', '2016-07-13 16:47:21', null);
INSERT INTO `recommend` VALUES ('8', '2016-07-13 16:47:30', '主播推荐位3', 'anchorZone', '1', '2016-07-13 16:47:30', null);
INSERT INTO `recommend` VALUES ('9', '2016-07-15 16:37:48', '图集推荐1', 'picture', '1', '2016-07-15 16:37:48', 'index');
INSERT INTO `recommend` VALUES ('10', '2016-07-15 16:37:56', '图集推荐2', 'picture', '1', '2016-07-15 16:37:56', null);
INSERT INTO `recommend` VALUES ('11', '2016-07-15 16:38:05', '图集推荐3', 'picture', '1', '2016-07-15 16:38:05', null);
INSERT INTO `recommend` VALUES ('12', '2016-07-27 09:59:56', '首页比赛视频推荐', 'matchZone', '1', '2016-07-27 09:59:56', 'matchZoneVideo');

-- ----------------------------
-- Table structure for `recommend_mapping`
-- ----------------------------
DROP TABLE IF EXISTS `recommend_mapping`;
CREATE TABLE `recommend_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `entity_id` bigint(20) DEFAULT NULL,
  `recommend_id` bigint(20) DEFAULT NULL,
  `recommend_type` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `recommend_local` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recommend_mapping
-- ----------------------------
INSERT INTO `recommend_mapping` VALUES ('4', '2016-07-12 15:57:16', '10', '3', 'matchZone', '1', '2016-07-12 15:57:16', 'index');
INSERT INTO `recommend_mapping` VALUES ('5', '2016-07-12 15:57:16', '10', '2', 'matchZone', '1', '2016-07-12 15:57:16', 'index');
INSERT INTO `recommend_mapping` VALUES ('6', '2016-07-12 15:57:16', '10', '1', 'matchZone', '1', '2016-07-12 15:57:16', 'index');
INSERT INTO `recommend_mapping` VALUES ('7', '2016-07-13 17:00:19', '1', '8', 'matchZone', '1', '2016-07-13 17:00:19', 'index');
INSERT INTO `recommend_mapping` VALUES ('8', '2016-07-13 17:00:19', '1', '7', 'matchZone', '1', '2016-07-13 17:00:19', 'index');
INSERT INTO `recommend_mapping` VALUES ('9', '2016-07-13 17:12:07', '1', '8', 'anchorZone', '1', '2016-07-18 13:41:59', 'index');
INSERT INTO `recommend_mapping` VALUES ('10', '2016-07-13 17:12:12', '1', '7', 'anchorZone', '1', '2016-07-18 13:41:59', 'index');
INSERT INTO `recommend_mapping` VALUES ('11', '2016-07-13 17:13:58', '2', '7', 'anchorZone', '1', '2016-07-13 17:13:58', 'index');
INSERT INTO `recommend_mapping` VALUES ('12', '2016-07-13 17:14:01', '2', '6', 'anchorZone', '1', '2016-07-13 17:14:01', 'index');
INSERT INTO `recommend_mapping` VALUES ('14', '2016-07-14 10:37:25', '3', '7', 'anchorZone', '1', '2016-07-14 10:46:05', 'index');
INSERT INTO `recommend_mapping` VALUES ('15', '2016-07-14 10:37:42', '3', '6', 'anchorZone', '1', '2016-07-14 10:46:05', 'index');
INSERT INTO `recommend_mapping` VALUES ('16', '2016-07-15 17:12:19', '1', '11', 'picture', '1', '2016-07-15 17:12:19', 'index');
INSERT INTO `recommend_mapping` VALUES ('17', '2016-07-15 17:12:19', '1', '10', 'picture', '1', '2016-07-15 17:12:19', 'index');
INSERT INTO `recommend_mapping` VALUES ('20', '2016-07-18 11:25:08', '3', '11', 'picture', '1', '2016-07-18 11:40:48', 'index');
INSERT INTO `recommend_mapping` VALUES ('21', '2016-07-18 11:25:08', '3', '10', 'picture', '1', '2016-07-18 11:40:48', 'index');
INSERT INTO `recommend_mapping` VALUES ('22', '2016-07-18 15:55:16', '4', '11', 'picture', '1', '2016-07-18 15:55:55', 'index');
INSERT INTO `recommend_mapping` VALUES ('23', '2016-07-18 15:55:16', '4', '9', 'picture', '1', '2016-07-18 15:55:55', 'index');
INSERT INTO `recommend_mapping` VALUES ('24', '2016-07-18 18:10:20', '1', '5', 'news', '1', '2016-07-21 13:50:07', 'index');
INSERT INTO `recommend_mapping` VALUES ('25', '2016-07-21 13:41:03', '2', '5', 'news', '1', '2016-07-21 13:49:52', 'index');
INSERT INTO `recommend_mapping` VALUES ('26', '2016-07-22 17:06:36', '11', '1', 'matchZone', '1', '2016-07-22 17:06:36', 'index');
INSERT INTO `recommend_mapping` VALUES ('27', '2016-07-22 17:06:59', '6', '1', 'matchZone', '1', '2016-07-22 17:06:59', 'index');
INSERT INTO `recommend_mapping` VALUES ('28', '2016-07-22 17:07:10', '5', '1', 'matchZone', '1', '2016-07-22 17:07:10', 'index');
INSERT INTO `recommend_mapping` VALUES ('29', '2016-07-25 16:47:18', '4', '5', 'news', '1', '2016-07-25 16:47:18', 'index');
INSERT INTO `recommend_mapping` VALUES ('30', '2016-07-25 16:47:56', '5', '5', 'news', '1', '2016-07-25 16:47:56', 'index');
INSERT INTO `recommend_mapping` VALUES ('31', '2016-07-27 10:00:05', '15', '12', 'matchZone', '1', '2016-07-27 10:00:05', 'matchZoneVideo');
INSERT INTO `recommend_mapping` VALUES ('32', '2016-07-27 10:00:11', '14', '12', 'matchZone', '1', '2016-07-27 10:00:11', 'matchZoneVideo');
INSERT INTO `recommend_mapping` VALUES ('33', '2016-07-27 10:00:17', '13', '12', 'matchZone', '1', '2016-07-27 10:00:17', 'matchZoneVideo');
INSERT INTO `recommend_mapping` VALUES ('34', '2016-07-27 10:00:22', '12', '12', 'matchZone', '1', '2016-07-27 10:00:22', 'matchZoneVideo');
INSERT INTO `recommend_mapping` VALUES ('35', '2016-08-04 15:06:02', '10', '5', 'news', '1', '2016-08-04 15:06:02', null);

-- ----------------------------
-- Table structure for `system_enum`
-- ----------------------------
DROP TABLE IF EXISTS `system_enum`;
CREATE TABLE `system_enum` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_enum
-- ----------------------------

-- ----------------------------
-- Table structure for `tags`
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `tag_type` varchar(100) DEFAULT NULL,
  `tag_zone_type` varchar(100) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tags
-- ----------------------------
INSERT INTO `tags` VALUES ('4', '2016-07-18 11:38:06', '测试', '1', 'picture', null, '2016-07-18 11:38:06');
INSERT INTO `tags` VALUES ('5', '2016-07-18 11:38:25', '美女', '1', 'picture', null, '2016-07-18 11:38:25');
INSERT INTO `tags` VALUES ('6', '2016-07-18 11:38:26', '氧气', '1', 'picture', null, '2016-07-18 11:38:26');
INSERT INTO `tags` VALUES ('13', '2016-07-21 13:49:37', '资讯内容', '1', 'news', 'matchZone', '2016-07-21 13:49:37');
INSERT INTO `tags` VALUES ('14', '2016-07-21 13:49:37', '自选', '1', 'news', 'matchZone', '2016-07-21 13:49:37');
INSERT INTO `tags` VALUES ('15', '2016-07-21 13:50:07', '哦也', '1', 'news', 'anchorZone', '2016-07-21 13:50:07');
INSERT INTO `tags` VALUES ('16', '2016-07-21 13:50:07', '啊啊', '1', 'news', 'anchorZone', '2016-07-21 13:50:07');
INSERT INTO `tags` VALUES ('17', '2016-07-25 16:47:57', '1231', '1', 'video', 'matchZone', '2016-07-25 16:47:57');
INSERT INTO `tags` VALUES ('18', '2016-07-27 09:53:52', 'qwe', '1', 'video', 'matchZone', '2016-07-27 09:53:52');
INSERT INTO `tags` VALUES ('19', '2016-07-27 09:54:20', '123', '1', 'video', 'matchZone', '2016-07-27 09:54:20');
INSERT INTO `tags` VALUES ('20', '2016-07-27 09:54:40', 'sdf', '1', 'video', 'matchZone', '2016-07-27 09:54:40');
INSERT INTO `tags` VALUES ('21', '2016-07-27 09:55:13', 'xcv', '1', 'video', 'matchZone', '2016-07-27 09:55:13');
INSERT INTO `tags` VALUES ('22', '2016-08-04 15:06:02', '123', '1', 'video', 'trade', '2016-08-04 15:06:02');
INSERT INTO `tags` VALUES ('23', '2016-11-19 15:28:13', '1212', '1', 'news', 'trade', '2016-11-19 15:28:13');
INSERT INTO `tags` VALUES ('24', '2016-11-19 15:29:55', 'dfg', '1', 'news', 'trade', '2016-11-19 15:29:55');

-- ----------------------------
-- Table structure for `tag_mapping`
-- ----------------------------
DROP TABLE IF EXISTS `tag_mapping`;
CREATE TABLE `tag_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `entity_id` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `tag_type` varchar(100) DEFAULT NULL,
  `tag_zone_type` varchar(100) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `tags_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tag_mapping
-- ----------------------------
INSERT INTO `tag_mapping` VALUES ('22', '2016-07-18 11:40:48', '3', '1', 'picture', null, '2016-07-18 11:40:48', '4');
INSERT INTO `tag_mapping` VALUES ('23', '2016-07-18 11:40:48', '3', '1', 'picture', null, '2016-07-18 11:40:48', '5');
INSERT INTO `tag_mapping` VALUES ('24', '2016-07-18 11:40:48', '3', '1', 'picture', null, '2016-07-18 11:40:48', '6');
INSERT INTO `tag_mapping` VALUES ('26', '2016-07-18 15:55:55', '4', '1', 'picture', null, '2016-07-18 15:55:55', '4');
INSERT INTO `tag_mapping` VALUES ('37', '2016-07-21 13:49:52', '2', '1', 'news', 'matchZone', '2016-07-21 13:49:52', '13');
INSERT INTO `tag_mapping` VALUES ('38', '2016-07-21 13:49:52', '2', '1', 'news', 'matchZone', '2016-07-21 13:49:52', '14');
INSERT INTO `tag_mapping` VALUES ('39', '2016-07-21 13:50:07', '1', '1', 'news', 'anchorZone', '2016-07-21 13:50:07', '15');
INSERT INTO `tag_mapping` VALUES ('40', '2016-07-21 13:50:07', '1', '1', 'news', 'anchorZone', '2016-07-21 13:50:07', '16');
INSERT INTO `tag_mapping` VALUES ('41', '2016-07-25 16:47:57', '5', '1', 'video', 'matchZone', '2016-07-25 16:47:57', '17');
INSERT INTO `tag_mapping` VALUES ('42', '2016-07-27 09:53:52', '6', '1', 'video', 'matchZone', '2016-07-27 09:53:52', '18');
INSERT INTO `tag_mapping` VALUES ('43', '2016-07-27 09:54:20', '7', '1', 'video', 'matchZone', '2016-07-27 09:54:20', '19');
INSERT INTO `tag_mapping` VALUES ('46', '2016-07-27 09:55:36', '9', '1', 'video', 'matchZone', '2016-07-27 09:55:36', '21');
INSERT INTO `tag_mapping` VALUES ('47', '2016-07-27 09:55:42', '8', '1', 'video', 'matchZone', '2016-07-27 09:55:42', '20');
INSERT INTO `tag_mapping` VALUES ('48', '2016-08-04 15:06:02', '10', '1', 'video', 'trade', '2016-08-04 15:06:02', '22');
INSERT INTO `tag_mapping` VALUES ('49', '2016-11-19 15:28:13', '11', '1', 'news', 'trade', '2016-11-19 15:28:13', '23');
INSERT INTO `tag_mapping` VALUES ('50', '2016-11-19 15:29:55', '13', '1', 'news', 'trade', '2016-11-19 15:29:55', '24');

-- ----------------------------
-- Table structure for `wc_sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `wc_sys_menu`;
CREATE TABLE `wc_sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `parent_uuid` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `icon_path` varchar(255) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of wc_sys_menu
-- ----------------------------
INSERT INTO `wc_sys_menu` VALUES ('1', '网站运营管理界面', '1', 'website.index', '464817a915ad11e6bc0a562a0a586b7e', '1', '1', null, '2016-05-09 00:00:00', '2016-05-09 00:00:00', null, null, null);
INSERT INTO `wc_sys_menu` VALUES ('11', '超级管理员界面', '1', 'admin', '216b86e1169e11e6bc0a562a0a586b7e', '1', '3', null, '2016-05-10 00:00:00', '2016-05-10 00:00:00', null, null, null);
INSERT INTO `wc_sys_menu` VALUES ('13', '系统设置', '1', '', '3d7278e3169f11e6bc0a562a0a586b7e', '2', '1', '216b86e1169e11e6bc0a562a0a586b7e', '2016-05-10 00:00:00', '2016-05-10 00:00:00', '', null, null);
INSERT INTO `wc_sys_menu` VALUES ('14', '账户管理', '1', 'admin.sys-user.list', '2a56579c16a011e6bc0a562a0a586b7e', '3', '1', '3d7278e3169f11e6bc0a562a0a586b7e', '2016-05-10 00:00:00', '2016-05-10 00:00:00', null, null, null);
INSERT INTO `wc_sys_menu` VALUES ('15', '角色管理', '1', 'admin.sys-role.list', 'be92b45f16a011e6bc0a562a0a586b7e', '3', '2', '3d7278e3169f11e6bc0a562a0a586b7e', '2016-05-10 00:00:00', '2016-05-10 00:00:00', null, null, null);
INSERT INTO `wc_sys_menu` VALUES ('16', '菜单管理', '1', 'admin.sys-menu.list', '08b268ac16a111e6bc0a562a0a586b7e', '3', '3', '3d7278e3169f11e6bc0a562a0a586b7e', '2016-05-10 00:00:00', '2016-05-10 00:00:00', null, null, null);
INSERT INTO `wc_sys_menu` VALUES ('17', '专区管理', '1', 'zone', '14e5b979259f469b81e03bd910b60f2a', '2', null, '464817a915ad11e6bc0a562a0a586b7e', '2016-06-29 17:06:11', '2016-06-29 17:06:11', null, null, null);
INSERT INTO `wc_sys_menu` VALUES ('18', '赛事管理', '1', 'zone.match-zone.list', 'eb52c63f29834372b96c3178f7caef92', '3', '1', '14e5b979259f469b81e03bd910b60f2a', '2016-06-29 17:08:42', '2016-06-29 17:08:42', null, null, null);
INSERT INTO `wc_sys_menu` VALUES ('19', '战队管理', '1', 'zone.match-team.list', '42eda74db42f475bb3afcabb319ac4cc', '3', '2', '14e5b979259f469b81e03bd910b60f2a', '2016-07-12 13:56:29', '2016-07-12 13:56:29', null, null, null);
INSERT INTO `wc_sys_menu` VALUES ('20', '推荐管理', '1', '', 'a7c2d30365394dd1955b6ff5151d1561', '2', '2', '464817a915ad11e6bc0a562a0a586b7e', '2016-07-12 14:00:20', '2016-07-12 14:00:20', null, null, null);
INSERT INTO `wc_sys_menu` VALUES ('21', '推荐位管理', '1', 'recommend.recommend.list', '79b001d980bc4ee1ba62a239076c5174', '3', '1', 'a7c2d30365394dd1955b6ff5151d1561', '2016-07-12 14:01:46', '2016-07-12 14:01:46', null, null, null);
INSERT INTO `wc_sys_menu` VALUES ('22', '广告位管理', '1', 'recommend.advertising.list', '332496b643d948b9a69cedac66ee3c8b', '3', '2', 'a7c2d30365394dd1955b6ff5151d1561', '2016-07-12 14:06:42', '2016-07-12 14:06:42', null, null, null);
INSERT INTO `wc_sys_menu` VALUES ('23', '主播管理', '1', 'zone.anchor-zone.list', 'e84e77159af84b41b2c5ed434fd27cff', '3', '3', '14e5b979259f469b81e03bd910b60f2a', '2016-07-12 14:08:33', '2016-07-12 14:08:33', null, null, null);
INSERT INTO `wc_sys_menu` VALUES ('24', '图集管理', '1', 'picture.list', '726f73b93e104f1da1ae1a3ebeb35312', '2', '3', '464817a915ad11e6bc0a562a0a586b7e', '2016-07-15 16:33:45', '2016-07-15 16:33:45', null, null, null);
INSERT INTO `wc_sys_menu` VALUES ('25', '图集管理', '1', 'picture.list', '05e3395972734a49bb87a152faf24595', '3', '1', '726f73b93e104f1da1ae1a3ebeb35312', '2016-07-15 16:36:44', '2016-07-15 16:36:44', null, null, null);
INSERT INTO `wc_sys_menu` VALUES ('26', '资讯管理', '1', null, 'b1484f59cacb4dfd94f33225d08bbe43', '2', null, '464817a915ad11e6bc0a562a0a586b7e', '2016-07-18 17:05:31', '2016-07-18 17:05:31', null, null, null);
INSERT INTO `wc_sys_menu` VALUES ('27', '资讯列表', '1', 'info.list', '621ff0befd4149e1997381fb6c8fb389', '3', '1', 'b1484f59cacb4dfd94f33225d08bbe43', '2016-07-18 17:08:21', '2016-07-18 17:08:21', null, null, null);

-- ----------------------------
-- Table structure for `wc_sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `wc_sys_role`;
CREATE TABLE `wc_sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_type` varchar(255) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of wc_sys_role
-- ----------------------------
INSERT INTO `wc_sys_role` VALUES ('1', 'admin', null, '1', 'd151d4b115ac11e6bc0a562a0a586b7e', '2016-05-09 00:00:00', '2016-05-09 00:00:00', 'SUPER_ADMIN', null, null);
INSERT INTO `wc_sys_role` VALUES ('2', 'test', null, '0', '67753d09bc4b44549d67ccef1d6d3474', '2016-05-12 19:23:08', '2016-05-12 19:29:32', 'SUPER_ADMIN', null, null);
INSERT INTO `wc_sys_role` VALUES ('3', 'test', 'test1', '0', '29556b5573f54f1686d8c764cb817224', '2016-05-12 19:34:57', '2016-05-12 19:39:52', 'SUPER_ADMIN', null, null);
INSERT INTO `wc_sys_role` VALUES ('4', 'role_test', 'role_test', '1', '3d6e56d3e5364b2ba770f538db385cc7', '2016-05-16 14:31:02', '2016-05-16 14:31:02', 'SUPER_ADMIN', null, null);
INSERT INTO `wc_sys_role` VALUES ('5', 'test1', 'test', '1', '3361aa9ad9614ea484933bfbbc654b35', '2016-05-20 17:25:10', '2016-05-20 17:25:10', 'COMPANY_ADMIN', null, null);
INSERT INTO `wc_sys_role` VALUES ('6', 'DISTRIBUTOR', 'DISTRIBUTOR', '1', '21cca3ee446744069a2e2b555bfb9983', '2016-05-23 14:02:11', '2016-05-23 14:02:11', 'DISTRIBUTOR', null, null);
INSERT INTO `wc_sys_role` VALUES ('7', '经销商1', '1', '1', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-23 16:21:31', '2016-05-23 16:21:31', 'DISTRIBUTOR', null, null);
INSERT INTO `wc_sys_role` VALUES ('8', '员工角色1', '员工角色1', '1', '6e4982a1574e480890f3c5221f0e3857', '2016-05-23 17:23:13', '2016-05-23 17:23:13', 'STAFF', null, null);

-- ----------------------------
-- Table structure for `wc_sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `wc_sys_role_menu`;
CREATE TABLE `wc_sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` int(11) NOT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `menu_uuid` varchar(255) DEFAULT NULL,
  `role_uuid` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=510 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of wc_sys_role_menu
-- ----------------------------
INSERT INTO `wc_sys_role_menu` VALUES ('111', '1', 'b5af330fd83446a085ecfc52691d89f8', '464817a915ad11e6bc0a562a0a586b7e', '3d6e56d3e5364b2ba770f538db385cc7', '2016-05-18 15:28:05', '2016-05-18 15:28:05', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('112', '1', '20083a03b15b48f0884bf765905162b2', '5e3aa32281c64ff38c3cb3027fa571f0', '3d6e56d3e5364b2ba770f538db385cc7', '2016-05-18 15:28:05', '2016-05-18 15:28:05', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('113', '1', 'e5d308396bc54b9b873cee0096468b5d', 'e558d6d43da64641ab41fc15a4c92f19', '3d6e56d3e5364b2ba770f538db385cc7', '2016-05-18 15:28:05', '2016-05-18 15:28:05', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('114', '1', '78644109cd9d4dcca275a2fe510da691', '216b86e1169e11e6bc0a562a0a586b7e', '3d6e56d3e5364b2ba770f538db385cc7', '2016-05-18 15:28:05', '2016-05-18 15:28:05', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('115', '1', '12d3f549dd9440f6abb04ac21dc7ea6e', '3d7278e3169f11e6bc0a562a0a586b7e', '3d6e56d3e5364b2ba770f538db385cc7', '2016-05-18 15:28:06', '2016-05-18 15:28:06', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('116', '1', '87f029d742cf42c4b3bca6c5bf43a2f0', '2a56579c16a011e6bc0a562a0a586b7e', '3d6e56d3e5364b2ba770f538db385cc7', '2016-05-18 15:28:06', '2016-05-18 15:28:06', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('117', '1', 'e15e5d08c48545c5bd4979ed77fa5027', '1a1e9dab1c9e11e6bc0a562a0a586b7e', '3d6e56d3e5364b2ba770f538db385cc7', '2016-05-18 15:28:06', '2016-05-18 15:28:06', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('118', '1', '9f6a38be46704cd99570ef76ee739ea2', '4c2fd04446ed419d91b808040ae3a716', '3d6e56d3e5364b2ba770f538db385cc7', '2016-05-18 15:28:06', '2016-05-18 15:28:06', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('119', '1', '98cb85b398774d6c9b756643f2bfc0ae', 'f8c7155105b444a79c273d138ae8117b', '3d6e56d3e5364b2ba770f538db385cc7', '2016-05-18 15:28:06', '2016-05-18 15:28:06', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('378', '1', '260d052466264fe7a034205fb4a6939b', '464817a915ad11e6bc0a562a0a586b7e', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('379', '1', 'a2cff0f95e5d4fc3b601b6fd5709d620', '5e3aa32281c64ff38c3cb3027fa571f0', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('380', '1', 'f752509480714a8d903fa2c65b6497d6', 'fb193c2bfa3f4775b0e8d51b7eb4fca9', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('381', '1', 'fc356b7936ea409e9fb2dc74c42e2a29', 'e558d6d43da64641ab41fc15a4c92f19', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('382', '1', 'aebe839e5bbd4461b468ab575761e665', 'ecc9c31de3c542449534e2165b5817cf', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('383', '1', '6e9aac07491a4517b8d3beadbdb89024', 'b7824d95f3f94a0a8d2eb40974982996', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('384', '1', '9041a672dea84813892a0392aa6ceb0e', '7ad6841115ad11e6bc0a562a0a586b7e', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('385', '1', '840abb7d5d434d40a0be2ce9d167ad8c', 'a5d8d0b215ad11e6bc0a562a0a586b7e', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('386', '1', 'd37c7ce93c194ff986469ffa6558b10d', 'f78eedfc167811e6bc0a562a0a586b7e', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('387', '1', '98dfd440e62746b681e593d5566ec4d7', 'ac7145ce168911e6bc0a562a0a586b7e', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('388', '1', 'ec66f96b5d914565aaf9b7cb940fb299', '6210f3421cbc11e6bc0a562a0a586b7e', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('389', '1', '4f606aadf4d54336894fba7b52f430e7', '70b0b4b918ed11e6bc0a562a0a586b7e', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('390', '1', '2fff30d418fa42f5b5fe39b365044e09', '8e676ea21b3b11e6bc0a562a0a586b7e', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('391', '1', '69f54d14172443b9b67b9f917238cbad', 'f12681191cc111e6bc0a562a0a586b7e', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('392', '1', '5cb6f952f168402ab997cfd9337e1f07', 'f008a02f1d8f11e6bc0a562a0a586b7e', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('393', '1', '4a40daea5c7f4edaaa167fcce8e83f83', 'e8c4b45e1e5411e6bc0a562a0a586b7e', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('394', '1', '0deb950b048e4f778291f4287ed096fb', 'fd0fd4512f77492c96c9fc1b76b97319', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('395', '1', '98917e6049cd43149747ab488e5eb980', '0f3acd6c61ba4410abe2a1c0655095f7', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('396', '1', '2119a914c4dc431ea274c19434a25517', '1b8fdd150ab8418fb58708733c36ba13', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('397', '1', '09f1bf26af344ef2886a16401e067089', 'ef6197de20c211e6bc0a562a0a586b7e', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('398', '1', 'ac97023d9bbf45d0a75c59dc442b71eb', '668fb68d20c411e6bc0a562a0a586b7e', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('402', '1', '0507587e6dea431c8914775261c0051d', '464817a915ad11e6bc0a562a0a586b7e', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('403', '1', '8981ff11e8c24e92862a6962ffe74306', '5e3aa32281c64ff38c3cb3027fa571f0', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('404', '1', 'efe86ffe334f4dcea7626c5ddc9fa10a', 'fb193c2bfa3f4775b0e8d51b7eb4fca9', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('405', '1', '22749f49d5494b7fbd11e60c1ee7e3a8', 'e558d6d43da64641ab41fc15a4c92f19', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('406', '1', 'fc0eebbd7959434eacbee7176f86d8ed', 'ecc9c31de3c542449534e2165b5817cf', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('407', '1', 'e0772b098b4148fdacba6457a19260de', 'b7824d95f3f94a0a8d2eb40974982996', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('408', '1', '67a2f49c4ffe4226a77a71920f66b881', '7ad6841115ad11e6bc0a562a0a586b7e', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('409', '1', 'e48f0f3ca9a84745ad80bf6930246f0e', 'a5d8d0b215ad11e6bc0a562a0a586b7e', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('410', '1', 'f6d1a59564d24a38a1898392ce83fb96', 'f78eedfc167811e6bc0a562a0a586b7e', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('411', '1', '4c607a1278414f45897b9d46f7638e1c', 'ac7145ce168911e6bc0a562a0a586b7e', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('412', '1', '21bce7213be14741b76a6ef8ea815792', '6210f3421cbc11e6bc0a562a0a586b7e', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('413', '1', '395ddc38f33140a58444fca62054a5b2', '70b0b4b918ed11e6bc0a562a0a586b7e', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('414', '1', 'e542279282de45b089e3e2e30f14558d', '8e676ea21b3b11e6bc0a562a0a586b7e', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('415', '1', 'aa3e90211603464d81a6d2c9326940a7', 'f12681191cc111e6bc0a562a0a586b7e', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('416', '1', '4228206163dd4c46970b123f8e67c633', 'f008a02f1d8f11e6bc0a562a0a586b7e', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('417', '1', '94362a14dd2c4a35a9ee2556dcdfdbb1', 'e8c4b45e1e5411e6bc0a562a0a586b7e', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('418', '1', '000dc59e0c114c9bbb75f80ce5016f34', 'fd0fd4512f77492c96c9fc1b76b97319', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('419', '1', 'b1bf12749c354680860c2bd2788a609d', '0f3acd6c61ba4410abe2a1c0655095f7', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('420', '1', 'c1bf0d0dfe184478aff7088fca50a786', '1b8fdd150ab8418fb58708733c36ba13', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('421', '1', 'a25eadea5bde4c8ba1a7a33b7c326f34', 'ef6197de20c211e6bc0a562a0a586b7e', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('422', '1', '190b49aa193040198cbd01908750fa78', '668fb68d20c411e6bc0a562a0a586b7e', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('423', '1', 'eba237e5d8394166882569dfe6398b70', '3624cb6c208e11e6bc0a562a0a586b7e', '3361aa9ad9614ea484933bfbbc654b35', '2016-05-27 09:40:03', '2016-05-27 09:40:03', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('424', '1', '58655f12825b457dafa3808304ae3560', '37a52aea209111e6bc0a562a0a586b7e', '3361aa9ad9614ea484933bfbbc654b35', '2016-05-27 09:40:03', '2016-05-27 09:40:03', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('425', '1', '231a1360a76948b99ae96d7e176cea0a', '2965490f209111e6bc0a562a0a586b7e', '3361aa9ad9614ea484933bfbbc654b35', '2016-05-27 09:40:03', '2016-05-27 09:40:03', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('427', '1', 'f3482dea26d111e6bc0a562a0a586b7e', 'cfc7598426d111e6bc0a562a0a586b7e', '6e4982a1574e480890f3c5221f0e3857', '2016-05-27 09:39:36', '2016-05-27 09:39:36', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('428', '1', '5af3ae5326d211e6bc0a562a0a586b7e', 'cfc7598426d111e6bc0a562a0a586b7e', '483f1c4c142f445b8febfce4b0af5f26', '2016-05-27 09:39:56', '2016-05-27 09:39:56', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('493', '1', 'c6af2da51ef941fa9917ecfd86540683', '464817a915ad11e6bc0a562a0a586b7e', 'd151d4b115ac11e6bc0a562a0a586b7e', '2016-07-18 17:08:46', '2016-07-18 17:08:46', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('494', '1', 'd71457ec47864552b32f00ea0c7f8e60', '14e5b979259f469b81e03bd910b60f2a', 'd151d4b115ac11e6bc0a562a0a586b7e', '2016-07-18 17:08:46', '2016-07-18 17:08:46', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('495', '1', '3fd8951563ef408c95e2888e99ae48ed', 'eb52c63f29834372b96c3178f7caef92', 'd151d4b115ac11e6bc0a562a0a586b7e', '2016-07-18 17:08:46', '2016-07-18 17:08:46', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('496', '1', '8ef3002b90d54382902efe37f535f5e6', '42eda74db42f475bb3afcabb319ac4cc', 'd151d4b115ac11e6bc0a562a0a586b7e', '2016-07-18 17:08:46', '2016-07-18 17:08:46', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('497', '1', '9d2d0b3516194a21bd34888b06951db9', 'e84e77159af84b41b2c5ed434fd27cff', 'd151d4b115ac11e6bc0a562a0a586b7e', '2016-07-18 17:08:46', '2016-07-18 17:08:46', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('498', '1', '528772e763c84bd99eddf3cc7a3d7f90', 'b1484f59cacb4dfd94f33225d08bbe43', 'd151d4b115ac11e6bc0a562a0a586b7e', '2016-07-18 17:08:46', '2016-07-18 17:08:46', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('499', '1', '0bd69eb4027c4f73b7199815b5150c4f', '621ff0befd4149e1997381fb6c8fb389', 'd151d4b115ac11e6bc0a562a0a586b7e', '2016-07-18 17:08:46', '2016-07-18 17:08:46', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('500', '1', '3fc641f32dbf42cfae9568bffb865fc0', 'a7c2d30365394dd1955b6ff5151d1561', 'd151d4b115ac11e6bc0a562a0a586b7e', '2016-07-18 17:08:46', '2016-07-18 17:08:46', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('501', '1', '0b9dc523e42c405f84ae53d9f948c702', '79b001d980bc4ee1ba62a239076c5174', 'd151d4b115ac11e6bc0a562a0a586b7e', '2016-07-18 17:08:46', '2016-07-18 17:08:46', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('502', '1', '2f00d15677204b4eb676bef7968472ff', '332496b643d948b9a69cedac66ee3c8b', 'd151d4b115ac11e6bc0a562a0a586b7e', '2016-07-18 17:08:46', '2016-07-18 17:08:46', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('503', '1', '373bd4640b03429da1a4487532e69378', '726f73b93e104f1da1ae1a3ebeb35312', 'd151d4b115ac11e6bc0a562a0a586b7e', '2016-07-18 17:08:46', '2016-07-18 17:08:46', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('504', '1', '950b9edea0f0441ea4043f21aa153bac', '05e3395972734a49bb87a152faf24595', 'd151d4b115ac11e6bc0a562a0a586b7e', '2016-07-18 17:08:46', '2016-07-18 17:08:46', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('505', '1', 'aaa2484f39e2444db0067d305b8dc1c5', '216b86e1169e11e6bc0a562a0a586b7e', 'd151d4b115ac11e6bc0a562a0a586b7e', '2016-07-18 17:08:46', '2016-07-18 17:08:46', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('506', '1', 'b6e28edd71224883a79c21fa14e62df5', '3d7278e3169f11e6bc0a562a0a586b7e', 'd151d4b115ac11e6bc0a562a0a586b7e', '2016-07-18 17:08:46', '2016-07-18 17:08:46', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('507', '1', 'd97d3431d49b4d558ea30e79def30116', '2a56579c16a011e6bc0a562a0a586b7e', 'd151d4b115ac11e6bc0a562a0a586b7e', '2016-07-18 17:08:46', '2016-07-18 17:08:46', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('508', '1', '2ef3be95b03d4942959b630c6a46c20b', 'be92b45f16a011e6bc0a562a0a586b7e', 'd151d4b115ac11e6bc0a562a0a586b7e', '2016-07-18 17:08:46', '2016-07-18 17:08:46', null, null);
INSERT INTO `wc_sys_role_menu` VALUES ('509', '1', 'effcc8eebea8416eaa26b9da03955a35', '08b268ac16a111e6bc0a562a0a586b7e', 'd151d4b115ac11e6bc0a562a0a586b7e', '2016-07-18 17:08:46', '2016-07-18 17:08:46', null, null);

-- ----------------------------
-- Table structure for `wc_sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `wc_sys_user`;
CREATE TABLE `wc_sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `parent_uuid` varchar(255) DEFAULT NULL,
  `mp_uuid` varchar(255) DEFAULT NULL,
  `company_uuid` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `dealer_uuid` varchar(255) DEFAULT NULL,
  `user_type` varchar(255) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of wc_sys_user
-- ----------------------------
INSERT INTO `wc_sys_user` VALUES ('1', 'jEFArlyT5O+FSM6ZIT9oNQ==', 'WkA81BY4NMYMyaYOp7nIb/lbU2tGNpapWU4DdvtJLE4=', '1', 'admin', 'c6328d22f60d4aa4960c6e6a70cd75d9', null, '0', '0', null, '2015-05-06 00:00:00', '2016-05-10 17:15:30', null, 'SUPER_ADMIN', null, null);
INSERT INTO `wc_sys_user` VALUES ('2', 'O9gREVpHYbM6t+1bPRohAQ==', 'DJVp9AbFyQPZAR4eeLeisC6M9kLDkNZGLEwf+jUfKMU=', '0', 'test', '4f4d0581987b4b1aaa5da152e60a8727', 'c6328d22f60d4aa4960c6e6a70cd75d9', null, null, null, '2016-05-12 16:27:55', '2016-05-12 17:51:52', null, 'SUPER_ADMIN', null, null);
INSERT INTO `wc_sys_user` VALUES ('3', 'mstC12wkK7mgeiG4ColBww==', 'AKHhU5AoNAyYbyO6I5oHvzzr+E2OFTeWEX/+7o4shZU=', '0', 'test', '72941b3b55c74f14b9f3a8ca8469ad75', 'c6328d22f60d4aa4960c6e6a70cd75d9', null, null, null, '2016-05-12 17:53:55', '2016-05-12 17:53:59', null, 'SUPER_ADMIN', null, null);
INSERT INTO `wc_sys_user` VALUES ('4', 'kyXI+2LZys2F1P1iAUQPGw==', 's91WbXoHAZ4VNTEUHYJ5eRgLkNHATRwi+7TVRugqXMg=', '0', 'test', '788affcf563b4aa18ce932c1c86482e9', 'c6328d22f60d4aa4960c6e6a70cd75d9', null, null, null, '2016-05-12 17:55:02', '2016-05-12 17:55:05', null, 'SUPER_ADMIN', null, null);
INSERT INTO `wc_sys_user` VALUES ('5', 'Jqu+F6uYePe1AC4S/4JZRg==', 'LOnr1cigsyINhKRxIBF7JQNCBEdrChQ3F3i5kvNOFRQ=', '0', 'test', '46487a3d635041bda824765da36547d0', 'c6328d22f60d4aa4960c6e6a70cd75d9', null, null, null, '2016-05-12 17:58:43', '2016-05-12 18:01:42', null, 'SUPER_ADMIN', null, null);
INSERT INTO `wc_sys_user` VALUES ('6', 'VhZHIokjI+CYKa052WTB9w==', 'IkG+EAfZTurwodfDsa7OST0FBGohHn8lewmEsgXR9YI=', '1', 'test', '8ef19d7bfbff4a21838ca938fc0a3b85', 'c6328d22f60d4aa4960c6e6a70cd75d9', 'f0d8c2d32d434bceab3f9f4577e51747', null, null, '2016-05-13 14:38:30', '2016-05-13 14:38:30', null, 'SUPER_ADMIN', null, null);
INSERT INTO `wc_sys_user` VALUES ('7', 'MwlDzliQFvgG7yPGIHIC2A==', 'T4xnVbfbkTC4EWbzYqp2R8e9vtRomGQ9Fk1O0usm7jI=', '1', 'test1', '62226cda1c7c4df0b154a2c7ba69df04', 'c6328d22f60d4aa4960c6e6a70cd75d9', null, null, null, '2016-05-13 14:38:35', '2016-05-13 14:38:35', null, 'SUPER_ADMIN', null, null);
INSERT INTO `wc_sys_user` VALUES ('8', 'N/XMU+c8yx2oWPgpqzJE0g==', 'GSpuVJb8IH7It8us5j+STvQq1AN9AP+qEVciF/JKSFM=', '1', 'dd', 'c1b2b18c109c442dbe493f095a2cbba4', 'c6328d22f60d4aa4960c6e6a70cd75d9', null, null, null, '2016-05-20 13:35:00', '2016-05-20 13:35:00', null, 'SUPER_ADMIN', null, null);
INSERT INTO `wc_sys_user` VALUES ('9', 'zAyldrl9SutUv6zia7oO/A==', 'vW6Rh1Rxewx4ULZZA6tmu6JAKPz5BfwIktNKHseX5Rk=', '1', 'test2', '1ecf48bd81724173bc9164a323615540', 'c6328d22f60d4aa4960c6e6a70cd75d9', null, null, null, '2016-05-20 16:52:15', '2016-05-20 16:52:15', null, 'SUPER_ADMIN', null, null);
INSERT INTO `wc_sys_user` VALUES ('10', 'RV2Y49pSHtB+vrn718uVuw==', 'i3NoZhNCqq5V8mfi76ik3OgbLJNKua/eJoCz0bz4kPk=', '1', 'test3', '3fb3c9dc1fba46a0b133a7ca12875a5b', 'c6328d22f60d4aa4960c6e6a70cd75d9', null, null, null, '2016-05-20 17:25:44', '2016-05-20 17:54:14', null, 'COMPANY_ADMIN', null, null);
INSERT INTO `wc_sys_user` VALUES ('11', 'Q1HsZg7/bhBJCpnkD9rqjQ==', 'DJ/DGJywekWuy0qL+x88s7BwJYJIg27PN4TSJ3YYXSM=', '1', 'company_admin', '783a33e01c434bd1843a4582680a5fb6', '3fb3c9dc1fba46a0b133a7ca12875a5b', null, null, null, '2016-05-23 13:29:33', '2016-05-27 09:37:30', null, 'DISTRIBUTOR', null, null);
INSERT INTO `wc_sys_user` VALUES ('12', '4F3vpcZD/VB7zXsWlmCVnQ==', 'e5MU0m5F6sWa6qI1MKCzqYYNL4M9U21RAmbdxItdRbQ=', '1', 'company_admin1', '1526fdecbd194929a812dc390bd10454', 'c6328d22f60d4aa4960c6e6a70cd75d9', null, null, null, '2016-05-23 13:39:23', '2016-05-23 13:41:17', null, 'DISTRIBUTOR', null, null);
INSERT INTO `wc_sys_user` VALUES ('13', '8PLR5QIMNa9B2SZO7B/haQ==', 'RyNsLmETicUgmad5rI0I5qk/wnuZvgJN87b2eHxZzMw=', '0', 'company_user_test', 'b8e28a85b1654ea88f4d389926985fb0', 'c6328d22f60d4aa4960c6e6a70cd75d9', null, null, null, '2016-05-23 14:11:43', '2016-05-23 14:13:09', null, 'DISTRIBUTOR', null, null);
INSERT INTO `wc_sys_user` VALUES ('14', 'o6WXMH/8yXvaHe3UD4AKrQ==', '0yf2yGYoQUki8F2fag6aYkSVlrmzGhZe/zz+9o2/x0Y=', '1', 'company_user_test', 'f4acca6ab8a74051909060d13932f51a', 'c6328d22f60d4aa4960c6e6a70cd75d9', null, null, null, '2016-05-23 14:13:38', '2016-05-23 14:13:38', null, 'DISTRIBUTOR', null, null);
INSERT INTO `wc_sys_user` VALUES ('15', 'bpvXHVzfXEY8uOSxUZ586g==', 'W8TKb9VeEe9nK4+rZBkJ/ia6ih7BCNVLrIVYh1TVMqM=', '1', '员工1', '27d76ffe6c0941318de00e04913c32a6', 'c6328d22f60d4aa4960c6e6a70cd75d9', null, null, null, '2016-05-23 17:23:29', '2016-05-27 09:38:30', null, 'STAFF', null, null);
INSERT INTO `wc_sys_user` VALUES ('16', 'v3Se35J1QhRy6emiUtp1Gg==', 'jhoRboFNvIMoKz5n4+F/OkB6OLJ763ZCWVpEy1uolNM=', '1', 'test_remark', '468741da310141ee9da88a58a9ed675d', 'c6328d22f60d4aa4960c6e6a70cd75d9', null, null, 'test', '2016-05-26 10:47:39', '2016-05-26 10:47:39', null, 'STAFF', null, null);
INSERT INTO `wc_sys_user` VALUES ('17', 'tBa4MzvQZN6NKQ6yNIN8tQ==', 'bosev29GKkyvtGnHqgibe2iyrDuQTH9SBG5X3RUKrzU=', '1', '员工222', '9847bd4b0c864b8c95f6f22c2e6af2c2', '783a33e01c434bd1843a4582680a5fb6', null, null, null, '2016-05-27 09:42:18', '2016-05-27 09:42:18', null, 'STAFF', null, null);
INSERT INTO `wc_sys_user` VALUES ('18', 'cUSpPl44XM1NWBLhG7Nzyw==', 'LLY01mNuXm18nJn7m8ZRE3MNICSOLYh0S7NVFYbUtBg=', '1', 'miller', 'c49b7167bd244d21ab764b990736cbae', 'c6328d22f60d4aa4960c6e6a70cd75d9', '123456', null, 'miller 测试', '2016-05-27 16:58:19', '2016-05-27 16:58:19', null, 'SUPER_ADMIN', null, null);

-- ----------------------------
-- Table structure for `wc_sys_user_action`
-- ----------------------------
DROP TABLE IF EXISTS `wc_sys_user_action`;
CREATE TABLE `wc_sys_user_action` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `mp_uuid` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wc_sys_user_action
-- ----------------------------

-- ----------------------------
-- Table structure for `wc_sys_user_login`
-- ----------------------------
DROP TABLE IF EXISTS `wc_sys_user_login`;
CREATE TABLE `wc_sys_user_login` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `mp_uuid` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wc_sys_user_login
-- ----------------------------

-- ----------------------------
-- Table structure for `wc_sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `wc_sys_user_role`;
CREATE TABLE `wc_sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` int(11) NOT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `role_uuid` varchar(255) DEFAULT NULL,
  `user_uuid` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of wc_sys_user_role
-- ----------------------------
INSERT INTO `wc_sys_user_role` VALUES ('14', '1', '2c479b581c364f35bec4c0148f02c768', '3d6e56d3e5364b2ba770f538db385cc7', '62226cda1c7c4df0b154a2c7ba69df04', '2016-05-16 14:31:13', '2016-05-16 14:31:13', null, null);
INSERT INTO `wc_sys_user_role` VALUES ('15', '1', 'd0abf403502942bbbef0f9733fe6bc54', '3d6e56d3e5364b2ba770f538db385cc7', '8ef19d7bfbff4a21838ca938fc0a3b85', '2016-05-16 14:31:13', '2016-05-16 14:31:13', null, null);
INSERT INTO `wc_sys_user_role` VALUES ('17', '1', '7112b06046164857afb7729d3dfc147d', '3361aa9ad9614ea484933bfbbc654b35', '3fb3c9dc1fba46a0b133a7ca12875a5b', '2016-05-23 10:02:47', '2016-05-23 10:02:47', null, null);
INSERT INTO `wc_sys_user_role` VALUES ('18', '1', '693506e5f04c455aac444eb67653696f', '483f1c4c142f445b8febfce4b0af5f26', 'f4acca6ab8a74051909060d13932f51a', '2016-05-23 14:13:38', '2016-05-23 16:34:23', null, null);
INSERT INTO `wc_sys_user_role` VALUES ('19', '1', '45148eba0f394665bbefbf47584866e3', '6e4982a1574e480890f3c5221f0e3857', '27d76ffe6c0941318de00e04913c32a6', '2016-05-23 17:23:29', '2016-05-23 17:26:12', null, null);
INSERT INTO `wc_sys_user_role` VALUES ('20', '1', 'e99eb372ee8649e5ac48c4bb9c726cba', '6e4982a1574e480890f3c5221f0e3857', '468741da310141ee9da88a58a9ed675d', '2016-05-26 10:47:39', '2016-05-26 10:47:39', null, null);
INSERT INTO `wc_sys_user_role` VALUES ('21', '1', 'ed0eae69879c4448b9c123d572b06cdf', 'd151d4b115ac11e6bc0a562a0a586b7e', 'c49b7167bd244d21ab764b990736cbae', '2016-05-27 16:59:03', '2016-05-27 16:59:03', null, null);
INSERT INTO `wc_sys_user_role` VALUES ('22', '1', '6aa1c88caccd49cc867eb621fa4a0ee4', 'd151d4b115ac11e6bc0a562a0a586b7e', 'c6328d22f60d4aa4960c6e6a70cd75d9', '2016-05-27 16:59:03', '2016-05-27 16:59:03', null, null);

-- ----------------------------
-- Table structure for `wc_user_action_log`
-- ----------------------------
DROP TABLE IF EXISTS `wc_user_action_log`;
CREATE TABLE `wc_user_action_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action_url` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `http_method` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `request_body` longtext,
  `request_parameter` varchar(255) DEFAULT NULL,
  `response_body` longtext,
  `username` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `wx_username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wc_user_action_log
-- ----------------------------

-- ----------------------------
-- Table structure for `wc_user_login_log`
-- ----------------------------
DROP TABLE IF EXISTS `wc_user_login_log`;
CREATE TABLE `wc_user_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `session_id` varchar(255) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `wx_username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wc_user_login_log
-- ----------------------------
