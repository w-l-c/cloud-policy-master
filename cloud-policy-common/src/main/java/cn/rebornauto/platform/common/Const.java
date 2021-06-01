package cn.rebornauto.platform.common;

import java.text.SimpleDateFormat;

public class Const {

    public final static Byte ISSYS = 1;
    public final static Byte ISNOTSYS = 0;

    public final  static SimpleDateFormat SDF_DATA = new SimpleDateFormat("yyyy-MM-dd");

    public final static SimpleDateFormat SDF_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public final static SimpleDateFormat SDF_HM = new SimpleDateFormat("HH:mm");

    // 用户session
    public static final String SESSION_USER = "sessionUser";

    // 图片验证码sessionName
    public final static String SafeCode_SessionName = "SafeCode_SessionName";

    // 短信验证码sessionName
    public final static String MobilCode_SessionName = "MobilCode_SessionName";

    //类型:1 目录 ，2 菜单，3 列表，4 按钮
    public final static byte MENU_ITEM_GROUP = 1;
    public final static byte MENU_ITEM = 2;
    public final static byte MENU_ITEM_LIST = 3;
    public final static byte MENU_ITEM_BUT = 4;

    //状态 0未审核   1有效数据   2已删除数据
    public final static byte Status_Init = 0;
    public final static byte Status_Normal = 1;
    public final static byte Status_Del = 2;
    
    //状态 1有效数据   －1作废数据
    public final static byte STATUS_1 = 1;
    public final static byte STATUS__1 = -1;
    
    /**
     * 1已申请
     */
    public final static int CHECK_STATUS_1 = 1;
    /**
     * 2已上传
     */
    public final static int CHECK_STATUS_2 = 2;
    /**
     * 3待审核
     */
    public final static int CHECK_STATUS_3 = 3;
    /**
     * 4已审核
     */
    public final static int CHECK_STATUS_4 = 4;
    /**
     * 5已驳回
     */
    public final static int CHECK_STATUS_5 = 5;
    
    /**
     * 微信授权返回状态 0返回注册页面
     */
    public final static byte TYPE_LOGON = 0;
    /**
     * 微信授权返回状态 1返回用户信息页面
     */    
    public final static byte TYPE_USER = 1;
    /**
     * 微信授权返回状态 2返回错误信息页面
     */
    public final static byte TYPE_ERROR= 2;
    

    /**
     * 认证状态   1未认证
     */
    public final static int AUTH_STATUS_1= 1;
    
    /**
     * 认证状态  2已认证
     */
    public final static int AUTH_STATUS_2 = 2;
    
    /**
     * 认证状态  3失败
     */
    public final static int AUTH_STATUS_3 = 3;
    
    /**
     * 认证状态 认证中
     */
    public final static int AUTH_STATUS_4 = 4;

    
    
    /**
     * 签约状态   1未签约
     */
    public final static int SIGN_STATUS_1= 1;
    
    /**
     * 签约状态  2已签约
     */
    public final static int SIGN_STATUS_2 = 2;
    
    /**
     *签约状态 3签约失败
     */
    public final static int SIGN_STATUS_3 = 3;

    /**
     * 签约状态  4签约待审核
     */
    public final static int SIGN_STATUS_4 = 4;
    /**
     * 签约状态  5签约审核拒绝
     */
    public final static int SIGN_STATUS_5 = 5;

    
    //状态 1有效数据   －1作废数据
    public final static int DATA_STATUS_1 = 1;
    public final static int DATA_STATUS__1 = -1;

    //充值状态  1 已申请     2 待审核   3 已到账   4 已驳回
    /**
     * 1 已申请
     */
    public final static int recharge_1 = 1;
    /**
     * 2 待审核
     */
    public final static int recharge_2 = 2;
    /**
     * 3 已到账
     */
    public final static int recharge_3 = 3;
    /**
     * 4 已驳回
     */
    public final static int recharge_4 = 4;

    //开票状态  1 未开票       2 已开票
    public final static int invoice_status_1 = 1;
    public final static int invoice_status_2 = 2;
    //发票状态  1 待开票       2 已开票
    public final static int out_invoice_status_1 = 1;
    public final static int out_invoice_status_2 = 2;

