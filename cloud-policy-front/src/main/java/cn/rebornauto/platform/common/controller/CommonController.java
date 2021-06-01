package cn.rebornauto.platform.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.rebornauto.platform.common.data.view.BankDicOptionVO;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.SysDicOptionVO;
import cn.rebornauto.platform.common.service.CommonService;

@RestController
@RequestMapping("/gzh/common")
public class CommonController extends BaseController {
	
    @Autowired
    private CommonService commonService;    
    
    /**
 	 * 字典 国籍&Id
 	 * 
 	 * @return
 	 */
 	@RequestMapping("/nationalityDic")
 	public Response nationalityDic() {
 		List<SysDicOptionVO> nationalityDics = commonService.selectNationalityDics();
 		return Response.ok().body(nationalityDics);
 	}
 	
 	
    /**
 	 * 字典 证件(类型)名称&id
 	 * 
 	 * @return
 	 */
 	@RequestMapping("/idTypeDic")
 	public Response idTypeDic() {
 		List<SysDicOptionVO> idTypeDics = commonService.selectIdTypeDics();
 		return Response.ok().body(idTypeDics);
 	}
    
    
    /**
	 * 字典 银行名称&code
	 * 
	 * @return
	 */
	@RequestMapping("/bankDic")
	public Response bankDic() {
		List<BankDicOptionVO> bankDics = commonService.selectBankDics();
		return Response.ok().body(bankDics);
	}
	
}
