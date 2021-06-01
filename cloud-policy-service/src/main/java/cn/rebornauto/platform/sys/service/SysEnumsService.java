package cn.rebornauto.platform.sys.service;

import java.util.List;

import cn.rebornauto.platform.sys.entity.SysEnums;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Mar 18, 2020 4:50:15 PM
 */
public interface SysEnumsService {

	List<SysEnums> getEnumsByCategory(String category);
}
