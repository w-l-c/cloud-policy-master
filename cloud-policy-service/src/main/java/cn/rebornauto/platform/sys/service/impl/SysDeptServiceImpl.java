package cn.rebornauto.platform.sys.service.impl;

import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.service.impl.BaseServiceImpl;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Query;
import cn.rebornauto.platform.sys.dao.SysDeptMapper;
import cn.rebornauto.platform.sys.entity.SysDept;
import cn.rebornauto.platform.sys.entity.SysDeptCriteria;
import cn.rebornauto.platform.sys.form.SysDeptForm;
import cn.rebornauto.platform.sys.service.SysDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class SysDeptServiceImpl extends BaseServiceImpl<SysDept, Integer, SysDeptCriteria, SysDeptMapper,Query> implements SysDeptService {

    @Override
    public SysDept rootDept() {
        SysDeptCriteria sdc = new SysDeptCriteria();
        sdc.createCriteria().andParentidEqualTo(0).andStatusEqualTo(Const.Status_Normal);
        List<SysDept> list = mapper.selectByExample(sdc);
        return (list != null && list.size() == 1) ? list.get(0) : null;
    }

    @Override
    public int save(SysDeptForm sysDeptForm) {
        if(sysDeptForm==null || sysDeptForm.getParentid()==null){
            return 0;
        }
        //一个部门下不能来个相同名字的子部门
        SysDeptCriteria example = new SysDeptCriteria();
        example.createCriteria().andIdEqualTo(sysDeptForm.getParentid());
        List<SysDept> list = mapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            return 0;
        }
        example.clear();
        example.createCriteria().andParentidEqualTo(sysDeptForm.getParentid()).andNameEqualTo(sysDeptForm.getName());
        list = mapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return -1;
        }
        SysDept dept = sysDeptForm.getDept();
        dept.setCreatetime(Calendar.getInstance().getTime());
        return save(dept);
    }

    @Override
    public int update(SysDeptForm sysDeptForm) {
        SysDept sysdept = mapper.selectByPrimaryKey(sysDeptForm.getId());
        if (sysdept == null) {
            return 0;
        }
        //顶级部门不能改变父级
        if (sysdept.getParentid() == 0) {
            sysDeptForm.setParentid(0);
            sysdept = sysDeptForm.getDept();
            sysdept.setUpdatetime(Calendar.getInstance().getTime());
            return update(sysdept);
        }
        //一个部门下不能来个相同名字的子部门
        SysDeptCriteria example = new SysDeptCriteria();
        example.createCriteria().andIdEqualTo(sysDeptForm.getParentid()).andStatusEqualTo(Const.Status_Normal);
        List<SysDept> list = mapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            return 0;
        }
        example.clear();
        example.createCriteria().andParentidEqualTo(sysDeptForm.getParentid()).andNameEqualTo(sysDeptForm.getName()).andIdNotEqualTo(sysDeptForm.getId()).andStatusEqualTo(Const.Status_Normal);
        list = mapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return -1;
        }
        sysdept = sysDeptForm.getDept();
        sysdept.setUpdatetime(Calendar.getInstance().getTime());
        return update(sysdept);
    }

    @Override
    public List<SysDept> findChildByParentId(int parentId) {
        SysDeptCriteria example = new SysDeptCriteria();
        example.createCriteria().andParentidEqualTo(parentId);
        //.andStatusEqualTo(Const.Status_Normal);
        List<SysDept> depts = mapper.selectByExample(example);
        for (SysDept d : depts) {
            List<SysDept> childs = findChildByParentId(d.getId());
            d.setChildren(childs);
        }
        return depts;
    }

    @Override
    @Transactional
    public int remove(int id) {
        SysDept record = new SysDept();
        record.setUpdatetime(Calendar.getInstance().getTime());
        record.setStatus(Const.Status_Del);
        List<Integer> ids = childIds(id);
        ids.add(id);
        SysDeptCriteria example = new SysDeptCriteria();
        example.createCriteria().andIdIn(ids);
        return mapper.updateByExampleSelective(record, example);
    }

    /**
     * 获取parentId所有子孙节点的id集合
     * @param parentId
     * @return
     */
    private List<Integer> childIds(int parentId) {
        List<Integer> list = new ArrayList<Integer>();
        SysDeptCriteria example = new SysDeptCriteria();
        example.createCriteria().andParentidEqualTo(parentId).andStatusEqualTo(Const.Status_Normal);
        List<SysDept> depts = mapper.selectByExample(example);
        for (SysDept d : depts) {
            list.add(d.getId());
            list.addAll(childIds(d.getId()));
        }
        return list;
    }

    @Override
    public long countQuery(Query query) {
        return 0;
    }

    @Override
    public List<SysDept> pageQuery(Pagination pagination, Query query) {
        return null;
    }
}
