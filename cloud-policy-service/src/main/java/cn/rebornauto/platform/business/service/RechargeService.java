package cn.rebornauto.platform.business.service;

import cn.rebornauto.platform.business.entity.CustomerInfo;
import cn.rebornauto.platform.business.form.RechargeForm;
import cn.rebornauto.platform.business.query.RechargeQuery;
import cn.rebornauto.platform.business.vo.RechargeVo;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.sys.entity.SysUser;

import java.util.List;

public interface RechargeService {
    long count(RechargeQuery query, SysUser sysUser);

    Response save(RechargeForm form);

    Response edit(RechargeForm form);

    List<RechargeVo> page(RechargeQuery query, SysUser sysUser, Pagination pagination);

    CustomerInfo getCustomerInfo(String customerId);

    Response submitCheck(RechargeForm form);

    Response checkSuccess(RechargeForm form,SysUser user);

    Response checkFail(RechargeForm form);

    int count4CheckList(RechargeQuery query);

    List<RechargeVo> page4CheckList(RechargeQuery query, Pagination pagination);

	List<RechargeVo> selectRechargeExcelByQuery(RechargeQuery query);

}
