package cn.rebornauto.platform.common.enums;

import cn.rebornauto.platform.common.Const;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据状态字典
 */
public enum DeletedEnum {

    NORMAL(Const.Status_Normal,"正常"),

    DEL(Const.Status_Del,"已删除");

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

    DeletedEnum(Byte code, String value){

        this.code = code;

        this.value = value;

    }

    public static Map<Byte , String> getMap(){
        Map<Byte,String> map = new HashMap<>();
        for(DeletedEnum e:DeletedEnum.values()){
            map.put(e.getCode(),e.getValue());
        }
        return map;
    }
}
