package cn.rebornauto.platform.business.dao;

import org.apache.ibatis.annotations.Param;

import cn.rebornauto.platform.business.entity.MakeInvoice;
import tk.mybatis.mapper.common.Mapper;

public interface MakeInvoiceMapper extends Mapper<MakeInvoice> {
	
	int updateMakeInvoiceByCustomerId(@Param("bean") MakeInvoice bean);
}