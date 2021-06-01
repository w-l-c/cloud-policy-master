package cn.rebornauto.platform.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {
    public static boolean StringIsNumber(String str){
        Pattern pattern = Pattern.compile("-?[0-9]*.?[0-9]*");
        Matcher matcher = pattern.matcher((CharSequence) str);
        boolean result = matcher.matches();
        return result;
    }
    public static String checkUrl(String url1,String url2, String path) {
        if ((path+url1).equals(url2)) {
            return url1;
        }
        return url2;
    }
}
