package cn.rebornauto.platform.common.util;
/** Title: 数据合并
 * Description: 
 * Company: 
 * @author kgc
 * @date May 17, 2019 3:36:17 PM
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.rebornauto.platform.business.entity.OrderDetail;
import cn.rebornauto.platform.business.entity.OrderDetailMerge;
import cn.rebornauto.platform.business.entity.OrderDetailVO;
import cn.rebornauto.platform.common.Const;

public class ListGroup {

	public static void main(String[] args) {
		List<OrderDetailVO> list = new ArrayList<OrderDetailVO>();
		OrderDetailVO a1 = new OrderDetailVO();
		a1.setId(1);
		a1.setAmount(new BigDecimal("98000"));
		a1.setOpenBankNo("AAA");
		list.add(a1);
		OrderDetailVO a2 = new OrderDetailVO();
		a2.setId(2);
		a2.setAmount(new BigDecimal("18000"));
		a2.setOpenBankNo("AAA");
		list.add(a2);
		OrderDetailVO a3 = new OrderDetailVO();
		a3.setId(3);
		a3.setAmount(new BigDecimal("78000"));
		a3.setOpenBankNo("AAA");
		list.add(a3);
		OrderDetailVO a4 = new OrderDetailVO();
		a4.setId(4);
		a4.setAmount(new BigDecimal("66000"));
		a4.setOpenBankNo("BBB");
		list.add(a4);
		OrderDetailVO a5 = new OrderDetailVO();
		a5.setId(5);
		a5.setAmount(new BigDecimal("16000"));
		a5.setOpenBankNo("BBB");
		list.add(a5);
		OrderDetailVO a6 = new OrderDetailVO();
		a6.setId(6);
		a6.setAmount(new BigDecimal("16000"));
		a6.setOpenBankNo("CCC");
		list.add(a6);
		OrderDetailVO a7 = new OrderDetailVO();
		a7.setId(7);
		a7.setAmount(new BigDecimal("82000"));
		a7.setOpenBankNo("CCC");
		list.add(a7);
		OrderDetailVO a8 = new OrderDetailVO();
		a8.setId(8);
		a8.setAmount(new BigDecimal("20000"));
		a8.setOpenBankNo("BBB");
		list.add(a8);
		
		BigDecimal amountLimit = new BigDecimal("98000");

		SortList<OrderDetailVO> sortList = new SortList<OrderDetailVO>();  
        sortList.sort(list, "getAmount", "desc");  
        
        for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getId()+"  "+list.get(i).getOpenBankNo()+"  "+list.get(i).getAmount());
		}
        
		List<OrderDetailMerge> groupList = getListByGroup(list,amountLimit);
		for (OrderDetailMerge bean : groupList) {
			System.out.print(bean.getOpenBankNo() + "		");
			System.out.print(bean.getAmount() + "		");
			System.out.print(bean.getMergeId() + "		");
			System.out.println(bean.getRemark());
		}
		
		
	}

	/**
	 * 分组根据额度合并
	 * @param list
	 * @param amountLimit
	 * @return
	 */
	public static List<OrderDetailMerge> getListByGroup(List<OrderDetailVO> list,BigDecimal amountLimit) {
		List<OrderDetailMerge> result = new ArrayList<OrderDetailMerge>();
		//值合并map
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		
		//明细表id合并map
		Map<String, StringBuffer> idMap = new HashMap<String,  StringBuffer>();
		//明细表id合并map
		Map<String, StringBuffer> mergeMap = new HashMap<String,  StringBuffer>();
				

		for (OrderDetailVO bean : list) {
			int orderDetailId = bean.getId();
			if (map.containsKey(bean.getOpenBankNo())) {
				if(map.get(bean.getOpenBankNo()).add(bean.getAmount()).compareTo(amountLimit)==0) {
					String mergeId = RandomUtil.randomId();
					
					StringBuffer idSb = new StringBuffer();
					if(idMap.get(bean.getOpenBankNo()).length()==0) {
						idSb.append(orderDetailId);
						idMap.put(bean.getOpenBankNo(), idSb);
					}else {
						idSb = idMap.get(bean.getOpenBankNo()).append(",").append(orderDetailId);
					}
					OrderDetailMerge temp = new OrderDetailMerge();
					temp.setAmount(map.get(bean.getOpenBankNo()).add(bean.getAmount()));
					temp.setOpenBankNo(bean.getOpenBankNo());
					temp.setMergeId(mergeId);
					temp.setRemark(idSb.toString());
					temp.setOrderId(bean.getOrderId());
					temp.setDaiZhengId(bean.getDaiZhengId());
					temp.setPaymentChannelId(bean.getPaymentChannelId());
					temp.setAgentName(bean.getAgentName());
					temp.setIdcardno(bean.getIdcardno());
					temp.setDataStatus(Const.DATA_STATUS_1);
					result.add(temp);
					mergeMap.put(mergeId, idSb);
					map.remove(bean.getOpenBankNo());
					idMap.put(bean.getOpenBankNo(),new StringBuffer());
				}else if(map.get(bean.getOpenBankNo()).add(bean.getAmount()).compareTo(amountLimit)>0) {
					String mergeId = RandomUtil.randomId();
					
					StringBuffer idSb = new StringBuffer();
					if(idMap.get(bean.getOpenBankNo()).length()==0) {
						idSb.append(orderDetailId);
						idMap.put(bean.getOpenBankNo(), idSb);
					}else {
						idSb = idMap.get(bean.getOpenBankNo());
					}
					OrderDetailMerge temp = new OrderDetailMerge();
					temp.setAmount(map.get(bean.getOpenBankNo()));
					temp.setOpenBankNo(bean.getOpenBankNo());
					temp.setMergeId(mergeId);
					temp.setRemark(idSb.toString());
					temp.setOrderId(bean.getOrderId());
					temp.setDaiZhengId(bean.getDaiZhengId());
					temp.setPaymentChannelId(bean.getPaymentChannelId());
					temp.setAgentName(bean.getAgentName());
					temp.setIdcardno(bean.getIdcardno());
					temp.setDataStatus(Const.DATA_STATUS_1);
					result.add(temp);
					mergeMap.put(mergeId, idSb);
					map.put(bean.getOpenBankNo(), bean.getAmount());
					idMap.put(bean.getOpenBankNo(),new StringBuffer().append(orderDetailId));
				}else {
					map.put(bean.getOpenBankNo(), map.get(bean.getOpenBankNo()).add(bean.getAmount()));
					StringBuffer idSb = new StringBuffer();
					if(idMap.get(bean.getOpenBankNo()).length()==0) {
						idSb.append(orderDetailId);
						idMap.put(bean.getOpenBankNo(), idSb);
					}else {
						idSb = idMap.get(bean.getOpenBankNo()).append(",").append(orderDetailId);
					}
					idMap.put(bean.getOpenBankNo(), idSb);
				}
			} else {
				map.put(bean.getOpenBankNo(), bean.getAmount());
				StringBuffer idSb = new StringBuffer();
				idSb.append(orderDetailId);
				idMap.put(bean.getOpenBankNo(), idSb);
				if(bean.getAmount().compareTo(amountLimit)==0) {
					String mergeId = RandomUtil.randomId();
					OrderDetailMerge temp = new OrderDetailMerge();
					temp.setAmount(bean.getAmount());
					temp.setOpenBankNo(bean.getOpenBankNo());
					temp.setMergeId(mergeId);
					temp.setRemark(idSb.toString());
					temp.setOrderId(bean.getOrderId());
					temp.setDaiZhengId(bean.getDaiZhengId());
					temp.setPaymentChannelId(bean.getPaymentChannelId());
					temp.setAgentName(bean.getAgentName());
					temp.setIdcardno(bean.getIdcardno());
					temp.setDataStatus(Const.DATA_STATUS_1);
					result.add(temp);
					map.put(bean.getOpenBankNo(), BigDecimal.ZERO);
					mergeMap.put(mergeId, idSb);
					idMap.put(bean.getOpenBankNo(), new StringBuffer());
				}
			}
		}
		for (Entry<String, BigDecimal> entry : map.entrySet()) {
			String mergeId = RandomUtil.randomId();
			StringBuffer idSb = idMap.get(entry.getKey());
			mergeMap.put(mergeId, idSb);
			OrderDetailMerge temp = new OrderDetailMerge();
			temp.setAmount(entry.getValue());
			temp.setOpenBankNo(entry.getKey());
			temp.setMergeId(mergeId);
			temp.setRemark(idSb.toString());
			for (OrderDetailVO bean : list) {
				if(entry.getKey().equals(bean.getOpenBankNo())) {
					temp.setOrderId(bean.getOrderId());
					temp.setDaiZhengId(bean.getDaiZhengId());
					temp.setPaymentChannelId(bean.getPaymentChannelId());
					temp.setAgentName(bean.getAgentName());
					temp.setIdcardno(bean.getIdcardno());
					temp.setDataStatus(Const.DATA_STATUS_1);
					break;
				}
			}
			if(temp.getAmount().compareTo(BigDecimal.ZERO)>0) {
				result.add(temp);
			}
		}
		
//		Iterator<Map.Entry<String, StringBuffer>> entries = mergeMap.entrySet().iterator(); 
//		while (entries.hasNext()) { 
//		  Map.Entry<String, StringBuffer> entry = entries.next(); 
//		  System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue()); 
//		}
		return result;
	}
	
	public static List<OrderDetail> getListByGroup2(List<OrderDetail> list,BigDecimal amountLimit) {
		List<OrderDetail> result = new ArrayList<OrderDetail>();
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();

		for (OrderDetail bean : list) {
			if (map.containsKey(bean.getOpenBankNo())) {
				if(map.get(bean.getOpenBankNo()).add(bean.getAmount()).compareTo(amountLimit)==0) {
					OrderDetail temp = new OrderDetail();
					temp.setAmount(map.get(bean.getOpenBankNo()).add(bean.getAmount()));
					temp.setOpenBankNo(bean.getOpenBankNo());
					result.add(temp);
					map.put(bean.getOpenBankNo(), BigDecimal.ZERO);
				}else if(map.get(bean.getOpenBankNo()).add(bean.getAmount()).compareTo(amountLimit)>0) {
					OrderDetail temp = new OrderDetail();
					temp.setAmount(map.get(bean.getOpenBankNo()));
					temp.setOpenBankNo(bean.getOpenBankNo());
					result.add(temp);
					map.put(bean.getOpenBankNo(), bean.getAmount());
				}else {
					map.put(bean.getOpenBankNo(), map.get(bean.getOpenBankNo()).add(bean.getAmount()));
				}
			} else {
				map.put(bean.getOpenBankNo(), bean.getAmount());
				if(bean.getAmount().compareTo(amountLimit)==0) {
					OrderDetail temp = new OrderDetail();
					temp.setAmount(bean.getAmount());
					temp.setOpenBankNo(bean.getOpenBankNo());
					result.add(temp);
					map.put(bean.getOpenBankNo(), BigDecimal.ZERO);
				}
			}
		}
		for (Entry<String, BigDecimal> entry : map.entrySet()) {
			OrderDetail temp = new OrderDetail();
			temp.setAmount(entry.getValue());
			temp.setOpenBankNo(entry.getKey());
			result.add(temp);
		}
		return result;
	}
}

