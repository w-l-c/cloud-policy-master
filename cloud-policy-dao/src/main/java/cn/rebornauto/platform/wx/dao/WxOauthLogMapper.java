package cn.rebornauto.platform.wx.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.rebornauto.platform.wx.entity.WxOauthLog;
import cn.rebornauto.platform.wx.entity.WxOauthLogCriteria;

public interface WxOauthLogMapper {
    int countByExample(WxOauthLogCriteria example);

    int deleteByExample(WxOauthLogCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(WxOauthLog record);

    int insertSelective(WxOauthLog record);

    List<WxOauthLog> selectByExample(WxOauthLogCriteria example);

    WxOauthLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WxOauthLog record, @Param("example") WxOauthLogCriteria example);

    int updateByExample(@Param("record") WxOauthLog record, @Param("example") WxOauthLogCriteria example);

    int updateByPrimaryKeySelective(WxOauthLog record);

    int updateByPrimaryKey(WxOauthLog record);

	List<Map<String, Object>> selectByPhone(@Param("phone") String phone);
}