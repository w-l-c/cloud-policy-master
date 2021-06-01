package cn.rebornauto.platform.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rebornauto.platform.business.dao.PersonalCertificateStatusMapper;
import cn.rebornauto.platform.business.service.PersonalCertificateStatusService;
import cn.rebornauto.platform.business.vo.PersonalCertificateStatusVo;
@Service
public class PersonalCertificateStatusServiceImpl implements PersonalCertificateStatusService{
	
@Autowired
PersonalCertificateStatusMapper personalCertificateStatusMapper;

@Override
public List<PersonalCertificateStatusVo> findPersonalInProcess() {
	// TODO Auto-generated method stub
	return personalCertificateStatusMapper.findPersonalInProcess();
}
}
