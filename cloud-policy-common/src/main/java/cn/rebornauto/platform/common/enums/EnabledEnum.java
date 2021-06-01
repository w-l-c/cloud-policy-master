package cn.rebornauto.platform.common.enums;

import cn.rebornauto.platform.common.SysConst;

import java.util.HashMap;
import java.util.Map;

/**
 * 账户启用状态字典
 */
public enum EnabledEnum {

    ENABLE(SysConst.Enable,"启用"),

    DISABLE(SysConst.Disable,"禁用");

    private Byte code;

    private String value;

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    EnabledEnum(Byte code, String value){

        this.code = code;

        this.value = value;

    }

    public static Map<Byte , String> getMap(){
        Map<Byte,String> map = new HashMap<>();
        for(EnabledEnum e:EnabledEnum.values()){
            map.put(e.getCode(),e.getValue());
        }
        return map;
    }
}
