package cn.rebornauto.platform.business.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rebornauto.platform.business.dao.OrderDetailMapper;
import cn.rebornauto.platform.business.dao.OrderDetailMergeMapper;
import cn.rebornauto.platform.business.entity.OrderDetailMerge;
import cn.rebornauto.platform.business.entity.OrderDetailVO;
import cn.rebornauto.platform.business.service.OrderDetailMergeService;
import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.ReturnMsgConst;
import cn.rebornauto.platform.common.SysConfigKey;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.util.ListGroup;
import cn.rebornauto.platform.common.util.LogUtil;
import cn.rebornauto.platform.common.util.RandomUtil;
import cn.rebornauto.platform.common.util.SortList;
import cn.rebornauto.platform.payment.service.OrderService;
import cn.rebornauto.platform.sys.entity.SysUser;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date May 19, 2019 8:23:05 AM
 */
@Service
public class OrderDetailMergeServiceImpl implements OrderDetailMergeService {

	@Autowired
	SysConfigService sysConfigService;
	
	@Autowired
	OrderDetailMapper orderDetailMapper;
	
	@Autowired
	OrderDetailMergeMapper orderDetailMergeMapper;
	
	@Autowired
	OrderService orderService;
	
	/**
	 * 更新订单明细表以及插入汇总表
	 */
	@Override
	public Response saveMergeAndUpdateDetail(List<OrderDetailVO> orderDetailVOList,String sysPaySwitch,SysUser sysUser) {
		Response resp = new Response();
		List<OrderDetailMerge> groupList = new ArrayList<OrderDetailMerge>();
		try {
			//获取代理人最高可支付额度
			String agentQuota = sysConfigService.findValueByKey(SysConfigKey.agentQuota, sysPaySwitch);
			SortList<OrderDetailVO> sortList = new SortList<OrderDetailVO>();  
	        sortList.sort(orderDetailVOList, "getAmount", "desc");  
			groupList = ListGroup.getListByGroup(orderDetailVOList,new BigDecimal(agentQuota));
			for (OrderDetailMerge bean : groupList) {
				//更新明细表合并编号
				String[] str = bean.getRemark().split(",");
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("ids", str);
				params.put("mergeId", bean.getMergeId());
				params.put("modifyoper", sysUser.getNickname());
				orderDetailMapper.updateDetailByIds(params);
				//插入汇总表
				bean.setCreatetime(LocalDateTime.now());
				bean.setCreateoper(sysUser.getNickname());
				orderDetailMergeMapper.insertSelective(bean);
			}
			orderService.updateMergeCountByOrderId(orderDetailVOList.get(0).getOrderId(), groupList.size());
			return resp.ok().body(groupList);
		} catch (Exception e) {
			e.printStackTrace();
			return resp.error().message(ReturnMsgConst.RETURN_600029.getMsg()+e.getMessage());
		}
	}
	
	/**
	 * 更新订单明细表以及插入汇总表
	 */
	@Override
	public Response saveAndUpdateDetail(List<OrderDetailVO> orderDetailVOList,String sysPaySwitch,SysUser sysUser) {
		Response resp = new Response();
		List<OrderDetailMerge> groupList = new ArrayList<OrderDetailMerge>();
		try {
			for (OrderDetailVO bean : orderDetailVOList) {
				String mergeId = RandomUtil.randomId();
				//更新明细表合并编号
				String[] str = {String.valueOf(bean.getId())};
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("ids", str);
				params.put("mergeId", mergeId);
				params.put("modifyoper", sysUser.getNickname());
				orderDetailMapper.updateDetailByIds(params);
				//插入汇总表
				OrderDetailMerge temp = new OrderDetailMerge();
				temp.setAmount(bean.getAmount());
				temp.setOpenBankNo(bean.getOpenBankNo());
				temp.setMergeId(mergeId);
				temp.setRemark(bean.getId().toString());
				temp.setOrderId(bean.getOrderId());
				temp.setDaiZhengId(bean.getDaiZhengId());
				temp.setPaymentChannelId(bean.getPaymentChannelId());
				temp.setAgentName(bean.getAgentName());
				temp.setIdcardno(bean.getIdcardno());
				temp.setDataStatus(Const.DATA_STATUS_1);
				temp.setCreatetime(LocalDateTime.now());
				temp.setCreateoper(sysUser.getNickname());
				orderDetailMergeMapper.insertSelective(temp);
				groupList.add(temp);
			}
			orderService.updateMergeCountByOrderId(orderDetailVOList.get(0).getOrderId(), groupList.size());
			LogUtil.info("===========groupListSize:"+groupList.size());
			return resp.ok().body(groupList);
		} catch (Exception e) {
			e.printStackTrace();
			return resp.error().message(ReturnMsgConst.RETURN_600029.getMsg()+e.getMessage());
		}
	}
}
