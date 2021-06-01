package cn.rebornauto.platform.pay.tonglian.model;

/**
 * @author ligewei
 * @date 2017年11月15日
 */
public class SingleTranReq {

    /**
     * 交易流水表标识
     */
    private String transactionFlowId;

    /**
     * 客户姓名
     */
    private String ACCOUNT_NAME;

    /**
     * 银行卡号
     */
    private String ACCOUNT_NO;

    /**
     * 交易金额
     */
    private String AMOUNT;

    /**
     * 电话
     */
    private String TEL;

    /**
     * 银行代码
     */
    private String BANK_CODE;

    /**
     * 业务代码
     */
    private String BUSINESS_CODE;

    /**
     * 账户属性(0 : 个人 1 : 公司)
     */
    private String ACCOUNT_PROP;

    /**
     * 账户类型(00:银行卡,01:存折,02:信用卡)
     */
    private String ACCOUNT_TYPE;

    /**
     * 提交时间 : 请用DateUtil.getAllTime();
     */
    private String SUBMIT_TIME;

    private String PROVINCE;

    private String CITY;
    
    private String ID_TYPE;      //开户证件类型
    
	private String ID;      //证件号
    
    /**
     * 分账记录序号
     */
    private String FX_SN;
    /**
     * 分序账户
     */
    private String FX_MERCHANT_ID;
    /**
     * 分序金额
     */
    private String FX_AMOUNT;
    
    /**
     * 快捷支付签约协议号
     */
    private String AGRMNO;
    /**
     * 支付行号
     */
    private String UNION_BANK;
    /**
     * 开户行名称(开户行详细名称，也叫网点)
     */
    private String BANK_NAME;

    public String getSUBMIT_TIME() {
        return SUBMIT_TIME;
    }

    public void setSUBMIT_TIME(String SUBMIT_TIME) {
        this.SUBMIT_TIME = SUBMIT_TIME;
    }

    public String getTransactionFlowId() {
        return transactionFlowId;
    }

    public void setTransactionFlowId(String transactionFlowId) {
        this.transactionFlowId = transactionFlowId;
    }

    public String getACCOUNT_NAME() {
        return ACCOUNT_NAME;
    }

    public void setACCOUNT_NAME(String ACCOUNT_NAME) {
        this.ACCOUNT_NAME = ACCOUNT_NAME;
    }

    public String getACCOUNT_NO() {
        return ACCOUNT_NO;
    }

    public void setACCOUNT_NO(String ACCOUNT_NO) {
        this.ACCOUNT_NO = ACCOUNT_NO;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String TEL) {
        this.TEL = TEL;
    }

    public String getBANK_CODE() {
        return BANK_CODE;
    }

    public void setBANK_CODE(String BANK_CODE) {
        this.BANK_CODE = BANK_CODE;
    }

    public String getBUSINESS_CODE() {
        return BUSINESS_CODE;
    }

    public void setBUSINESS_CODE(String BUSINESS_CODE) {
        this.BUSINESS_CODE = BUSINESS_CODE;
    }

    public String getACCOUNT_PROP() {
        return ACCOUNT_PROP;
    }

    public void setACCOUNT_PROP(String ACCOUNT_PROP) {
        this.ACCOUNT_PROP = ACCOUNT_PROP;
    }

    public String getACCOUNT_TYPE() {
        return ACCOUNT_TYPE;
    }

    public void setACCOUNT_TYPE(String ACCOUNT_TYPE) {
        this.ACCOUNT_TYPE = ACCOUNT_TYPE;
    }

    public String getPROVINCE() {
        return PROVINCE;
    }

    public void setPROVINCE(String PROVINCE) {
        this.PROVINCE = PROVINCE;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getFX_SN() {
		return FX_SN;
	}

	public void setFX_SN(String fX_SN) {
		FX_SN = fX_SN;
	}

	public String getFX_MERCHANT_ID() {
		return FX_MERCHANT_ID;
	}

	public void setFX_MERCHANT_ID(String fX_MERCHANT_ID) {
		FX_MERCHANT_ID = fX_MERCHANT_ID;
	}

	public String getFX_AMOUNT() {
		return FX_AMOUNT;
	}

	public void setFX_AMOUNT(String fX_AMOUNT) {
		FX_AMOUNT = fX_AMOUNT;
	}
	
	public String getID_TYPE() {
		return ID_TYPE;
	}

	public void setID_TYPE(String iD_TYPE) {
		ID_TYPE = iD_TYPE;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getAGRMNO() {
		return AGRMNO;
	}

	public void setAGRMNO(String aGRMNO) {
		AGRMNO = aGRMNO;
	}

	public String getUNION_BANK() {
		return UNION_BANK;
	}

	public void setUNION_BANK(String uNION_BANK) {
		UNION_BANK = uNION_BANK;
	}

	public String getBANK_NAME() {
		return BANK_NAME;
	}

	public void setBANK_NAME(String bANK_NAME) {
		BANK_NAME = bANK_NAME;
	}

	@Override
	public String toString() {
		return "SingleTranReq [transactionFlowId=" + transactionFlowId
				+ ", ACCOUNT_NAME=" + ACCOUNT_NAME + ", ACCOUNT_NO="
				+ ACCOUNT_NO + ", AMOUNT=" + AMOUNT + ", TEL=" + TEL
				+ ", BANK_CODE=" + BANK_CODE + ", BUSINESS_CODE="
				+ BUSINESS_CODE + ", ACCOUNT_PROP=" + ACCOUNT_PROP
				+ ", ACCOUNT_TYPE=" + ACCOUNT_TYPE + ", SUBMIT_TIME="
				+ SUBMIT_TIME + ", PROVINCE=" + PROVINCE + ", CITY=" + CITY
				+ ", ID_TYPE=" + ID_TYPE + ", ID=" + ID + ", FX_SN=" + FX_SN
				+ ", FX_MERCHANT_ID=" + FX_MERCHANT_ID + ", FX_AMOUNT="
				+ FX_AMOUNT + ", AGRMNO=" + AGRMNO+ ", UNION_BANK=" + UNION_BANK+ ", BANK_NAME=" + BANK_NAME + "]";
	}

	
}
