package cn.rebornauto.platform.business.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.rebornauto.platform.business.dao.PaymentChannelConfigMapper;
import cn.rebornauto.platform.business.entity.PayInfoTable;
import cn.rebornauto.platform.business.entity.PaymentChannelConfig;
import cn.rebornauto.platform.business.form.PaymentChannelForm;
import cn.rebornauto.platform.business.query.PaymentChannelQuery;
import cn.rebornauto.platform.business.service.DaiZhengInfoService;
import cn.rebornauto.platform.business.service.PaymentChannelConfigService;
import cn.rebornauto.platform.business.service.PaymentChannelService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.view.PaymentChannelConfigVO;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.util.BigDecimalUtil;
import cn.rebornauto.platform.common.util.DateUtils;
import cn.rebornauto.platform.common.util.StringUtil;
import cn.rebornauto.platform.pay.sandpay.dsf.demo.SandBase;
import cn.rebornauto.platform.pay.sandpay.entity.SandInfo;
import cn.rebornauto.platform.pay.sandpay.enums.SandInterfaceEnums;
import cn.rebornauto.platform.pay.sandpay.request.QueryBalanceRequest;
import cn.rebornauto.platform.pay.sandpay.response.QueryBalanceResponse;
import cn.rebornauto.platform.pay.tonglian.Enum.PayStatusEnum;
import cn.rebornauto.platform.pay.tonglian.aipg.etdtlquery.EtDtl;
import cn.rebornauto.platform.pay.tonglian.bean.AccountDetail;
import cn.rebornauto.platform.pay.tonglian.entity.TongLianInfo;
import cn.rebornauto.platform.pay.tonglian.response.QueryDetailResponse;
import cn.rebornauto.platform.pay.tonglian.utils.DateUtil;
import cn.rebornauto.platform.payment.service.impl.tonglianpay.BaseHandler;
import cn.rebornauto.platform.sys.entity.SysUser;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 27, 2019 11:54:45 PM
 */
@Service
public class PaymentChannelServiceImpl implements PaymentChannelService {
	
	public static  Logger logger = LoggerFactory.getLogger(PaymentChannelServiceImpl.class);

	@Autowired
	PaymentChannelConfigMapper paymentChannelConfigMapper;
	
	@Autowired
	DaiZhengInfoService daiZhengInfoService;
	
	@Autowired
	SysDicService sysDicService;
	
	@Autowired
	PaymentChannelConfigService paymentChannelConfigService;
	
	@Autowired
	BaseHandler baseHandler;
	
