package cn.rebornauto.platform.business.service;

import java.util.Map;

public interface IdcardZonenumInfoService {
     /**
      * 查询身份证区位号
      * @return
      */
	Map<Integer, String> selectZoneNums();

}
