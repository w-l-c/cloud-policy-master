package cn.rebornauto.platform.business.controller;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.rebornauto.platform.payment.service.SandPayService;
@Component
@Configurable
@EnableScheduling
public class SandPayQueryOrderQuartz {

    private static final Logger logger = LoggerFactory.getLogger(SandPayQueryOrderQuartz.class);

    @Autowired
    SandPayService sandPayService;

    /**
     * 定时杉徳支付 订单查询  每5分钟执行一次
     */
    @Scheduled(cron="0 */5 * * * ?")
    public void execute() {
    	HttpServletRequest req = null;
    	logger.info("处理杉徳订单start");
    	sandPayService.queryOrderAndUpdate(req);
    	logger.info("处理杉徳订单end");
    }
}
