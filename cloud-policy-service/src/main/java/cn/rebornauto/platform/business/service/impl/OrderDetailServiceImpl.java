package cn.rebornauto.platform.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rebornauto.platform.business.dao.OrderDetailMapper;
import cn.rebornauto.platform.business.entity.OrderDetail;
import cn.rebornauto.platform.business.entity.OrderDetailVO;
import cn.rebornauto.platform.business.query.OrderDetailQuery;
import cn.rebornauto.platform.business.service.OrderDetailService;
import cn.rebornauto.platform.common.data.request.Pagination;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date May 2, 2019 11:06:18 AM
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	OrderDetailMapper orderDetailMapper;

	@Override
	public OrderDetail selectSumAmountByOrderId(Integer orderId) {
		return orderDetailMapper.selectSumAmountByOrderId(orderId);
	}

	@Override
	public List<OrderDetailVO> selectUnpayOrderInfoByOrderId(Integer orderId) {
		return orderDetailMapper.selectUnpayOrderInfoByOrderId(orderId);
	}
	
	@Override
    public List<OrderDetailVO> pageDetailQuery(Pagination pagination, OrderDetailQuery query) {
        return orderDetailMapper.selectByDetailQuery(pagination, query);
    }
	
	@Override
    public long countDetailQuery(OrderDetailQuery query) {
        return orderDetailMapper.countByDetailQuery(query);
    }
	
	@Override
	public List<OrderDetailVO> exportPayErrorDataExcel(Integer orderId) {
        return orderDetailMapper.exportPayErrorDataExcel(orderId);
    }
}
