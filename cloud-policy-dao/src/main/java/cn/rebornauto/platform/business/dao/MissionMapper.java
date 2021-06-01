package cn.rebornauto.platform.business.dao;

import cn.rebornauto.platform.business.entity.Mission;
import cn.rebornauto.platform.business.query.MissionQuery;
import cn.rebornauto.platform.business.vo.MissionVO;
import cn.rebornauto.platform.common.data.request.Pagination;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MissionMapper extends Mapper<Mission> {

    List<MissionVO> selectByQuery(@Param("p") Pagination pagination, @Param("q") MissionQuery query);

    List<MissionVO> selectMissionAcceptByQuery(@Param("p") Pagination pagination, @Param("q") MissionQuery query);

    int countByQuery(@Param("q")MissionQuery query);

    int countMissionAcceptByQuery(@Param("q")MissionQuery query);

    MissionVO selectDetailByMissionNo(@Param("missionNo")String missionNo);

    int updateByMissionNo(@Param("record")Mission mission);
}