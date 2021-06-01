package cn.rebornauto.platform.business.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.rebornauto.platform.business.entity.CustomerInfo;
import cn.rebornauto.platform.business.entity.CustomerTotalQuota;
import cn.rebornauto.platform.business.service.CustomerQuotaService;
import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.business.vo.CustomerQuotaVo;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.util.BigDecimalUtil;
import cn.rebornauto.platform.sys.entity.SysUser;
import cn.rebornauto.platform.upload.entity.UploadInfo;

/**
 * @author XJX
 * 账户余额展示
 */
@RestController
@RequestMapping("/customerQuota")
public class CustomerQuotaController extends BaseController {
    @Autowired
    private CustomerQuotaService customerQuotaService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private SysDicService sysDicService;
    @PostMapping("/list")
    @RequiresPermissions("customerQuota:list")
    public Response list() {
        SysUser sysUser = currentUser();
        Map<String,Object> map = new HashMap<String,Object>();
        if (!isAdministrator(sysUser.getCustomerId())) {
            CustomerQuotaVo customerQuota = customerQuotaService.selectVoByCustomerId(sysUser.getCustomerId());
            CustomerInfo customerInfo = customerQuotaService.selectCustomerInfo(sysUser.getCustomerId());
            UploadInfo uploadInfo=sysConfigService.getUploadInfo(sysDicService.selectSysPaySwitch());
//            map.put("customerBalance",customerQuota.getCustomerBalance());
            BigDecimal subtract = customerQuota.getCustomerBalance().subtract(customerQuota.getFrozenAmount());
//            map.put("availableBalance",subtract);
//            map.put("frozenAmount",customerQuota.getFrozenAmount());
//            map.put("pendingApprAmount",customerQuota.getPendingApprAmount());
//            map.put("loanAmount",customerQuota.getLoanAmount());
//            map.put("rechargeAmount",customerQuota.getRechargeAmount());
            if (StringUtils.isNotEmpty(customerInfo.getQrCodeImgPicUrl())) {
                map.put("qrCodeImgPicUrl",uploadInfo.getDomain() + customerInfo.getQrCodeImgPicUrl());
            } else {
                map.put("qrCodeImgPicUrl","");
            }
            
            map.put("customerBalanceFin",BigDecimalUtil.formatTosepara(customerQuota.getCustomerBalance()));
            map.put("availableBalanceFin",BigDecimalUtil.formatTosepara(subtract));
            map.put("frozenAmountFin",BigDecimalUtil.formatTosepara(customerQuota.getFrozenAmount()));
            map.put("pendingApprAmountFin",BigDecimalUtil.formatTosepara(customerQuota.getPendingApprAmount()));
            map.put("loanAmountFin",BigDecimalUtil.formatTosepara(customerQuota.getLoanAmount()));
            map.put("rechargeAmountFin",BigDecimalUtil.formatTosepara(customerQuota.getRechargeAmount()));
        } else {
            CustomerTotalQuota customerTotalQuota = customerQuotaService.select4CustomerTotalQuota();
//            map.put("customerBalance",customerTotalQuota.getCustomerBalance());
//            map.put("availableBalance",customerTotalQuota.getAvailableBalance());
//            map.put("frozenAmount",customerTotalQuota.getFrozenAmount());
//            map.put("pendingApprAmount",customerTotalQuota.getPendingApprAmount());
//            map.put("loanAmount",customerTotalQuota.getLoanAmount());
//            map.put("rechargeAmount",customerTotalQuota.getRechargeAmount());
            map.put("qrCodeImgPicUrl","");
            
            map.put("customerBalanceFin",BigDecimalUtil.formatTosepara(customerTotalQuota.getCustomerBalance()));
            map.put("availableBalanceFin",BigDecimalUtil.formatTosepara(customerTotalQuota.getAvailableBalance()));
            map.put("frozenAmountFin",BigDecimalUtil.formatTosepara(customerTotalQuota.getFrozenAmount()));
            map.put("pendingApprAmountFin",BigDecimalUtil.formatTosepara(customerTotalQuota.getPendingApprAmount()));
            map.put("loanAmountFin",BigDecimalUtil.formatTosepara(customerTotalQuota.getLoanAmount()));
            map.put("rechargeAmountFin",BigDecimalUtil.formatTosepara(customerTotalQuota.getRechargeAmount()));
        }
        return Response.ok().body(map);
    }
}
