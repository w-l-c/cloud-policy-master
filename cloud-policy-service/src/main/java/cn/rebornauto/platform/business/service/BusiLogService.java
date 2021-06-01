package cn.rebornauto.platform.business.service;

import cn.rebornauto.platform.business.entity.BusiLog;
import cn.rebornauto.platform.business.query.BusiLogQuery;
import cn.rebornauto.platform.business.vo.BusiLogVo;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.sys.entity.SysUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BusiLogService {
    int save(BusiLog busiLog);
    int add(HttpServletRequest req, String busiType, int optionType, String optionContent, SysUser sysUser);

    int count(BusiLogQuery query);

    List<BusiLogVo> list(BusiLogQuery query, Pagination pagination);
    
	List<BusiLogVo> selectBusiLogExcelByQuery(BusiLogQuery query);
}
