package cn.rebornauto.platform.business.service;

import cn.rebornauto.platform.pay.sandpay.entity.SandInfo;
import cn.rebornauto.platform.pay.tonglian.entity.TongLianInfo;


/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 27, 2019 11:52:05 PM
 */
public interface PaymentChannelConfigService {

	TongLianInfo getTongLianInfo(Integer paymentChannelId,String sysSwitch);
	
	SandInfo getSandInfo(Integer paymentChannelId,String sysSwitch);
}
