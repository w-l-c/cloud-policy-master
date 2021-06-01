package cn.rebornauto.platform.sys.service.impl;

import cn.rebornauto.platform.common.service.impl.BaseServiceImpl;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.sys.dao.SysLogsMapper;
import cn.rebornauto.platform.sys.entity.SysLogs;
import cn.rebornauto.platform.sys.entity.SysLogsCriteria;
import cn.rebornauto.platform.sys.query.SysLogQuery;
import cn.rebornauto.platform.sys.service.SysLogsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;

@Service
public class SysLogsServiceImpl extends BaseServiceImpl<SysLogs, Integer, SysLogsCriteria, SysLogsMapper, SysLogQuery> implements SysLogsService {

    @Override
    public long countQuery(SysLogQuery query) {
        SysLogsCriteria criteria = new SysLogsCriteria();
        SysLogsCriteria.Criteria ec = criteria.createCriteria();
        if(StringUtils.hasText(query.getUsername())){
           ec.andUsernameEqualTo(query.getUsername());
        }
        if(StringUtils.hasText(query.getSourceip())){
            ec.andSourceipEqualTo(query.getSourceip());
        }
        return mapper.countByExample(criteria);
    }

    @Override
    public List<SysLogs> pageQuery(Pagination pagination, SysLogQuery query) {
        SysLogsCriteria criteria = new SysLogsCriteria();
        criteria.setOrderByClause("id desc");
        criteria.setLimitStart(pagination.getOffset());
        criteria.setLimitLength(pagination.getPageSize());
        SysLogsCriteria.Criteria ec = criteria.createCriteria();
        if(StringUtils.hasText(query.getUsername())){
            ec.andUsernameEqualTo(query.getUsername());
        }
        if(StringUtils.hasText(query.getSourceip())){
            ec.andSourceipEqualTo(query.getSourceip());
        }
        return mapper.selectByExample(criteria);
    }
}
