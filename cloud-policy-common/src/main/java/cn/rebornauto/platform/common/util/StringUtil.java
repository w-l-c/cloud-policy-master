package cn.rebornauto.platform.common.util;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;


/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date May 20, 2019 2:17:48 PM
 */
public class StringUtil {

	public static String nullToString(Object obj) {
		if(null!=obj) {
			if (StringUtils.isNotEmpty(obj.toString())) {
				return obj.toString();
			} else {
				return "";
			}
		}else {
			return "";
		}
		
	}
	
	
	/**
	 * 去除字符串前后和中间所有空格、制表符等等空白
	 * @param str
	 * @return
	 */
	public static String formatBlankString(String str) {
		if(StringUtils.isNotBlank(str)) {
			str = StringUtils.deleteWhitespace(str);
			return str;
		}else {
			return str;
		}
	}
	
	/**
	 * 格式化json字符串，方便查看
	 * 
	 * @param json
	 * @return
	 * @throws IOException
	 * @author SmallTree
	 * @since 1.0
	 * @2018年6月14日下午8:48:19
	 */
	public static String formatJson(String json) throws IOException {
		if(StringUtils.isNotBlank(json)) {
			ObjectMapper mapper = new ObjectMapper();
			Object obj = mapper.readValue(json, Object.class);
			String formatJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
			return formatJson;
		}else {
			return "";
		}
		
	}
	
	public static void writeStr2Res(String jsonStr, HttpServletResponse res) {
		res.setContentType("text/html");
		res.setCharacterEncoding("utf-8");
		try {
			res.getWriter().write(jsonStr);
//			System.out.println(jsonStr);
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
	
	/**
	 * 获取杉徳余额转金额
	 * @param str
	 * @return
	 */
	public static String getSandBalanceAmount(String str) {
		String temp = "";
		if(StringUtils.isNotBlank(str)) {
			int strLength = str.length();
			for (int i = 1; i < strLength; i++) {
				if(!"0".equals(str.substring(i,i+1))) {
					BigDecimal bd = new BigDecimal(str.substring(i, strLength)).multiply(new BigDecimal("0.01"));
					temp = BigDecimalUtil.formatTosepara(bd);
					return temp;
				}
			}
			return temp;
		}else {
			return temp;
		}
	}
	
	/**
	 * 获取流水号
	 * @return
	 */
	public static String getSerialNo(String merchantId) {
		String serialNo = merchantId+"-"+String.valueOf(System.currentTimeMillis())+"-"+ (int)(Math.random()*1000);
		return serialNo;
    	
	}
	
	
	public static void main(String[] args) {
		System.out.println(getSandBalanceAmount("+000000929739"));
	}
}
