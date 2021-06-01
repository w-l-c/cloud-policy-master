package cn.rebornauto.platform.business.dao;

import org.apache.ibatis.annotations.Param;

import cn.rebornauto.platform.business.entity.WxCustomer;
import cn.rebornauto.platform.business.query.AgentInfoQuery;
import cn.rebornauto.platform.business.vo.WxCustomerVo;
import tk.mybatis.mapper.common.Mapper;

public interface WxCustomerMapper extends Mapper<WxCustomer> {

	WxCustomerVo selectByQuery(@Param("q")AgentInfoQuery query);
}