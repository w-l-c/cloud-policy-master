package cn.rebornauto.platform.business.service.impl;

import cn.rebornauto.platform.business.dao.AgentInfoMapper;
import cn.rebornauto.platform.business.dao.AgentQuotaMapper;
import cn.rebornauto.platform.business.entity.AgentQuota;
import cn.rebornauto.platform.business.service.AgentQuotaService;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.pay.tonglian.utils.DateUtil;
import cn.rebornauto.platform.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date May 15, 2019 5:45:00 PM
 */
@Service
public class AgentQuotaServiceImpl implements AgentQuotaService {

	@Autowired
	AgentQuotaMapper agentQuotaMapper;
	@Autowired
	AgentInfoMapper agentInfoMapper;
	/**
	 * 获取代理人当月额度
	 */
	@Override
	public AgentQuota getAgentQuota(String agentIdcardno,SysUser user) {
		AgentQuota query = new AgentQuota();
		query.setAgentIdcardno(agentIdcardno);
		query.setYearmonth(DateUtil.format(new Date(), "yyyyMM"));
		query.setDataStatus(Const.DATA_STATUS_1);
		
		AgentQuota quota = agentQuotaMapper.selectOne(query);
		if(null==quota) {
			AgentQuota queryBean = new AgentQuota();
			queryBean.setLoanAmount(BigDecimal.ZERO);
			queryBean.setCreateoper(user.getNickname());
			queryBean.setCreatetime(LocalDateTime.now());
			queryBean.setAgentIdcardno(agentIdcardno);
			queryBean.setYearmonth(DateUtil.format(new Date(), "yyyyMM"));
			queryBean.setDataStatus(Const.DATA_STATUS_1);
			agentQuotaMapper.insertSelective(queryBean);
			return	queryBean;
		}else {
			return	quota;
		}
	}	
	
	@Override
	public void initAgentQuota(String agentIdcardno,String userName) {
		AgentQuota query = new AgentQuota();
		query.setAgentIdcardno(agentIdcardno);
		query.setYearmonth(DateUtil.format(new Date(), "yyyyMM"));
		query.setDataStatus(Const.DATA_STATUS_1);
		
		AgentQuota quota = agentQuotaMapper.selectOne(query);
		if(null==quota) {
			AgentQuota queryBean = new AgentQuota();
			queryBean.setLoanAmount(BigDecimal.ZERO);
			queryBean.setCreateoper(userName);
			queryBean.setCreatetime(LocalDateTime.now());
			queryBean.setAgentIdcardno(agentIdcardno);
			queryBean.setYearmonth(DateUtil.format(new Date(), "yyyyMM"));
			queryBean.setDataStatus(Const.DATA_STATUS_1);
			agentQuotaMapper.insertSelective(queryBean);
		}
	}
	
	/**
	 * 增加代理人放款额度
	 * @param record
	 */
	public void addAgentQuotaLoanAmount(AgentQuota record) {
		record.setAgentIdcardno(getAgentIdNo(record.getAgentOpenBankNo()));
		agentQuotaMapper.addAgentQuotaLoanAmount(record);
	}	
	/**
	 * 减少代理人放款额度
	 * @param record
	 */
	public void cancelAgentQuotaLoanAmount(AgentQuota record) {
		record.setAgentIdcardno(getAgentIdNo(record.getAgentOpenBankNo()));
		agentQuotaMapper.cancelAgentQuotaLoanAmount(record);
	}
	public String getAgentIdNo(String openBankNo) {
		return agentInfoMapper.selectAgentIdcardnoByOpenBankNo(openBankNo);
	}
}
