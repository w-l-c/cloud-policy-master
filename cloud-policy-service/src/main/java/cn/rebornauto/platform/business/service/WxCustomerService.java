package cn.rebornauto.platform.business.service;

import cn.rebornauto.platform.business.query.AgentInfoQuery;
import cn.rebornauto.platform.business.vo.WxCustomerVo;

public interface WxCustomerService {
     /**
      * 查询客户和微信公众号绑定记录表是否有此用户的记录
      * @param query
      * @return
      */
	WxCustomerVo selectByQuery(AgentInfoQuery query);
    /**
     * insertSelective客户和微信公众号
     * @param query
     */
	void insertSelective(AgentInfoQuery query);

}
