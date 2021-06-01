package cn.rebornauto.platform.upload.entity;

import java.io.Serializable;

import lombok.Data;

/** Title: 图片上传对应的必要参数对象
 * Description: 
 * Company: 
 * @author kgc
 * @date May 2, 2019 9:39:57 AM
 */
@Data
public class UploadInfo implements Serializable{

	/**
	 * 默认的
	 */
	private String key;
	/**
	 * 本地的
	 */
	private String local;
	/**
	 * 图片路径
	 */
	private String url;
	/**
	 * 域名
	 */
	private String domain;
	
	
}
