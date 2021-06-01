package cn.rebornauto.platform.common.handler.intercept;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BusLogger {

    String logtype() default "";

    String remark() default "";

    int bussessid() default 0;

}
