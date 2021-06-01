package cn.rebornauto.platform.business.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rebornauto.platform.business.dao.CustomerInfoMapper;
import cn.rebornauto.platform.business.dao.CustomerQuotaMapper;
import cn.rebornauto.platform.business.dao.MakeInvoiceDetailMapper;
import cn.rebornauto.platform.business.dao.MakeInvoiceMapper;
import cn.rebornauto.platform.business.dao.PostAddressMapper;
import cn.rebornauto.platform.business.entity.CustomerInfo;
import cn.rebornauto.platform.business.entity.MakeInvoice;
import cn.rebornauto.platform.business.entity.MakeInvoiceDetail;
import cn.rebornauto.platform.business.entity.PostAddress;
import cn.rebornauto.platform.business.form.InvoiceForm;
import cn.rebornauto.platform.business.query.InvoiceQuery;
import cn.rebornauto.platform.business.service.MakeInvoiceDetailService;
import cn.rebornauto.platform.business.service.MakeInvoiceService;
import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.business.vo.MakeInvoiceDetailVO;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.ReturnMsgConst;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.util.BigDecimalUtil;
import cn.rebornauto.platform.common.util.RandomUtil;
import cn.rebornauto.platform.sys.dao.SysEnumsMapper;
import cn.rebornauto.platform.sys.entity.SysEnums;
import cn.rebornauto.platform.sys.entity.SysUser;
import tk.mybatis.mapper.entity.Example;

/** Title: 开票管理
 * Description: 
 * Company: 
 * @author kgc
 * @date Mar 17, 2020 4:23:05 PM
 */
@Service
public class MakeInvoiceDetailServiceImpl implements MakeInvoiceDetailService {


	@Autowired
	MakeInvoiceMapper makeInvoiceMapper;
	
	@Autowired
	MakeInvoiceDetailMapper makeInvoiceDetailMapper;
	
	@Autowired
	CustomerInfoMapper customerInfoMapper;
	
	@Autowired
	PostAddressMapper postAddressMapper;
	
	@Autowired
	CustomerQuotaMapper customerQuotaMapper;
	
	@Autowired
	SysEnumsMapper sysEnumsMapper;
	
	@Autowired
	MakeInvoiceService makeInvoiceService;
	
	@Autowired
	SysConfigService sysConfigService;

	@Override
	public int count(InvoiceQuery query) {
		return makeInvoiceDetailMapper.count(query);
	}

	@Override
	public List<MakeInvoiceDetailVO> list(InvoiceQuery query, Pagination pagination, SysUser currentUser) {
		List<MakeInvoiceDetailVO> list = makeInvoiceDetailMapper.list(query, pagination);
		if(null!=list && list.size()>0) {
			//域名
			String domain = sysConfigService.findValueByKey("domain");
			for (int i = 0; i < list.size(); i++) {
				MakeInvoiceDetailVO detail = list.get(i);
				detail.setApplyAmountFin(BigDecimalUtil.formatTosepara(detail.getApplyAmount()));
				detail.setInvoiceAmountFin(BigDecimalUtil.formatTosepara(detail.getInvoiceAmount()));
				String invoicePicUrl = detail.getInvoicePicUrl();
				
				if(StringUtils.isNotBlank(invoicePicUrl)) {
					String url = "";
					String[] str = invoicePicUrl.split(",");
					for (int j = 0; j < str.length; j++) {
						url = url + domain + str[j] + ",";
					}
					if(StringUtils.isNotBlank(url)) {
						url = url.substring(0, url.length()-1);
					}
					list.get(i).setInvoicePicUrl(url);
				}
				
			}
		}
		return list;
	}

