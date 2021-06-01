package cn.rebornauto.platform.business.dao;

import java.util.List;

import cn.rebornauto.platform.business.entity.PersonalCertificateStatus;
import cn.rebornauto.platform.business.vo.PersonalCertificateStatusVo;
import tk.mybatis.mapper.common.Mapper;

public interface PersonalCertificateStatusMapper extends Mapper<PersonalCertificateStatus> {

	List<PersonalCertificateStatusVo> findPersonalInProcess();
}