package cn.rebornauto.platform.business.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class WxCustomerVo implements Serializable {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String  openid;
	
	  private String customerId;

}