	/**
	 * 开票申请
	 */
	@Override
	public MakeInvoiceDetailVO apply(SysUser user) {
		MakeInvoiceDetailVO vo = new MakeInvoiceDetailVO();
		//客户编号
		String customerId = user.getCustomerId();
		//发票编号8位数字编号
		String invoiceNo = RandomUtil.getInvoiceNo();
		//已申请发票(张)
		int applyNum = 1;
		CustomerInfo customerInfoQuery = new CustomerInfo();
		customerInfoQuery.setId(customerId);
		CustomerInfo customerInfo = customerInfoMapper.selectOne(customerInfoQuery);
		//商户别名
		String customerName = customerInfo.getCustomerName();
		//纳税人类型   1一般纳税人   2小规模纳税人
		int taxpayerType = customerInfo.getTaxpayerType();
		//发票抬头
		String invoiceTitle = customerInfo.getInvoiceTitle();
		//服务费率
		BigDecimal serviceRate =  customerInfo.getServiceRate().divide(new BigDecimal(100));
		//纳税人识别号
		String taxpayerNumber = customerInfo.getTaxpayerNumber();
		//开票电话
		String tel = customerInfo.getTel();
		//注册地址
		String customerRegAddress = customerInfo.getCustomerRegAddress();
		//支行名称
		String customerSubOpenBankName = customerInfo.getCustomerSubOpenBankName();
		
		//可开票金额
		MakeInvoice makeInvoice = makeInvoiceService.getInvoiceAmount(customerId);
		BigDecimal invoiceAmount = makeInvoice.getInvoiceAmount();
		
		
		vo.setServiceRate(serviceRate);
		vo.setCustomerId(customerId);
		vo.setCustomerName(customerName);
		vo.setInvoiceNo(invoiceNo);
		vo.setApplyNum(applyNum);
		vo.setCustomerName(invoiceTitle);
		vo.setTaxpayerType(taxpayerType);
		vo.setApplyAmount(invoiceAmount);
		vo.setInvoiceAmount(invoiceAmount);
		vo.setInvoiceTitle(invoiceTitle);
		vo.setTaxpayerNumber(taxpayerNumber);
		vo.setCustomerOpenBankName(customerInfo.getCustomerOpenBankName());
		vo.setCustomerOpenBankNo(customerInfo.getCustomerOpenBankNo());
		vo.setTel(tel);
		vo.setCustomerRegAddress(customerRegAddress);
		vo.setCustomerSubOpenBankName(customerSubOpenBankName);
		
		//邮寄地址相关信息
		PostAddress postAddressQuery = new PostAddress();
		postAddressQuery.setCustomerId(customerId);
		postAddressQuery.setDataStatus(Const.DATA_STATUS_1);
		PostAddress postAddress = postAddressMapper.selectOne(postAddressQuery);
		if(null!=postAddress) {
			vo.setAddress(postAddress.getAddress());
			vo.setReceiver(postAddress.getReceiver());
			vo.setMobile(postAddress.getMobile());
		}
		
		
		return vo;
	}

