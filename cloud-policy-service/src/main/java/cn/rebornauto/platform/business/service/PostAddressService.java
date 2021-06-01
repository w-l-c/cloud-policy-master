package cn.rebornauto.platform.business.service;

import cn.rebornauto.platform.business.entity.PostAddress;
import cn.rebornauto.platform.business.form.PostAddressForm;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.sys.entity.SysUser;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Mar 17, 2020 3:07:41 PM
 */
public interface PostAddressService {

	PostAddress detail(String customerId);

	Response save(PostAddressForm form, SysUser user);

}
