package cn.rebornauto.platform.business.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rebornauto.platform.business.entity.MakeInvoiceDetail;
import cn.rebornauto.platform.business.query.InvoiceQuery;
import cn.rebornauto.platform.business.vo.MakeInvoiceDetailVO;
import cn.rebornauto.platform.common.data.request.Pagination;
import tk.mybatis.mapper.common.Mapper;

public interface MakeInvoiceDetailMapper extends Mapper<MakeInvoiceDetail> {
	int count(@Param("q") InvoiceQuery query);

    List<MakeInvoiceDetailVO> list(@Param("q") InvoiceQuery query, @Param("p") Pagination pagination);

    BigDecimal getTotalApplyAmount(@Param("q") InvoiceQuery query);

	MakeInvoiceDetailVO selectByPrimaryKeyVO(String invoiceNo);
    
	BigDecimal getTotalCapitalAmount(@Param("q") InvoiceQuery query);
}