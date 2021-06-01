package cn.rebornauto.platform.common.util;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ligewei
 * @create 2019/03/20 16:59
 */
public class DateUtils {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_WITH_CHINESE = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_YYMMDD_HHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_YYMMDD = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_YYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");

    public static LocalDateTime dateToLocalDateTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }
    public static Date LocalDateTimeToUdate(LocalDateTime localDateTime) {
    	if(null==localDateTime) {
    		return null;
    	}
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    public static String LocalDateTimeToChineseString(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return SIMPLE_DATE_FORMAT_WITH_CHINESE.format(date);
    }

    public static Date getCurrentDay(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR,0);
        return calendar.getTime();
    }

    public static Integer getSecond(LocalDateTime end){
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now,end);
        Long millis = duration.toMillis();
        int second = (int) (millis/1000);
        return second;
    }

    public static String LocalDateTimeToString(LocalDateTime localDateTime) {
        if(localDateTime == null){
            return  "";
        }
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return SIMPLE_DATE_FORMAT_YYMMDD_HHMMSS.format(date);
    }
    
    /**
     * 字符串转日期函数：格式必须为"yyyy-mm-dd"
     * @param str
     * @param format "yyyy-mm-dd"
     * @return
     */
	public static LocalDateTime toDateyyyyMMdd(String str,String format) {
		try {
			if (str.equals(""))
				return null;
			SimpleDateFormat myFormatter = new SimpleDateFormat(format);
			Instant instant = myFormatter.parse(str).toInstant();
	        ZoneId zone = ZoneId.systemDefault();
	        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
			return localDateTime;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String localDateTimeToYYYYMMDD(LocalDateTime localDateTime) {
		if(null==localDateTime) {
			return "";
		}
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return SIMPLE_DATE_FORMAT_YYMMDD.format(date);
    }
	
	
	public static String localDateTimeToStringYYMMDDHHMMSS(LocalDateTime localDateTime) {
        if(localDateTime == null){
            return  "";
        }
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return SIMPLE_DATE_FORMAT_YYMMDDHHMMSS.format(date);
    }
}
