package cn.rebornauto.platform.business.dao;

import cn.rebornauto.platform.business.entity.Area;
import cn.rebornauto.platform.business.entity.RegionOption;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AreaMapper extends Mapper<Area> {
    List<RegionOption> selectChildren(String adcode);
}