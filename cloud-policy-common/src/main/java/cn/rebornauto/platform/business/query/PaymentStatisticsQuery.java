package cn.rebornauto.platform.business.query;

import cn.rebornauto.platform.common.data.request.Query;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class PaymentStatisticsQuery extends Query {
    private String month;
    private String agentName;
    private String customerId;

    public void setMonth(String month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM");
        if (month!= null && !"".equals(month)) {
            try {
                Date parse = sdf.parse(month);
                this.month = sdf1.format(parse);
            } catch (ParseException e) {
                this.month=sdf1.format(new Date());
            }
        } else {
            this.month=sdf1.format(new Date());
        }

    }
}
