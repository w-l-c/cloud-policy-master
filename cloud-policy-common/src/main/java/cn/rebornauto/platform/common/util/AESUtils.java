package cn.rebornauto.platform.common.util;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/** 
 * 编码工具类 
 * 1.将byte[]转为各种进制的字符串 
 * 2.base 64 encode 
 * 3.base 64 decode 
 * 4.获取byte[]的md5值 
 * 5.获取字符串md5值 
 * 6.结合base64实现md5加密 
 * 7.AES加密 
 * 8.AES加密为base 64 code 
 * 9.AES解密 
 * 10.将base 64 code AES解密 
 * @author uikoo9 
 * @version 0.0.7.20140601 
 */
public class AESUtils {
	
	private static final String key = "2s5i3ji35ca8d50n";

	public static void main(String[] args) throws Exception {
//		String key = "2s5i3ji35ca8d50n";
		//		System.out.println("加密密钥和解密密钥：" + key);
		//		System.out.println("加密前：" + content);
		//

//		String aa = aesEncrypt("{\"interNumber\":\"10000002\",\"token\":\"dgsdfHYWFadfh\",\"mobile\":\"13391391217\"}", key);
//		System.out.println(aa);
		String encrypt = encode("{\"interNumber\":\"10000002\",\"token\":\"dgsdfHYWFadfh\",\"mobile\":\"13391391217\"}");
		
//		String encrypt = encode("13313313313", key);
		String encrypt2 = URLEncoder.encode(encrypt, "UTF-8");
		System.out.println(encrypt);
		System.out.println(encrypt2);
		System.out.println(decode("1H+TMjVM5RTz0TBqelk9d6JDV3iWVxjL+ho956/ESabKqSDg5xzIREuHMeHAYhKZK7NWwowAaBKI7SY+ajIft68Lb98ucAJ/kC/gILczXR4="));
	}

	/** 
	 * 将byte[]转为各种进制的字符串 
	 * @param bytes byte[] 
	 * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制 
	 * @return 转换后的字符串 
	 */
	public static String binary(byte[] bytes, int radix) {
		return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数  
	}

	/** 
	 * base 64 encode 
	 * @param bytes 待编码的byte[] 
	 * @return 编码后的base 64 code 
	 */
	@SuppressWarnings("restriction")
	public static String base64Encode(byte[] bytes) {
		return new BASE64Encoder().encode(bytes);
	}

	/** 
	 * base 64 decode 
	 * @param base64Code 待解码的base 64 code 
	 * @return 解码后的byte[] 
	 * @throws Exception 
	 */
	@SuppressWarnings("restriction")
	public static byte[] base64Decode(String base64Code) throws Exception {
		return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
	}

	/** 
	 * 获取byte[]的md5值 
	 * @param bytes byte[] 
	 * @return md5 
	 * @throws Exception 
	 */
	public static byte[] md5(byte[] bytes) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(bytes);

		return md.digest();
	}

	/** 
	 * 获取字符串md5值 
	 * @param msg  
	 * @return md5 
	 * @throws Exception 
	 */
	public static byte[] md5(String msg) throws Exception {
		return StringUtils.isEmpty(msg) ? null : md5(msg.getBytes());
	}

	/** 
	 * 结合base64实现md5加密 
	 * @param msg 待加密字符串 
	 * @return 获取md5后转为base64 
	 * @throws Exception 
	 */
	public static String md5Encrypt(String msg) throws Exception {
		return StringUtils.isEmpty(msg) ? null : base64Encode(md5(msg));
	}

	/** 
	 * AES加密 
	 * @param content 待加密的内容 
	 * @param encryptKey 加密密钥 
	 * @return 加密后的byte[] 
	 * @throws Exception 
	 */
	public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(encryptKey.getBytes()));

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));

		return cipher.doFinal(content.getBytes("utf-8"));
	}

	/** 
	 * AES加密为base 64 code 
	 * @param content 待加密的内容 
	 * @param encryptKey 加密密钥 
	 * @return 加密后的base 64 code 
	 * @throws Exception 
	 */
	public static String aesEncrypt(String content, String encryptKey) throws Exception {
		return base64Encode(aesEncryptToBytes(content, encryptKey));
	}

	/** 
	 * AES解密 
	 * @param encryptBytes 待解密的byte[] 
	 * @param decryptKey 解密密钥 
	 * @return 解密后的String 
	 * @throws Exception 
	 */
	public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(decryptKey.getBytes()));
		//		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
		byte[] decryptBytes = cipher.doFinal(encryptBytes);

		return new String(decryptBytes);
	}

	/** 
	 * 将base 64 code AES解密 
	 * @param encryptStr 待解密的base 64 code 
	 * @param decryptKey 解密密钥 
	 * @return 解密后的string 
	 * @throws Exception 
	 */
	public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
		return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
	}

	/**
	 * @Description:编码
	 * @param encryptString
	 * @param encryptKey
	 * @return
	 * @throws Exception 
	 * @return: String      
	 * @throws
	 */
	public static String encode(String encryptString, String encryptKey) throws Exception {
		if (!StringUtils.isEmpty(encryptKey)) {
			SecretKeySpec skeySpec = new SecretKeySpec(encryptKey.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] bytes = cipher.doFinal(encryptString.getBytes("UTF-8"));
			return new String(Base64.encodeBase64(bytes), "UTF-8");
		} else {
			return null;
		}
	}
	
	/**
	 * @Description:编码
	 * @param encryptString
	 * @param encryptKey
	 * @return
	 * @throws Exception 
	 * @return: String      
	 * @throws
	 */
	public static String encode(String encryptString) throws Exception {
		if (!StringUtils.isEmpty(key)) {
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] bytes = cipher.doFinal(encryptString.getBytes("UTF-8"));
			return new String(Base64.encodeBase64(bytes), "UTF-8");
		} else {
			return null;
		}
	}

	/**
	 * @Description: 解码
	 * @param decryptBytes
	 * @param decryptKey
	 * @return
	 * @throws Exception 
	 * @return: String      
	 * @throws
	 */
	public static String decode(String decryptString, String decryptKey) throws Exception {
		if (!StringUtils.isEmpty(decryptString)) {
			byte[] decryptBytes = decryptString.getBytes("UTF-8");
			byte[] data = Base64.decodeBase64(decryptBytes);
			SecretKeySpec skeySpec = new SecretKeySpec(decryptKey.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			return new String(cipher.doFinal(data), "UTF-8");
		} else {
			return null;
		}
	}
	
	/**
	 * @Description: 解码
	 * @param decryptBytes
	 * @param decryptKey
	 * @return
	 * @throws Exception 
	 * @return: String      
	 * @throws
	 */
	public static String decode(String decryptString) throws Exception {
		if (!StringUtils.isEmpty(decryptString)) {
			byte[] decryptBytes = decryptString.getBytes("UTF-8");
			byte[] data = Base64.decodeBase64(decryptBytes);
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			return new String(cipher.doFinal(data), "UTF-8");
		} else {
			return null;
		}
	}
}