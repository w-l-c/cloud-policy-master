package cn.rebornauto.platform.business.dao;

import cn.rebornauto.platform.business.entity.CustomerQuota;
import cn.rebornauto.platform.business.query.CustomerTotalQuotaQuery;
import cn.rebornauto.platform.business.vo.CustomerQuotaVo;
import cn.rebornauto.platform.business.vo.CustomerTotalQuotaVo;
import cn.rebornauto.platform.common.data.request.Pagination;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerQuotaMapper extends Mapper<CustomerQuota> {
    CustomerQuota selectByCustomerId(@Param("customerId") String customerId);

    int count(@Param("q") CustomerTotalQuotaQuery query);

    List<CustomerTotalQuotaVo> list(@Param("q") CustomerTotalQuotaQuery query, @Param("p") Pagination pagination);
    
    int cancelCustomerQuotaByCustomerId(@Param("customerId") String customerId,@Param("amount") BigDecimal amount);
    
    int addCustomerQuotaByCustomerId(@Param("customerId") String customerId,@Param("amount") BigDecimal amount);

    CustomerQuotaVo selectVoByCustomerId(@Param("customerId") String customerId);

    List<CustomerTotalQuotaVo> listAll(@Param("q") CustomerTotalQuotaQuery query);
}