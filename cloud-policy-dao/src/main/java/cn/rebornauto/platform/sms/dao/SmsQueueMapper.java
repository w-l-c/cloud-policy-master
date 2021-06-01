package cn.rebornauto.platform.sms.dao;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;
import cn.rebornauto.platform.sms.entity.SmsQueue;

public interface SmsQueueMapper extends Mapper<SmsQueue> {
	
	int countByDay(@Param("mobile") String mobile,@Param("templateId") String templateId);
}