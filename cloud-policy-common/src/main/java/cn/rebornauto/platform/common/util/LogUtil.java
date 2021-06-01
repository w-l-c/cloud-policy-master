package cn.rebornauto.platform.common.util;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2015/12/10 0010.
 */
public class LogUtil {

	/**
	 * debug日志
	 */
    private static Logger debugLogger = null ;
    /**
	 * info日志
	 */
    private static Logger infoLogger = null ;
    /**
	 * warn日志
	 */
    private static Logger warnLogger = null ;
    /**
	 * error日志
	 */
    private static Logger errorLogger = null ;

    static{
        debugLogger = Logger.getLogger("debugLog");
        infoLogger = Logger.getLogger("infoLog");
        warnLogger = Logger.getLogger("warnLog");
        errorLogger = Logger.getLogger("errorLog");
    }

    public static void debug(Object message){
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        debugLogger.debug(stack[2].getClassName()+"."+stack[2].getMethodName()+"() --- Line:"+stack[2].getLineNumber()+" --- Message:"+message);
    }

    public static void info(Object message){
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        infoLogger.info(stack[2].getClassName()+"."+stack[2].getMethodName()+"() --- Line:"+stack[2].getLineNumber()+" --- Message:"+message);
    }

    public static void warn(Object message){
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        warnLogger.warn(stack[2].getClassName()+"."+stack[2].getMethodName()+"() --- Line:"+stack[2].getLineNumber()+" --- Message:"+message);
    }

    public static void error(Object message){
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        errorLogger.error(stack[2].getClassName()+"."+stack[2].getMethodName()+"() --- Line:"+stack[2].getLineNumber()+" --- Message:"+message);
    }

}
