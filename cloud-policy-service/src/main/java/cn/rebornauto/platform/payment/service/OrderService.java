package cn.rebornauto.platform.payment.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.rebornauto.platform.business.entity.Order;
import cn.rebornauto.platform.business.entity.OrderDetailVO;
import cn.rebornauto.platform.business.entity.OrderVO;
import cn.rebornauto.platform.business.entity.TransactionFlow;
import cn.rebornauto.platform.business.form.OrderForm;
import cn.rebornauto.platform.business.query.OrderQuery;
import cn.rebornauto.platform.business.vo.QuotaVO;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.sys.entity.SysUser;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 30, 2019 1:46:37 PM
 */
public interface OrderService {

	List<OrderVO> pageQuery(Pagination pagination, OrderQuery query);
	
	long countQuery(OrderQuery query);
	
	Response singleUpload(OrderForm form,boolean isAdmin);
	
	Response batchUpload(OrderForm form,boolean isAdmin);
	
	Response examinePayment(OrderForm form,String sysPaySwitch,HttpServletRequest req,SysUser currentUser,String transactionFlowId);
	
	Response checkQuotaIsEnough(List<OrderDetailVO> orderDetailList,String customerId);
	
	Response save(OrderForm form,boolean isAdmin);
	
	Order selectOne(Order param);

	Response payApply(OrderForm form,boolean isAdmin);
	
	boolean isTongLianPayTime();
	
	int updateCheckinfoByOrderId(Integer orderId,String checkoper,Integer checkStatus,String remark);
	
	Response paymentAgain(OrderForm form,String sysPaySwitch,HttpServletRequest req,SysUser currentUser);
	
	Response cancelOrderDetail(OrderForm form,boolean isAdmin);
	
	Response batchPaymentAgain(OrderForm form,String sysPaySwitch,HttpServletRequest req,SysUser currentUser,String transactionFlowId);
	
	int updateMergeCountByOrderId(Integer orderId,int mergeCount);

	Response createMergeAndTransflowAddQuota(List<OrderDetailVO> orderDetailVOList, String isMerge, String sysPaySwitch,
			SysUser currentUser, OrderForm form,QuotaVO quotaVO);
	
	void addQuotaPayAgain(QuotaVO quotaVO,SysUser currentUser,TransactionFlow transactionFlow,Integer paymentChannelId);
	
	void batchPaymentAgainInit(QuotaVO quotaVO,SysUser currentUser,List<String> transactionFlowIdList,Integer paymentChannelId);
	
	Order selectByPrimaryKey(Integer id);

	void closeOrder(int id);

	List<OrderVO> selectPaySuccessExcelByQuery(OrderQuery query);
}
