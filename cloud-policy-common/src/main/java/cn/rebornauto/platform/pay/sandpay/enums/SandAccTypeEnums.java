package cn.rebornauto.platform.pay.sandpay.enums;


/** Title: 杉徳接口枚举
 * Description: 
 * Company: 
 * @author kgc
 * @date Nov 26, 2019 5:23:56 PM
 */
public enum SandAccTypeEnums {

	/**
	 * 公司账户
	 */
	ACC_TYPE_3("3","公司账户"),
	/**
	 * 银行卡
	 */
	ACC_TYPE_4("4","银行卡"),
	;

    private String code;

    private String msg;
    
    public static String getMsg(String code){
        for(SandAccTypeEnums e: SandAccTypeEnums.values()){
            if(e.code.equals(code)){
                return e.msg;
            }
        }
        return "";
    }
    
    private SandAccTypeEnums(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
	public static boolean contain(String code){
        return !"-".equals(code);
    }
}
