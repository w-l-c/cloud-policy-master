package cn.rebornauto.platform.pay.tonglian.Enum;

/**
 * @author ligewei
 * @date 2017年11月21日
 */
public enum PayStatusEnum {
	/**
	 * 成功
	 */
    SUCCESS("2","成功")
    /**
     * 处理中
     */
    ,IN_PROCESSED("4","处理中")
    /**
     * 失败
     */
    ,ERROR("3","失败")
    /**
     * 待人工干预
     */
    ,WAIT_HUMAN_DEAL("5","待人工干预")
    /**
     * 支付关闭
     */
    ,CLOSED("6","支付关闭");

    private String code;

    private String msg;

    public static String getMsg(String code){
        for(PayStatusEnum e: PayStatusEnum.values()){
            if(e.code.equals(code)){
                return e.msg;
            }
        }
        return "-";
    }

    private PayStatusEnum(String code, String msg){
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

    public static String getCode(String code) {
        for(PayStatusEnum e: PayStatusEnum.values()){
            if(e.msg.equals(code)){
                return e.code;
            }
        }
        return "-";
    }
}
