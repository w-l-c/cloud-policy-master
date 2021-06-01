package cn.rebornauto.platform.business.service;

import cn.rebornauto.platform.business.query.CustomerTotalQuotaQuery;
import cn.rebornauto.platform.business.vo.CustomerTotalQuotaVo;
import cn.rebornauto.platform.common.data.request.Pagination;

import java.util.List;

public interface CustomerTotalQuotaService {
    int count(CustomerTotalQuotaQuery query);

    List<CustomerTotalQuotaVo> list(CustomerTotalQuotaQuery query, Pagination pagination);

    List<CustomerTotalQuotaVo> listAll(CustomerTotalQuotaQuery query);
}