	@Override
	public Response tongLianBalanceAndDetail(String qDate) {
		PayInfoTable body = PayInfoTable.factory();
		/****????????????****/
		//?????????????????? ??????????????????
		String sysPaySwitch = sysDicService.selectSysPaySwitch();
		TongLianInfo tongLianInfo = paymentChannelConfigService.getTongLianInfo(Const.SYS_PAYMENT_CHANNEL_TONGLIAN, sysPaySwitch);
//		tongLianInfo.setUrl("https://tlt.allinpay.com/aipg/ProcessServlet");
//		tongLianInfo.setMerchantId("200290000029891");
//		tongLianInfo.setPfxPassword("111111");
//		tongLianInfo.setPassword("111111");
//		tongLianInfo.setUserName("20029000002989104");
//		tongLianInfo.setTltcerPath("/Users/si/Downloads/????????????/????????????/allinpay-pds.cer");
//		tongLianInfo.setPfxPath("/Users/si/Downloads/????????????/????????????/20029000002989104.p12");
//		tongLianInfo.setBusinessCodeDf("09900");
		
		
		BigDecimal leftAmount = BigDecimal.ZERO;
		Map<String, Object> result = new HashMap<String,Object>();
		try {
			result = baseHandler.queryMerchantLeftAmount(tongLianInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object temp = result.get("balance");
		if (null != temp) {
			leftAmount = new BigDecimal(result.get("balance").toString()).multiply(new BigDecimal("0.01"));
		}
		body.setBalance(BigDecimalUtil.formatTosepara(leftAmount));
		
		/****????????????****/
		
		/****????????????****/
		QueryDetailResponse queryDetailResponse = new QueryDetailResponse();
       
		try {
			qDate = qDate.replaceAll("-", "");
			result = baseHandler.queryMerchantDetail(tongLianInfo,qDate);
			
			//????????????
			String status = (String) result.get("status");
			
			List<AccountDetail> list = new ArrayList<AccountDetail>();
			if(status.contentEquals(PayStatusEnum.SUCCESS.getCode())) {
				//????????????
				Integer sum = (Integer) result.get("sum");
				//??????????????????
				List<EtDtl> etDtlList = (List<EtDtl>) result.get("etDtlList");
				if(null!=etDtlList && etDtlList.size()>0) {
					for (int i = 0; i < etDtlList.size(); i++) {
						AccountDetail detail = new AccountDetail();
						detail.setAcctno(etDtlList.get(i).getACCTNO());
						detail.setAmount(BigDecimalUtil.formatTosepara(new BigDecimal(etDtlList.get(i).getAMOUNT()).multiply(new BigDecimal("0.01"))));
						detail.setCrdr(etDtlList.get(i).getCRDR());
						detail.setMemo(etDtlList.get(i).getMEMO());
						detail.setPaycode(etDtlList.get(i).getPAYCODE());
						detail.setPostdate(DateUtils.dateToLocalDateTime(DateUtil.parse(etDtlList.get(i).getPOSTDATE(), "yyyyMMddHHmmss")));
						detail.setSn(etDtlList.get(i).getSN());
						detail.setTrxid(etDtlList.get(i).getTRXID());
						detail.setVoucherno(etDtlList.get(i).getVOUCHERNO());
						list.add(detail);
					}
				}
				queryDetailResponse.setSum(sum);
				queryDetailResponse.setList(list);
				queryDetailResponse.setMsg("????????????!");
			}else {
				queryDetailResponse.setMsg("????????????!");
			}
			queryDetailResponse.setStatus(status);
			
//			AccountDetail detail = new AccountDetail();
//			detail.setAmount(BigDecimalUtil.formatTosepara(new BigDecimal("100").multiply(new BigDecimal("0.01"))));
//			detail.setTrxid("12204353");
//			detail.setPostdate(DateUtils.dateToLocalDateTime(DateUtil.parse(DateUtil.getAllTime(), "yyyyMMddHHmmss")));
//			list.add(detail);
			
			body.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/****????????????****/
		
		return Response.ok().body(body);
	}
	
	public Response sandBalanceAndDetail(String qDate) {
		logger.info(SandInterfaceEnums.QUERY_BALANCE.getMsg());
		QueryBalanceResponse queryBalanceResponse = new QueryBalanceResponse();
		
		//?????????????????? ??????????????????
        String sysPaySwitch = sysDicService.selectSysPaySwitch();
		
        SandInfo sandInfo = paymentChannelConfigService.getSandInfo(Const.SYS_PAYMENT_CHANNEL_SAND, sysPaySwitch);
//        sandInfo.setSandsdkSandCertPath("/Users/si/Desktop/sand_sc/sand.cer");
//        sandInfo.setSandsdkSignCertPath("/Users/si/Desktop/sand_sc/10130872.pfx");
//        sandInfo.setSandsdkUrl("https://caspay.sandpay.com.cn/agent-main/openapi/");
        
        
        QueryBalanceRequest request = new QueryBalanceRequest();
        
        JSONObject jsonRequest = (JSONObject) JSONObject.toJSON(request);
		
		String reqAddr = SandInterfaceEnums.QUERY_BALANCE.getCode();//???????????????????????????
		
		JSONObject resp = SandBase.requestServer(jsonRequest.toJSONString(), reqAddr, SandBase.MER_BALANCE_QUERY, sandInfo);
		
		System.out.println("resp:"+resp);	
		if(resp!=null) {
			System.out.println("????????????["+resp.getString("respCode")+"]");	
			logger.info("????????????["+resp.getString("respCode")+"]");
			System.out.println("???????????????["+resp.getString("respDesc")+"]");
			logger.info("???????????????["+resp.getString("respDesc")+"]");
			System.out.println("?????????["+resp.getString("balance")+"]");
			logger.info("?????????["+resp.getString("balance")+"]");
			
			queryBalanceResponse = JSON.toJavaObject(resp,QueryBalanceResponse.class);
			queryBalanceResponse.setBalance(StringUtil.getSandBalanceAmount(queryBalanceResponse.getBalance()));
		
		}else {
			System.out.println("??????????????????????????????");
			logger.error("??????????????????????????????");	
		}
		PayInfoTable body = PayInfoTable.factory();
		if(StringUtils.isBlank(queryBalanceResponse.getBalance())) {
			queryBalanceResponse.setBalance("0");
		}
		body.setBalance(queryBalanceResponse.getBalance());

        return Response.ok().body(body);
	}

	@Override
	public int count(PaymentChannelQuery query) {
		return paymentChannelConfigMapper.count(query);
	}

	@Override
	public List<PaymentChannelConfigVO> list(PaymentChannelQuery query, Pagination pagination, SysUser currentUser) {
		return paymentChannelConfigMapper.list(query, pagination);
	}

	@Override
	public Response edit(PaymentChannelForm form) {
		//???????????????????????????
		PaymentChannelConfig query = new PaymentChannelConfig();
		query.setKey(form.getKey());
		query.setPaymentChannelId(form.getPaymentChannelId());
		query.setSysSwitch(form.getSysSwitch());
		PaymentChannelConfig paymentChannelConfig = paymentChannelConfigMapper.selectOne(query);
		
		if(0!=form.getId()) {//??????
			if(null!=paymentChannelConfig & paymentChannelConfig.getId()!=form.getId()) {
				return Response.error().message("?????????????????????????????????????????????!");
			}
			PaymentChannelConfig record = new PaymentChannelConfig();
			record.setId(form.getId());
			record.setKey(form.getKey());
			record.setModifyoper(form.getCurrUserName());
			record.setModifytime(LocalDateTime.now());
			record.setPaymentChannelId(form.getPaymentChannelId());
			record.setRemark(form.getRemark());
			record.setSysSwitch(form.getSysSwitch());
			record.setValue(form.getValue());
			record.setDataStatus(null!=form.getDataStatus()?form.getDataStatus():Const.DATA_STATUS_1);
			paymentChannelConfigMapper.updateByPrimaryKeySelective(record);
		}else {//??????
			if(null!=paymentChannelConfig) {
				return Response.error().message("???????????????????????????????????????????????????!");
			}
			
			PaymentChannelConfig record = new PaymentChannelConfig();
			record.setKey(form.getKey());
			record.setCreateoper(form.getCurrUserName());
			record.setCreatetime(LocalDateTime.now());
			record.setPaymentChannelId(form.getPaymentChannelId());
			record.setRemark(form.getRemark());
			record.setSysSwitch(form.getSysSwitch());
			record.setValue(form.getValue());
			record.setDataStatus(Const.DATA_STATUS_1);
			paymentChannelConfigMapper.insertSelective(record);
		}
		return Response.ok();
	}
}


