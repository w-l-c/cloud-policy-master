package cn.rebornauto.platform.business.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rebornauto.platform.business.dao.StatMapper;
import cn.rebornauto.platform.business.query.StatQuery;
import cn.rebornauto.platform.business.service.StatService;
import cn.rebornauto.platform.business.vo.MonthStatVO;
import cn.rebornauto.platform.business.vo.Stat;
import cn.rebornauto.platform.business.vo.StatCustomerVO;
import cn.rebornauto.platform.business.vo.StatMoneyOrder;
import cn.rebornauto.platform.common.data.request.Pagination;

/** Title: 统计相关
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 2, 2020 11:18:22 AM
 */
@Service
public class StatServiceImpl implements StatService {
	
	@Autowired
	StatMapper statMapper;

	private static final String queryByDay = "QUERY_BY_DAY";
    private static final String queryByMonth = "QUERY_BY_MONTH";
    private static final String queryByYear = "QUERY_BY_YEAR";
	/**
	 * 充值统计
	 */
	@Override
	public List<Stat> rechargeStat(StatQuery query) {
		List<Stat> statList = new ArrayList<Stat>();
        Integer year = query.getYear();
        if(year==null){
            year = LocalDate.now().getYear();
        }
        Integer month = query.getMonth();
        
    	if(month!=null){
            String monthStr = ""+month;
            if(month<10){
                monthStr = "0"+month;
            }
            LocalDate localDate = LocalDate.of(year, month, 1);
            int dateCount = localDate.lengthOfMonth();
            query.setQueryType(queryByDay);
            for (int i = 1; i <=dateCount; i++) {
                LocalDate date = localDate.withDayOfMonth(i);
                query.setDate(date);
                Stat stat = statMapper.selectRechargeStatByQuery(query);
                if(null==stat) {
                	stat = new Stat();
                }
                if(i<10){
                	stat.setDayOrMonth(monthStr+"-0"+i);
                }else {
                	stat.setDayOrMonth(monthStr+"-"+i);
                }
                statList.add(stat);
            }
        }else {
            LocalDate localDate = LocalDate.of(year, 1, 1);
            query.setQueryType(queryByMonth);
            for (int i = 1; i <=12; i++) {
                LocalDate date = localDate.withMonth(i);
                query.setDate(date);
                Stat stat = statMapper.selectRechargeStatByQuery(query);
                if(null==stat) {
                	stat = new Stat();
                }
                if(i<10){
                	stat.setDayOrMonth(""+year+"-0"+i);
                }else {
                	stat.setDayOrMonth(""+year+"-"+i);
                }
                statList.add(stat);
            }
        }
        return statList;
	}

	/**
	 * 放款统计
	 */
	@Override
	public List<Stat> paymentStat(StatQuery query) {
		List<Stat> statList = new ArrayList<Stat>();
        Integer year = query.getYear();
        if(year==null){
            year = LocalDate.now().getYear();
        }
        Integer month = query.getMonth();
        if(month!=null){
            String monthStr = ""+month;
            if(month<10){
                monthStr = "0"+month;
            }
            LocalDate localDate = LocalDate.of(year, month, 1);
            int dateCount = localDate.lengthOfMonth();
            query.setQueryType(queryByDay);
            for (int i = 1; i <=dateCount; i++) {
                LocalDate date = localDate.withDayOfMonth(i);
                query.setDate(date);
                Stat stat = statMapper.selectPaymentStatByQuery(query);
                if(null==stat) {
                	stat = new Stat();
                }
                if(i<10){
                	stat.setDayOrMonth(monthStr+"-0"+i);
                }else {
                	stat.setDayOrMonth(monthStr+"-"+i);
                }
                statList.add(stat);
            }
        }else {
            LocalDate localDate = LocalDate.of(year, 1, 1);
            query.setQueryType(queryByMonth);
            for (int i = 1; i <=12; i++) {
                LocalDate date = localDate.withMonth(i);
                query.setDate(date);
                Stat stat = statMapper.selectPaymentStatByQuery(query);
                if(null==stat) {
                	stat = new Stat();
                }
                if(i<10){
                	stat.setDayOrMonth(""+year+"-0"+i);
                }else {
                	stat.setDayOrMonth(""+year+"-"+i);
                }
                statList.add(stat);
            }
        }
        return statList;
	}

	@Override
	public List<StatMoneyOrder> rechargeMoneyOrder(StatQuery query) {
		List<StatMoneyOrder> statList = new ArrayList<StatMoneyOrder>();
        Integer year = query.getYear();
        if(year==null){
            year = LocalDate.now().getYear();
        }
        Integer month = query.getMonth();
        
        Integer day = query.getDay();
        
        String dateStr = String.valueOf(year);
    	if(null!=month){
    		if(null!=day && day!=0) {
    			String monthStr = ""+month;
                if(month<10){
                    monthStr = "0"+month;
                }
                String dayStr = ""+day;
                if(day<10){
                	dayStr = "0"+day;
                }
                
                dateStr = dateStr+monthStr+dayStr;
                query.setQueryType(queryByDay);
    		}else {
    			String monthStr = ""+month;
                if(month<10){
                    monthStr = "0"+month;
                }
                dateStr = dateStr+monthStr;
                query.setQueryType(queryByMonth);
    		}
        }else {
            query.setQueryType(queryByYear);
        }
    	query.setDateStr(dateStr);
        statList = statMapper.selectRechargeStatMoneyOrderByQuery(query);
        return statList;
	}

	@Override
	public List<StatMoneyOrder> paymentMoneyOrder(StatQuery query) {
		List<StatMoneyOrder> statList = new ArrayList<StatMoneyOrder>();
        Integer year = query.getYear();
        if(year==null){
            year = LocalDate.now().getYear();
        }
        Integer month = query.getMonth();
        
        Integer day = query.getDay();
        
        String dateStr = String.valueOf(year);
    	if(month!=null){
    		if(null!=day && day!=0) {
    			String monthStr = ""+month;
                if(month<10){
                    monthStr = "0"+month;
                }
                String dayStr = ""+day;
                if(day<10){
                	dayStr = "0"+day;
                }
                
                dateStr = dateStr+monthStr+dayStr;
                query.setQueryType(queryByDay);
    		}else {
	            String monthStr = ""+month;
	            if(month<10){
	                monthStr = "0"+month;
	            }
	            dateStr = dateStr+monthStr;
	            query.setQueryType(queryByMonth);
    		}
        }else {
            query.setQueryType(queryByYear);
        }
    	query.setDateStr(dateStr);
        statList = statMapper.selectPaymentStatMoneyOrderByQuery(query);
		return statList;
	}

	@Override
	public List<StatCustomerVO> selectStatCustomerByQuery(StatQuery query) {
		return statMapper.selectStatCustomerByQuery(query);
	}

	@Override
	public int monthStatCount(StatQuery query) {
		return statMapper.monthStatCount(query);
	}

	@Override
	public List<MonthStatVO> selectMonthStatList(StatQuery query, Pagination pagination) {
		return statMapper.selectMonthStatList(query,pagination);
	}

	@Override
	public List<MonthStatVO> excelMonthStatList(StatQuery query) {
		return statMapper.excelMonthStatList(query);
	}

}
