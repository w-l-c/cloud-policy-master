package cn.rebornauto.platform.business.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.rebornauto.platform.business.entity.DaiZhengInfo;
import cn.rebornauto.platform.business.form.DaiZhengForm;
import cn.rebornauto.platform.business.service.DaiZhengService;
import cn.rebornauto.platform.business.vo.DaiZhengInfoVo;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Query;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.common.util.BigDecimalUtil;
import cn.rebornauto.platform.common.util.CheckUtil;

@RestController
@RequestMapping("/daiZheng")
public class DaiZhengController extends BaseController {
    @Autowired
    private DaiZhengService daiZhengService;
    @PostMapping("/add")
    @RequiresPermissions("daiZheng:add")
    public Response add(@RequestBody Request<DaiZhengForm, Query> request, HttpServletRequest req) throws Exception {
        DaiZhengForm form = request.getForm();
        boolean b = paramCheck(form);
        if (b) {
            return Response.error().message("参数格式错误");
        }
        setCurrUser(form);
        String ip = getIP(req);
        int i = daiZhengService.save(form);
        return list(request);

    }

    /**
     * 修改代征主体信息
     * @param request
     * @return
     * @throws Exception 
     */
    @PostMapping("/edit")
    @RequiresPermissions("daiZheng:edit")
    public Response edit(@RequestBody Request<DaiZhengForm, Query> request,HttpServletRequest req) throws Exception {
        DaiZhengForm form = request.getForm();
        setCurrUser(form);
        int code = daiZhengService.edit(form);
    	if (code < 1) {
			return Response.factory().code(-1).message("代征主体信息不存在");
	 	}
        return list(request);

    }
    @PostMapping("/list")
    @RequiresPermissions("daiZheng:list")
    public Response list(@RequestBody Request<DaiZhengForm, Query> request) {
        Pagination pagination = request.getPagination();
        Query query = request.getQuery();
        TableBody body = TableBody.factory();
        long rowcount = daiZhengService.count(query);
        pagination.setTotal(rowcount);
        body.setPagination(pagination);
        BigDecimal totalAmount = new BigDecimal("0.00");
        List<DaiZhengInfo> list1 = daiZhengService.all();
        for (DaiZhengInfo daiZhengInfo : list1) {
            BigDecimal payAmount = daiZhengInfo.getPayAmount();
            totalAmount = totalAmount.add(payAmount);
        }
        List<DaiZhengInfoVo> list = daiZhengService.list(pagination);
        Map map = new HashMap();
        map.put("totalAmount",BigDecimalUtil.formatTosepara(totalAmount));
        map.put("list",list);
        map.put("pagination",pagination);
        return Response.ok().body(map);
    }

    /**
     * 判断规定参数是否都是数字，如果是返回false，不是返回true
     * @param form
     * @return
     */
    public boolean paramCheck(DaiZhengForm form) {
        boolean b = CheckUtil.StringIsNumber(form.getExtraTax().toString());
        boolean c = CheckUtil.StringIsNumber(form.getPersonalTax().toString());
        boolean d = CheckUtil.StringIsNumber(form.getValueAddedTax().toString());
        if (b && c && d) {
            return false;
        }
        return true;
    }
    
    /**
     * 尚尚签证书申请
     * @param request
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping("/register")
    @RequiresPermissions("daiZheng:register")
    public Response register(@RequestBody Request<DaiZhengForm, Query> request, HttpServletRequest req) throws Exception {
        DaiZhengForm form = request.getForm();
        setCurrUser(form);
        daiZhengService.register(form);
		return list(request);
    } 
    
    /**
     * 尚尚签证书申请查询
     * @param request
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping("/registerQuery")
    @RequiresPermissions("daiZheng:registerQuery")
    public Response registerQuery(@RequestBody Request<DaiZhengForm, Query> request, HttpServletRequest req) throws Exception {
    	Query query = request.getQuery();
    	DaiZhengForm form = request.getForm();
        setCurrUser(form);
        daiZhengService.registerQuery(form);
		return list(request);
    } 
}
