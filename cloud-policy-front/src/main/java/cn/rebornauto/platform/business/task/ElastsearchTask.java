package cn.rebornauto.platform.business.task;

import java.time.LocalDateTime;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.rebornauto.platform.business.service.AgentInfoService;
import cn.rebornauto.platform.business.service.PersonalCertificateStatusService;
import cn.rebornauto.platform.business.vo.PersonalCertificateStatusVo;

@Component
@Slf4j
@EnableScheduling
public class ElastsearchTask {

    @Autowired
    private AgentInfoService  agentInfoService;
    
    @Autowired
    private PersonalCertificateStatusService  personalCertificateStatusService;
    /**
     * 代理人签约定时
     */
    @Scheduled(fixedDelay = 1000 * 120)
    public void addVehicle() {
        log.info(LocalDateTime.now().toString());
        List<PersonalCertificateStatusVo> personalCertificateStatusVos=personalCertificateStatusService.findPersonalInProcess();
        personalCertificateStatusVos.forEach(personalCertificateStatusVo -> {
        	agentInfoService.timingPersonalCertificateQuery(personalCertificateStatusVo);
		 });	 
    }
}
