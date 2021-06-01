package cn.rebornauto.platform.sys.query;

import cn.rebornauto.platform.common.data.request.Query;
import lombok.Data;

@Data
public class SysRoleQuery extends Query{

    private Byte status;
    private String rolename;


}
