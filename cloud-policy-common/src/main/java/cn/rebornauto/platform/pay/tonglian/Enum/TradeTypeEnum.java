package cn.rebornauto.platform.pay.tonglian.Enum;

/**
 * @author ligewei
 * @date 2017年11月20日
 */
public enum TradeTypeEnum {
    SINGLE_REPAYMENT("100014","单笔代付","0"),SINGLE_COLLECTION("100011","单笔代收","1","4"),
    SIGN("211005","四要素签约",""),
    MULTI_REPAYMENT("100002","批量代付","2"),MULTI_COLLECTION("100001","批量代收","3"),
    QUICK_PAYMENT_SMS("310001","快捷支付短信触发",""),
    QUICK_PAYMENT_SIGN("310002","快捷支付五要素签约",""),
    QUICK_PAYMENT("310011","快捷支付代收","1","4"),
    QUERY_MERCHANT_INFO("300000","账户信息查询",""),
    /**
     * 帐务明细查询
     */
    QUERY_MERCHANT_RECHARGE_DETAIL("300005","帐务明细查询","");

    private String code;

    private String msg;

    private String tradeCode;

    private String childrenCode;

    public static String getMsg(String code){
        for(TradeTypeEnum e: TradeTypeEnum.values()){
            if(e.code.equals(code)){
                return e.msg;
            }
        }
        return "-";
    }
    public static String getTradeCode(String code){
        for(TradeTypeEnum e: TradeTypeEnum.values()){
            if(e.code.equals(code)){
                return e.tradeCode;
            }
        }
        return "-";
    }
    public static String getChildrenCode(String code){
        for(TradeTypeEnum e: TradeTypeEnum.values()){
            if(e.code.equals(code)){
                return e.childrenCode;
            }
        }
        return "-";
    }

    private TradeTypeEnum(String code, String msg,String tradeCode){
        this.code = code;
        this.msg = msg;
        this.tradeCode = tradeCode;
    }
    private TradeTypeEnum(String code, String msg,String tradeCode,String childrenCode){
        this.code = code;
        this.msg = msg;
        this.tradeCode = tradeCode;
        this.childrenCode = childrenCode;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
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

    public String getChildrenCode() {
        return childrenCode;
    }

    public void setChildrenCode(String childrenCode) {
        this.childrenCode = childrenCode;
    }

    public static boolean contain(String code){
        return !"-".equals(code);
    }
}
