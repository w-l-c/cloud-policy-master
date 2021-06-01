package cn.rebornauto.platform.business.service.impl;

import cn.rebornauto.platform.business.dao.CustomerQuotaMapper;
import cn.rebornauto.platform.business.query.CustomerTotalQuotaQuery;
import cn.rebornauto.platform.business.service.CustomerTotalQuotaService;
import cn.rebornauto.platform.business.vo.CustomerTotalQuotaVo;
import cn.rebornauto.platform.common.data.request.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerTotalQuotaServiceImpl implements CustomerTotalQuotaService {
    @Autowired
    private CustomerQuotaMapper customerQuotaMapper;
    @Override
    public int count(CustomerTotalQuotaQuery query) {
        return customerQuotaMapper.count(query);
    }

    @Override
    public List<CustomerTotalQuotaVo> list(CustomerTotalQuotaQuery query, Pagination pagination) {
        return customerQuotaMapper.list(query,pagination);
    }

    @Override
    public List<CustomerTotalQuotaVo> listAll(CustomerTotalQuotaQuery query) {
        return customerQuotaMapper.listAll(query);

    }
}
