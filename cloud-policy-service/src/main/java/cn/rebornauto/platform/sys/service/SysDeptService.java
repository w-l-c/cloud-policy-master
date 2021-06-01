package cn.rebornauto.platform.sys.service;

import cn.rebornauto.platform.common.service.BaseService;
import cn.rebornauto.platform.common.data.request.Query;
import cn.rebornauto.platform.sys.entity.SysDept;
import cn.rebornauto.platform.sys.entity.SysDeptCriteria;
import cn.rebornauto.platform.sys.form.SysDeptForm;

import java.util.List;

public interface SysDeptService extends BaseService<SysDept, Integer, SysDeptCriteria,Query> {
    SysDept rootDept();

    int save(SysDeptForm sysDeptForm);

    int update(SysDeptForm sysDeptForm);

    List<SysDept> findChildByParentId(int parentId);

    int remove(int id);
}