    //日志业务类型
    /**
     * 客户
     */
    public final static String busi_log_busi_type_1 = "customer";//客户
    /**
     * 代理人
     */
    public final static String busi_log_busi_type_2 = "agent";//代理人
    /**
     * 代征主体
     */
    public final static String busi_log_busi_type_3 = "daiZheng";//代征主体
    /**
     * 充值
     */
    public final static String busi_log_busi_type_4 = "recharge";//充值
    /**
     * 付款
     */
    public final static String busi_log_busi_type_5 = "payment";//付款
    /**
     * 发票
     */
    public final static String busi_log_busi_type_6 = "invoice";//发票
    /**
     * 订单
     */
    public final static String busi_log_busi_type_7 = "order";//订单
    /**
     * 付款审核
     */
    public final static String busi_log_busi_type_8 = "examine";//付款审核
    /**
     * 交易结果查询和更新 
     */
    public final static String busi_log_busi_type_9 = "tradeResult";//交易结果查询和更新 
    /**
     * 开票
     */
    public final static String busi_log_busi_type_10 = "makeInvoice";//开票
    /**
     * 邮寄地址
     */
    public final static String busi_log_busi_type_11 = "postAddress";//邮寄地址
    /**
     * 任务
     */
    public final static String busi_log_busi_type_12 = "mission";//任务
    /**
     * 任务认领
     */
    public final static String busi_log_busi_type_13 = "missionAccept";//任务认领


    //日志操作
    /**
     * 新增
     */
    public final static int busi_log_option_type_xinzeng = 1;//新增
    /**
     * 修改
     */
    public final static int busi_log_option_type_xiugai = 2;//修改
    /**
     * 作废
     */
    public final static int busi_log_option_type_zuofei = 3;//作废
    /**
     * 启用
     */
    public final static int busi_log_option_type_qiyong = 4;//启用
    /**
     * 申请
     */
    public final static int busi_log_option_type_shenqing = 5;//申请
    /**
     * 通过
     */
    public final static int busi_log_option_type_tongguo = 6;//通过
    /**
     * 驳回
     */
    public final static int busi_log_option_type_bohui = 7;//驳回
    /**
     * 成功
     */
    public final static int busi_log_option_type_success = 8;//成功
    /**
     * 失败
     */
    public final static int busi_log_option_type_fail = 9;//失败

    //管理员角色id
    public final static int admin_role_id = 999;
    //状态 test测试环境   online生产环境
    /**
     * 测试环境
     */
    public final static String SYS_PAY_SWITCH_TEST = "test";
    /**
     * 生产环境
     */
    public final static String SYS_PAY_SWITCH_ONLINE = "online";
    
    /**
     * 支付通道 1通联
     */
    public final static int SYS_PAYMENT_CHANNEL_TONGLIAN =1;
    
    /**
     * 支付通道 2杉徳
     */
    public final static int SYS_PAYMENT_CHANNEL_SAND =2;
    
    /**  
     * gzh用户状态 0新用户
     */
    public final static int NEW_USER_STATUS =0;
    /**
     * gzh用户状态 1老用户
     */
    public final static int OLD_USER_STATUS =1;
    
    /**  
     * 字典状态 0有效
     */
    public final static int DISABLED_VALID =0;
    /**
     * 字典状态 1无效
     */
    public final static int DISABLED_INEFFECTIVE =1;
    
    /**
     * 管理员账号customer_id
     */
    public final static String ADMINISTRATOR_CUSTOMER_ID ="9999";
    
    /**
     * 客户管理菜单，代理人菜单
     * 非管理账号根据customer_id条件查询如果跟当前用户的customer_id不匹配，给他默认查询不到数据的条件
     */
    public final static String NOTFOUND ="notFound";

    /**
     * 额度充足1
     */
    public final static int QUOTA_LIMIT_1 =1;
    /**
     * 额度不足0
     */
    public final static int QUOTA_LIMIT_0 =0;
    
    /**
     * 登陆成功 
     */
    public final static String LOGIN_SUCCESS ="login_success";
    /**
     * 登陆失败
     */
    public final static String LOGIN_FAIL ="login_fail";
    
    
    
