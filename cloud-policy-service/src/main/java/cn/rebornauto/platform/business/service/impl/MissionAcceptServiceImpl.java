package cn.rebornauto.platform.business.service.impl;

import cn.rebornauto.platform.business.dao.MissionAgentAcceptMapper;
import cn.rebornauto.platform.business.query.MissionAcceptQuery;
import cn.rebornauto.platform.business.service.MissionAcceptService;
import cn.rebornauto.platform.business.vo.MissionAgentAcceptVo;
import cn.rebornauto.platform.common.data.request.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ligewei
 * @create 2020/06/16 16:45
 */
@Service
public class MissionAcceptServiceImpl implements MissionAcceptService {

    @Autowired
    private MissionAgentAcceptMapper missionAgentAcceptMapper;

    @Override
    public List<MissionAgentAcceptVo> selectByQuery(Pagination pagination, MissionAcceptQuery query) {

        return missionAgentAcceptMapper.selectByQuery(pagination,query);
    }
}
