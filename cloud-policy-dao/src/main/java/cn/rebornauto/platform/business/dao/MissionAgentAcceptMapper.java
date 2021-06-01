package cn.rebornauto.platform.business.dao;

import cn.rebornauto.platform.business.entity.MissionAgentAccept;
import cn.rebornauto.platform.business.query.MissionAcceptQuery;
import cn.rebornauto.platform.business.vo.AgentBankNoVo;
import cn.rebornauto.platform.business.vo.MissionAgentAcceptVo;
import cn.rebornauto.platform.common.data.request.Pagination;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MissionAgentAcceptMapper extends Mapper<MissionAgentAccept> {

    List<AgentBankNoVo> selectByCustomerId(@Param("customerId")String customerId);

    int insertList(@Param("list") List<MissionAgentAccept> missionAgentAccepts);

    List<MissionAgentAcceptVo> selectByQuery(@Param("p") Pagination pagination, @Param("q") MissionAcceptQuery query);
}