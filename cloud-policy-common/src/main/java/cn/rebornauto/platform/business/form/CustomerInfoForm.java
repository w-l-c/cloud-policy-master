package cn.rebornauto.platform.business.form;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import cn.rebornauto.platform.common.data.request.Form;


@Data
public class CustomerInfoForm extends  Form{
	private String customerName;
	private BigDecimal serviceRate;
	private String invoiceTitle;
	private Integer taxpayerType;
	private String taxpayerNumber;
	private String customerRegAddress;
	private String customerOpenName;
	private String customerOpenBankCode;
	private String customerOpenBankNo;
	private String invoiceContent;
	private String receiver;
	private String customerMobile;
	private String customerAddress;
	private Integer daiZhengId;
	private String remark;	
	private String qrCodeLogoImgPicUrl;	
	//private String logoPath;	
	private String customerNickname;
	private String customerSubOpenBankName;
	
	private String tel;
	
	private String invoiceDemoUrl;	
	
	public void setQrCodeLogoImgPicUrl(List qrCodeLogoImgPicUrls) {
	    this.qrCodeLogoImgPicUrl = getPicUrl(qrCodeLogoImgPicUrls);
	}

	private String getPicUrl(List picUrls) {
	    return String.join(",", picUrls);
	}   

}
