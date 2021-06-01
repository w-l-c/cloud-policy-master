package cn.rebornauto.platform.business.query;

import cn.rebornauto.platform.common.data.request.Query;
import lombok.Data;

@Data
public class PostAddressQuery extends Query {
    /**
     *  客户编号
     */
    private String customerId;
    
}
