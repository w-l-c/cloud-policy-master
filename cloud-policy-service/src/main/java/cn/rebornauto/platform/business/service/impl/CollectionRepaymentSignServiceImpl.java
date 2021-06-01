package cn.rebornauto.platform.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rebornauto.platform.business.dao.CollectionRepaymentSignMapper;
import cn.rebornauto.platform.business.entity.CollectionRepaymentSignVO;
import cn.rebornauto.platform.business.query.CollectionRepaymentSignQuery;
import cn.rebornauto.platform.business.service.CollectionRepaymentSignService;
import cn.rebornauto.platform.common.data.request.Pagination;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date May 3, 2019 8:10:02 AM
 */
@Service
public class CollectionRepaymentSignServiceImpl implements
		CollectionRepaymentSignService {

	@Autowired
	CollectionRepaymentSignMapper collectionRepaymentSignMapper;
	
	@Override
	public List<CollectionRepaymentSignVO> pageQuery(Pagination pagination,
			CollectionRepaymentSignQuery query) {
		return collectionRepaymentSignMapper.selectByQuery(pagination, query);
	}

	@Override
	public long countQuery(CollectionRepaymentSignQuery query) {
		return collectionRepaymentSignMapper.countByQuery(query);
	}
	
	@Override
	public List<CollectionRepaymentSignVO> selectInProcessedFromSandPay() {
		return collectionRepaymentSignMapper.selectInProcessedFromSandPay();
	}
	

}
