package cn.rebornauto.platform.business.query;

import cn.rebornauto.platform.common.data.request.Query;
import lombok.Data;

/**
 * @author ligewei
 * @create 2020/06/08 10:20
 */
@Data
public class MissionQuery extends Query {

    private String customerId;

    private String missionNo;

    private String customerName;

    private String releasetime;

    public void setReleasetime(String releasetime) {
        this.releasetime = releasetime;
        if (releasetime != null && !"".equals(releasetime)) {
            String[] split = releasetime.split("~");
            this.startTime = split[0];
            this.endTime = split[1];
        }
    }

    private String startTime;

    private String endTime;

    private Integer status;
}
