package cn.rebornauto.platform.sys.query;

import cn.rebornauto.platform.common.data.request.Query;
import lombok.Data;

@Data
public class SysLogQuery extends Query{

    private String username;
    private String sourceip;


}
