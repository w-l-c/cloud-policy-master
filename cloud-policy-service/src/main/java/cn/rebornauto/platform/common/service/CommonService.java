package cn.rebornauto.platform.common.service;

import java.util.List;

import cn.rebornauto.platform.business.entity.CustomerInfo;
import cn.rebornauto.platform.common.data.view.BankDicOptionVO;
import cn.rebornauto.platform.common.data.view.DaiZhengDicOptionVO;
import cn.rebornauto.platform.common.data.view.SysDicOptionVO;

public interface CommonService {
    /**
     * 字典 银行名称&code
     * @return
     */
	List<BankDicOptionVO> selectBankDics();
	
	/**
	 * 字典 代征主体名称&id
	 * @return
	 */
	List<DaiZhengDicOptionVO> selectDaiZhengDics();
	
    /**
     * 字典 国籍&Id
     * @return
     */
	List<SysDicOptionVO> selectNationalityDics();
   
	/**
	 * 字典 证件(类型)名称&id
	 * @return
	 */
	List<SysDicOptionVO> selectIdTypeDics();

    List selectParamAll();

	List selectParam4CustomerId(String customerId);

    CustomerInfo selectCustomerInfo(String customerId);
}
