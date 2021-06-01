package cn.rebornauto.platform.business.vo;

import java.math.BigDecimal;

import lombok.Data;

/** Title: 展示vo
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 2, 2020 10:59:24 AM
 */
@Data
public class Stat {
	/**
     *  客户昵称
     */
//    private String customerNickname;
    /**
     *  金额
     */
    private BigDecimal amount = BigDecimal.ZERO;
    /**
     * 日或月
     */
    private String dayOrMonth;
}
