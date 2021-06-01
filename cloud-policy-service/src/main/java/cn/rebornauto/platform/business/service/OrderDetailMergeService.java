package cn.rebornauto.platform.business.service;

import java.util.List;

import cn.rebornauto.platform.business.entity.OrderDetailVO;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.sys.entity.SysUser;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date May 19, 2019 8:22:48 AM
 */
public interface OrderDetailMergeService {

	Response saveMergeAndUpdateDetail(List<OrderDetailVO> orderDetailVOList,String sysPaySwitch,SysUser sysUser);
	
	Response saveAndUpdateDetail(List<OrderDetailVO> orderDetailVOList,String sysPaySwitch,SysUser sysUser);
}
