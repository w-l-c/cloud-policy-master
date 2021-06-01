package cn.rebornauto.platform.business.service;

import cn.rebornauto.platform.business.form.InvoiceForm;
import cn.rebornauto.platform.business.query.InvoiceQuery;
import cn.rebornauto.platform.business.vo.InvoiceVo;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.sys.entity.SysUser;

import java.util.List;

public interface InvoiceService {
    int count(InvoiceQuery query);

    int edit(InvoiceForm form);

    List<InvoiceVo> list(InvoiceQuery query, Pagination pagination, SysUser currentUser);
}
