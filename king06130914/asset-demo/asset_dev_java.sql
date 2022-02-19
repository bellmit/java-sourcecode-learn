/*
 Navicat Premium Data Transfer

 Source Server         : 固定资产-开发数据库
 Source Server Type    : MySQL
 Source Server Version : 50624
 Source Host           : 10.10.13.139:3306
 Source Schema         : asset_dev

 Target Server Type    : MySQL
 Target Server Version : 50624
 File Encoding         : 65001

 Date: 27/01/2021 20:09:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for as_admin_node
-- ----------------------------
DROP TABLE IF EXISTS `as_admin_node`;
CREATE TABLE `as_admin_node` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `menu_name` varchar(50) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `node_name` char(50) NOT NULL DEFAULT '' COMMENT '节点名称',
  `pid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父节点编号',
  `paths` varchar(500) NOT NULL DEFAULT '' COMMENT '路径paths',
  `menu_class` char(50) NOT NULL DEFAULT '' COMMENT '菜单class样式',
  `menu_icon` char(50) NOT NULL DEFAULT '' COMMENT '样式ico 图标',
  `sort_num` int(10) unsigned NOT NULL DEFAULT '100' COMMENT '排序序号',
  `fe_route` char(50) NOT NULL DEFAULT '' COMMENT '前端路由名',
  `api_routes` varchar(255) NOT NULL DEFAULT '' COMMENT '后端路由URI 集合 | 分隔 ',
  `api_routes_name` varchar(255) NOT NULL DEFAULT '' COMMENT '后端路由URI 名称集合|分割',
  `is_menu` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否菜单',
  `button_config` varchar(255) DEFAULT '' COMMENT '显示按钮   asset_add#资产添加|asset_del#资产删除',
  `is_default` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否默认节点 0：否  1：是',
  `is_admin` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否超管权限 0：否  1：是',
  `is_config` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否配置项',
  `flag` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '1：共有权限  2：PC   3：APP',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sort_num` (`sort_num`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='后台 (菜单)权限节点表';

-- ----------------------------
-- Table structure for as_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `as_admin_role`;
CREATE TABLE `as_admin_role` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `role_name` char(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  `type` tinyint(1) NOT NULL DEFAULT '2' COMMENT '角色类型 1:超级管理  2：普通角色',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='后台-角色表';

-- ----------------------------
-- Table structure for as_admin_role_node
-- ----------------------------
DROP TABLE IF EXISTS `as_admin_role_node`;
CREATE TABLE `as_admin_role_node` (
  `role_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '角色id',
  `node_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '菜单或按钮或动作id',
  PRIMARY KEY (`role_id`,`node_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='后台-角色-动作关联表';

-- ----------------------------
-- Table structure for as_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `as_admin_user`;
CREATE TABLE `as_admin_user` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `user_name` char(50) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '账号',
  `password` varchar(70) NOT NULL DEFAULT '' COMMENT '密码',
  `phone` varchar(13) NOT NULL DEFAULT '' COMMENT '手机',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  `user_type` tinyint(1) unsigned NOT NULL DEFAULT '2' COMMENT '用户类型',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态 1:正常  2:禁用 ',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否软删除 0-否  1-是',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `org_role` (`role_id`) USING BTREE,
  KEY `user_name` (`user_name`) USING BTREE,
  KEY `phone` (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户账号表';

-- ----------------------------
-- Table structure for as_anti_fake_printer
-- ----------------------------
DROP TABLE IF EXISTS `as_anti_fake_printer`;
CREATE TABLE `as_anti_fake_printer` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `printer_name` varchar(50) NOT NULL COMMENT '打印机型号',
  `is_pass` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否全型号免验证 0-否  1-是',
  `describe` varchar(300) DEFAULT NULL COMMENT '描述',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0 未删除  1已删除',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='防伪配置表';

-- ----------------------------
-- Table structure for as_anti_fake_serial_number
-- ----------------------------
DROP TABLE IF EXISTS `as_anti_fake_serial_number`;
CREATE TABLE `as_anti_fake_serial_number` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `printer_id` bigint(20) NOT NULL COMMENT '防伪配置id',
  `serial_number` varchar(100) NOT NULL COMMENT '序列号',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0未删除   1已删除',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `serial_number` (`serial_number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='防伪配置授权序列号表';

-- ----------------------------
-- Table structure for as_area
-- ----------------------------
DROP TABLE IF EXISTS `as_area`;
CREATE TABLE `as_area` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `area_name` varchar(50) NOT NULL DEFAULT '' COMMENT '区域名称',
  `area_code` varchar(8) NOT NULL DEFAULT '' COMMENT '区域CODE',
  `pid` bigint(20) unsigned DEFAULT '0' COMMENT '节点',
  `root_org_id` int(11) DEFAULT NULL COMMENT '顶级组织编号',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '0-未删除  1：已删除',
  `sort_num` int(11) DEFAULT '0' COMMENT '排序序号',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `orgid_delete` (`root_org_id`,`is_delete`) USING BTREE,
  KEY `root_org_id` (`root_org_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='资产区域表';

-- ----------------------------
-- Table structure for as_asset
-- ----------------------------
DROP TABLE IF EXISTS `as_asset`;
CREATE TABLE `as_asset` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `asset_id` varchar(32) NOT NULL DEFAULT '' COMMENT '固定资产编号',
  `tpl_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '使用模板编号 0-表示固定模板',
  `asset_code` varchar(32) NOT NULL DEFAULT '' COMMENT '自定义编码',
  `custom_type_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '自定义分类ID',
  `asset_name` varchar(50) NOT NULL DEFAULT '' COMMENT '固定资产名称',
  `buy_at` timestamp NULL DEFAULT NULL COMMENT '购买日期',
  `root_org_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '归属（顶级组织）ID',
  `org_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属组织ID',
  `use_org_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '使用组织ID',
  `emp_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '使用人员工编号',
  `area_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '区域编号',
  `source` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '来源ID  1-购入 2-自建 3-盘盈 4-调入 5-在建工程转入 6-租赁 7-接受捐赠 8-其他',
  `storage_position` varchar(50) DEFAULT '' COMMENT '存放位置',
  `admin_userid` bigint(20) NOT NULL DEFAULT '0' COMMENT '管理者账号ID',
  `standard` varchar(50) DEFAULT '' COMMENT '规格型号',
  `unit` char(20) DEFAULT '' COMMENT '计量单位',
  `worth` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '价值（元）',
  `time_limit` varchar(20) DEFAULT '' COMMENT '使用期限（月）',
  `supplier` varchar(50) DEFAULT '' COMMENT '供应商',
  `remarks` varchar(255) DEFAULT '' COMMENT '备注',
  `photourl` text NOT NULL COMMENT '图片URL',
  `asset_appendix` text NOT NULL COMMENT '附件URL',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1：闲置  2： 在用   3:报废  4:维修  5：借用  21:报废待审 22:调拨待审 23:领用待审 24:退换待审 25:借用待审 26:归还待审 27:维修待审 28:变更待审 ',
  `before_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '业务调整前状态',
  `repair_before_status` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '本次维修前状态（冗余字段，可关联最后一次维修前状态）',
  `is_flow` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否流程中转 0-否 1-是',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除 0-否  1-是',
  `tpl_attr_data` text COMMENT '模版属性JSON数据',
  `label_tid` varchar(30) DEFAULT '' COMMENT 'RFID-TID',
  `label_epcid` varchar(30) DEFAULT '' COMMENT 'rfid_epcid',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_asset_id` (`asset_id`) USING BTREE,
  KEY `status` (`status`) USING BTREE,
  KEY `root_org_id` (`root_org_id`) USING BTREE,
  KEY `org_id` (`org_id`) USING BTREE,
  KEY `emp_id` (`emp_id`) USING BTREE,
  KEY `buy_at` (`buy_at`) USING BTREE,
  KEY `custom_type_id` (`custom_type_id`) USING BTREE,
  KEY `asset_name` (`asset_name`) USING BTREE,
  KEY `update_time` (`update_time`) USING BTREE,
  KEY `assetid_orgid_status` (`asset_id`,`root_org_id`,`status`) USING BTREE,
  KEY `tpl_id_is_delete` (`tpl_id`,`is_delete`) USING BTREE,
  KEY `created_at_is_delete` (`create_time`,`is_delete`) USING BTREE,
  KEY `root_org_id_is_delete` (`root_org_id`,`is_delete`) USING BTREE,
  KEY `use_org_id` (`use_org_id`) USING BTREE,
  KEY `area_id` (`area_id`) USING BTREE,
  KEY `org_updated_at` (`root_org_id`,`is_delete`,`update_time`) USING BTREE,
  KEY `is_delete_root_asset_id` (`is_delete`,`root_org_id`,`asset_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='固定资产-信息记录表';


-- ----------------------------
-- Table structure for as_asset_attr
-- ----------------------------
DROP TABLE IF EXISTS `as_asset_attr`;
CREATE TABLE `as_asset_attr` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `asset_id` varchar(32) NOT NULL COMMENT '固定资产编号ID',
  `attr_id` varchar(32) NOT NULL COMMENT '属性ID',
  `attr_val` text COMMENT '属性值（文本value或者复合数据KEY）',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `asset_id` (`asset_id`) USING BTREE,
  KEY `attr_id` (`attr_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='固定资产-属性结果（记录表）';


-- ----------------------------
-- Table structure for as_asset_catalog
-- ----------------------------
DROP TABLE IF EXISTS `as_asset_catalog`;
CREATE TABLE `as_asset_catalog` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `catalog_name` varchar(50) NOT NULL DEFAULT '' COMMENT '资产目录名称',
  `catalog_code` char(8) NOT NULL COMMENT '目录编码',
  `pid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父级编号',
  `paths` varchar(300) NOT NULL DEFAULT '0' COMMENT '父级结构树 0,1,2',
  `level` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '所在层级',
  `sort_num` int(11) unsigned NOT NULL DEFAULT '100' COMMENT '排序',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `catalog_code` (`catalog_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='资产目录表';


-- ----------------------------
-- Table structure for as_contract
-- ----------------------------
DROP TABLE IF EXISTS `as_contract`;
CREATE TABLE `as_contract` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `root_org_id` bigint(20) NOT NULL COMMENT '顶级组织',
  `version_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '版本编号',
  `contract_no` varchar(50) DEFAULT '' COMMENT '合同编号',
  `amount` decimal(10,0) DEFAULT '0' COMMENT '合同金额',
  `contract_type` tinyint(1) DEFAULT '0' COMMENT '合同类型 1-新签订  2-续约',
  `pay_type` tinyint(4) unsigned DEFAULT '0' COMMENT '支付方式 1-对公转账 2-银联付款 3-支付宝  4-微信',
  `sign_at` timestamp NULL DEFAULT NULL COMMENT '签订日期',
  `due_at` timestamp NULL DEFAULT NULL COMMENT '到期日期',
  `sales_unit` varchar(100) DEFAULT '' COMMENT '销售单位',
  `salesperson` varchar(50) DEFAULT '' COMMENT '销售人',
  `tel` varchar(30) DEFAULT '' COMMENT '电话',
  `status` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '1- 生效  2- 过期',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `root_org_id` (`root_org_id`) USING BTREE,
  KEY `version_id` (`version_id`) USING BTREE,
  KEY `due_at` (`due_at`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='合同表';

-- ----------------------------
-- Table structure for as_contract_expand
-- ----------------------------
DROP TABLE IF EXISTS `as_contract_expand`;
CREATE TABLE `as_contract_expand` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `root_org_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '顶级组织编号',
  `version_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '当前绑定版本编号',
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '用户类型 1-正常  2-测试',
  `enabled_asset_log` tinyint(1) NOT NULL DEFAULT '2' COMMENT '开启资产履历模块 1-关闭  2-开启',
  `enabled_comsumables` tinyint(1) NOT NULL DEFAULT '2' COMMENT '开启耗材管理模块 1-关闭  2-开启',
  `enabled_wechat` tinyint(1) NOT NULL DEFAULT '2' COMMENT '开启微信员工端 1-关闭  2-开启',
  `enabled_acct` tinyint(1) NOT NULL DEFAULT '2' COMMENT '开启财务模块 1-关闭  2-开启',
  `enabled_custom_code` tinyint(1) NOT NULL DEFAULT '2' COMMENT '开启自定义编码 1-关闭  2-开启',
  `expand_tag_num` int(11) NOT NULL DEFAULT '0' COMMENT '扩展的标签数量(视情况-可为负数)',
  `expand_asset_num` int(11) NOT NULL DEFAULT '0' COMMENT '扩展的资产数量(视情况-可为负数)',
  `expand_consumables_num` int(11) NOT NULL DEFAULT '0' COMMENT '扩展耗材数量',
  `expand_template_num` int(11) NOT NULL DEFAULT '0' COMMENT '扩展模板数量',
  `expand_account_num` int(11) NOT NULL DEFAULT '0' COMMENT '扩展帐号数量',
  `sign_at` timestamp NULL DEFAULT NULL COMMENT '有效合同开始时间',
  `due_at` timestamp NULL DEFAULT NULL COMMENT '有效合同结束时间',
  `enabled_asset_audit` tinyint(4) unsigned NOT NULL DEFAULT '2' COMMENT '资产审批模块 1-关闭  2-开启',
  `expand_employee_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '员工扩展数量',
  `expand_company_num` int(11) NOT NULL DEFAULT '0' COMMENT '企业扩展数量',
  `expand_photo_num` int(11) NOT NULL DEFAULT '0' COMMENT '照片扩展数量',
  `expand_appendix_num` int(11) NOT NULL DEFAULT '0' COMMENT '附件扩展数量',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `root_org_id` (`root_org_id`) USING BTREE,
  KEY `version_id` (`version_id`) USING BTREE,
  KEY `due_at` (`due_at`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='合同表- 合同内容补充升级';

-- ----------------------------
-- Table structure for as_custom_type
-- ----------------------------
DROP TABLE IF EXISTS `as_custom_type`;
CREATE TABLE `as_custom_type` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `custom_type_name` char(50) NOT NULL DEFAULT '' COMMENT '自定义分类名称',
  `custom_type_code` varchar(20) NOT NULL DEFAULT '' COMMENT '自定义分类编码',
  `root_org_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '最顶级企业ID',
  `pid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父级组织',
  `paths` varchar(255) NOT NULL DEFAULT '' COMMENT '路径',
  `level` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '组织等级',
  `sort_num` int(11) unsigned DEFAULT '0' COMMENT '排序序号',
  `is_delete` tinyint(1) unsigned DEFAULT '0' COMMENT '0-未删除  1：已删除',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `root_org_id` (`root_org_id`) USING BTREE,
  KEY `custom_type_name` (`custom_type_name`) USING BTREE,
  KEY `sort_num` (`sort_num`) USING BTREE,
  KEY `update_time` (`update_time`) USING BTREE,
  KEY `pid` (`pid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户（资产分类）表';


-- ----------------------------
-- Table structure for as_employee
-- ----------------------------
DROP TABLE IF EXISTS `as_employee`;
CREATE TABLE `as_employee` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `py` varchar(50) NOT NULL DEFAULT '' COMMENT '简拼',
  `emp_name` char(50) NOT NULL DEFAULT '' COMMENT '员工姓名',
  `password` varchar(70) NOT NULL DEFAULT '' COMMENT '密码',
  `emp_no` char(20) NOT NULL DEFAULT '' COMMENT '员工工号',
  `root_org_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '顶级组织id',
  `org_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '所属组织id',
  `phone` char(20) NOT NULL DEFAULT '' COMMENT '手机号码',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '邮箱',
  `status` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '在职状态:1-在职, 2-离职',
  `position` varchar(32) NOT NULL DEFAULT '' COMMENT '职位信息',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除: 0未删除 1 已删除',
  `is_default_login` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否默认登录该企业 0-否 1-是',
  `last_login_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '上一次登陆时间',
  `current_login_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '当前登陆时间',
  `pc_refresh_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'pc 刷新时间(用户状态发生变化,同步操作APP)',
  `app_refresh_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'APP 刷新时间(用户状态发生变化,同步操作PC)',
  `last_updatepwd_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后一次修改密码的时间',
  `agreement_status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '弹窗协议状态 1-关闭  2-已同意',
  `jpush_id` varchar(50) NOT NULL DEFAULT '' COMMENT 'APP 极光推送ID',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `root_org_id` (`root_org_id`) USING BTREE,
  KEY `org_id` (`org_id`) USING BTREE,
  KEY `emp_no` (`emp_no`) USING BTREE,
  KEY `emp_name` (`emp_name`) USING BTREE,
  KEY `phone` (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='员工表';

-- ----------------------------
-- Table structure for as_equipment
-- ----------------------------
DROP TABLE IF EXISTS `as_equipment`;
CREATE TABLE `as_equipment` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `equipment_name` varchar(50) NOT NULL COMMENT '设备名称',
  `describe` varchar(200) DEFAULT NULL COMMENT '设备描述',
  `authorization_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '授权类型 1序列号  2企业',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '是否删除 0 未删除  1已删除',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `equipment_name` (`equipment_name`) USING BTREE,
  KEY `authorization_type` (`authorization_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='设备表';

-- ----------------------------
-- Table structure for as_equipment_org
-- ----------------------------
DROP TABLE IF EXISTS `as_equipment_org`;
CREATE TABLE `as_equipment_org` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `equipment_id` bigint(20) NOT NULL COMMENT '设备id',
  `root_org_id` bigint(20) NOT NULL COMMENT '企业id',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '是否删除 0 未删除  1已删除',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `equipment_id` (`equipment_id`) USING BTREE,
  KEY `root_org_id` (`root_org_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='设备授权企业';

-- ----------------------------
-- Table structure for as_equipment_serial_number
-- ----------------------------
DROP TABLE IF EXISTS `as_equipment_serial_number`;
CREATE TABLE `as_equipment_serial_number` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `equipment_id` bigint(20) NOT NULL COMMENT '设备id',
  `serial_number` varchar(100) NOT NULL COMMENT '序列号',
  `bind_root_org_id` bigint(20) DEFAULT '0' COMMENT '已绑定顶级公司id',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '是否删除 0未删除   1已删除',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `equipment_id` (`equipment_id`) USING BTREE,
  KEY `serial_number` (`serial_number`) USING BTREE,
  KEY `bind_root_org_id` (`bind_root_org_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='设备授权序列号表';

-- ----------------------------
-- Table structure for as_industry
-- ----------------------------
DROP TABLE IF EXISTS `as_industry`;
CREATE TABLE `as_industry` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `industry_name` varchar(50) NOT NULL DEFAULT '' COMMENT '行业名称',
  `industry_code` varchar(50) NOT NULL DEFAULT '' COMMENT '行业编码',
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级编号',
  `paths` varchar(300) NOT NULL DEFAULT '0' COMMENT '父级结构树 0,1,2',
  `level` tinyint(2) NOT NULL DEFAULT '1' COMMENT '所在层级',
  `sort_num` int(11) unsigned NOT NULL DEFAULT '100' COMMENT '排序',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `industry_code` (`industry_code`) USING BTREE,
  KEY `pid` (`pid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='行业表';

-- ----------------------------
-- Table structure for as_node
-- ----------------------------
DROP TABLE IF EXISTS `as_node`;
CREATE TABLE `as_node` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `menu_name` varchar(50) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `node_name` char(50) NOT NULL DEFAULT '' COMMENT '节点名称',
  `pid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父节点编号',
  `paths` varchar(500) NOT NULL DEFAULT '' COMMENT '路径paths',
  `menu_class` char(50) NOT NULL DEFAULT '' COMMENT '菜单class样式',
  `menu_icon` char(50) NOT NULL DEFAULT '' COMMENT '样式ico 图标',
  `sort_num` int(10) unsigned NOT NULL DEFAULT '100' COMMENT '排序序号',
  `fe_route` char(50) NOT NULL DEFAULT '' COMMENT '前端路由名',
  `api_routes` varchar(500) NOT NULL DEFAULT '' COMMENT '后端路由URI 集合 | 分隔 ',
  `api_routes_name` varchar(500) NOT NULL DEFAULT '' COMMENT '后端路由URI 名称集合|分割',
  `button_config` varchar(255) NOT NULL DEFAULT '' COMMENT 'asset_add#资产添加|asset_del#资产删除',
  `is_menu` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否菜单',
  `is_default` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否默认节点 0：否  1：是',
  `is_admin` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否超管权限 0：否  1：是',
  `is_config` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否配置项目',
  `flag` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '1：共有权限  2：PC   3：APP',
  `is_show_sonmenu` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '是否展示子节点菜单 0 -否  1-是',
  `is_page_auth` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否页面级 0：否  1：是',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sort_num` (`sort_num`) USING BTREE,
  KEY `id_menu` (`id`,`is_menu`) USING BTREE,
  KEY `id_config` (`id`,`is_config`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='(菜单)权限节点表';

-- ----------------------------
-- Table structure for as_notice
-- ----------------------------
DROP TABLE IF EXISTS `as_notice`;
CREATE TABLE `as_notice` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '消息类型  1-站内公告   2-内容消息 3-系统到期提醒',
  `title` varchar(50) NOT NULL COMMENT '标题',
  `outline` varchar(255) NOT NULL COMMENT '概要',
  `content` text NOT NULL COMMENT '内容',
  `push_model` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '发布方式 1-发布概要 2-发布详情',
  `photourl` varchar(255) NOT NULL COMMENT '内容图片',
  `publisher` varchar(50) NOT NULL COMMENT '发布人',
  `level` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '通知优先级 1-紧急  2-高  -3普通',
  `scene` tinyint(4) NOT NULL DEFAULT '3' COMMENT '消息推送场景 1-单个用户  2-多个用户  3-全体用户',
  `push_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发布时间',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '公告结束时间',
  `has_pc` tinyint(4) DEFAULT '1' COMMENT '是否发布到PC 0-否 1-是',
  `has_android` tinyint(4) DEFAULT '1' COMMENT '是否发布到安卓端 0-否 1-是',
  `has_ios` tinyint(4) DEFAULT '1' COMMENT '是否发布到IOS端 0-否 1-是',
  `has_pda` tinyint(4) DEFAULT '1' COMMENT '是否发布到PDA端 0-否 1-是',
  `content_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '消息内容类型  1-盘点  2-资产待审批   3-耗材待审批  4-使用到期待处置资产  5-借用到期资产 6-资产维修完成 7-资产审批通过或驳回 8-耗材审批通过或驳回 9-系统到期提醒',
  `content_data` text COMMENT '消息内容数据-JSON数据样式，用于消息跳转',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `has_pc` (`has_pc`) USING BTREE,
  KEY `has_android` (`has_android`) USING BTREE,
  KEY `has_ios` (`has_ios`) USING BTREE,
  KEY `has_pda` (`has_pda`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='通知表';

-- ----------------------------
-- Table structure for as_notice_tag
-- ----------------------------
DROP TABLE IF EXISTS `as_notice_tag`;
CREATE TABLE `as_notice_tag` (
  `notice_id` bigint(20) NOT NULL COMMENT '通告编号',
  `user_id` bigint(20) DEFAULT NULL,
  `is_read` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否阅读 0-否 1-是',
  `reading_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '阅读时间',
  `type` tinyint(2) NOT NULL DEFAULT '3' COMMENT '通告类型  1-单个用户  2-多个用户  3-全体用户',
  KEY `notice_id` (`notice_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `is_read` (`is_read`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='通知-标记表';

-- ----------------------------
-- Table structure for as_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `as_operation_log`;
CREATE TABLE `as_operation_log` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '操作用户id',
  `root_org_id` bigint(20) unsigned NOT NULL COMMENT '所属组织',
  `module` varchar(50) NOT NULL COMMENT '模块名称',
  `action_type` varchar(50) NOT NULL COMMENT '操作',
  `cont` varchar(200) NOT NULL COMMENT '操作内容',
  `created_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='前台操作日志表';

-- ----------------------------
-- Table structure for as_organization
-- ----------------------------
DROP TABLE IF EXISTS `as_organization`;
CREATE TABLE `as_organization` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `industry_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '行业编号',
  `org_name` char(50) NOT NULL DEFAULT '' COMMENT '组织名称',
  `org_code` varchar(8) NOT NULL DEFAULT '' COMMENT '组织编码',
  `root_org_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '最顶级企业ID',
  `company_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '归属公司ID',
  `pid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父级组织',
  `paths` varchar(200) NOT NULL DEFAULT '' COMMENT '路径',
  `level` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '组织等级',
  `sort_num` int(11) unsigned DEFAULT '0' COMMENT '排序序号',
  `type` tinyint(1) unsigned DEFAULT '0' COMMENT '组织类型:1-公司,2-部门',
  `created_userid` int(10) unsigned DEFAULT '0' COMMENT '创建人',
  `is_delete` tinyint(1) unsigned DEFAULT '0' COMMENT '0-未删除  1：已删除',
  `created_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `root_org_id` (`root_org_id`) USING BTREE,
  KEY `pid` (`pid`) USING BTREE,
  KEY `industry_id` (`industry_id`) USING BTREE,
  KEY `org_name` (`org_name`) USING BTREE,
  KEY `paths` (`paths`) USING BTREE,
  KEY `sort_num` (`sort_num`) USING BTREE,
  KEY `company_id` (`company_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='组织表';


-- ----------------------------
-- Table structure for as_organization_config
-- ----------------------------
DROP TABLE IF EXISTS `as_organization_config`;
CREATE TABLE `as_organization_config` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `root_org_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '企业ID',
  `asset_ly_audit` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '资产领用审批 1-关闭 2-开启',
  `asset_th_audit` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '资产退还审批 1-关闭 2-开启',
  `asset_jy_audit` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '资产借用审批 1-关闭 2-开启',
  `asset_gh_audit` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '资产归还审批 1-关闭 2-开启',
  `asset_bg_audit` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '资产变更审批 1-关闭 2-开启',
  `asset_wx_audit` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '资产维修审批 1-关闭 2-开启',
  `asset_db_audit` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '资产调拨审批 1-关闭 2-开启',
  `asset_bf_audit` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '资产报废审批 1-关闭 2-开启',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `root_org_id` (`root_org_id`) USING BTREE COMMENT '唯一顶级组织键'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for as_platform_setting
-- ----------------------------
DROP TABLE IF EXISTS `as_platform_setting`;
CREATE TABLE `as_platform_setting` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `key` varchar(50) NOT NULL DEFAULT '' COMMENT '配置项的键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '配置项名称',
  `value` varchar(100) NOT NULL DEFAULT '' COMMENT '配置项的值',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='平台配置表';


-- ----------------------------
-- Table structure for as_pm_version
-- ----------------------------
DROP TABLE IF EXISTS `as_pm_version`;
CREATE TABLE `as_pm_version` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `version_name` varchar(30) DEFAULT '' COMMENT '版本名称',
  `module_config` text COMMENT '版本配置',
  `is_allow_update` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否允许设置更新 0-否 1-是',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='商品版本';

-- ----------------------------
-- Table structure for as_role
-- ----------------------------
DROP TABLE IF EXISTS `as_role`;
CREATE TABLE `as_role` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `role_name` char(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  `type` tinyint(1) NOT NULL DEFAULT '2' COMMENT '角色类型 1:超级管理  2：普通角色',
  `root_org_id` bigint(20) unsigned NOT NULL COMMENT '所属顶级组织ID',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `root_org_id` (`root_org_id`) USING BTREE,
  KEY `type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色表';

-- ----------------------------
-- Table structure for as_role_node
-- ----------------------------
DROP TABLE IF EXISTS `as_role_node`;
CREATE TABLE `as_role_node` (
  `role_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '角色id',
  `node_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '菜单或按钮或动作id',
  PRIMARY KEY (`role_id`,`node_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色-动作关联表';

-- ----------------------------
-- Table structure for as_sms_info
-- ----------------------------
DROP TABLE IF EXISTS `as_sms_info`;
CREATE TABLE `as_sms_info` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `phone` char(20) DEFAULT '' COMMENT '接收的手机号',
  `sms_type` tinyint(3) unsigned DEFAULT '0' COMMENT '短信类型: 1-验证码, 2-盘点推送 ，3-帐号通知',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '发送状态（1成功， 2失败）',
  `cont` varchar(200) DEFAULT '' COMMENT '短信内容',
  `org_name` varchar(200) DEFAULT '' COMMENT '企业名称',
  `user_id` bigint(20) DEFAULT '0' COMMENT '接收者平台账号',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='短信发送信息记录表';

-- ----------------------------
-- Table structure for as_system_version
-- ----------------------------
DROP TABLE IF EXISTS `as_system_version`;
CREATE TABLE `as_system_version` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `version_name` varchar(30) NOT NULL DEFAULT '' COMMENT '版本名称',
  `version_number` smallint(5) NOT NULL COMMENT '版本号',
  `update_type` tinyint(1) NOT NULL COMMENT '更新类型 1 强制更新  2非强制更新',
  `update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '版本更新日期',
  `update_content` varchar(400) NOT NULL COMMENT '更新内容',
  `url` varchar(255) DEFAULT '' COMMENT '下载地址',
  `platform` tinyint(1) DEFAULT '3' COMMENT '所属平台类型：1：IOS；2：安卓  3:pc',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统版本记录';

-- ----------------------------
-- Table structure for as_tag_attr
-- ----------------------------
DROP TABLE IF EXISTS `as_tag_attr`;
CREATE TABLE `as_tag_attr` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `attr_key` varchar(255) NOT NULL COMMENT '属性关联字段KEY',
  `attr_name` varchar(255) NOT NULL DEFAULT '' COMMENT '标签属性名称',
  `sort_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序序号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='标签-属性表';


-- ----------------------------
-- Table structure for as_tag_size
-- ----------------------------
DROP TABLE IF EXISTS `as_tag_size`;
CREATE TABLE `as_tag_size` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `size_long` varchar(30) NOT NULL COMMENT '尺寸 长',
  `size_wide` varchar(30) NOT NULL COMMENT '宽',
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '类型 1- 默认类型  2-定制',
  `max_attr_num` tinyint(2) NOT NULL DEFAULT '0' COMMENT '最大属性数量',
  `qrcode_position` tinyint(2) NOT NULL DEFAULT '1' COMMENT '二维码位置类型（打印时需要用到）',
  `sort_num` int(11) unsigned DEFAULT '0' COMMENT '排序序号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='标签模板-尺寸';

-- ----------------------------
-- Table structure for as_template
-- ----------------------------
DROP TABLE IF EXISTS `as_template`;
CREATE TABLE `as_template` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tpl_id` varchar(32) NOT NULL DEFAULT '' COMMENT '模板编号',
  `tpl_code` varchar(32) NOT NULL DEFAULT '' COMMENT '模板编码',
  `tpl_name` varchar(50) NOT NULL DEFAULT '' COMMENT '模版名称',
  `root_org_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '顶级（公司）组织编号',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否软删除 0-否 1-是',
  `catalog_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '模板归属资产目录编号',
  `root_tplid` varchar(32) DEFAULT '' COMMENT '根模板编号',
  `clone_tplid` varchar(32) DEFAULT '' COMMENT '克隆的模板编号',
  `created_admin_userid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '后端 创建者编号',
  `updated_admin_userid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '后端 更新者编号',
  `is_close` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否关闭 0:否  1：关闭',
  `is_hot` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否热门 0：否   1:是  ',
  `is_recommend` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否推荐  0：否  1：是',
  `scene` smallint(4) unsigned NOT NULL DEFAULT '3' COMMENT '模板场景 1：资产通用   2：系统云模板   3：用户自定义',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `tpl_id` (`tpl_id`) USING BTREE,
  KEY `root_org_id` (`root_org_id`) USING BTREE,
  KEY `scene` (`scene`) USING BTREE,
  KEY `catalog_id` (`catalog_id`) USING BTREE,
  KEY `update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='固定资产-用户模板表';

-- ----------------------------
-- Table structure for as_template_attr
-- ----------------------------
DROP TABLE IF EXISTS `as_template_attr`;
CREATE TABLE `as_template_attr` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tpl_id` varchar(32) NOT NULL COMMENT '模板编号ID',
  `attr_id` varchar(32) NOT NULL DEFAULT '' COMMENT '属性编号ID',
  `attr_name` varchar(50) NOT NULL DEFAULT '' COMMENT '属性显示（中文）名称',
  `attr_type` smallint(4) NOT NULL DEFAULT '1' COMMENT '属性文本类型(单行、多行、下拉、图片)',
  `attr_val` text COMMENT '属性值JSON',
  `is_required` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否必填 0：否  1：是',
  `is_show` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否显示 0-不显示  1-显示',
  `sort_num` int(11) unsigned DEFAULT '100' COMMENT '排序',
  `is_editable` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否可二次编辑 0:否 1：是',
  `created_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_attr_id` (`attr_id`) USING BTREE,
  KEY `tpl_id` (`tpl_id`) USING BTREE,
  KEY `tpl_attr_id` (`tpl_id`,`attr_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='固定资产-用户模板（属性表）';

-- ----------------------------
-- Table structure for as_user
-- ----------------------------
DROP TABLE IF EXISTS `as_user`;
CREATE TABLE `as_user` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `uid` char(10) NOT NULL DEFAULT '' COMMENT '用户唯一ID JC30000001 开始',
  `user_name` char(50) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '用户昵称',
  `password` varchar(70) NOT NULL DEFAULT '' COMMENT '密码',
  `phone` varchar(13) NOT NULL DEFAULT '' COMMENT '手机',
  `root_org_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '顶级组织编号ID',
  `role_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '角色id',
  `user_type` smallint(4) unsigned NOT NULL DEFAULT '3' COMMENT '用户类型 1-超级管理员  2-公司管理员 3-普通',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态 1:正常  2:禁用 ',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否软删除 0-否  1-是',
  `default_tag_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '默认打印标签ID',
  `default_cftag_id` bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '耗材默认打印标签模板id',
  `default_cftag_code` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '耗材默认打印标签模板 二维码内容 1-耗材唯一ID 2-耗材条码号',
  `default_tpl_id` varchar(50) DEFAULT '' COMMENT '默认资产模版编号',
  `jpush_id` varchar(50) NOT NULL DEFAULT '' COMMENT 'APP 极光推送ID',
  `last_login_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '上一次登陆时间',
  `current_login_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '当前登陆时间',
  `pc_refresh_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'pc 刷新时间(用户状态发生变化,同步操作APP)',
  `app_refresh_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'APP 刷新时间(用户状态发生变化,同步操作PC)',
  `last_updatepwd_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后一次修改密码的时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uid` (`uid`) USING BTREE,
  KEY `root_org_id` (`root_org_id`) USING BTREE,
  KEY `user_name` (`user_name`) USING BTREE,
  KEY `phone` (`phone`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `role_id` (`role_id`) USING BTREE,
  KEY `user_type` (`user_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户账号表';


-- ----------------------------
-- Table structure for as_user_consult
-- ----------------------------
DROP TABLE IF EXISTS `as_user_consult`;
CREATE TABLE `as_user_consult` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `contact_info` varchar(50) NOT NULL DEFAULT '' COMMENT '联系方式',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1-待跟进  2-已跟进',
  `client_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '客户端ip',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户官网咨询（推送钉钉）';

-- ----------------------------
-- Table structure for as_user_ext
-- ----------------------------
DROP TABLE IF EXISTS `as_user_ext`;
CREATE TABLE `as_user_ext` (
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `source` tinyint(4) NOT NULL DEFAULT '1' COMMENT '来源  1-本系统（pc端） 2-本系统（Android端） 3-本系统（iOS端）4-官网来源 10-代理商平台同步 ',
  `customer_id` int(11) NOT NULL DEFAULT '0' COMMENT '代理商平台注册的客户id',
  `agent_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '所属代理商ID',
  `business_status` tinyint(2) NOT NULL DEFAULT '3' COMMENT '业务状态(1.未同步（没有）；2.已同步；3.试用-未到期；4.试用-已到期；5.成交-免费版（没有）；6.成交-付费版；7.成交-即将到期；8.成交-已到期；9.成交-已流失；10.已注销)',
  `default_material_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '默认材质id',
  `default_concentration` int(11) NOT NULL DEFAULT '0' COMMENT '默认浓度',
  `print_upgrade_status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '打印服务升级弹窗状态 1-需要  2-不需要',
  `agreement_status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '弹窗协议状态 1-关闭  2-已同意',
  `guide_status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '引导“常用功能”、“系统初始化配置” 状态 1-需要  2-不需要',
  `type_power` text COMMENT '类型范围',
  `area_power` text COMMENT '区域范围',
  `org_power` text COMMENT '组织范围',
  `cf_type_power` text COMMENT '耗材分类范围',
  `cf_depot_power` text COMMENT '耗材仓库范围',
  `acct_company_power` text COMMENT '财务授权公司权限',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户扩展表';

-- ----------------------------
-- Table structure for as_user_feedback
-- ----------------------------
DROP TABLE IF EXISTS `as_user_feedback`;
CREATE TABLE `as_user_feedback` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户id （来源是微信员工端时，则为员工id）',
  `feedback_content` varchar(400) DEFAULT '' COMMENT '反馈内容',
  `reply_content` varchar(400) DEFAULT '' COMMENT '回复内容',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1 未读  2已读   3已回复',
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '添加时间',
  `reply_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '回复时间',
  `source` tinyint(4) unsigned NOT NULL DEFAULT '2' COMMENT '1-pc端  2-Android端  3-iOS端   4-PDA  5-微信员工端  6-pc员工端 7-Android员工端  8-ios员工端',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户反馈（推送钉钉）';


-- ----------------------------
-- Table structure for as_user_msg
-- ----------------------------
DROP TABLE IF EXISTS `as_user_msg`;
CREATE TABLE `as_user_msg` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `msg_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '留言类型（1.更改企业名称,  2.账户注销)',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `root_org_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '顶级组织编号ID',
  `user_name` char(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `phone` varchar(13) NOT NULL DEFAULT '' COMMENT '手机',
  `org_name` char(50) NOT NULL DEFAULT '' COMMENT '企业名称',
  `content` varchar(300) NOT NULL DEFAULT '' COMMENT '留言说明',
  `is_handle` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否处理 0-未处理 1-已处理',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `root_org_id` (`root_org_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户留言表';


-- ----------------------------
-- Table structure for as_user_regconfig
-- ----------------------------
DROP TABLE IF EXISTS `as_user_regconfig`;
CREATE TABLE `as_user_regconfig` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `version_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '版本编号',
  `status` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '状态  1- 执行中  2- 已失效',
  `use_life` int(10) NOT NULL DEFAULT '0' COMMENT '使用期限',
  `use_life_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '使用期限类型  1-天  2-年',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `version_id` (`version_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='注册用户-数据配置表';

