package cn.rebornauto.platform.pay.sandpay.enums;


/** Title: 杉徳接口枚举
 * Description: 
 * Company: 
 * @author kgc
 * @date Nov 26, 2019 5:23:56 PM
 */
public enum SandResultFlagEnums {

	/**
	 * 成功
	 */
	RESULT_FLAG_0("0","成功"),
	/**
	 * 失败
	 */
	RESULT_FLAG_1("1","失败"),
	/**
	 * 处理中(等银行返回明确结果)
	 */
	RESULT_FLAG_2("2","处理中"),
	;

    private String code;

    private String msg;
    
    public static String getMsg(String code){
        for(SandResultFlagEnums e: SandResultFlagEnums.values()){
            if(e.code.equals(code)){
                return e.msg;
            }
        }
        return "";
    }
    
    private SandResultFlagEnums(String code, String msg){
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
