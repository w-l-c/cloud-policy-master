package cn.rebornauto.platform.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;

public class PasswordHander {
    public static String encodedPassword(String password, String salt) {
        SimpleHash hash = new SimpleHash("md5", password, salt, 2);
        String encodedPassword = hash.toHex();
        return encodedPassword;
    }
}
