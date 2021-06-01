/*
Navicat MySQL Data Transfer

Source Server         : FHM_car300
Source Server Version : 50670
Source Host           : rm-uf636guq410osh7y1o.mysql.rds.aliyuncs.com:3306
Source Database       : auto-auction-test

Target Server Type    : MYSQL
Target Server Version : 50670
File Encoding         : 65001

Date: 2019-04-04 14:21:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  `parentid` int(11) DEFAULT NULL COMMENT '父级id',
  `status` tinyint(255) unsigned DEFAULT NULL COMMENT '状态1正常',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `deptorder` smallint(255) unsigned DEFAULT '1' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '总公司', '0', '1', '2018-03-29 18:10:23', null, '总公司', '1');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `menuname` varchar(255) NOT NULL COMMENT '名字',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限表示',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `router` varchar(255) DEFAULT NULL COMMENT '前端路由',
  `menutype` tinyint(4) DEFAULT NULL COMMENT '类型:1 目录 ，2 菜单，3 列表，4 按钮',
  `parentid` int(11) DEFAULT NULL COMMENT '父级id',
  `status` tinyint(255) unsigned DEFAULT NULL COMMENT '状态，1 正常',
  `menulevel` int(4) unsigned DEFAULT NULL COMMENT '层级',
  `menuorder` int(4) unsigned DEFAULT '1' COMMENT '排序',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '顶级菜单', null, null, null, '1', '0', '1', '0', '1', '2018-03-29 18:29:18', '2018-03-29 18:29:21', '顶级菜单');
INSERT INTO `sys_menu` VALUES ('2', '权限管理', null, 'setting', '/permission', '1', '1', '1', '1', '4', '2018-04-03 17:50:33', '2019-04-03 09:33:28', '权限管理');
INSERT INTO `sys_menu` VALUES ('3', '部门管理', 'sys:dept:tree', '', '/permission/department', '2', '2', '1', '2', '1', '2018-04-03 18:00:07', '2018-08-23 11:13:20', '部门管理');
INSERT INTO `sys_menu` VALUES ('4', '菜单管理', 'sys:menu:list', '', '/permission/menumanage', '2', '2', '1', '2', '2', '2018-04-04 10:08:14', '2018-08-23 15:03:51', '菜单管理');
INSERT INTO `sys_menu` VALUES ('5', '角色管理', 'sys:role:list', '', '/permission/rolemanage', '2', '2', '1', '2', '3', '2018-04-04 10:15:56', '2018-08-23 11:13:20', '角色管理');
INSERT INTO `sys_menu` VALUES ('6', '用户管理', 'sys:user:list', '', '/permission/usermanage', '2', '2', '1', '2', '4', '2018-04-04 10:23:41', '2018-08-23 11:13:20', '用户列表');
INSERT INTO `sys_menu` VALUES ('7', '部门管理列表', 'sys:dept:tree', null, '/permission/department', '3', '3', '1', '3', '1', '2018-04-03 18:00:07', null, '部门管理列表');
INSERT INTO `sys_menu` VALUES ('8', '编辑', 'sys:dept:update', null, 'permission:department:update', '4', '3', '1', '3', '0', '2018-04-04 10:04:01', null, '部门编辑');
INSERT INTO `sys_menu` VALUES ('9', '删除', 'sys:dept:del', null, 'permission:department:delete', '4', '3', '1', '3', '0', '2018-04-04 10:05:18', null, '部门删除');
INSERT INTO `sys_menu` VALUES ('10', '菜单管理列表', 'sys:menu:list', null, 'menumanage', '3', '4', '1', '3', '1', '2018-04-04 10:08:14', null, '菜单管理列表');
INSERT INTO `sys_menu` VALUES ('11', '新建', 'sys:menu:save', null, 'permission:menumanage:save', '4', '4', '1', '3', '0', '2018-04-04 10:13:00', null, '新建菜单');
INSERT INTO `sys_menu` VALUES ('12', '编辑', 'sys:menu:update', null, 'permission:menumanage:update', '4', '4', '1', '3', '0', '2018-04-04 10:14:05', null, '修改菜单');
INSERT INTO `sys_menu` VALUES ('13', '删除', 'sys:menu:del', null, 'permission:menumanage:delete', '4', '4', '1', '3', '0', '2018-04-04 10:15:06', null, '删除菜单');
INSERT INTO `sys_menu` VALUES ('14', '角色管理列表', 'sys:role:list', null, 'rolemanage', '3', '5', '1', '3', '1', '2018-04-04 10:15:56', null, '角色管理列表');
INSERT INTO `sys_menu` VALUES ('15', '新建', 'sys:role:save', null, 'permission:rolemanage:save', '4', '5', '1', '3', '0', '2018-04-04 10:18:27', null, '新建角色');
INSERT INTO `sys_menu` VALUES ('16', '编辑', 'sys:role:update', null, 'permission:rolemanage:update', '4', '5', '1', '3', '0', '2018-04-04 10:19:33', null, '修改角色');
INSERT INTO `sys_menu` VALUES ('17', '用户管理列表', 'sys:user:list', null, 'usermanage', '3', '6', '1', '3', '1', '2018-04-04 10:23:41', null, '用户管理列表');
INSERT INTO `sys_menu` VALUES ('18', '新建', 'sys:user:save', null, 'permission:usermanage:save', '4', '6', '1', '3', '0', '2018-04-04 10:24:26', null, '新建用户');
INSERT INTO `sys_menu` VALUES ('19', '修改', 'sys:user:update', null, 'permission:usermanage:update', '4', '6', '1', '3', '0', '2018-04-04 10:25:13', null, '修改用户');
INSERT INTO `sys_menu` VALUES ('20', '新建', 'sys:dept:save', null, 'permission:department:save', '4', '3', '1', '3', '0', '2018-04-04 10:06:35', null, '部门新建');
INSERT INTO `sys_menu` VALUES ('21', '删除', 'sys:role:del', null, 'permission:rolemanage:delete', '4', '5', '1', '3', '0', '2018-04-04 10:20:30', null, '删除角色');
INSERT INTO `sys_menu` VALUES ('22', '删除', 'sys:user:del', null, 'permission:usermanage:delete', '4', '6', '1', '3', '0', '2018-04-04 10:25:48', null, '删除用户');
INSERT INTO `sys_menu` VALUES ('23', '系统日志', 'sys:log:list', '', '/permission/systemlog', '2', '2', '1', '2', '5', '2018-04-04 10:26:29', '2018-08-23 11:13:20', '系统日志');
INSERT INTO `sys_menu` VALUES ('24', '系统日志列表', 'sys:log:list', null, 'systemlog', '3', '23', '1', '3', '1', '2018-04-04 10:26:29', null, '系统日志列表');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(11) DEFAULT NULL COMMENT '随机uuid',
  `account` varchar(255) NOT NULL COMMENT '登录账户',
  `password` varchar(255) NOT NULL COMMENT '加密后的密码',
  `mobile` varchar(16) DEFAULT NULL COMMENT '手机',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `status` tinyint(255) unsigned DEFAULT NULL COMMENT '状态',
  `issys` tinyint(255) unsigned DEFAULT NULL COMMENT '是否管理员，1是，0否',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `deptid` int(11) unsigned DEFAULT NULL COMMENT '部门id',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '42caaea', 'admin', '928bfd2577490322a6e19b793691467e', '18516670899', '系统管理员', '1', '1', 'admin@ebaochina.com', '4', '', '2018-03-29 18:10:23', '2018-08-23 11:13:20', 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png');

-- ----------------------------
-- Table structure for tb_bidder
-- ----------------------------
DROP TABLE IF EXISTS `tb_bidder`;
CREATE TABLE `tb_bidder` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(64) DEFAULT NULL COMMENT '电话（登录名）',
  `password` varchar(32) DEFAULT NULL COMMENT '登录密码',
  `store` varchar(255) DEFAULT NULL COMMENT '门店名称',
  `province` varchar(16) DEFAULT NULL COMMENT '省',
  `city` varchar(16) DEFAULT NULL COMMENT '市',
  `district` varchar(16) DEFAULT NULL COMMENT '区',
  `address` varchar(255) DEFAULT NULL COMMENT '所在地区',
  `enabled` tinyint(1) DEFAULT '1' COMMENT '是否开启',
  `token` varchar(64) DEFAULT NULL COMMENT '权限凭证',
  `expired_time` datetime DEFAULT NULL COMMENT 'token过期时间',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(255) DEFAULT NULL COMMENT '更新人',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `is_hidden` tinyint(1) DEFAULT '0' COMMENT '是否隐藏',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobile` (`mobile`),
  UNIQUE KEY `uq_bidder_mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8 COMMENT='竞价方';

-- ----------------------------
-- Records of tb_bidder
-- ----------------------------
INSERT INTO `tb_bidder` VALUES ('10000', '瑞博恩', '15022356987', '630eca54e136f090a5434e30c9c31fff', '瑞博恩', '310000', '310100000000', '310105000000', '天会2号楼1006', '1', 'b5747d98dc7d461faccd04ec0a32980e', '2019-04-10 22:38:32', '2019-04-03 09:49:24', 'admin', null, null, '0', '0');

-- ----------------------------
-- Table structure for tb_sys_dic
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_dic`;
CREATE TABLE `tb_sys_dic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(255) NOT NULL,
  `group` varchar(64) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `enabled` tinyint(255) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='枚举字段表';

-- ----------------------------
-- Records of tb_sys_dic
-- ----------------------------


DROP TABLE IF EXISTS t_payment_channel ;
CREATE TABLE t_payment_channel 
    ( 
        id int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '支付通道id', 
        name varchar(100) COMMENT '支付通道名称', 
        data_status int(1) DEFAULT '1' COMMENT '数据状态   1有效   -1无效', 
        remark varchar(200) COMMENT '备注', 
        createoper varchar(50) COMMENT '创建人', 
        createtime datetime COMMENT '创建时间', 
        modifyoper varchar(50) COMMENT '修改人', 
        modifytime datetime COMMENT '修改时间', 
        PRIMARY KEY USING BTREE (id) 
    ) 
    ENGINE= InnoDB DEFAULT CHARSET= utf8 COMMENT= '支付通道表';


DROP TABLE IF EXISTS t_payment_channel_config ;
CREATE TABLE t_payment_channel_config 
    ( 
        id int(10) unsigned NOT NULL AUTO_INCREMENT , 
        payment_channel_id int(10) COMMENT '支付通道id', 
        key_ varchar(200) COMMENT '参数key   比如支付url、商户号、公钥、私钥、密码等等', 
        value_ varchar(1000) COMMENT '参数value  对应key的值', 
        data_status int(1) DEFAULT '1' COMMENT '数据状态   1有效   -1无效', 
        remark varchar(200) COMMENT '备注', 
        createoper varchar(50) COMMENT '创建人', 
        createtime datetime COMMENT '创建时间', 
        modifyoper varchar(50) COMMENT '修改人', 
        modifytime datetime COMMENT '修改时间', 
        PRIMARY KEY USING BTREE (id) ,
        CONSTRAINT index_key UNIQUE USING BTREE (payment_channel_id,key_) 
    ) 
    ENGINE= InnoDB DEFAULT CHARSET= utf8 COMMENT= '支付通道参数配置表';

DROP TABLE IF EXISTS t_dai_zheng_info ;
CREATE TABLE t_dai_zheng_info 
    ( 
        id int(10) unsigned NOT NULL AUTO_INCREMENT, 
        name varchar(100) COMMENT '代征主体名称', 
        left_amount decimal(15,2) COMMENT '账户余额（元）', 
        pay_amount decimal(15,2) COMMENT '累计付款金额（元）', 
        pend_pay_amount decimal(15,2) COMMENT '待付款金额（元）', 
        company_name varchar(100) COMMENT '公司名称', 
        address varchar(200) COMMENT '公司地址', 
        logo varchar(200) COMMENT 'logo', 
        linkman varchar(50) COMMENT '联系人', 
        personal_tax decimal(10,6) COMMENT '个税', 
        value_added_tax decimal(10,6) COMMENT '增值税', 
        extra_tax decimal(10,6) COMMENT '附加税', 
        open_bank varchar(100) COMMENT '开户行', 
        open_name varchar(100) COMMENT '账户名称', 
        open_bank_no varchar(100) COMMENT '银行卡号', 
        data_status int(1) DEFAULT '1' COMMENT '数据状态   1有效   -1无效', 
        remark varchar(200) COMMENT '备注', 
        createoper varchar(50) COMMENT '创建人', 
        createtime datetime COMMENT '创建时间', 
        modifyoper varchar(50) COMMENT '修改人', 
        modifytime datetime COMMENT '修改时间', 
        PRIMARY KEY USING BTREE (id) 
    ) 
    ENGINE= InnoDB DEFAULT CHARSET= utf8 COMMENT= '代征主体表';



DROP TABLE IF EXISTS t_customer_info;
CREATE TABLE t_customer_info 
    ( 
        id varchar(64)  NOT NULL  COMMENT '客户编号   根据8开头规则生成自增4位数字', 
        name varchar(100) COMMENT '客户名称', 
        service_rate decimal(10,6) COMMENT '服务费率', 
        invoice_title varchar(100) COMMENT '发票抬头', 
        invoice_content varchar(300) COMMENT '发票内容',
        taxpayer_type int(11) COMMENT '纳税人类型   1一般纳税人   2小规模纳税人', 
        taxpayer_number varchar(100) COMMENT '纳税人识别号', 
        reg_address varchar(200) COMMENT '单位注册地址', 
        open_bank varchar(100) COMMENT '开户行', 
        open_bank_no varchar(100) COMMENT '银行卡号', 
        entertime datetime COMMENT '入驻时间', 
        receiver varchar(100) COMMENT '收件人', 
        mobile varchar(30) COMMENT '联系电话', 
        address varchar(100) COMMENT '地址', 
        qr_code_img_pic_url varchar(200) COMMENT '二维码图片显示相对路径', 
        qr_code_img_post_url varchar(500) COMMENT '二维码扫描接口url，含后端加密信息，根据每个客户信息唯一生成', 
        data_status int(1) DEFAULT '1' COMMENT '数据状态   1有效   -1无效', 
        remark varchar(200) COMMENT '备注', 
        createoper varchar(50) COMMENT '创建人', 
        createtime datetime COMMENT '创建时间', 
        modifyoper varchar(50) COMMENT '修改人', 
        modifytime datetime COMMENT '修改时间', 
        PRIMARY KEY USING BTREE (id) 
    ) 
    ENGINE= InnoDB DEFAULT CHARSET= utf8 COMMENT= '客户信息表';


DROP TABLE IF EXISTS t_agent_info ;
CREATE TABLE t_agent_info 
    ( 
        id int(10) unsigned NOT NULL AUTO_INCREMENT, 
        nationality_id int(11) COMMENT '国籍', 
        name varchar(100) COMMENT '代理人姓名', 
        openid varchar(100) COMMENT '代理人openid，用于便于已经在客户中注册过的代理人，信息反显直接提交绑定另外的客户', 
        id_type int(11) COMMENT '证件类型   1身份证', 
        idcardno varchar(20) COMMENT '证件号码', 
        front_idcard_pic_url varchar(200) COMMENT '正面证件图片', 
        back_idcard_pic_url varchar(200) COMMENT '反面证件图片', 
        mobile varchar(30) COMMENT '手机号', 
        agent_qr_code_pic_url varchar(200) COMMENT '代理人二维码图片显示相对路径', 
        agent_qr_code_img_post_url varchar(500) COMMENT '代理人二维码扫描接口url，含后端加密信息，根据每个代理人信息唯一生成', 
        remark varchar(200) COMMENT '备注', 
        createoper varchar(50) COMMENT '创建人', 
        createtime datetime COMMENT '注册时间', 
        modifyoper varchar(50) COMMENT '修改人', 
        modifytime datetime COMMENT '修改时间', 
        PRIMARY KEY USING BTREE (id) 
    ) 
    ENGINE= InnoDB DEFAULT CHARSET= utf8 COMMENT= '代理人信息表';

DROP TABLE IF EXISTS t_agent_customer ;
CREATE TABLE t_agent_customer 
    ( 
        id int(10) unsigned NOT NULL AUTO_INCREMENT, 
        customer_id varchar(64)  NOT NULL  COMMENT '客户编号',
        agent_id int(10) NOT NULL COMMENT '代理人id',
        recommender_id int(10) COMMENT '推荐人编号，就是其他代理人id',
        auth_status int(1) DEFAULT '1' COMMENT '认证状态   1未认证、2已认证、3已拒绝', 
        sign_status int(1) DEFAULT '1' COMMENT '签约状态   1未签约、2已签约', 
        data_status int(1) DEFAULT '1' COMMENT '数据状态   1有效   -1无效', 
        remark varchar(200) COMMENT '备注', 
        createoper varchar(50) COMMENT '创建人', 
        createtime datetime COMMENT '注册时间', 
        modifyoper varchar(50) COMMENT '修改人', 
        modifytime datetime COMMENT '修改时间', 
        PRIMARY KEY USING BTREE (id) 
    ) 
    ENGINE= InnoDB DEFAULT CHARSET= utf8 COMMENT= '代理人客户关系表';


DROP TABLE IF EXISTS t_agent_bank_no_info ;
CREATE TABLE t_agent_bank_no_info 
    ( 
        id int(10) unsigned NOT NULL AUTO_INCREMENT, 
        agent_id int(10)  COMMENT '代理人id', 
        open_bank_no varchar(100) COMMENT '银行卡号', 
        data_status int(1) DEFAULT '1' COMMENT '数据状态   1有效   -1无效', 
        remark varchar(200) COMMENT '备注', 
        createoper varchar(50) COMMENT '创建人', 
        createtime datetime COMMENT '注册时间', 
        modifyoper varchar(50) COMMENT '修改人', 
        modifytime datetime COMMENT '修改时间', 
        PRIMARY KEY USING BTREE (id) 
    ) 
    ENGINE= InnoDB DEFAULT CHARSET= utf8 COMMENT= '代理人银行卡信息表';



DROP TABLE IF EXISTS t_order ;
CREATE TABLE t_order
    ( 
        id int(11) unsigned NOT NULL AUTO_INCREMENT, 
        applytime datetime COMMENT '申请时间', 
        customer_id varchar(64)  NOT NULL  COMMENT '客户编号', 
        agent_commission decimal(10,6) COMMENT '代理人佣金', 
        real_payment decimal(10,6) COMMENT '实际付款', 
        recharge int(1) DEFAULT '1' COMMENT '充值状态 1未到账，2已到账', 
        recharge_voucher_path varchar(300)  COMMENT '充值凭证', 
        arrivetime datetime COMMENT '到账时间', 
        data_status int(1) DEFAULT '1' COMMENT '数据状态   1有效   -1无效', 
        remark varchar(200) COMMENT '备注', 
        createoper varchar(50) COMMENT '创建人', 
        createtime datetime COMMENT '注册时间', 
        modifyoper varchar(50) COMMENT '修改人', 
        modifytime datetime COMMENT '修改时间', 
        PRIMARY KEY USING BTREE (id) 
    ) 
    ENGINE= InnoDB DEFAULT CHARSET= utf8 COMMENT= '付款订单申请表';


DROP TABLE IF EXISTS t_payment ;
CREATE TABLE t_payment 
    ( 
        id int(11) unsigned NOT NULL AUTO_INCREMENT, 
        order_id int(11)  COMMENT '订单号', 
        customer_id varchar(64)   COMMENT '客户编号', 
        total_count int(11) DEFAULT '0' COMMENT '支付总笔数', 
        pay_status  int(11) DEFAULT '1'  COMMENT '支付状态  1未支付、2支付处理中、3已支付、4支付失败、5待人工干预',
        check_status  int(11) DEFAULT '1'  COMMENT '支付状态  1已申请(订单申请已经到账的数据)、2已上传(已提交excel或单次数据)、3待审核（提交放款申请）、4已审核（已放款）、5已驳回', 
        createoper varchar(50) COMMENT '申请人', 
        createtime datetime COMMENT '申请时间', 
        checkoper varchar(50) COMMENT '审核人', 
        checktime datetime COMMENT '审核时间', 
        pay_stat varchar(50) COMMENT '支付统计', 
        data_status int(1) DEFAULT '1' COMMENT '数据状态   1有效   -1无效', 
        remark varchar(200) COMMENT '备注', 
        PRIMARY KEY USING BTREE (id) 
    ) 
    ENGINE= InnoDB DEFAULT CHARSET= utf8 COMMENT= '财务付款管理表';


--上传 excel 后入订单详情表
DROP TABLE IF EXISTS t_order_detail ;
CREATE TABLE t_order_detail
    ( 
        id int(11) unsigned NOT NULL AUTO_INCREMENT, 
        order_id int(11)  COMMENT '订单号', 
        dai_zheng_id  int(11)  COMMENT '代征主体id', 
        payment_channel_id  int(11)  COMMENT '支付通道id', 
        policy_no varchar(100)  COMMENT '保单号', 
        policy_amount decimal(10,6) COMMENT '保费', 
        amount decimal(10,6) COMMENT '佣金', 
        agent_id int(10)  COMMENT '代理人id,通过身份证匹配到代理人id，便于将来关联代理人查询', 
        name varchar(20) COMMENT '姓名', 
        idcardno varchar(20) COMMENT '身份证', 
        open_bank_no varchar(64) COMMENT '银行卡号', 
        outtime datetime COMMENT '出单时间', 
        source varchar(500) COMMENT '来源   单次上传  或者  xxx.xls', 
        data_status int(1) DEFAULT '1' COMMENT '数据状态   1有效   -1无效', 
        remark varchar(200) COMMENT '备注', 
        createoper varchar(50) COMMENT '创建人', 
        createtime datetime COMMENT '注册时间', 
        modifyoper varchar(50) COMMENT '修改人', 
        modifytime datetime COMMENT '修改时间', 
        PRIMARY KEY USING BTREE (id) 
    ) 
    ENGINE= InnoDB DEFAULT CHARSET= utf8 COMMENT= '订单详情表（excel导入数据或者单次提交数据入此表、编辑明细数据）';


--发起支付时创建数据 
--将来和支付渠道对接时可能还会涉及到增加字段，目前还未可知
DROP TABLE IF EXISTS t_transaction_flow ;
CREATE TABLE t_transaction_flow
    ( 
        id int(11) unsigned NOT NULL AUTO_INCREMENT, 
        transaction_flow_id varchar(100) NOT NULL COMMENT '交易流水表标识 yyyyMMddHHmmss+6位随机数字', 
        order_id int(11)  COMMENT '订单号', 
        payment_channel_id int(10) COMMENT '支付通道id，再次支付 更换支付通道时需要', 
        dai_zheng_id  int(11)  COMMENT '代征主体id，再次支付 更换代征主体时需要', 
        policy_no varchar(100)  COMMENT '保单号', 
        policy_amount decimal(10,6) COMMENT '保费', 
        amount decimal(10,6) COMMENT '佣金', 
        agent_id int(10)  COMMENT '代理人id,通过身份证匹配到代理人id，并且便于再次支付时获取代理人其他银行卡信息', 
        name varchar(20) COMMENT '姓名', 
        idcardno varchar(20) COMMENT '身份证', 
        open_bank_no varchar(64) COMMENT '银行卡号', 
        outtime datetime COMMENT '出单时间', 
        source varchar(500) COMMENT '来源   单次上传  或者  xxx.xls', 
        pay_status  int(11) DEFAULT '1'  COMMENT '支付状态  1未支付、2支付处理中、3已支付、4支付失败、5待人工干预', 
        ft_type_id int(2) COMMENT '业务类型id  1线上支付、2线下支付等等', 
        complete_time datetime COMMENT '完成时间',  
        bank_code varchar(50) COMMENT '银行代码,支付通道需要的银行行号之类信息', 
        trade_code varchar(100) COMMENT '交易代码，与支付结构匹配   比如100014', 
        result varchar(200) COMMENT '支付结果  支付成功、余额不足、已挂失卡等等支付渠道返回信息', 
        remark varchar(200) COMMENT '备注', 
        createoper varchar(50) COMMENT '创建人', 
        createtime datetime COMMENT '注册时间', 
        modifyoper varchar(50) COMMENT '修改人', 
        modifytime datetime COMMENT '修改时间', 
        PRIMARY KEY USING BTREE (id) ,
        CONSTRAINT index_transaction_flow_id UNIQUE USING BTREE (transaction_flow_id) 
    ) 
    ENGINE= InnoDB DEFAULT CHARSET= utf8 COMMENT= '交易流水表';

--将来和支付渠道对接时可能还会涉及到增加字段，目前还未可知
DROP TABLE IF EXISTS t_collection_repayment_sign ;
CREATE  TABLE t_collection_repayment_sign 
    ( 
        id int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id', 
        request_sn varchar(50) NOT NULL COMMENT '支付流水号', 
        transaction_flow_id varchar(100) NOT NULL COMMENT '交易流水表标识',
        trade_code varchar(100) COMMENT '交易代码，与支付结构匹配   比如100014', 
        bank_code varchar(50) COMMENT '银行代码,支付通道需要的银行行号之类信息', 
        name varchar(20) COMMENT '姓名', 
        idcardno varchar(20) COMMENT '身份证', 
        open_bank_no varchar(64) COMMENT '银行卡号', 
        amount decimal(10,2) COMMENT '金额', 
        complete_time datetime COMMENT '完成时间', 
        post_time datetime COMMENT '提交时间', 
        remark varchar(250) COMMENT '备注', 
        status varchar(20) COMMENT '交易状态', 
        status1 varchar(20) COMMENT '返回值中的状态码', 
        status2 varchar(20) COMMENT '返回值详请中的状态码', 
        createoper varchar(50) COMMENT '操作员', 
        PRIMARY KEY USING BTREE (id, request_sn) 
    ) 
    ENGINE= InnoDB DEFAULT CHARSET= utf8 COMMENT= '支付流水表';



DROP TABLE IF EXISTS t_invoice ;
CREATE  TABLE t_invoice 
    ( 
        id int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id', 
        order_id int(11)  COMMENT '订单号', 
        invoice_time  datetime COMMENT '开票时间', 
        customer_id varchar(64)  NOT NULL  COMMENT '客户编号', 
        agent_commission decimal(14,6) COMMENT '代理人佣金', 
        real_payment decimal(14,6) COMMENT '实际付款', 
        recharge int(1) DEFAULT '1' COMMENT '充值状态 1未到账，2已到账', 
        invoice_status int(1) DEFAULT '1' COMMENT '开票状态 1未开票，2已开票', 
        express_company  varchar(100)  COMMENT '快递公司名', 
        express_no  varchar(100)  COMMENT '快递单号', 
        invoice_amount decimal(14,6)  COMMENT '开票金额', 
        invoice_title varchar(100) COMMENT '发票抬头', 
        taxpayer_type int(11) COMMENT '纳税人类型   1一般纳税人   2小规模纳税人', 
        taxpayer_number varchar(100) COMMENT '纳税人识别号', 
        reg_address varchar(200) COMMENT '单位注册地址', 
        open_bank varchar(100) COMMENT '开户行', 
        open_bank_no varchar(100) COMMENT '银行卡号', 
        invoice_content varchar(300) COMMENT '发票内容',
        receiver varchar(100) COMMENT '收件人', 
        mobile varchar(30) COMMENT '联系电话', 
        address varchar(100) COMMENT '地址', 
        invoice_pic_url varchar(500) COMMENT '发票图片地址', 
        data_status int(1) DEFAULT '1' COMMENT '数据状态   1有效   -1无效', 
        remark varchar(200) COMMENT '备注', 
        createoper varchar(50) COMMENT '创建人', 
        createtime datetime COMMENT '创建时间', 
        modifyoper varchar(50) COMMENT '修改人', 
        modifytime datetime COMMENT '修改时间', 
        PRIMARY KEY USING BTREE (id) 
    ) 
    ENGINE= InnoDB DEFAULT CHARSET= utf8 COMMENT= '发票管理';



DROP TABLE IF EXISTS t_busi_log ;
CREATE  TABLE t_busi_log 
    ( 
        id int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id', 
        customer_id varchar(64)  NOT NULL  COMMENT '客户编号', 
        ip varchar(100) COMMENT 'ip地址', 
        option_type int(1) DEFAULT '1' COMMENT '数据状态   1查询,2新增,3修改,4作废,5启用等等', 
        option_content varchar(1000) COMMENT '操作内容', 
        data_status int(1) DEFAULT '1' COMMENT '数据状态   1有效   -1无效', 
        remark varchar(200) COMMENT '备注', 
        createoper varchar(50) COMMENT '创建人', 
        createtime datetime COMMENT '创建时间', 
        modifyoper varchar(50) COMMENT '修改人', 
        modifytime datetime COMMENT '修改时间', 
        PRIMARY KEY USING BTREE (id) 
    ) 
    ENGINE= InnoDB DEFAULT CHARSET= utf8 COMMENT= '业务操作日志管理';