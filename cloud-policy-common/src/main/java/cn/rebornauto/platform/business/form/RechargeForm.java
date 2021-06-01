package cn.rebornauto.platform.business.form;

import cn.rebornauto.platform.common.data.request.Form;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RechargeForm extends Form {
    private Integer daiZhengId;
    private LocalDateTime applytime;
    private BigDecimal agentCommission;
    private BigDecimal realPayment;
    private String rechargeVoucherPicUrl;
    private LocalDateTime arrivetime;
    private Integer dataStatus;
    private String ip;
    private String customerId;
    public void setRechargeVoucherPicUrl(List rechargeVoucherPicUrl) {
        this.rechargeVoucherPicUrl = getPicUrl(rechargeVoucherPicUrl);

    }
    private static String getPicUrl(List picUrls) {
        if (picUrls != null) {
            return String.join(",", picUrls);
        }
        return "";
    }
}
