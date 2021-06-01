package cn.rebornauto.platform.business.service.impl;

import cn.rebornauto.platform.business.dao.CustomerInfoMapper;
import cn.rebornauto.platform.business.dao.CustomerQuotaMapper;
import cn.rebornauto.platform.business.dao.CustomerTotalQuotaMapper;
import cn.rebornauto.platform.business.entity.CustomerInfo;
import cn.rebornauto.platform.business.entity.CustomerQuota;
import cn.rebornauto.platform.business.entity.CustomerTotalQuota;
import cn.rebornauto.platform.business.service.CustomerQuotaService;
import cn.rebornauto.platform.business.vo.CustomerQuotaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerQuotaServiceImpl implements CustomerQuotaService {
    @Autowired
    private CustomerQuotaMapper customerQuotaMapper;
    @Autowired
    private CustomerTotalQuotaMapper customerTotalQuotaMapper;
    @Autowired
    private CustomerInfoMapper customerInfoMapper;
    @Override
    public CustomerQuota selectByCustomerId(String customerId) {
        return customerQuotaMapper.selectByCustomerId(customerId);
    }

    @Override
    public CustomerTotalQuota select4CustomerTotalQuota() {
        return customerTotalQuotaMapper.selectByCustomerId();
    }

    @Override
    public CustomerInfo selectCustomerInfo(String customerId) {
        return customerInfoMapper.selectByPrimaryKey(customerId);
    }

    @Override
    public CustomerQuotaVo selectVoByCustomerId(String customerId) {
        return customerQuotaMapper.selectVoByCustomerId(customerId);
    }

    @Override
    public CustomerQuotaVo selectVo4CustomerTotalQuota() {
        return customerTotalQuotaMapper.selectVo4CustomerTotalQuota();
    }
}
