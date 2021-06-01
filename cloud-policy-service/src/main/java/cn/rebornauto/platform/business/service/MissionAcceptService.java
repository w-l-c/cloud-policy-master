package cn.rebornauto.platform.business.service;

import cn.rebornauto.platform.business.query.MissionAcceptQuery;
import cn.rebornauto.platform.business.vo.MissionAgentAcceptVo;
import cn.rebornauto.platform.common.data.request.Pagination;

import java.util.List;

/**
 * @author ligewei
 * @create 2020/06/09 14:15
 */
public interface MissionAcceptService {

    /**
     * 任务发布管理列表
     * @param pagination
     * @param query
     * @return
     */
    List<MissionAgentAcceptVo> selectByQuery(Pagination pagination, MissionAcceptQuery query);

}
