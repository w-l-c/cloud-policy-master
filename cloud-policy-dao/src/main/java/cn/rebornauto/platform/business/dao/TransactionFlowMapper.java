package cn.rebornauto.platform.business.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;
import cn.rebornauto.platform.business.entity.TransactionFlow;
import cn.rebornauto.platform.business.entity.TransactionFlowVO;
import cn.rebornauto.platform.business.query.StatQuery;
import cn.rebornauto.platform.business.query.TransactionListQuery;
import cn.rebornauto.platform.common.data.request.Pagination;

public interface TransactionFlowMapper extends Mapper<TransactionFlow> {
	BigDecimal selectInProcessedCustomerAmount(@Param("customerId")String customerId);
	
	List<TransactionFlowVO> selectByQuery(@Param("p") Pagination pagination, @Param("q") TransactionListQuery query);

    long countByQuery(@Param("q") TransactionListQuery query);

    List<TransactionFlowVO> selectRequestSnById(@Param("transactionFlowId")String transactionFlowId);
    
    List<String> selectPayErrorList(@Param("orderId")Integer orderId);
    
    int updatePayStatusByTransactionFlowIds(Map<String, Object> params);
    
    TransactionFlow selectSumAmountByOrderId(@Param("orderId") Integer orderId);
    
    List<TransactionFlow> selectByTransactionFlowIds(Map<String, Object> params);
    
    TransactionFlowVO selectTransactionFlowCountByQuery(@Param("q") TransactionListQuery query);
    
    List<TransactionFlowVO> selectTransactionFlowListByQuery(@Param("p") Pagination pagination, @Param("q") TransactionListQuery query);

	List<TransactionFlowVO> selectTransactionFlowExcelByQuery(@Param("q")TransactionListQuery query);

}