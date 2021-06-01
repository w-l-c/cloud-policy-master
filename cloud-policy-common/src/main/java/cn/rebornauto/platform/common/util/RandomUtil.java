package cn.rebornauto.platform.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

public class RandomUtil {
	
	public static final String NUMBERCHAR = "0123456789";

    public final static String randomStr(int len){
       String str =  UUID.randomUUID().toString().replace("-","").substring(0,len);
       return str;
    }

    public final static String dateSeq(){
       // IF+年份后两位+月+日+五位数字(由00001开始)
       return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
    }

    public final static String dateSeqByPattern(LocalDateTime dateTime,String pattern){
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public final static String randomId(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))+randomStr(10);
    }
    
    /**
     * 获取发票编码
     * @return
     */
    public final static String getInvoiceNo(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("SSS"))+randomNumberString(5);
    }
    
    /**
	  * 返回一个定长的随机字符串(只包含数字)
	  * 
	  * @param length
	  *            随机字符串长度
	  * @return 随机字符串
	  */
	public static String randomNumberString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(NUMBERCHAR.charAt(random.nextInt(NUMBERCHAR.length())));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(getInvoiceNo());
	}
}
