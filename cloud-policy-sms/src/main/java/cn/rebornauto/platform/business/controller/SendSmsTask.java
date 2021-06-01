package cn.rebornauto.platform.business.controller;


import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tk.mybatis.mapper.entity.Example;
import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.SysConfigKey;
import cn.rebornauto.platform.common.sms.JuheMSM;
import cn.rebornauto.platform.sms.entity.SmsQueue;
import cn.rebornauto.platform.sms.service.SmsQueueService;
@Component
@Configurable
@EnableScheduling
public class SendSmsTask {

    private static final Logger logger = LoggerFactory.getLogger(SendSmsTask.class);

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

    //登录验证码
    public static final String Template_LoginVerify = "LoginVerify";
    

    /**
     * 读取数据库队列
     */
    @Autowired
    private SmsQueueService smsQueueService;

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 发送短信验证码
     */
    @Scheduled(cron="*/5 * * * * ?")
    public void send() {
    	Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, -10);
    	Example smsExample = new Example(SmsQueue.class);
    	smsExample.createCriteria()
//    	.andEqualTo("templateId", Template_LoginVerify)
    	.andEqualTo("status", SMS_0).andGreaterThanOrEqualTo("gmtCreate", c.getTime());

        List<SmsQueue> list = smsQueueService.selectByExample(smsExample);
        if (list != null && list.size() > 0) {
            for (SmsQueue s : list) {
                //todo 调用客户端发送
                try {
                    s.setGmtSend(LocalDateTime.now());
                    s.setStatus(SMS_1);
                    //登录发送短信开关   true开启    false关闭
                    String loginVerify = sysConfigService.findValueByKey(SysConfigKey.SmsLoginVerify);
                    
                    //验证发送短信开关   true开启    false关闭
                    String agentVerify = sysConfigService.findValueByKey(SysConfigKey.SmsAgentVerify);
                    
                    //瓜瓜车险 登录发送短信开关   true开启    false关闭
                    String ggcxLoginVerify = sysConfigService.findValueByKey(SysConfigKey.SmsGgcxLoginVerify);
                    if ("true".equals(loginVerify)&&s.getTemplateId().equalsIgnoreCase(Const.Template_LoginVerify)) {
                    	//判断是否黑名单
                    	if(checkIsBlackList(s)) {
                    		s.setRetMessage("属于黑名单");
                    		logger.info(s.getMobile()+"属于黑名单");
                    	}else {
                    		//查询当天此手机号码发送数量
                    		int sendCount = smsQueueService.countByDay(s.getMobile(), s.getTemplateId());
                    		
                    		//短信发送次数配置
                            int smsSendCount = Integer.parseInt(sysConfigService.findValueByKey(SysConfigKey.SmsSendCount));
                            //已发送次数超过当天配置次数，并且不属于白名单的手机号,  不再发送
                    		if(sendCount>smsSendCount && !checkIsWhiteList(s)) {
                    			s.setRetMessage("次数超过 "+smsSendCount+"次");
                    			logger.info(s.getMobile()+"次数超过 "+smsSendCount+"次");
                    		} else {
                    			//短信请求地址
                                String smsJuHeSendUrl = sysConfigService.findValueByKey(SysConfigKey.SmsJuHeSendUrl);
                                //短信模版id
                                String smsJuHeTemplateId = sysConfigService.findValueByKey(SysConfigKey.SmsJuHeTemplateId);
                                //短信key
                                String smsJuHeAPPKEY = sysConfigService.findValueByKey(SysConfigKey.SmsJuHeAPPKEY);
                                StringBuffer sb = new StringBuffer();
                                sb.append("#code#=").append(s.getContent());
                    	    	String tpl_value = URLEncoder.encode(sb.toString(), "UTF-8");
                                
                                
                    	    	String ret = JuheMSM.sendSms(s.getMobile(), smsJuHeTemplateId, tpl_value,smsJuHeSendUrl,smsJuHeAPPKEY);
                    	    	JSONObject object = JSONObject.fromObject(ret);
                                if(object.getInt("error_code")==0){
                                    System.out.println(object.get("result"));
                                    s.setRetCode("0");
                                    s.setRetMessage(object.get("reason")+object.get("result").toString());
                                }else{
                                    System.out.println(object.get("error_code")+":"+object.get("reason"));
                                    s.setRetCode(object.get("error_code").toString());
                                    s.setRetMessage(object.get("reason").toString().length() > 1000 ? object.get("reason").toString().substring(0, 1000) : object.get("reason").toString());
                                }
                    		}
                    	}
                    }else if("true".equals(agentVerify)&&s.getTemplateId().equalsIgnoreCase(Const.Template_AgentVerify)) {
                    	//判断是否黑名单
                    	if(checkIsBlackList(s)) {
                    		s.setRetMessage("属于黑名单");
                    		logger.info(s.getMobile()+"属于黑名单");
                    	}else {
                    		//查询当天此手机号码发送数量
                    		int sendCount = smsQueueService.countByDay(s.getMobile(), s.getTemplateId());
                    		
                    		//短信发送次数配置
                            int smsSendCount = Integer.parseInt(sysConfigService.findValueByKey(SysConfigKey.SmsSendCount));
                            //已发送次数超过当天配置次数，并且不属于白名单的手机号,  不再发送
                    		if(sendCount>smsSendCount && !checkIsWhiteList(s)) {
                    			s.setRetMessage("次数超过 "+smsSendCount+"次");
                    			logger.info(s.getMobile()+"次数超过 "+smsSendCount+"次");
                    		} else {
                    			//短信请求地址
                                String smsJuHeSendUrl = sysConfigService.findValueByKey(SysConfigKey.SmsJuHeSendUrl);
                                //短信模版id
                                String smsJuHeTemplateId = sysConfigService.findValueByKey(SysConfigKey.SmsJuHeTemplateId);
                                //短信key
                                String smsJuHeAPPKEY = sysConfigService.findValueByKey(SysConfigKey.SmsJuHeAPPKEY);
                                StringBuffer sb = new StringBuffer();
                                sb.append("#code#=").append(s.getContent());
                    	    	String tpl_value = URLEncoder.encode(sb.toString(), "UTF-8");
                                
                                
                    	    	String ret = JuheMSM.sendSms(s.getMobile(), smsJuHeTemplateId, tpl_value,smsJuHeSendUrl,smsJuHeAPPKEY);
                    	    	JSONObject object = JSONObject.fromObject(ret);
                                if(object.getInt("error_code")==0){
                                    System.out.println(object.get("result"));
                                    s.setRetCode("0");
                                    s.setRetMessage(object.get("reason")+object.get("result").toString());
                                }else{
                                    System.out.println(object.get("error_code")+":"+object.get("reason"));
                                    s.setRetCode(object.get("error_code").toString());
                                    s.setRetMessage(object.get("reason").toString().length() > 1000 ? object.get("reason").toString().substring(0, 1000) : object.get("reason").toString());
                                }
                    		}
                    	}
                    }else if("true".equals(ggcxLoginVerify)&&s.getTemplateId().equalsIgnoreCase(Const.Template_WxDealerVerify)) {
                    	//判断是否黑名单
                    	if(checkIsBlackList(s)) {
                    		s.setRetMessage("属于黑名单");
                    		logger.info(s.getMobile()+"属于黑名单");
                    	}else {
                    		//查询当天此手机号码发送数量
                    		int sendCount = smsQueueService.countByDay(s.getMobile(), s.getTemplateId());
                    		
                    		//短信发送次数配置
                            int smsSendCount = Integer.parseInt(sysConfigService.findValueByKey(SysConfigKey.SmsSendCount));
                            //已发送次数超过当天配置次数，并且不属于白名单的手机号,  不再发送
                    		if(sendCount>smsSendCount && !checkIsWhiteList(s)) {
                    			s.setRetMessage("次数超过 "+smsSendCount+"次");
                    			logger.info(s.getMobile()+"次数超过 "+smsSendCount+"次");
                    		} else {
                    			//短信请求地址
                                String smsJuHeSendUrl = sysConfigService.findValueByKey(SysConfigKey.SmsJuHeSendUrl);
                                //短信模版id
                                String smsJuHeTemplateId = sysConfigService.findValueByKey(SysConfigKey.SmsJuHeGgcxTemplateId);
                                //短信key
                                String smsJuHeAPPKEY = sysConfigService.findValueByKey(SysConfigKey.SmsJuHeAPPKEY);
                                StringBuffer sb = new StringBuffer();
                                sb.append("#code#=").append(s.getContent());
                    	    	String tpl_value = URLEncoder.encode(sb.toString(), "UTF-8");
                                
                                
                    	    	String ret = JuheMSM.sendSms(s.getMobile(), smsJuHeTemplateId, tpl_value,smsJuHeSendUrl,smsJuHeAPPKEY);
                    	    	JSONObject object = JSONObject.fromObject(ret);
                                if(object.getInt("error_code")==0){
                                    System.out.println(object.get("result"));
                                    s.setRetCode("0");
                                    s.setRetMessage(object.get("reason")+object.get("result").toString());
                                }else{
                                    System.out.println(object.get("error_code")+":"+object.get("reason"));
                                    s.setRetCode(object.get("error_code").toString());
                                    s.setRetMessage(object.get("reason").toString().length() > 1000 ? object.get("reason").toString().substring(0, 1000) : object.get("reason").toString());
                                }
                    		}
                    	}
                    }
                    smsQueueService.update(s);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.toString());
                } finally {
                    logger.info("发送短信:" + s.toString());
                    smsQueueService.update(s);
                }
            }
        }
    }
    
    /**
     * 判断是否黑名单
     * @param bean
     * @return
     */
    public boolean checkIsBlackList(SmsQueue bean) {
    	boolean flag = false;
    	//判断是否黑名单
    	String smsBlackList = sysConfigService.findValueByKey(SysConfigKey.SmsBlackList);
    	if(StringUtils.isNotBlank(smsBlackList)) {
    		String[] blackList = smsBlackList.split(",");
    		for (int i = 0; i < blackList.length; i++) {
				if(blackList[i].equals(bean.getMobile())) {
					flag=true;
				}
			}
    	}
		return flag;
    }
    
    /**
     * 判断是否白名单
     * @param bean
     * @return
     */
    public boolean checkIsWhiteList(SmsQueue bean) {
    	boolean flag = false;
    	//判断是否黑名单
    	String smsWhiteList = sysConfigService.findValueByKey(SysConfigKey.SmsWhiteList);
    	if(StringUtils.isNotBlank(smsWhiteList)) {
    		String[] whiteList = smsWhiteList.split(",");
    		for (int i = 0; i < whiteList.length; i++) {
				if(whiteList[i].equals(bean.getMobile())) {
					flag=true;
				}
			}
    	}
		return flag;
    }
    
    
    
}
