package cn.rebornauto.platform.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rebornauto.platform.business.entity.AgentQuota;
import cn.rebornauto.platform.business.vo.AgentQuotaVO;
import tk.mybatis.mapper.common.Mapper;

public interface AgentQuotaMapper extends Mapper<AgentQuota> {
    void cancelAgentQuotaLoanAmount(@Param("record") AgentQuota record);

    void addAgentQuotaLoanAmount(@Param("record") AgentQuota record);
    /**
     * 获取订单内每个人额度汇总数据
     * @param orderId
     * @return
     */
    List<AgentQuotaVO> getSumPersonalQuotaByOrderId(@Param("orderId") Integer orderId);
    /**
     * 根据身份证号码获取当月已用额度
     * @param list
     * @return
     */
    List<AgentQuota> selectAgentQuotaList(@Param("list") List<String> list);
}