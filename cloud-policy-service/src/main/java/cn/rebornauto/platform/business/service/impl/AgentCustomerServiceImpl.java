package cn.rebornauto.platform.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import cn.rebornauto.platform.business.dao.AgentCustomerMapper;
import cn.rebornauto.platform.business.service.AgentCustomerService;
/**
 * <p>Title: OauthController</p>  
 * <p>Description:</p>  
 * @author zjl  
 * @date 2019年4月28日
 */
@Service
public class AgentCustomerServiceImpl implements AgentCustomerService{
	
	@Autowired
	AgentCustomerMapper  agentCustomerMapper;
}
