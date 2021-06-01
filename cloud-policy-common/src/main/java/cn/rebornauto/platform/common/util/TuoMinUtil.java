package cn.rebornauto.platform.common.util;

import org.apache.commons.lang.StringUtils;

/** Title: 脱敏工具
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 8, 2020 10:35:59 AM
 */
public class TuoMinUtil {
	// 手机号码前三后四脱敏
    public static String mobileEncrypt(String mobile) {
        if (StringUtils.isEmpty(mobile) || (mobile.length() != 11)) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    //身份证前三后四脱敏
    public static String idcardEncrypt(String idcard) {
        if (StringUtils.isEmpty(idcard) || (idcard.length() < 8)) {
            return idcard;
        }
        return idcard.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
    }

    //护照前2后3位脱敏，护照一般为8或9位
    public static String idPassport(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        return id.substring(0, 2) + new String(new char[id.length() - 5]).replace("\0", "*") + id.substring(id.length() - 3);
    }
    
    // bankNo码前3后4脱敏
    public static String bankNoEncrypt(String bankNo) {
        if (StringUtils.isEmpty(bankNo)) {
            return bankNo;
        }
        return bankNo.substring(0, 3) + "**********" + bankNo.substring(bankNo.length() - 4);
    }
}
