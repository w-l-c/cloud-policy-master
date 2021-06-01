package cn.rebornauto.platform.business.query;

import cn.rebornauto.platform.common.data.request.Query;
import lombok.Data;

@Data
public class BusiLogQuery extends Query {
    private String customerId;
    private String createtime;
    private String customerName;
    private String startTime;

    private String endTime;
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
        if (createtime != null && !"".equals(createtime)) {
            String[] split = createtime.split("~");
            this.startTime = split[0];
            this.endTime = split[1];
        }
    }
}
