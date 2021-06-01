package cn.rebornauto.platform.business.service;

import java.util.List;

import cn.rebornauto.platform.business.form.PaymentChannelForm;
import cn.rebornauto.platform.business.query.PaymentChannelQuery;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.view.PaymentChannelConfigVO;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.sys.entity.SysUser;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Apr 27, 2019 11:52:05 PM
 */
public interface PaymentChannelService {

	Response tongLianBalanceAndDetail(String qDate);
	
	Response sandBalanceAndDetail(String qDate);

	int count(PaymentChannelQuery query);

	List<PaymentChannelConfigVO> list(PaymentChannelQuery query, Pagination pagination, SysUser currentUser);

	Response edit(PaymentChannelForm form);
}
