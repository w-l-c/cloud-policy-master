package cn.rebornauto.platform.business.dao;

import cn.rebornauto.platform.business.entity.AgentCustomer;
import cn.rebornauto.platform.business.query.PaymentStatisticsQuery;
import cn.rebornauto.platform.business.vo.PaymentStatisticsVo;
import cn.rebornauto.platform.common.data.request.Pagination;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AgentCustomerMapper extends Mapper<AgentCustomer> {
    List<PaymentStatisticsVo> queryPaymentStatistics(@Param("p")Pagination pagination, @Param("q")PaymentStatisticsQuery query);

    long countByQueryPaymentStatistics(@Param("q")PaymentStatisticsQuery query);

    List<PaymentStatisticsVo> queryPaymentStatisticsForExport(@Param("q")PaymentStatisticsQuery query);

    List<AgentCustomer> listByAgentId(@Param("agentId")int agentId);
}