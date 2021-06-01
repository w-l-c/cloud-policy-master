package cn.rebornauto.platform.business.service;

import java.util.List;

import cn.rebornauto.platform.business.entity.AgentBankNoInfo;
import cn.rebornauto.platform.business.form.AgentBankNoInfoForm;
import cn.rebornauto.platform.business.vo.AgentBankNoVo;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.pay.tonglian.entity.TongLianInfo;

/**
 * 
 * <p>
 * Title: AgentBankNoInfoService
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zjl
 * @date 2019年5月1日
 */
public interface AgentBankNoInfoService {
	/**
	 * GZH根据代理人id查询他的所有银行卡
	 * 
	 * @param agentId
	 * @return
	 */
	List<AgentBankNoVo> selectAgentBankNoListByAgentId(int agentId);

	/**
	 * GZH通联三要素校验
	 * 
	 * @param form
	 * @param tongLianInfo
	 * @return
	 */
	Response verification(AgentBankNoInfoForm form, TongLianInfo tongLianInfo);

	/**
	 * GZH通联4三要素校验并根据代理人id录入银行卡的信息
	 * 
	 * @param form
	 * @param tongLianInfo
	 * @return
	 */
	Response addAgentBankNoInfo(AgentBankNoInfoForm form,
			TongLianInfo tongLianInfo);
	
	/**
	 * 获取代理人卡号
	 * @param agentOpenBankNo
	 * @return
	 */
	AgentBankNoInfo selectAgentBankNoInfoByOpenBankNo(String  agentOpenBankNo);
	
	/**
     * 根据代理人id查询银行卡信息验证的客户银行卡
     * @param agentId
     * @return
     */
	List<AgentBankNoVo> selectAgentBankNoListAuthByAgentId(int agentId);

}
