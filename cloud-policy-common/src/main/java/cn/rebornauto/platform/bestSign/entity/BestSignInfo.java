package cn.rebornauto.platform.bestSign.entity;

import java.io.Serializable;

import lombok.Data;
@Data
public class BestSignInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 上上签开发者ID
	 */
	private String developerId;
	/**
	 * 上上签私钥
	 */
	private String privateKey;
	/**
	 * 上上签接口地址
	 */
	private String serverHost;
	/**
	 * 上上签用户唯一标识
	 */
	private String account;
	
	/**
	 * 字体文件
	 */
	private String simsun;
	/**
	 * 合同模板路径
	 */
	private String modelPdfPath;
	
	
	/**
	 * 合同路径
	 */
	private String pdfPath;
	
	/**
	 * 签章完成pdf路径
	 */
	private String downloadPdfPath;	
	
	/**
	 * 签章完成pdfZIP包路径
	 */
	private String downloadPdfZipPath;
	
	
	/**
	 * 上上签回调接口
	 */
	private String callbackInterface;

}
