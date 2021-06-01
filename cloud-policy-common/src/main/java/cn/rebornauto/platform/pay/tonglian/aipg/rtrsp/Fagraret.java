package cn.rebornauto.platform.pay.tonglian.aipg.rtrsp;
/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 3, 2018 4:45:25 PM
 */
public class Fagraret {

	private String RET_CODE  ;
	
	private String ERR_MSG   ;
	
	private String ISSENDSMS;
	/**
	 * 协议号
	 */
	private String AGRMNO;
	/**
	 * 完成日期
	 */
	private String SETTLE_DAY;
	/**
	 * 卡号后4位
	 */
	private String ACCT_SUFFIX;

	public String getRET_CODE() {
		return RET_CODE;
	}

	public void setRET_CODE(String rET_CODE) {
		RET_CODE = rET_CODE;
	}

	public String getERR_MSG() {
		return ERR_MSG;
	}

	public void setERR_MSG(String eRR_MSG) {
		ERR_MSG = eRR_MSG;
	}

	public String getISSENDSMS() {
		return ISSENDSMS;
	}

	public void setISSENDSMS(String iSSENDSMS) {
		ISSENDSMS = iSSENDSMS;
	}

	public String getAGRMNO() {
		return AGRMNO;
	}

	public void setAGRMNO(String aGRMNO) {
		AGRMNO = aGRMNO;
	}

	public String getSETTLE_DAY() {
		return SETTLE_DAY;
	}

	public void setSETTLE_DAY(String sETTLE_DAY) {
		SETTLE_DAY = sETTLE_DAY;
	}

	public String getACCT_SUFFIX() {
		return ACCT_SUFFIX;
	}

	public void setACCT_SUFFIX(String aCCT_SUFFIX) {
		ACCT_SUFFIX = aCCT_SUFFIX;
	}

	@Override
	public String toString() {
		return "Fagraret [RET_CODE=" + RET_CODE + ", ERR_MSG=" + ERR_MSG
				+ ", ISSENDSMS=" + ISSENDSMS + ", AGRMNO=" + AGRMNO
				+ ", SETTLE_DAY=" + SETTLE_DAY + ", ACCT_SUFFIX=" + ACCT_SUFFIX
				+ "]";
	}
	
	
}
