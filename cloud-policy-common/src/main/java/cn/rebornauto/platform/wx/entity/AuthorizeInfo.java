package cn.rebornauto.platform.wx.entity;

import lombok.Data;

@Data
public class AuthorizeInfo {
	
	/**
	 * 微信公众号授权链接
	 */
	private String authorize;
	/**
	 * 微信appid
	 */
	private String appid;
	/**
	 * 微信公众号关注连接
	 */
	private String publicNumber;


}
