package cn.rebornauto.platform.pay.sandpay.entity;

import java.io.Serializable;

import lombok.Data;

/** Title: 杉徳支付对应的必要参数对象
 * Description: 
 * Company: 
 * @author kgc
 * @date May 2, 2019 9:39:57 AM
 */
@Data
public class SandInfo implements Serializable{

	/**
	 * 支付请求地址
	 */
	private String sandsdkUrl;
	/**
	 * 商户ID
	 */
	private String sandsdkMid;
	/**
	 * 平台商户ID
	 */
	private String sandsdkPlMid;
	/**
	 * 私钥地址
	 */
	private String sandsdkSignCertPath;
	/**
	 * 私钥密码
	 */
	private String sandsdkSignCertPwd;
	/**
	 * 杉徳公钥
	 */
	private String sandsdkSandCertPath;
}
