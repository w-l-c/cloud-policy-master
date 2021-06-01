package cn.rebornauto.platform.bestSign.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 厦门思明区税务局委托书签章位置配置
 */
@Data
public class XmsmqswjwtsInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 共享经济合作伙伴协议合同模板
	 */
	private String modelXmsmqswjwtsName;
	/**
	 * 共享经济合作伙伴协议合同
	 */
	private String xmsmqswjwtsName;
	
	/**
	 * 共享经济合作伙伴协议合同页数
	 */
	private String xmsmqswjwtsPageSize;
	
	/**
	 * 共享经济合作伙伴协议合同盖章页码
	 */
	private String xmsmqswjwtsPageNum;
	
	/**
	 * 共享经济合作伙伴协议X轴盖章位置
	 */
	private String xmsmqswjwtsX;
	
	/**
	 * 共享经济合作伙伴协议Y轴盖章位置
	 */
	private String xmsmqswjwtsY;
	
	/**
	 * 共享经济合作伙伴协议X轴签名位置
	 */
	private String personxmsmqswjwtsX;
	
	/**
	 * 共享经济合作伙伴协议Y轴签名位置
	 */
	private String personxmsmqswjwtsY;
	
}
