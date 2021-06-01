package cn.rebornauto.platform.business.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rebornauto.platform.business.dao.OrderMapper;
import cn.rebornauto.platform.business.dao.TransactionFlowMapper;
import cn.rebornauto.platform.business.entity.OrderDetailMerge;
import cn.rebornauto.platform.business.entity.TransactionFlow;
import cn.rebornauto.platform.business.entity.TransactionFlowVO;
import cn.rebornauto.platform.business.query.TransactionListQuery;
import cn.rebornauto.platform.business.service.TransactionFlowService;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.pay.tonglian.Enum.PayStatusEnum;
import tk.mybatis.mapper.entity.Example;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date May 2, 2019 1:53:34 PM
 */
@Service
public class TransactionFlowServiceImpl implements TransactionFlowService {

	@Autowired
	TransactionFlowMapper transactionFlowMapper;
	
	@Autowired
	OrderMapper orderMapper;
	
	@Override
	public TransactionFlow selectOneByMergeId(OrderDetailMerge detailMerge) {
		TransactionFlow query = new TransactionFlow();
		query.setMergeId(detailMerge.getMergeId());
//		query.setAgentName(detailVo.getAgentName());
//		query.setOrderId(detailVo.getOrderId());
//		query.setOpenBankNo(detailVo.getOpenBankNo());
//		query.setIdcardno(detailVo.getIdcardno());
		return transactionFlowMapper.selectOne(query);
	}
	
	@Override
    public List<TransactionFlowVO> pageQuery(Pagination pagination, TransactionListQuery query) {
        return transactionFlowMapper.selectByQuery(pagination, query);
    }
	
	@Override
    public long countQuery(TransactionListQuery query) {
        return transactionFlowMapper.countByQuery(query);
    }
	
	public TransactionFlow selectOne(String transactionFlowId) {
		TransactionFlow transactionFlow = new TransactionFlow();
		transactionFlow.setTransactionFlowId(transactionFlowId);
        return transactionFlowMapper.selectOne(transactionFlow);
    }
	
	/**
	 * 修改支付状态
	 * @param transactionFlowId
	 * @param payStatus
	 * @param currUserName
	 * @return
	 */
	public int updatePayStatus(String  transactionFlowId,int payStatus,String currUserName) {
		Example example = new Example(TransactionFlow.class);
		example.createCriteria().andEqualTo("transactionFlowId", transactionFlowId);
		TransactionFlow record = new TransactionFlow();
		record.setPayStatus(payStatus);
		record.setModifyoper(currUserName);
		record.setModifytime(LocalDateTime.now());
		return transactionFlowMapper.updateByExampleSelective(record, example);
	}

	@Override
	public List<TransactionFlowVO> selectRequestSnById(
			String transactionFlowId) {
		return transactionFlowMapper.selectRequestSnById(transactionFlowId);
	}
	
	/**
	 * 查找支付失败的数据
	 * @param orderId
	 * @return
	 */
	public List<String> selectPayErrorList(Integer orderId){
		return transactionFlowMapper.selectPayErrorList(orderId);
	}
	
	/**
	 * 批量更新状态
	 * @param params
	 * @return
	 */
	public int updatePayStatusByTransactionFlowIds(Map<String, Object> params) {
		return transactionFlowMapper.updatePayStatusByTransactionFlowIds(params);
	}
	
	/**
	 * 查询数据
	 * @param params
	 * @return
	 */
	public List<TransactionFlow> selectByTransactionFlowIds(Map<String, Object> params) {
		return transactionFlowMapper.selectByTransactionFlowIds(params);
	}
	
	@Override
	@Transactional
	public Response closeTransactionFlow(TransactionFlow bean) {
		Response resp = new Response();
		try {
			//更新关闭状态
			Example example = new Example(TransactionFlow.class);
			example.createCriteria().andEqualTo("transactionFlowId", bean.getTransactionFlowId());
			TransactionFlow record = new TransactionFlow();
			record.setPayStatus(Integer.parseInt(PayStatusEnum.CLOSED.getCode()));
			record.setTransactionFlowId(bean.getTransactionFlowId());
			transactionFlowMapper.updateByExampleSelective(record, example);
			//合并数据减少一条
			orderMapper.updateMergeCountByOrderId(bean.getOrderId());
			//重新汇总支付统计
			orderMapper.updatePayStatByOrderId(bean.getOrderId());
			
			//更新订单状态
      		orderMapper.updateOrderStatusByOrderId(bean.getOrderId());
			return resp.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return resp.error().message("关闭支付异常:"+e.getMessage());
		}
		
	}
	
	@Override
	public TransactionFlow selectByPrimaryKey(String transactionFlowId) {
		return transactionFlowMapper.selectByPrimaryKey(transactionFlowId);
	}
	
	
	@Override
    public List<TransactionFlowVO> selectTransactionFlowListByQuery(Pagination pagination, TransactionListQuery query) {
        return transactionFlowMapper.selectTransactionFlowListByQuery(pagination, query);
    }
	
	@Override
    public TransactionFlowVO selectTransactionFlowCountByQuery(TransactionListQuery query) {
        return transactionFlowMapper.selectTransactionFlowCountByQuery(query);
    }

	@Override
	public List<TransactionFlowVO> selectTransactionFlowExcelByQuery(TransactionListQuery query) {
		return transactionFlowMapper.selectTransactionFlowExcelByQuery(query);
	}
	
}
