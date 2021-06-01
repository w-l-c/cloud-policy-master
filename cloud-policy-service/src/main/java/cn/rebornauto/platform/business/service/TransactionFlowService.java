package cn.rebornauto.platform.business.service;

import java.util.List;
import java.util.Map;

import cn.rebornauto.platform.business.entity.OrderDetailMerge;
import cn.rebornauto.platform.business.entity.TransactionFlow;
import cn.rebornauto.platform.business.entity.TransactionFlowVO;
import cn.rebornauto.platform.business.query.TransactionListQuery;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.view.Response;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date May 2, 2019 1:52:57 PM
 */
public interface TransactionFlowService {

	TransactionFlow selectOneByMergeId(OrderDetailMerge detailMerge);
	
	List<TransactionFlowVO> pageQuery(Pagination pagination, TransactionListQuery query);
	
	long countQuery(TransactionListQuery query);
	
	int updatePayStatus(String  transactionFlowId,int payStatus,String currUserName);
	
	TransactionFlow selectOne(String transactionFlowId);
	
	List<TransactionFlowVO> selectRequestSnById(String transactionFlowId);
	
	List<String> selectPayErrorList(Integer orderId);
	
	int updatePayStatusByTransactionFlowIds(Map<String, Object> params);
	
	Response closeTransactionFlow(TransactionFlow bean);
	
	List<TransactionFlow> selectByTransactionFlowIds(Map<String, Object> params);
	
	TransactionFlow selectByPrimaryKey(String transactionFlowId);

	TransactionFlowVO selectTransactionFlowCountByQuery(TransactionListQuery query);

	List<TransactionFlowVO> selectTransactionFlowListByQuery(Pagination pagination, TransactionListQuery query);

	List<TransactionFlowVO> selectTransactionFlowExcelByQuery(TransactionListQuery query);
}
