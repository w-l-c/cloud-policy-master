package cn.rebornauto.platform.business.service.impl;

import java.time.LocalDateTime;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rebornauto.platform.business.dao.PostAddressMapper;
import cn.rebornauto.platform.business.entity.PostAddress;
import cn.rebornauto.platform.business.form.PostAddressForm;
import cn.rebornauto.platform.business.service.PostAddressService;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.ReturnMsgConst;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.sys.entity.SysUser;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Mar 17, 2020 3:07:57 PM
 */
@Service
public class PostAddressServiceImpl implements PostAddressService {

	@Autowired
	PostAddressMapper postAddressMapper;

	/**
	 * 查询邮寄地址
	 */
	@Override
	public PostAddress detail(String customerId) {
		PostAddress record = new PostAddress();
		record.setCustomerId(customerId);
		return postAddressMapper.selectOne(record);
	}

	/**
	 * 保存邮寄地址
	 */
	@Override
	public Response save(PostAddressForm form, SysUser user) {
		try {
			String mobile = form.getMobile();
			if(StringUtils.isBlank(mobile)||(StringUtils.isNotBlank(mobile)&&mobile.length()!=11)) {
				return Response.error().message(ReturnMsgConst.RETURN_600040.getMsg());
			}
			
			PostAddress record = new PostAddress();
			record.setCustomerId(user.getCustomerId());
			record.setDataStatus(Const.DATA_STATUS_1);
			PostAddress bean = postAddressMapper.selectOne(record);
			
			if(null==bean) {
				record.setAddress(form.getAddress());
				record.setMobile(form.getMobile());
				record.setReceiver(form.getReceiver());
				record.setCreateoper(user.getNickname());
				record.setCreatetime(LocalDateTime.now());
				postAddressMapper.insertSelective(record);
			}else {
				bean.setAddress(form.getAddress());
				bean.setMobile(form.getMobile());
				bean.setReceiver(form.getReceiver());
				bean.setModifyoper(user.getNickname());
				bean.setModifytime(LocalDateTime.now());
				postAddressMapper.updateByPrimaryKeySelective(bean);
			}
			return Response.ok();
		} catch (Exception e) {
			return Response.error().message(ReturnMsgConst.RETURN_600038.getMsg()+" "+e.getMessage());
		}
	}
}
