package cn.rebornauto.platform.business.service.impl;

import cn.rebornauto.platform.business.dao.BusiLogMapper;
import cn.rebornauto.platform.business.entity.BusiLog;
import cn.rebornauto.platform.business.query.BusiLogQuery;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.vo.BusiLogVo;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.util.IpUtil;
import cn.rebornauto.platform.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BusiLogServiceImpl implements BusiLogService {
    @Autowired
    private BusiLogMapper busiLogMapper;

    /**
     * @Author  XJX
     * @param busiLog
     * @return
     * 操作日志添加
     */
    @Override
    public int save(BusiLog busiLog) {
        return busiLogMapper.insertSelective(busiLog);
    }

    @Override
    public int add(HttpServletRequest req, String busiType, int optionType, String optionContent, SysUser sysUser) {
        BusiLog busiLog = new BusiLog();

        busiLog.setIp(null==req?"":IpUtil.getIP(req));
        busiLog.setCustomerId(sysUser.getCustomerId());
        busiLog.setCreateoper(sysUser.getNickname());
        busiLog.setBusiType(busiType);
        busiLog.setOptionType(optionType);
        busiLog.setCreatetime(LocalDateTime.now());
        busiLog.setOptionContent(optionContent);
        return busiLogMapper.insertSelective(busiLog);
    }

    @Override
    public int count(BusiLogQuery query) {
        return busiLogMapper.count(query);
    }

    @Override
    public List<BusiLogVo> list(BusiLogQuery query, Pagination pagination) {
        return busiLogMapper.list(query, pagination);
    }

	@Override
	public List<BusiLogVo> selectBusiLogExcelByQuery(BusiLogQuery query) {
		return busiLogMapper.selectBusiLogExcelByQuery(query);
	}

}
