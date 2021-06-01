package cn.rebornauto.platform.business.service;

import java.util.List;

import cn.rebornauto.platform.business.entity.MakeInvoiceDetail;
import cn.rebornauto.platform.business.form.InvoiceForm;
import cn.rebornauto.platform.business.query.InvoiceQuery;
import cn.rebornauto.platform.business.vo.MakeInvoiceDetailVO;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.sys.entity.SysUser;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Mar 17, 2020 3:06:45 PM
 */
public interface MakeInvoiceDetailService {
	int count(InvoiceQuery query);


    List<MakeInvoiceDetailVO> list(InvoiceQuery query, Pagination pagination, SysUser currentUser);


    MakeInvoiceDetailVO apply(SysUser user);


    Response applySave(InvoiceForm form,SysUser user);


    Response examine(InvoiceForm form, SysUser user);


    MakeInvoiceDetailVO detail(String id);


	Response sendSave(SysUser user, InvoiceForm form);
}
