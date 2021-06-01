package cn.rebornauto.platform.business.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DaiZhengInfoVo  {

	private Integer id;

	/**
	 * 代征主体名称
	 */
	private String daiZhengName;

	/**
	 * 公司名称
	 */
	private String daiZhengCompanyName;

	/**
	 * 公司简称
	 */
	private String daiZhengSimpleCompanyName;

	/**
	 * 城市code
	 */
	private String cityAdcode;

	/**
	 * 省code
	 */
	private String provinceAdcode;

	/**
	 * 公司地址
	 */
	private String daiZhengAddress;

	/**
	 * logo
	 */
	private String logo;

	/**
	 * 联系人
	 */
	private String daiZhengLinkMan;

	/**
	 * 联系电话
	 */
	private String daiZhengLinkMobile;

	/**
	 * 联系邮箱
	 */
	private String daiZhengLinkEmail;

	/**
	 * 个税
	 */
	private BigDecimal personalTax;

	/**
	 * 增值税
	 */
	private BigDecimal valueAddedTax;

	/**
	 * 附加税
	 */
	private BigDecimal extraTax;

	/**
	 * 开户行
	 */
	private String daiZhengOpenBank;

	/**
	 * 账户名称
	 */
	private String daiZhengOpenName;

	/**
	 * 银行卡号
	 */
	private String daiZhengOpenBankNo;

	/**
	 * 累计充值金额（充值进来的费用总和）
	 */
	private BigDecimal rechargeAmount;

	/**
	 * 付款总额
	 */
	private BigDecimal payAmount;
	/**
	 * 付款总额  千分位
	 */
	private String payAmountFin;

	/**
	 * 账户余额
	 */
	private BigDecimal leftAmount;
	
	/**
	 * 账户余额
	 */
	private String leftAmountFin;

	/**
	 * 企业印章名称
	 */
	private String imageName;

	/**
	 * 企业印章图片
	 */

	private String sealImgPicUrl;

	/**
	 * 签章账号
	 */

	private String account;

	/**
	 * 工商注册号
	 */

	private String regCode;

	/**
	 * 组织机构代码
	 */

	private String orgCode;

	/**
	 * 税务登记证号
	 */

	private String taxCode;

	/**
	 * 法定代表人姓名
	 */

	private String legalPerson;

	/**
	 * 法定代表人证件类型 0-居民身份证 1-护照 B-港澳居民往来内地通行证 C-台湾居民来往大陆通行证 E-户口簿 F-临时居民身份证
	 */

	private String legalPersonIdentityType;

	private String legalPersonIdentity;

	private String userType;

	private Integer dataStatus;

	private String remark;

	private String createoper;

	private LocalDateTime createtime;

	private String modifyoper;

	private LocalDateTime modifytime;

	private String status;
}