	/**
	 * 开票申请保存
	 */
	@Override
	@Transactional
	public Response applySave(InvoiceForm form,SysUser user) {
		//查询客户信息
		CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(user.getCustomerId());
		
		//商户别名
		String customerName = customerInfo.getCustomerName();
		//纳税人类型   1一般纳税人   2小规模纳税人
		int taxpayerType = customerInfo.getTaxpayerType();
		//纳税人识别号
		String taxpayerNumber = customerInfo.getTaxpayerNumber();
		//发票抬头
		String invoiceTitle = customerInfo.getInvoiceTitle();
		
		//可开票金额
		MakeInvoice makeInvoice = makeInvoiceService.getInvoiceAmount(user.getCustomerId());
		BigDecimal invoiceAmount = makeInvoice.getInvoiceAmount();
		
		//申请开票金额 不能大于 可开票金额
		if(form.getApplyAmount().compareTo(invoiceAmount)>0) {
			return Response.error().message(ReturnMsgConst.RETURN_600041.getMsg());
		}
		
		
		MakeInvoiceDetail record = new MakeInvoiceDetail();
		MakeInvoiceDetail detailQuery = new MakeInvoiceDetail();
		detailQuery.setInvoiceNo(form.getInvoiceNo());
		MakeInvoiceDetail query = makeInvoiceDetailMapper.selectOne(detailQuery);
		if(null!=query) {//万一碰巧碰到随机生成的发票号重复，重新再生成一个
			record.setInvoiceNo(RandomUtil.getInvoiceNo());
		}else {
			record.setInvoiceNo(form.getInvoiceNo());
		}
		record.setCustomerId(user.getCustomerId());
		record.setApplyNum(1);
		record.setApplyAmount(form.getApplyAmount());
		record.setApplyOper(user.getNickname());
		record.setApplyTime(LocalDateTime.now());
		record.setCustomerName(customerInfo.getCustomerName());
		record.setInvoiceAmount(makeInvoice.getInvoiceAmount());
//					record.setInvoiceTime(LocalDateTime.now());
		record.setCustomerName(customerName);
		record.setTaxpayerType(taxpayerType);
		record.setTaxpayerNumber(taxpayerNumber);
		record.setInvoiceTitle(invoiceTitle);
		record.setInvoiceType(form.getInvoiceType());
		
		//总开票本金金额
		BigDecimal capitalAmount = BigDecimalUtil.formatToXiaoShu2(form.getApplyAmount().multiply((new BigDecimal(1).subtract(customerInfo.getServiceRate().multiply(new BigDecimal(0.01))))));
		record.setCapitalAmount(capitalAmount);
		record.setCustomerServiceRate(customerInfo.getServiceRate());
		
		//地址信息
		PostAddress postAddressQuery = new PostAddress();
		postAddressQuery.setCustomerId(user.getCustomerId());
		postAddressQuery.setDataStatus(Const.DATA_STATUS_1);
		PostAddress postAddress = postAddressMapper.selectOne(postAddressQuery);
		record.setAddress(postAddress.getAddress());
		record.setReceiver(postAddress.getReceiver());
		record.setMobile(postAddress.getMobile());
		record.setRemark(form.getRemark());//开票备注
		
		//保存开票明细表
		makeInvoiceDetailMapper.insertSelective(record);
		
		//修改开票主表  已申请开票金额  和  可开票金额
		MakeInvoice makeInvoiceUpdate = new MakeInvoice();
		makeInvoiceUpdate.setCustomerId(user.getCustomerId());
		makeInvoiceUpdate.setModifytime(LocalDateTime.now());
		makeInvoiceUpdate.setModifyoper(user.getNickname());
		makeInvoiceUpdate.setApplyAmount(makeInvoice.getApplyAmount().add(form.getApplyAmount()));
		Example example = new Example(MakeInvoice.class);
		example.createCriteria().andEqualTo("customerId", user.getCustomerId());
		makeInvoiceMapper.updateByExampleSelective(makeInvoiceUpdate, example);
		return Response.ok();

	}

	/**
	 * 开票，修改开票状态为已开票
	 */
	@Override
	@Transactional
	public Response examine(InvoiceForm form, SysUser user) {
		Integer id = Integer.parseInt(form.getInvoiceNo());
		MakeInvoiceDetail detail = makeInvoiceDetailMapper.selectByPrimaryKey(id);
		
		if(null==detail) {
			return Response.error().message(ReturnMsgConst.RETURN_600042.getMsg());
		}else {
			//待开票数据才能确认开票
			if(detail.getMakeInvoiceStatus()==Const.MAKE_INVOICE_STATUS_1) {
				//发票图片url
				String[] picUrl = form.getInvoicePicUrl();
				//发票图片为空提示报错
				if(null==picUrl || picUrl.length==0) {
					return Response.error().message(ReturnMsgConst.RETURN_600049.getMsg());
				}else {
					//发票url无域名组装，逗号分割
					String invoicePicUrl = "";
					for (int j = 0; j < picUrl.length; j++) {
						invoicePicUrl+=picUrl[j] +",";
					}
					if(StringUtils.isNotBlank(invoicePicUrl)) {
						invoicePicUrl = invoicePicUrl.substring(0, invoicePicUrl.length()-1);
					}
					detail.setMakeInvoiceStatus(Const.MAKE_INVOICE_STATUS_2);
					detail.setModifyoper(user.getNickname());
					detail.setModifytime(LocalDateTime.now());
					detail.setInvoicePicUrl(invoicePicUrl);
					detail.setInvoiceTime(LocalDateTime.now());
					makeInvoiceDetailMapper.updateByPrimaryKeySelective(detail);
					return Response.ok();
				}
			}else {
				return Response.error().message(ReturnMsgConst.RETURN_600033.getMsg());
			}
		}
	}

