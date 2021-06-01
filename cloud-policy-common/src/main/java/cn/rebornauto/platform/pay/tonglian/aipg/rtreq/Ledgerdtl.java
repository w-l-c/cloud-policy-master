package cn.rebornauto.platform.pay.tonglian.aipg.rtreq;
/** Title: 分序账号明细
 * Description: 
 * Company: 
 * @author kgc
 * @date Jan 3, 2018 10:36:02 AM
 */
public class Ledgerdtl {

	/**
	 * 分账记录序号
	 */
	private String SN;
	/**
	 * 商户ID
	 */
	private String MERCHANT_ID;
	/**
	 * 整数，单位分
	 */
	private String AMOUNT;
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANT_ID) {
		MERCHANT_ID = mERCHANT_ID;
	}
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	
	
}
