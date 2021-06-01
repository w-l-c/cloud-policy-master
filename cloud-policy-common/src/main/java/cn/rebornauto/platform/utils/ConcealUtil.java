package cn.rebornauto.platform.utils;

/**
 * @author ligewei
 * @create 2019/04/03 19:29
 */
public class ConcealUtil {

    private static final String SYMBOL = "*";

    public static String concealName(String name){
        if(name==null||name.trim().equals("")){
            return "";
        }
        if(name.length()>2) {
            name = name.substring(0,1)+SYMBOL+SYMBOL;
        }else{
            name = name.substring(0,1)+SYMBOL;
        }
        return name;
    }

    public static String concealMobile(String mobile){
        if(mobile==null||mobile.trim().equals("")){
            return "";
        }
        if(mobile.length()!=11){
            return mobile.substring(0,4)+SYMBOL+SYMBOL+SYMBOL+SYMBOL+SYMBOL+SYMBOL+SYMBOL;
        }else{
            return mobile.substring(0,3)+SYMBOL+SYMBOL+SYMBOL+SYMBOL+mobile.substring(7,mobile.length());
        }
    }
}
