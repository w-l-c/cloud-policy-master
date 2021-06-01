package cn.rebornauto.platform.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

/**
 * Title: Description: Company:
 * 
 * @author kgc
 * @date Feb 5, 2018 12:00:48 PM
 */
public class BigDecimalUtil {
	/**
	 * 2位小数
	 */
	public static String XIAOSHU2_CAIWU = "#,###.##"; 
	
	/**
	 * 2位小数
	 */
	public static String XIAOSHU2 = "##########.##"; 
	
	/**
	 * 无小数
	 */
	public static String XIAOSHU0 = "###"; 
	
	private static final String STR_FORMAT = "00";
	
	public static String formatTosepara(BigDecimal data) {
		String value = "0.00";
		if(null != data) {
			DecimalFormat df = new DecimalFormat(XIAOSHU2_CAIWU);
			value = df.format(data);
		}
		return value;
	}
	
	public static BigDecimal formatXIAOSHU0(BigDecimal data,String xiaoShu) {
		String value = "0";
		DecimalFormat df = new DecimalFormat(xiaoShu);
		value = df.format(data);
		return new BigDecimal(value);
	}
	
	public static BigDecimal formatToXiaoShu2(BigDecimal data) {
		String value = "";
		DecimalFormat df = new DecimalFormat(XIAOSHU2);
		value = df.format(data);
		return new BigDecimal(value);
	} 
	
	/**
	 * 转百分比
	 * @param data
	 * @return
	 */
	public static String formatToBaiFenBi(double data) {
		String str = "";
		DecimalFormat df = new DecimalFormat("0.00%"); 
//		DecimalFormat df = new DecimalFormat("0%"); 
		str = df.format(data);
		return str;  
	}
	
	/**
	 * 转百分比保留2位小数
	 * @param data
	 * @return
	 */
	public static String formatToBaiFenBi2(double data) {
		String str = "";
		DecimalFormat df = new DecimalFormat("0.00%"); 
//		DecimalFormat df = new DecimalFormat("0%"); 
		str = df.format(data);
		return str;  
	}
	
	/**
	 * 除100
	 * @param data
	 * @return
	 */
	public static BigDecimal formatToDivide100(BigDecimal data) {
		data = data.divide(new BigDecimal(100));
		return data;
	}
	
	/**
	 * 乘100
	 * @param data
	 * @return
	 */
	public static BigDecimal formatToMultiply100(BigDecimal data) {
		data = data.multiply(new BigDecimal(100));
		return data;
	}
	
	
	public static BigDecimal stringToBigDecimal(String str) {
		BigDecimal b = BigDecimal.ZERO;
		if(StringUtils.isNotBlank(str)) {
			b = new BigDecimal(str);
		}else {
			b = BigDecimal.ZERO;
		}
		return b;
	}
	
	public static BigDecimal strToBigDecimal(String str) {
		BigDecimal b = BigDecimal.ZERO;
		if(StringUtils.isNotBlank(str)) {
			b = new BigDecimal(str);
		}else {
			b = null;
		}
		return b;
	}
	
	/**
	 * 转12位字符串  传杉徳支付
	 * @param data
	 * @return
	 */
	public static String formatTo12Str(BigDecimal data) {
		String str = "";
		DecimalFormat df = new DecimalFormat("000000000000"); 
		str = df.format(data);
		return str;  
	}
	
	public static void main(String[] args) {
//		BigDecimal bd1 = new BigDecimal("33.2356");
//		BigDecimal bd2 = new BigDecimal("23.7967");
//		System.out.println(new BigDecimal(formatTosepara(bd1.subtract(bd2))));
//		System.out.println(formatTosepara(new BigDecimal("3355645633.23")));
//		DecimalFormat df = new DecimalFormat(XIAOSHU2_CAIWU);
//		String value = df.format(null);
		System.out.println(formatToXiaoShu2(new BigDecimal("3323.236")));
	}
	
}
