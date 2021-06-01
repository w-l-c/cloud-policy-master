package cn.rebornauto.platform.business.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerVo {
	private String id;
	private String customerName;
	private BigDecimal serviceRate;
	private String invoiceTitle;
	private String taxpayerNumber;
	private String receiver;
	private String customerOpenName;
	private String customerOpenBankName;
	private String customerOpenBankCode;
	private String customerOpenBankNo;
	private String entertime;
	private Integer taxpayerType;
	private String customerRegAddress;
	private String invoiceContent;
	private String customerMobile;
	private String customerAddress;
	private Integer daiZhengId;
	private String remark;
	private String qrCodeLogoImgPicUrl;
	private String qrCodeImgPicUrl;
	private String absoluteQrCodeLogoImgPicUrl;
	private String customerNickname;
	private String tel;
	private String customerSubOpenBankName;
	private String invoiceDemoUrl;

}
