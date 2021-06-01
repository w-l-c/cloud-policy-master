package cn.rebornauto.platform.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.rebornauto.platform.business.entity.CustomerInfo;
import cn.rebornauto.platform.common.data.view.BankDicOptionVO;
import cn.rebornauto.platform.common.data.view.DaiZhengDicOptionVO;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.service.CommonService;
import cn.rebornauto.platform.sys.entity.SysUser;

@RestController
@RequestMapping("/common")
public class CommonController extends BaseController {
    @Autowired
    private CommonService commonService;
    
    /**
	 * 字典 银行名称&code
	 * 
	 * @return
	 */
	@RequestMapping("/selectBankCodeAndNameList")
	public Response bankDic() {
		List<BankDicOptionVO> bankDics = commonService.selectBankDics();
		return Response.ok().body(bankDics);
	}
	
	
	/**
	 * 字典 代征主体名称&id
	 * 
	 * @return
	 */
	@RequestMapping("/selectDaiZhengList")
	public Response daiZhengDic() {
		List<DaiZhengDicOptionVO> daiZhengDics = commonService.selectDaiZhengDics();
		return Response.ok().body(daiZhengDics);
	}

//	@RequestMapping("/selectPaymentChannelList")
//	public Response selectPaymentChannelList() {
//		List<PaymentChannelVO> list = commonService.selectPaymentChannelList();
//		return Response.ok().body(list);
//	}

	/**
	 *
	 * @return
	 */
	@GetMapping("/selectCustomer")
	public Response selectParam() {
		SysUser sysUser = currentUser();
		List list = new ArrayList();
		if (isAdministrator(sysUser.getCustomerId())){
			list = commonService.selectParamAll();
		} else {
			list = commonService.selectParam4CustomerId(sysUser.getCustomerId());
		}
		return Response.ok().body(list);
	}
	@GetMapping("/selectCustomerInfo")
	public Response selectCustomerInfo() {
		SysUser sysUser = currentUser();
		CustomerInfo customerInfo = commonService.selectCustomerInfo(sysUser.getCustomerId());
		return Response.ok().body(customerInfo);
	}
}
