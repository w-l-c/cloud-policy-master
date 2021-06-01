package cn.rebornauto.platform.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rebornauto.platform.business.query.StatQuery;
import cn.rebornauto.platform.business.vo.MonthStatVO;
import cn.rebornauto.platform.business.vo.Stat;
import cn.rebornauto.platform.business.vo.StatCustomerVO;
import cn.rebornauto.platform.business.vo.StatMoneyOrder;
import cn.rebornauto.platform.common.data.request.Pagination;
import tk.mybatis.mapper.common.Mapper;

public interface StatMapper extends Mapper<Stat> {

	Stat selectRechargeStatByQuery(@Param("q") StatQuery query);
	
	Stat selectPaymentStatByQuery(@Param("q") StatQuery query);
	
	List<StatMoneyOrder> selectRechargeStatMoneyOrderByQuery(@Param("q") StatQuery query);
	
	List<StatMoneyOrder> selectPaymentStatMoneyOrderByQuery(@Param("q") StatQuery query);
	
	List<StatCustomerVO> selectStatCustomerByQuery(@Param("q") StatQuery query);

	int monthStatCount(@Param("q") StatQuery query);

	List<MonthStatVO> selectMonthStatList(@Param("q") StatQuery query, @Param("p")Pagination pagination);

	List<MonthStatVO> excelMonthStatList(@Param("q")StatQuery query);
}