package cn.rebornauto.platform.wx.message.req;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ligewei
 * @create 2019/04/04 15:05
 */
@Data
public class AuctionMsg {

    private LocalDateTime bidTime;

    private String modelName;

    private Integer isWinner;

    private String openid;

    private String status;
}
