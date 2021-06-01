


package cn.rebornauto.platform.business.dao;

import cn.rebornauto.platform.business.entity.AgentBankNoInfo;
import cn.rebornauto.platform.business.vo.AgentBankNoVo;
import cn.rebornauto.platform.business.vo.AgentInfoVO;
import cn.rebornauto.platform.business.vo.ExcelTemplateVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AgentBankNoInfoMapper extends Mapper<AgentBankNoInfo> {
	 /**
     * 根据代理人id查询银行卡信息
     * @param agentId
     * @return
     */
	List<AgentBankNoVo> selectAgentBankNoListByAgentId(int agentId);
	
	/**
	 * 根据当前客户编号以及代理人身份证号、姓名、银行卡号获取信息
	 * @param agentIdcardno
	 * @param agentName
	 * @param agentOpenBankNo
	 * @param customerId
	 * @return
	 */
	AgentInfoVO selectAgentInfoByAgentParam(@Param("agentIdcardno")String agentIdcardno,@Param("agentName")String agentName,@Param("agentOpenBankNo")String agentOpenBankNo,@Param("customerId")String customerId);

	/**
	 * 根据客户id获取代理人列表excel信息
	 * @param customerId
	 * @return
	 */
	List<ExcelTemplateVO> selectAgentListByCustomerId(@Param("customerId") String customerId);
	
	/**
     * 根据代理人id查询银行卡信息验证的客户银行卡
     * @param agentId
     * @return
     */
	List<AgentBankNoVo> selectAgentBankNoListAuthByAgentId(int agentId);
	
	/**
	 * 根据客户id获取代理人详细信息
	 * @param customerId
	 * @return
	 */
	List<AgentInfoVO> selectAgentInfoListByCustomerId(@Param("customerId") String customerId);
}