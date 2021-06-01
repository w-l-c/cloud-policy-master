package cn.rebornauto.platform.payment.service.impl.tonglianpay;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/** Title: 获取spring applicationContext
 * Description: 
 * Company: 
 * @author kgc
 * @date Feb 7, 2018 2:27:12 PM
 */
@Service
public class MyApplicationContextAware implements ApplicationContextAware {

    private static ApplicationContext applicationContext1;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext1 = applicationContext;
    }

    public static  ApplicationContext getApplicationContext(){
        return applicationContext1;
    }

}
