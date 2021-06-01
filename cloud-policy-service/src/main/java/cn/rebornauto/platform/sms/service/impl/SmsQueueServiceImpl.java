package cn.rebornauto.platform.sms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import cn.rebornauto.platform.sms.dao.SmsQueueMapper;
import cn.rebornauto.platform.sms.entity.SmsQueue;
import cn.rebornauto.platform.sms.service.SmsQueueService;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date May 22, 2019 1:35:37 PM
 */
@Service
public class SmsQueueServiceImpl implements SmsQueueService{

	@Autowired
    private SmsQueueMapper smsQueueMapper;

    @Override
    @Transactional
    public int save(SmsQueue smsQueue) {
        return smsQueueMapper.insertSelective(smsQueue);
    }

    @Override
    public SmsQueue findById(Integer id) {
        return smsQueueMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int update(SmsQueue smsQueue) {
        return smsQueueMapper.updateByPrimaryKeySelective(smsQueue);
    }


    
    @Override
    public List<SmsQueue> selectByExample(Example smsExample) {
        return smsQueueMapper.selectByExample(smsExample);
    }

	@Override
	public int countByDay(String mobile, String templateId) {
		return smsQueueMapper.countByDay(mobile, templateId);
	}

}
