package cn.rebornauto.platform.pay.tonglian.entity;

import java.io.Serializable;

import lombok.Data;

/** Title: 通联支付对应的必要参数对象
 * Description: 
 * Company: 
 * @author kgc
 * @date May 2, 2019 9:39:57 AM
 */
@Data
public class TongLianInfo implements Serializable{

	/**
	 * 支付请求地址
	 */
	private String url;
	/**
	 * 测试春律商户号
	 */
	private String merchantId;
	/**
	 * 商户密码
	 */
	private String pfxPassword;
	/**
	 * 账户密码
	 */
	private String password;
	/**
	 * 账户名
	 */
	private String userName;
	/**
	 * 通联公钥文件名
	 */
	private String tltcerPath;
	/**
	 * 私钥文件名
	 */
	private String pfxPath;
	/**
	 * 代付业务代码
	 */
	private String businessCodeDf;
	/**
	 * 放款描述
	 */
	private String rightLoanPrefix;
	/**
	 * 下载对账文件地址
	 */
	private String downloadUrl;
}
