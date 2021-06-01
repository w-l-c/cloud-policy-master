package cn.rebornauto.platform.business.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rebornauto.platform.business.entity.OrderDetail;
import cn.rebornauto.platform.business.entity.OrderDetailVO;
import cn.rebornauto.platform.business.query.OrderDetailQuery;
import cn.rebornauto.platform.common.data.request.Pagination;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date May 2, 2019 11:06:00 AM
 */
public interface OrderDetailService {

	OrderDetail selectSumAmountByOrderId(@Param("orderId") Integer orderId);
	
	List<OrderDetailVO> selectUnpayOrderInfoByOrderId(@Param("orderId") Integer orderId);

	List<OrderDetailVO> pageDetailQuery(Pagination pagination, OrderDetailQuery query);
	
	long countDetailQuery(OrderDetailQuery query);
	
	List<OrderDetailVO> exportPayErrorDataExcel(Integer orderId);
}
