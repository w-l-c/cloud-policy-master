package cn.rebornauto.platform.business.service.impl;

import cn.rebornauto.platform.business.dao.AreaMapper;
import cn.rebornauto.platform.business.entity.RegionOption;
import cn.rebornauto.platform.business.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    private final static String Root = "100000";

    @Autowired
    private AreaMapper areaMapper;

    @Override

    @Cacheable(cacheNames = "stringCache",key = "'AreaServiceImpl_listProvince'")
    public List<RegionOption> listProvince() {
        List<RegionOption> province = areaMapper.selectChildren(Root);
        province.forEach(r->{
            r.setChildren(listChildren(r.getValue()));
        });
        return province;
    }

    @Override
    public List<RegionOption> listChildren(String adcode) {
        return areaMapper.selectChildren(adcode);
    }
}