	/**
	 * 查看详情
	 */
	@Override
	public MakeInvoiceDetailVO detail(String id) {
		MakeInvoiceDetailVO detail = makeInvoiceDetailMapper.selectByPrimaryKeyVO(id);
		//查询客户信息
		CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(detail.getCustomerId());
		//纳税人识别号
		detail.setTaxpayerNumber(customerInfo.getTaxpayerNumber());
		//发票抬头
		detail.setCustomerName(customerInfo.getInvoiceTitle());
		//开户行名
		detail.setCustomerOpenBankName(customerInfo.getCustomerOpenBankName());
		//开户行号
		detail.setCustomerOpenBankNo(customerInfo.getCustomerOpenBankNo());
		//开票电话
		detail.setTel(customerInfo.getTel());
		if(detail.getPostStatus()==Const.POST_STATUS_2) {
			//寄送日期
			detail.setSendTime(detail.getModifytime());
		}
		//注册地址
		String customerRegAddress = customerInfo.getCustomerRegAddress();
		//支行名称
		String customerSubOpenBankName = customerInfo.getCustomerSubOpenBankName();
		detail.setCustomerRegAddress(customerRegAddress);
		detail.setCustomerSubOpenBankName(customerSubOpenBankName);
		
		
		String domain = sysConfigService.findValueByKey("domain");
		detail.setInvoiceDemoUrl(domain+customerInfo.getInvoiceDemoUrl());
		
		return detail;
	}

	/**
	 * 寄送保存
	 */
	@Override
	@Transactional
	public Response sendSave(SysUser user, InvoiceForm form) {
		MakeInvoiceDetail detailQuery = new MakeInvoiceDetail();
		detailQuery.setInvoiceNo(form.getInvoiceNo());
		MakeInvoiceDetail detail = makeInvoiceDetailMapper.selectOne(detailQuery);
		if(detail.getPostStatus()==Const.POST_STATUS_1 && detail.getMakeInvoiceStatus() == Const.MAKE_INVOICE_STATUS_2) {
			//根据前端key获取快递公司名称
			SysEnums query = new SysEnums();
			query.setCategory("expressCompany");
			query.setDataStatus(Const.DATA_STATUS_1);
			query.setEnumvalue(Integer.parseInt(form.getExpressCompany()));
			SysEnums sysEnums = sysEnumsMapper.selectOne(query);
			
			
			detail.setExpressCompany(sysEnums.getLiteral());
			detail.setExpressNo(form.getExpressNo());
			detail.setPostStatus(Const.POST_STATUS_2);
			detail.setModifyoper(user.getNickname());
			detail.setModifytime(LocalDateTime.now());
			
			makeInvoiceDetailMapper.updateByPrimaryKeySelective(detail);
			
			return Response.ok();
		}else if(detail.getPostStatus()==Const.POST_STATUS_2){
			return Response.error().message(ReturnMsgConst.RETURN_600035.getMsg());
		}else if(detail.getMakeInvoiceStatus() == Const.MAKE_INVOICE_STATUS_1){
			return Response.error().message(ReturnMsgConst.RETURN_600036.getMsg());
		}else {
			return Response.error().message(ReturnMsgConst.RETURN_600034.getMsg());
		}
	}

}
