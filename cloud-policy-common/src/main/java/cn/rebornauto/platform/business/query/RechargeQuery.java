package cn.rebornauto.platform.business.query;

import cn.rebornauto.platform.common.data.request.Query;
import lombok.Data;

@Data
public class RechargeQuery extends Query {
    /**
     *  客户编号
     */
    private String customerId;
    /**
     *  客户名称
     */
    private String customerName;
    
    private Integer recharge;
    /**
     *  到账日期
     */
    private String arrivetime;

    private String startTime;

    private String endTime;
    public void setArriveTime(String arrivetime) {
        this.arrivetime = arrivetime;
        if (arrivetime != null && !"".equals(arrivetime)) {
            String[] split = arrivetime.split("~");
            this.startTime = split[0];
            this.endTime = split[1];
        }
    }
    
    /**
     *  申请日期
     */
    private String applytime;

    private String applyStartTime;

    private String applyEndTime;
    public void setApplytime(String applytime) {
        this.applytime = applytime;
        if (applytime != null && !"".equals(applytime)) {
            String[] split = applytime.split("~");
            this.applyStartTime = split[0];
            this.applyEndTime = split[1];
        }
    }
    
    /**
     *  审核日期
     */
    private String checktime;

    private String checkStartTime;

    private String checkEndTime;
    public void setChecktime(String checktime) {
        this.checktime = checktime;
        if (checktime != null && !"".equals(checktime)) {
            String[] split = checktime.split("~");
            this.checkStartTime = split[0];
            this.checkEndTime = split[1];
        }
    }
    
}
