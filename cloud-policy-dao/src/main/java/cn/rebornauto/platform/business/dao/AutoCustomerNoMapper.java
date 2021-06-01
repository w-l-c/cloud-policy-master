package cn.rebornauto.platform.business.dao;

import cn.rebornauto.platform.business.entity.AutoCustomerNo;
import tk.mybatis.mapper.common.Mapper;

public interface AutoCustomerNoMapper extends Mapper<AutoCustomerNo> {

	void setCustomerId();

	AutoCustomerNo getAutoCustomerNo();
}