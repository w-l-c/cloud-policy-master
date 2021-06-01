package cn.rebornauto.platform.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rebornauto.platform.business.entity.PaymentChannelConfig;
import cn.rebornauto.platform.business.query.PaymentChannelQuery;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.view.PaymentChannelConfigVO;
import tk.mybatis.mapper.common.Mapper;

public interface PaymentChannelConfigMapper extends Mapper<PaymentChannelConfig> {
	int count(@Param("q") PaymentChannelQuery query);

    List<PaymentChannelConfigVO> list(@Param("q") PaymentChannelQuery query, @Param("p") Pagination pagination);
}