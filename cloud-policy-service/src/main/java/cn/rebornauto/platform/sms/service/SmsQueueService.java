package cn.rebornauto.platform.sms.service;

import java.util.List;

import tk.mybatis.mapper.entity.Example;
import cn.rebornauto.platform.sms.entity.SmsQueue;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date May 22, 2019 1:35:18 PM
 */
public interface SmsQueueService {
	int save(SmsQueue smsQueue);
	
	SmsQueue findById(Integer id);
	
	int update(SmsQueue smsQueue);
	
	List<SmsQueue> selectByExample(Example smsExample);
	
	int countByDay(String mobile,String templateId);
}
