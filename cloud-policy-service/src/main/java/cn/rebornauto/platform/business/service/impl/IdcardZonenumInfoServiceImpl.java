package cn.rebornauto.platform.business.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rebornauto.platform.business.dao.IdcardZonenumInfoMapper;
import cn.rebornauto.platform.business.service.IdcardZonenumInfoService;
@Service
public class IdcardZonenumInfoServiceImpl implements IdcardZonenumInfoService{
	
	@Autowired
	IdcardZonenumInfoMapper  idcardZonenumInfoMapper;
	
	@Override
	public Map<Integer, String> selectZoneNums() {
		// TODO Auto-generated method stub
		Map<Integer, String> zoneNum = new HashMap<Integer, String>();
        List<Map<String, Object>> list = idcardZonenumInfoMapper.selectZoneNums();
        for (Map<String, Object> map : list) {
            zoneNum.put((Integer) map.get("bl"), (String) map.get("cityName"));
        }
        return zoneNum;
	}
}
