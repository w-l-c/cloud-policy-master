package cn.rebornauto.platform.business.service;

import java.util.List;

import cn.rebornauto.platform.business.vo.PersonalCertificateStatusVo;

public interface PersonalCertificateStatusService {
     /**
      * 查询所有代理人个人证书申请处理中的信息
      * @return
      */
	List<PersonalCertificateStatusVo> findPersonalInProcess();

}
