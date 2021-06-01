package cn.rebornauto.platform.business.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rebornauto.platform.business.dao.WxCustomerMapper;
import cn.rebornauto.platform.business.entity.WxCustomer;
import cn.rebornauto.platform.business.query.AgentInfoQuery;
import cn.rebornauto.platform.business.service.WxCustomerService;
import cn.rebornauto.platform.business.vo.WxCustomerVo;
@Service
public class WxCustomerServiceImpl  implements WxCustomerService{

	@Autowired
	 WxCustomerMapper  wxCustomerMapper;

	@Override
	public WxCustomerVo selectByQuery(AgentInfoQuery query) {
		// TODO Auto-generated method stub
		return wxCustomerMapper.selectByQuery(query);
	}

	@Override
	public void insertSelective(AgentInfoQuery query) {
		// TODO Auto-generated method stub
		WxCustomer wxCustomer = new WxCustomer();
		wxCustomer.setOpenid(query.getOpenid());
		wxCustomer.setCustomerId(query.getCustomerId());
		wxCustomer.setCreatetime(LocalDateTime.now());
		wxCustomerMapper.insertSelective(wxCustomer);
	}
}