  //未发送
    public static final Byte SMS_0 = 0;

    //已发送
    public static final Byte SMS_1 = 1;

    //已删除
    public static final Byte SMS_2 = 2;
    
    public static final Byte Source_Type = 1;
    public static final Byte Source_Type2 = 2;
    public static final Byte Source_Type3 = 3;
    public static final Byte Source_Type_Pre = 4;

    //验证码
    public static final String Template_LoginVerify = "LoginVerify";
    //公众号校验代理人信息
    public static final String Template_AgentVerify = "AgentVerify";
    //小程序商家登录
    public static final String Template_WxDealerVerify = "wxDealerLogin";
    
    public final static String ERRNO="9999";

    public final static String ERRMSG="异常";

    /**
     * 尚尚签企业证书查询状态- taskId不存在或已过期
     */   
    public final static String APPLY_CERT_STATUS0="0";

    /**
     * 尚尚签企业证书查询状态-新申请
     */
    public final static String APPLY_CERT_STATUS1 ="1";
    /**
     * 尚尚签企业证书查询状态-申请中
     */
    public final static String APPLY_CERT_STATUS2 ="2";
    /**
     * 尚尚签企业证书查询状态-超时
     */
    public final static String APPLY_CERT_STATUS3 ="3";
    /**
     * 尚尚签企业证书查询状态-申请失败
     */
    public final static String APPLY_CERT_STATUS4 ="4";
    /**
     * 尚尚签企业证书查询状态-成功
     */
    public final static String APPLY_CERT_STATUS5 ="5";
    /**
     * 尚尚签企业证书查询状态-无效的申请（数据库无此值)
     */
    public final static String APPLY_CERT_STATUS6 ="-1";


    /**
     * 尚尚签企业证书查询状态- taskId不存在或已过期
     */   
    public final static String APPLY_CERT_MESSAGE0=" taskId不存在或已过期";

    /**
     * 尚尚签企业证书查询状态-新申请
     */
    public final static String APPLY_CERT_MESSAGE1 ="新申请";
    /**
     * 尚尚签企业证书查询状态-申请中
     */
    public final static String APPLY_CERT_MESSAGE2 ="申请中";
    /**
     * 尚尚签企业证书查询状态-超时
     */
    public final static String APPLY_CERT_MESSAGE3 ="超时";
    /**
     * 尚尚签企业证书查询状态-申请失败
     */
    public final static String APPLY_CERT_MESSAGE4 ="申请失败";
    /**
     * 尚尚签企业证书查询状态-成功
     */
    public final static String APPLY_CERT_MESSAGE5 ="成功";
    /**
     * 尚尚签企业证书查询状态-无效的申请（数据库无此值
     */
    public final static String APPLY_CERT_MESSAGE6 ="无效的申请（数据库无此值）";
   /**
    * 用户类型 1个人，2企业
    */
    public final static String    USER_TYPE1="1";
    /**
     * 用户类型 1个人，2企业
     */
    public final static String   USER_TYPE2="2";
    
    /**
     * 师域（天津）信息咨询有限公司:1
     */
    public final static int   DAI_ZHENG_ID_1=1;
    /**
     * 上海师祥科技有限公司:2
     */
    public final static int   DAI_ZHENG_ID_2=2;
    
    /**
     * 待开票
     */
    public final static int MAKE_INVOICE_STATUS_1 = 1;
    /**
     * 已开票
     */
    public final static int MAKE_INVOICE_STATUS_2 = 2;
    
    /**
     * 待寄送
     */
    public final static int POST_STATUS_1 = 1;
    /**
     * 已寄送
     */
    public final static int POST_STATUS_2 = 2;
    /**
     * 模版中判断开始字符
     */
    public final static String TEMPLATE_YUN_XIAO_WEI="云小微";
    /**
     * 付款模版不是最新，请下载最新模版
     */
    public final static String TEMPLATE_ERROR="付款模版不是最新，请下载最新模版!";
    /**
     * 正常相应
     */
    public final static String SAND_PAY_RESULT_CODE_0000="0000";

    public final static String MISSION_NO_PRE ="YXW-TASK-";
}
