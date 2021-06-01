package cn.rebornauto.platform.bestSign.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class GxjjhzhbxyInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 共享经济合作伙伴协议合同模板
	 */
	private String modelGxjjhzhbxyName;
	/**
	 * 共享经济合作伙伴协议合同
	 */
	private String gxjjhzhbxyName;
	
	/**
	 * 共享经济合作伙伴协议合同页数
	 */
	private String gxjjhzhbxyPageSize;
	
	/**
	 * 共享经济合作伙伴协议合同盖章页码
	 */
	private String gxjjhzhbxyPageNum;
	
	/**
	 * 共享经济合作伙伴协议X轴盖章位置
	 */
	private String gxjjhzhbxyX;
	
	/**
	 * 共享经济合作伙伴协议Y轴盖章位置
	 */
	private String gxjjhzhbxyY;
	
	/**
	 * 共享经济合作伙伴协议X轴签名位置
	 */
	private String persongxjjhzhbxyX;
	
	/**
	 * 共享经济合作伙伴协议Y轴签名位置
	 */
	private String persongxjjhzhbxyY;
	
}
