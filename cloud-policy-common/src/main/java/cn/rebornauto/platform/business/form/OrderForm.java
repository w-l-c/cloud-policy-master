package cn.rebornauto.platform.business.form;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import cn.rebornauto.platform.common.data.request.Form;
import lombok.Data;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 30, 2019 2:10:14 PM
 */
@Data
public class OrderForm extends Form{

	/**
	 *  订单号
	 */
	private Integer orderId;
	/**
	 * 申请日期  yyyy-MM-dd~yyyy-MM-dd
	 */
	private String applyTime;
	/**
	 *  客户名称 
	 */
	private String customerId;
	/**
	 *  客户名称 
	 */
	private String customerName;
	/**
	 * 代理人佣金
	 */
	private BigDecimal agentCommission;
	/**
	 * 代理人佣金
	 */
	private BigDecimal amount;
	/**
	 * 支付总笔数
	 */
	private Integer totalCount;
	/**
	 *  支付状态
	 */
	private Integer payStatus;
	/**
	 * 审批状态
	 */
	private Integer checkStatus;
	/**
	 *  申请人
	 */
	private String createoper;
	/**
	 *  审核人
	 */
	private String checkoper;
	/**
	 *  审核时间
	 */
	private Date checktime;
	/**
	 * 支付统计
	 */
	private String payStat;
	/**
	 * 保单号
	 */
	private String policyNo;
	/**
	 * 保费
	 */
	private BigDecimal policyAmount;
	/**
	 * 代理人姓名
	 */
	private String agentName;
	/**
	 * 出单日期 yyyy-MM-dd
	 */
	private String outtime;
	/**
	 * 卡号
	 */
	private String openBankNo;
	/**
	 * 身份证
	 */
	private String idcardno;
	/**
	 * excel  上传文件
	 */
	private String[] excefile;
	/**
	 * 支付渠道
	 */
	private Integer paymentChannelId;
	/**
	 * 代征主体
	 */
	private Integer daiZhengId;
	/**
	 * 审核拒绝原因
	 */
	private String remark;
	/**
	 * 交易流水号
	 */
	private String transactionFlowId;
	
	/**
	 * 卡号
	 */
	private String agentOpenBankNo;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private String responseTime;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private String completeTime;
	
	private int status;
	
	private String code;
	
	private String msg;
	/**
	 *  订单明细id
	 */
	private Integer orderDetailId;
	/**
	 * 合并id
	 */
	private String mergeId;
	
	/**
	 * 上传备注
	 */
	private String uploadRemark;
}
