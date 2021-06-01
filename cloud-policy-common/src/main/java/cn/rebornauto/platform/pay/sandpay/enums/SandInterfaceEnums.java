package cn.rebornauto.platform.pay.sandpay.enums;


/** Title: 杉徳接口枚举
 * Description: 
 * Company: 
 * @author kgc
 * @date Nov 26, 2019 5:23:56 PM
 */
public enum SandInterfaceEnums {

	/**
	 * 杉徳账户余额查询接口
	 */
	QUERY_BALANCE("queryBalance","杉徳账户余额查询接口"),
	/**
	 * 杉徳订单查询接口
	 */
	QUERY_ORDER("queryOrder","杉徳订单查询接口"),
	/**
	 * 杉徳实时代付接口
	 */
	AGENT_PAY("agentpay","杉徳实时代付接口"),
	;

    private String code;

    private String msg;
    
    public static String getMsg(String code){
        for(SandInterfaceEnums e: SandInterfaceEnums.values()){
            if(e.code.equals(code)){
                return e.msg;
            }
        }
        return "";
    }
    
    private SandInterfaceEnums(String code, String msg){
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
