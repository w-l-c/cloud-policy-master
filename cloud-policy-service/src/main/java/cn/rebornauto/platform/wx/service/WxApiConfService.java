package cn.rebornauto.platform.wx.service;

import cn.rebornauto.platform.wx.dao.WxApiConfMapper;
import cn.rebornauto.platform.wx.entity.WxApiConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxApiConfService {

    @Autowired
    private WxApiConfMapper wxApiConfMapper;

    public WxApiConf apiConf(String appId) {
        return wxApiConfMapper.selectByAppid(appId);
    }
    public int update(WxApiConf conf){
        return wxApiConfMapper.updateByPrimaryKey(conf);
    }
}
