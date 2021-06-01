package cn.rebornauto.platform.business.service;

public interface SysDicService {
    /**
     * 线上环境 online/测试环境test  开关
     * @return
     */
	String selectSysPaySwitch();
	/**
	 * 是否合并
	 * @return
	 */
	String selectIsMerge();
	/**
	 * 获取上传最大数量限制
	 * @return
	 */
	String selectUploadMaxData();

}
