package cn.rebornauto.platform.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;
import cn.rebornauto.platform.business.entity.CustomerInfo;
import cn.rebornauto.platform.business.query.CustomerInfoQuery;
import cn.rebornauto.platform.business.vo.CustomerVo;
import cn.rebornauto.platform.common.data.request.Pagination;

public interface CustomerInfoMapper extends Mapper<CustomerInfo> {

	int countByQuery(@Param("q") CustomerInfoQuery query);

	List<CustomerVo> pageQuery(@Param("p")Pagination pagination, @Param("q")CustomerInfoQuery query);
	
}