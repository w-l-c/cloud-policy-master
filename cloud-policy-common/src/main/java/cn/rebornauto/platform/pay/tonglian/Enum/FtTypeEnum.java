package cn.rebornauto.platform.pay.tonglian.Enum;

/**
 * 金融交易操作类型枚举
 *
 */
public enum FtTypeEnum {
    RIGHT_LOAN(1,"债权放款");

    private Integer code;

    private String msg;

    private String mainType;

    private String childType;
    
    private String childType2;

    public static String getMsg(Integer code){
        for(FtTypeEnum e:FtTypeEnum.values()){
            if(e.code.equals(code)){
                return e.msg;
            }
        }
        return "-";
    }
    public static String getMainType(Integer code){
        for(FtTypeEnum e:FtTypeEnum.values()){
            if(e.code.equals(code)){
                return e.mainType;
            }
        }
        return "-";
    }
    public static String getChildType(Integer code){
        for(FtTypeEnum e:FtTypeEnum.values()){
            if(e.code.equals(code)){
                return e.childType;
            }
        }
        return "-";
    }
    
    public static String getChildType2(Integer code){
        for(FtTypeEnum e:FtTypeEnum.values()){
            if(e.code.equals(code)){
                return e.childType2;
            }
        }
        return "-";
    }

    private FtTypeEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    private FtTypeEnum(Integer code,String msg,String mainType,String childType){
        this.code = code;
        this.msg = msg;
        this.mainType = mainType;
        this.childType = childType;
    }
    
    private FtTypeEnum(Integer code,String msg,String mainType,String childType,String childType2){
        this.code = code;
        this.msg = msg;
        this.mainType = mainType;
        this.childType = childType;
        this.childType2 = childType2;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public String getChildType() {
        return childType;
    }

    public void setChildType(String childType) {
        this.childType = childType;
    }

    public String getChildType2() {
		return childType2;
	}
    
	public void setChildType2(String childType2) {
		this.childType2 = childType2;
	}
	public static boolean contain(String code){
        return !"-".equals(code);
    }

}
