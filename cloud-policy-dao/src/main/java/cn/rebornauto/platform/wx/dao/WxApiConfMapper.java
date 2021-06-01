package cn.rebornauto.platform.wx.dao;

import java.util.List;

import cn.rebornauto.platform.wx.entity.WxApiConf;

public interface WxApiConfMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WxApiConf record);

    WxApiConf selectByPrimaryKey(Integer id);

    WxApiConf selectByAppid(String appid);

    List<WxApiConf> selectAll();

    int updateByPrimaryKey(WxApiConf record);
}