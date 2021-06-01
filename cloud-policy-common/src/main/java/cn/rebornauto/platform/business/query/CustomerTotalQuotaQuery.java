package cn.rebornauto.platform.business.query;

import cn.rebornauto.platform.common.data.request.Query;
import lombok.Data;

@Data
public class CustomerTotalQuotaQuery extends Query {
    /**
     *  客户编号
     */
    private String customerId;
    /**
     *  客户名称
     */
    private String customerName;
}
