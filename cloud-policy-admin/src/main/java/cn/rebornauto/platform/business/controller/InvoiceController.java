package cn.rebornauto.platform.business.controller;

import cn.rebornauto.platform.business.form.InvoiceForm;
import cn.rebornauto.platform.business.query.InvoiceQuery;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.service.InvoiceService;
import cn.rebornauto.platform.business.vo.InvoiceVo;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.sys.entity.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 发票管理
 * @author XJX
 */
@RestController
@RequestMapping("/invoice")
public class InvoiceController extends BaseController {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private BusiLogService busiLogService;

    /**
     * 修改开票
     * @param request
     * @param req
     * @return
     */
    @PostMapping("/edit")
    @RequiresPermissions("invoice:edit")
    public Response edit(@RequestBody Request<InvoiceForm, InvoiceQuery> request, HttpServletRequest req) {
        InvoiceForm form = request.getForm();
        setCurrUser(form);
        String ip = getIP(req);
        int i = invoiceService.edit(form);
        //开票操作日志
        busiLogService.add(req, Const.busi_log_busi_type_6, Const.busi_log_option_type_success,"已开票", currentUser());
        return list(request);
    }
    @PostMapping("/list")
    @RequiresPermissions("invoice:list")
    public Response list(@RequestBody Request<InvoiceForm, InvoiceQuery> request) {
        Pagination pagination = request.getPagination();
        InvoiceQuery query = request.getQuery();
        TableBody body = TableBody.factory();
        //获取操作人信息
        SysUser sysUser = currentUser();
        //判断是否为平台角色
        boolean administrator = isAdministrator(sysUser.getCustomerId());
        if (!administrator) {
            //不是平台角色需添加客户角色查询限制
            query.setCustomerId(sysUser.getCustomerId());
        }
        int rowcount = invoiceService.count(query);
        pagination.setTotal(rowcount);
        body.setPagination(pagination);
        List<InvoiceVo> list = invoiceService.list(query,pagination, currentUser());
        body.setList(list);
        return Response.ok().body(body);
    }
}
