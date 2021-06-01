package cn.rebornauto.platform.business.dao;

import cn.rebornauto.platform.business.entity.AgentInfo;
import cn.rebornauto.platform.business.query.AgentInfoQuery;
import cn.rebornauto.platform.business.vo.*;
import cn.rebornauto.platform.common.data.request.Pagination;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AgentInfoMapper extends Mapper<AgentInfo> {
	GzhAgentInfoVo selectAgentInfoByOpenid(String openid);
	GzhAgentInfoVo selectAgentInfoByOpenidNew(String openid);

	List<GzhAgentCustomerVo> selectAgentCustomerListByAgentId(int agentId);
	List<GzhAgentCustomerVo> selectAgentCustomerListByAgentIdNew(@Param("agentId") int agentId,@Param("customerId")String customerId);

	int countByQuery(@Param("q") AgentInfoQuery query);

	List<AgentVo> pageQuery(@Param("p")Pagination pagination, @Param("q")AgentInfoQuery query);

    String selectAgentIdcardnoByOpenBankNo(String openBankNo);

	SignInfoVo getSignInfo(@Param("id") int id,@Param("type") int type);

	GzhAgentCustomerVo selectAgentCustomerInfoById(int id);
		
	SignStatusInfoVo selectSignStatus(@Param("agentId") int agentId,@Param("daiZhengId") int daiZhengId);

	List<AuthInfoVo> selectAgentCustomerIds(@Param("agentId") int agentId,@Param("daiZhengId") int daiZhengId);

}