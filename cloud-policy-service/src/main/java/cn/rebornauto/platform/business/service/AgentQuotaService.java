package cn.rebornauto.platform.business.service;

import java.math.BigDecimal;

import cn.rebornauto.platform.business.entity.AgentQuota;
import cn.rebornauto.platform.sys.entity.SysUser;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date May 15, 2019 5:44:39 PM
 */
public interface AgentQuotaService {

	AgentQuota getAgentQuota(String agentOpenBankNo,SysUser user);
	
	void addAgentQuotaLoanAmount(AgentQuota record);
	
	void cancelAgentQuotaLoanAmount(AgentQuota record);
	
	void initAgentQuota(String agentIdcardno,String userName);
}
