package cn.rebornauto.platform.common;

/**
 * <p>Title:系统配置表key </p>
 * <p>Description: </p>
 * <p>Company: </p>
 * @author kgc
 * @date May 12, 2019 9:51:20 AM
 */
public class SysConfigKey {

	/**
	 * 通联支付时间段
	 */
    public final static String tonglianPayTime = "tonglianPayTime";
    
    /**
	 * 代理人可支付额度 
	 */
    public final static String agentQuota = "agentQuota";
    
    
    /**
     * 短信登录配置  true开启    false关闭
     */
    public static final String SmsLoginVerify="SmsLoginVerify";
    
    /**
     * 短信验证配置  true开启    false关闭
     */
    public static final String SmsAgentVerify="SmsAgentVerify";
    
    /**
     * 聚合短信发送url
     */
    public static final String SmsJuHeSendUrl="SmsJuHeSendUrl";
    
    /**
     * 聚合短信模版id
     */
    public static final String SmsJuHeTemplateId="SmsJuHeTemplateId";
    
    /**
     * 聚合短信key
     */
    public static final String SmsJuHeAPPKEY="SmsJuHeAPPKEY";
    
    /**
     * 短信配置黑名单   黑名单不可再次发送
     */
    public static final String SmsBlackList="SmsBlackList";
    
    /**
     * 短信配置白名单    白名单发送次数不受限制
     */
    public static final String SmsWhiteList="SmsWhiteList";
    
    /**
     * 短信发送次数配置 
     */
    public static final String SmsSendCount="SmsSendCount";
    
    /**
     * 支付通知信息
     */
    public static final String noticeMsg="noticeMsg";
    /**
     * 聚合短信模版id  瓜瓜车险
     */
    public static final String SmsJuHeGgcxTemplateId="SmsJuHeGgcxTemplateId";
    /**
     * 瓜瓜车险登录短信开关  true开启    false关闭
     */
    public static final String SmsGgcxLoginVerify="SmsGgcxLoginVerify";
}
