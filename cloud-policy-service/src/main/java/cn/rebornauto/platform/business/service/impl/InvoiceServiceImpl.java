package cn.rebornauto.platform.business.service.impl;

import cn.rebornauto.platform.business.dao.InvoiceMapper;
import cn.rebornauto.platform.business.entity.Invoice;
import cn.rebornauto.platform.business.form.InvoiceForm;
import cn.rebornauto.platform.business.query.InvoiceQuery;
import cn.rebornauto.platform.business.service.InvoiceService;
import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.business.vo.InvoiceVo;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.util.CheckUtil;
import cn.rebornauto.platform.sys.entity.SysUser;
import cn.rebornauto.platform.upload.entity.UploadInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Override
    public int count(InvoiceQuery query) {
        return invoiceMapper.count(query);
    }
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private SysDicService sysDicService;
    @Override
    public int edit(InvoiceForm form) {
        int id = form.getId();
        Invoice invoice = invoiceMapper.selectByPrimaryKey(id);
        invoice.setInvoiceTime(form.getInvoiceTime());
//        if (StringUtils.isNotEmpty(form.getInvoicePicUrl())) {
//            UploadInfo uploadInfo=sysConfigService.getUploadInfo(sysDicService.selectSysPaySwitch());
//            String s = CheckUtil.checkUrl(invoice.getInvoicePicUrl(), form.getInvoicePicUrl(), uploadInfo.getDomain());
//            invoice.setInvoicePicUrl(s);
//        }
//        invoice.setExpressCompany(form.getExpressCompany());
//        invoice.setExpressNo(form.getExpressNo());
//        if (StringUtils.isNotEmpty(form.getInvoicePicUrl())) {
//            invoice.setInvoiceStatus(Const.invoice_status_2);
//            invoice.setOutInvoiceStatus(Const.out_invoice_status_2);
//        }
        invoice.setModifyoper(form.getCurrUserName());
        invoice.setModifytime(LocalDateTime.now());

        return invoiceMapper.updateByPrimaryKeySelective(invoice);
    }

    @Override
    public List<InvoiceVo> list(InvoiceQuery query, Pagination pagination, SysUser currentUser) {
        List<InvoiceVo> list = invoiceMapper.list(query,pagination);
        UploadInfo uploadInfo=sysConfigService.getUploadInfo(sysDicService.selectSysPaySwitch());
        list.forEach(invoiceVo -> {
            if (StringUtils.isNotEmpty(invoiceVo.getInvoicePicUrl())) {
                invoiceVo.setInvoicePicUrl(uploadInfo.getDomain()+invoiceVo.getInvoicePicUrl());
            } else {
                invoiceVo.setInvoicePicUrl("");
            }
        });
        return list;
    }
}
