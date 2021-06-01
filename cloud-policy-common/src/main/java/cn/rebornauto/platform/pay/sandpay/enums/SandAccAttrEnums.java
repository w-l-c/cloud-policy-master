package cn.rebornauto.platform.pay.sandpay.enums;


/** Title: 杉徳接口枚举
 * Description: 
 * Company: 
 * @author kgc
 * @date Nov 26, 2019 5:23:56 PM
 */
public enum SandAccAttrEnums {

	/**
	 * 对私
	 */
	ACC_ATTR_0("0","对私"),
	/**
	 * 对公
	 */
	ACC_ATTR_1("1","对公"),
	;

    private String code;

    private String msg;
    
    public static String getMsg(String code){
        for(SandAccAttrEnums e: SandAccAttrEnums.values()){
            if(e.code.equals(code)){
                return e.msg;
            }
        }
        return "";
    }
    
    private SandAccAttrEnums(String code, String msg){
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
