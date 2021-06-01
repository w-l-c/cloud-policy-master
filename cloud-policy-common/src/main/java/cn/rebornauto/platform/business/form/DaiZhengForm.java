package cn.rebornauto.platform.business.form;

import cn.rebornauto.platform.common.data.request.Form;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DaiZhengForm extends Form {
    private String daiZhengName;
    private String daiZhengCompanyName;
    private String  daiZhengSimpleCompanyName;
    private String   cityAdcode;
    private String   provinceAdcode;
    private String daiZhengAddress;
    private String logo;
    private BigDecimal personalTax;
    private BigDecimal valueAddedTax;
    private BigDecimal extraTax;
    private String daiZhengOpenBank;
    private String daiZhengOpenName;
    private String daiZhengOpenBankNo;
    private Integer dataStatus;
    /**
     * 联系人
     */
    private String daiZhengLinkMan;
    /**
     * 联系手机
     */
    private String daiZhengLinkMobile;
    
    /**
     * 签章账号
     */
    private String account;

    /**
     * 企业印章名称
     */
    private String imageName;
    /**
     * 企业印章服带域名路径
     */
    private String sealImgPicUrl;
    
    /**
     * 企业印章服务器路径
     */
    //private String sealImgPicPath;
    /**
     * 工商注册号
     */
    private String regCode;
    
    /**
     *组织机构代码
     */
    private String   orgCode;
    
    /**
     *税务登记证号
     */
    private String   taxCode;  
    
    /**
     * 法定代表人姓名
     */
    private String legalPerson;
    
    /**
     *法定代表人证件类型 0-居民身份证 1-护照 B-港澳居民往来内地通行证 C-台湾居民来往大陆通行证  E-户口簿  F-临时居民身份证
     */
    private String   legalPersonIdentityType;
    
    /**
     *法定代表人证件号 
     */
    private String   legalPersonIdentity;
 
    
    public void setSealImgPicUrl(List sealImgPicUrls) {
        this.sealImgPicUrl = getPicUrl(sealImgPicUrls);
    }

    
    public void setUploadSealImgPicUrl(String UploadSealImgPicUrl) {
        this.sealImgPicUrl = UploadSealImgPicUrl;
    }
    
    public void setLogo(List logo) {
        this.logo = getPicUrl(logo);
    }
    private static String getPicUrl(List picUrls) {
        if (picUrls != null) {
            return String.join(",", picUrls);
        }
        return "";
    }
}
