package cn.rebornauto.platform.business.dao;

import java.math.BigDecimal;

import cn.rebornauto.platform.business.entity.CustomerTotalQuota;
import cn.rebornauto.platform.business.vo.CustomerQuotaVo;
import org.apache.ibatis.annotations.Param;

import cn.rebornauto.platform.business.entity.CustomerTotalQuota;
import tk.mybatis.mapper.common.Mapper;

public interface CustomerTotalQuotaMapper extends Mapper<CustomerTotalQuota> {
    CustomerTotalQuota selectByCustomerId();
    
    int cancelCustomerTotalQuotaByCustomerId(@Param("amount") BigDecimal amount);
    
    int addCustomerTotalQuotaByCustomerId(@Param("amount") BigDecimal amount);

    CustomerQuotaVo selectVo4CustomerTotalQuota();

}