package cn.rebornauto.platform.business.service;

import cn.rebornauto.platform.business.entity.CustomerInfo;
import cn.rebornauto.platform.business.entity.CustomerQuota;
import cn.rebornauto.platform.business.entity.CustomerTotalQuota;
import cn.rebornauto.platform.business.vo.CustomerQuotaVo;

public interface CustomerQuotaService {
    CustomerQuota selectByCustomerId(String customerId);

    CustomerTotalQuota select4CustomerTotalQuota();

    CustomerInfo selectCustomerInfo(String customerId);

    CustomerQuotaVo selectVoByCustomerId(String customerId);

    CustomerQuotaVo selectVo4CustomerTotalQuota();

}
