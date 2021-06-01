package cn.rebornauto.platform.business.service;

import java.util.List;

import cn.rebornauto.platform.business.query.StatQuery;
import cn.rebornauto.platform.business.vo.MonthStatVO;
import cn.rebornauto.platform.business.vo.Stat;
import cn.rebornauto.platform.business.vo.StatCustomerVO;
import cn.rebornauto.platform.business.vo.StatMoneyOrder;
import cn.rebornauto.platform.common.data.request.Pagination;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 2, 2020 11:17:58 AM
 */
public interface StatService {

	List<Stat> rechargeStat(StatQuery query);
	
	List<Stat> paymentStat(StatQuery query);

	List<StatMoneyOrder> rechargeMoneyOrder(StatQuery query);

	List<StatMoneyOrder> paymentMoneyOrder(StatQuery query);

	List<StatCustomerVO> selectStatCustomerByQuery(StatQuery query);

	int monthStatCount(StatQuery query);

	List<MonthStatVO> selectMonthStatList(StatQuery query, Pagination pagination);

	List<MonthStatVO> excelMonthStatList(StatQuery query);
}
