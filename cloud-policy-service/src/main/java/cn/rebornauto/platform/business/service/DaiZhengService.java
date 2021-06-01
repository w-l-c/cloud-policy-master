package cn.rebornauto.platform.business.service;

import java.util.List;

import cn.rebornauto.platform.business.entity.CertificateStatus;
import cn.rebornauto.platform.business.entity.DaiZhengInfo;
import cn.rebornauto.platform.business.form.DaiZhengForm;
import cn.rebornauto.platform.business.vo.DaiZhengInfoVo;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Query;

public interface DaiZhengService {
    int save(DaiZhengForm form) throws Exception;

    long count(Query query);

    List<DaiZhengInfoVo> list(Pagination pagination);

    int edit(DaiZhengForm form) throws Exception;

    List<DaiZhengInfo> all();
/**
 * 企业尚尚签证书申请
 * @param form
 * @return
 * @throws Exception
 */
CertificateStatus register(DaiZhengForm form) throws Exception;
/**
 * 企业尚尚签证书申请查询
 * @param query
 * @param form
 * @return
 * @throws Exception 
 */
CertificateStatus registerQuery(DaiZhengForm form) throws Exception;

}
