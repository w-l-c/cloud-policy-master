package cn.rebornauto.platform.business.service.impl;

import cn.rebornauto.platform.business.dao.MissionAgentAcceptMapper;
import cn.rebornauto.platform.business.dao.MissionMapper;
import cn.rebornauto.platform.business.entity.Mission;
import cn.rebornauto.platform.business.entity.MissionAgentAccept;
import cn.rebornauto.platform.business.form.MissionForm;
import cn.rebornauto.platform.business.query.MissionQuery;
import cn.rebornauto.platform.business.service.MissionService;
import cn.rebornauto.platform.business.vo.AgentBankNoVo;
import cn.rebornauto.platform.business.vo.ExcelTemplateVO;
import cn.rebornauto.platform.business.vo.MissionVO;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.enums.MissionStatusEnum;
import cn.rebornauto.platform.common.util.LogUtil;
import cn.rebornauto.platform.utils.ExcelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author ligewei
 * @create 2020/06/09 14:52
 */
@Service
public class MissionServiceImpl implements MissionService {

    @Autowired
    private MissionMapper missionMapper;

    @Autowired
    private MissionAgentAcceptMapper missionAgentAcceptMapper;

    @Override
    public List<MissionVO> selectMissionReleaseByQuery(Pagination pagination, MissionQuery query) {
        pagination.setTotal(missionMapper.countByQuery(query));
        return missionMapper.selectByQuery(pagination,query);
    }

    @Override
    public List<MissionVO> selectMissionExamineByQuery(Pagination pagination, MissionQuery query) {
        query.setStatus(MissionStatusEnum.MISSION_STATUS_2.getIndex());
        pagination.setTotal(missionMapper.countByQuery(query));
        return missionMapper.selectByQuery(pagination, query);
    }

    @Override
    public List<MissionVO> selectMissionAcceptByQuery(Pagination pagination, MissionQuery query) {
        pagination.setTotal(missionMapper.countMissionAcceptByQuery(query));
        return missionMapper.selectMissionAcceptByQuery(pagination, query);
    }

    @Override
    public int add(MissionForm form) {
        Mission mission = new Mission();
        mission.setMissionNo(Const.MISSION_NO_PRE+UUID.randomUUID());
        mission.setCustomerId(form.getUserCustomerId());
        mission.setMissionName(form.getMissionName());
        mission.setMissionRemark(form.getMissionRemark());
        LocalDateTime now = LocalDateTime.now();
        mission.setReleasetime(now);
        mission.setAmount(form.getAmount());
        mission.setStatus(MissionStatusEnum.MISSION_STATUS_1.getIndex());
        mission.setDataStatus(Const.DATA_STATUS_1);
        mission.setCreateoper(form.getCurrUserName());
        mission.setCreatetime(now);
        return missionMapper.insertSelective(mission);
    }

    @Override
    public int edit(MissionForm form) {
        Mission mission = new Mission();
        mission.setMissionNo(form.getMissionNo());
        mission.setMissionName(form.getMissionName());
        mission.setMissionRemark(form.getMissionRemark());
        mission.setAmount(form.getAmount());
        mission.setModifyoper(form.getCurrUserName());
        mission.setModifytime(LocalDateTime.now());
        return missionMapper.updateByMissionNo(mission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MissionVO selectDetailByMissionNo(String missionNo) {
        return missionMapper.selectDetailByMissionNo(missionNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int toExamine(MissionForm form) {
        Mission mission = new Mission();
        mission.setMissionNo(form.getMissionNo());
        mission.setStatus(MissionStatusEnum.MISSION_STATUS_2.getIndex());
        mission.setModifyoper(form.getCurrUserName());
        mission.setModifytime(LocalDateTime.now());
        return missionMapper.updateByMissionNo(mission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int adopt(MissionForm form) {
        Mission mission = new Mission();
        mission.setMissionNo(form.getMissionNo());
        mission.setStatus(MissionStatusEnum.MISSION_STATUS_3.getIndex());
        LocalDateTime now = LocalDateTime.now();
        //mission.setReleasetime(now);
        mission.setModifyoper(form.getCurrUserName());
        mission.setModifytime(now);
        return missionMapper.updateByMissionNo(mission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int reject(MissionForm form) {
        Mission mission = new Mission();
        mission.setMissionNo(form.getMissionNo());
        mission.setStatus(MissionStatusEnum.MISSION_STATUS_4.getIndex());
        mission.setModifyoper(form.getCurrUserName());
        mission.setModifytime(LocalDateTime.now());
        return missionMapper.updateByMissionNo(mission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response upload(MissionForm form) {
        ArrayList<ExcelTemplateVO> excelTemplateVOS = ExcelUtil.readExcel(new File(form.getExcel()));
        List<String> openBankNos = new ArrayList<>();
        if(CollectionUtils.isEmpty(excelTemplateVOS)){
            return Response.error().message("解析excel失败，请检查格式是否正确");
        }

        MissionVO vo = missionMapper.selectDetailByMissionNo(form.getMissionNo());
        List<AgentBankNoVo> agentBankNoVos = missionAgentAcceptMapper.selectByCustomerId(vo.getCustomerId());
        Iterator<ExcelTemplateVO> iterator = excelTemplateVOS.iterator();
        List<MissionAgentAccept> agentAccepts = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        agentBankNoVos.forEach(item->{
            MissionAgentAccept model = new MissionAgentAccept();
            excelTemplateVOS.forEach(excelTemplateVO->{
                if(item.getAgentOpenBankNo().equals(excelTemplateVO.getOpenBankNo())){
                    model.setAgentId(item.getAgentId());
                    model.setAmount(excelTemplateVO.getAgentCommission());
                    model.setMissionNo(form.getMissionNo());
                    model.setDataStatus(Const.DATA_STATUS_1);
                    model.setCompletetime(now);
                    model.setCreateoper(form.getCurrUserName());
                    model.setCreatetime(now);
                    agentAccepts.add(model);
                }
            });
        });
        while(iterator.hasNext()){
            ExcelTemplateVO model = iterator.next();
            agentBankNoVos.forEach(item-> {
                if(item.getAgentOpenBankNo().equals(model.getOpenBankNo())){
                    iterator.remove();
                }
            });
        }
        if(CollectionUtils.isNotEmpty(excelTemplateVOS)){
            LogUtil.info("excel有未匹配数据存在！");
            int size = excelTemplateVOS.size();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < excelTemplateVOS.size(); i++) {
                sb.append(excelTemplateVOS.get(i).getAgentName()+" "+excelTemplateVOS.get(i).getOpenBankNo()+"\r\n");
                if(i==4) {
                    sb.append("......");
                    break;
                }
            };
            return Response.error().message("有"+size+"条未匹配代理人数据，分别是\r\n"+sb.toString());
        }else {
            LogUtil.info("excel数据正常");
        }
        Mission mission = new Mission();

        mission.setMissionNo(form.getMissionNo());
        mission.setStatus(MissionStatusEnum.MISSION_STATUS_5.getIndex());
        mission.setModifyoper(form.getCurrUserName());
        mission.setModifytime(now);
        mission.setExcel(form.getExcel());
        mission.setFile(form.getFile());
        mission.setImage(form.getImage());
        if(missionMapper.updateByMissionNo(mission)<1){
            return Response.error().message("修改任务数据失败，请稍后重试");
        }

        missionAgentAcceptMapper.insertList(agentAccepts);
        return Response.ok().message("成功");
    }
}
