package cn.rebornauto.platform.business.controller;

import cn.rebornauto.platform.business.query.CustomerTotalQuotaQuery;
import cn.rebornauto.platform.business.service.CustomerTotalQuotaService;
import cn.rebornauto.platform.business.vo.CustomerTotalQuotaVo;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.data.request.Form;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.common.util.BigDecimalUtil;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customerTotalQuota")
public class CustomerTotalQuotaController extends BaseController {
    @Autowired
    private CustomerTotalQuotaService customerTotalQuotaService;
    @PostMapping("/list")
    @RequiresPermissions("customerTotalQuota:list")
    public Response list(@RequestBody Request<Form, CustomerTotalQuotaQuery> request) {
        TableBody body = TableBody.factory();

        CustomerTotalQuotaQuery query = request.getQuery();
        int rowcount = customerTotalQuotaService.count(query);
        List<CustomerTotalQuotaVo> list = customerTotalQuotaService.listAll(query);
        int i = 0;
        BigDecimal loanAmountSum = new BigDecimal("0.00");
        if (list.size()>0) {
            for (CustomerTotalQuotaVo customerTotalQuotaVo : list) {
                if (!customerTotalQuotaVo.getCustomerId().equals("9999")) {
                    BigDecimal loanAmount = customerTotalQuotaVo.getLoanAmount();
                    loanAmountSum = loanAmountSum.add(loanAmount);
                    if (customerTotalQuotaVo.getDataStatus() == 1) {
                        i++;
                    }
                }
            }
        }
        Pagination pagination = request.getPagination();
        pagination.setTotal(rowcount);
//        List<CustomerTotalQuotaVo> list1 = customerTotalQuotaService.list(query,pagination);
        Map map = new HashMap();
        map.put("loanAmountSum",BigDecimalUtil.formatTosepara(loanAmountSum));
        map.put("customerCount",i);
        map.put("list",list);
        map.put("pagination",pagination);
        return Response.ok().body(map);
    }
}
