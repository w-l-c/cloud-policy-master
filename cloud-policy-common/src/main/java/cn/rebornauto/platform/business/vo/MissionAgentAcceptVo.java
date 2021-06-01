package cn.rebornauto.platform.business.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ligewei
 * @create 2020/06/16 16:48
 */
@Data
public class MissionAgentAcceptVo {

    private Integer id;
    private String missionNo;
    private String missionName;
    private String missionRemark;
    private Integer agentId;
    private LocalDateTime completetime;
    private BigDecimal amount;
    private Integer dataStatus;
    private String createoper;
    private LocalDateTime createtime;
    private String modifyoper;
    private LocalDateTime modifytime;
}
