package cn.rebornauto.platform.business.service;

import cn.rebornauto.platform.business.entity.RegionOption;

import java.util.List;

public interface AreaService {

    List<RegionOption> listProvince();

    List<RegionOption> listChildren(String adcode);

}
