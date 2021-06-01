package cn.rebornauto.platform.pay.tonglian.model;

/**
 * @author ligewei
 * @create 2017/12/21 15:13
 */
public class SingleSignReq {

    private String SUBMIT_TIME;

    private String BANK_CODE;

    private String ACCOUNT_TYPE;

    private String ACCOUNT_NO;

    private String ACCOUNT_NAME;

    private String ACCOUNT_PROP;

    private String ID_TYPE;

    private String ID;

    private String TEL;

    public String getSUBMIT_TIME() {
        return SUBMIT_TIME;
    }

    public void setSUBMIT_TIME(String SUBMIT_TIME) {
        this.SUBMIT_TIME = SUBMIT_TIME;
    }

    public String getBANK_CODE() {
        return BANK_CODE;
    }

    public void setBANK_CODE(String BANK_CODE) {
        this.BANK_CODE = BANK_CODE;
    }

    public String getACCOUNT_TYPE() {
        return ACCOUNT_TYPE;
    }

    public void setACCOUNT_TYPE(String ACCOUNT_TYPE) {
        this.ACCOUNT_TYPE = ACCOUNT_TYPE;
    }

    public String getACCOUNT_NO() {
        return ACCOUNT_NO;
    }

    public void setACCOUNT_NO(String ACCOUNT_NO) {
        this.ACCOUNT_NO = ACCOUNT_NO;
    }

    public String getACCOUNT_NAME() {
        return ACCOUNT_NAME;
    }

    public void setACCOUNT_NAME(String ACCOUNT_NAME) {
        this.ACCOUNT_NAME = ACCOUNT_NAME;
    }

    public String getACCOUNT_PROP() {
        return ACCOUNT_PROP;
    }

    public void setACCOUNT_PROP(String ACCOUNT_PROP) {
        this.ACCOUNT_PROP = ACCOUNT_PROP;
    }

    public String getID_TYPE() {
        return ID_TYPE;
    }

    public void setID_TYPE(String ID_TYPE) {
        this.ID_TYPE = ID_TYPE;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String TEL) {
        this.TEL = TEL;
    }
}
