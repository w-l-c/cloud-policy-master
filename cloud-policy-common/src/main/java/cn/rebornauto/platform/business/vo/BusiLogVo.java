package cn.rebornauto.platform.business.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BusiLogVo {
    private Integer id;
    private String customerId;
    private String ip;
    private String optionContent;
    private String createoper;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createtime;
    private String customerName;
    private String remark;
    private Integer dataStatus;
}
