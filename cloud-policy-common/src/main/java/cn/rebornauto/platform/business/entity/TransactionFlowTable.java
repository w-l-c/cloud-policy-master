package cn.rebornauto.platform.business.entity;

import java.math.BigDecimal;

import lombok.Data;
import cn.rebornauto.platform.common.data.view.TableBody;

@Data
public class TransactionFlowTable extends TableBody {
    
    /**
     * 支付总笔数
     */
    private Integer totalCountQuery;

    /**
     * 累计付款总金额（元）
     */
    private BigDecimal totalAmountQuery;
    
    /**
     * 财务千分位
     */
    private String totalAmountQueryFin;
    
    public static TransactionFlowTable factory(){
        return new TransactionFlowTable();
    }
}