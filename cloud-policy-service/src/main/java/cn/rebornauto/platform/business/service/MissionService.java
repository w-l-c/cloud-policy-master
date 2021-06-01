package cn.rebornauto.platform.business.service;

import cn.rebornauto.platform.business.form.MissionForm;
import cn.rebornauto.platform.business.query.MissionQuery;
import cn.rebornauto.platform.business.vo.MissionVO;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.view.Response;

import java.util.List;

/**
 * @author ligewei
 * @create 2020/06/09 14:15
 */
public interface MissionService {

    /**
     * 任务发布管理列表
     * @param pagination
     * @param query
     * @return
     */
    List<MissionVO> selectMissionReleaseByQuery(Pagination pagination,MissionQuery query);

    /**
     * 任务审核列表
     * @param pagination
     * @param query
     * @return
     */
    List<MissionVO> selectMissionExamineByQuery(Pagination pagination,MissionQuery query);

    /**
     * 任务认领列表
     * @param pagination
     * @param query
     * @return
     */
    List<MissionVO> selectMissionAcceptByQuery(Pagination pagination,MissionQuery query);

    /**
     * 新增
     * @param form
     */
    int add(MissionForm form);

    /**
     * 编辑
     * @param form
     */
    int edit(MissionForm form);

    /**
     * 查看明细
     * @param missionNo
     * @return
     */
    MissionVO selectDetailByMissionNo(String missionNo);

    /**
     * 提交审核
     * @param form
     */
    int toExamine(MissionForm form);

    /**
     * 审核通过
     * @param form
     */
    int adopt(MissionForm form);

    /**
     * 审核驳回
     * @param form
     */
    int reject(MissionForm form);

    /**
     * 数据上传
     * @param form
     */
    Response upload(MissionForm form);

}
