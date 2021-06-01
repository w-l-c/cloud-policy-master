package cn.rebornauto.platform.business.dao;

import cn.rebornauto.platform.business.entity.Invoice;
import cn.rebornauto.platform.business.query.InvoiceQuery;
import cn.rebornauto.platform.business.vo.InvoiceVo;
import cn.rebornauto.platform.common.data.request.Pagination;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface InvoiceMapper extends Mapper<Invoice> {
    int count(@Param("q") InvoiceQuery query);

    List<InvoiceVo> list(@Param("q") InvoiceQuery query, @Param("p") Pagination pagination);

    Invoice selectByOrderId(@Param("orderId")Integer orderId);
}