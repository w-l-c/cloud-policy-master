package cn.rebornauto.platform.sys.service.impl;

import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.service.impl.BaseServiceImpl;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.sys.dao.SysRoleMapper;
import cn.rebornauto.platform.sys.dao.SysRoleMenuMapper;
import cn.rebornauto.platform.sys.entity.SysRole;
import cn.rebornauto.platform.sys.entity.SysRoleCriteria;
import cn.rebornauto.platform.sys.entity.SysRoleMenu;
import cn.rebornauto.platform.sys.entity.SysRoleMenuCriteria;
import cn.rebornauto.platform.sys.form.SysRoleForm;
import cn.rebornauto.platform.sys.query.SysRoleQuery;
import cn.rebornauto.platform.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole, Integer, SysRoleCriteria, SysRoleMapper,SysRoleQuery> implements SysRoleService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    private int duplicationCheck(SysRoleForm sysRole){
        SysRoleCriteria example = new SysRoleCriteria();
        SysRoleCriteria.Criteria ec = example.createCriteria();
        ec.andRolenameEqualTo(sysRole.getRoleName());
        if(sysRole.getId()>0){
            ec.andIdNotEqualTo(sysRole.getId());
        }
       return this.mapper.countByExample(example);
    }

    @Override
    @Transactional
    public int save(SysRoleForm sysRole) {
        if(duplicationCheck(sysRole)>0){
            return -1;
        }
        SysRole role = new SysRole();
        role.setCreatetime(new Date());
        role.setRemark(sysRole.getRemark());
        role.setRolename(sysRole.getRoleName());
        role.setStatus((byte) sysRole.getStatus());
        this.mapper.insert(role);
        int roleid = role.getId();
        List<String> menuids = sysRole.getMenuids();
        if (menuids!=null && menuids.size()>0) {
            for (String mid : menuids) {
                SysRoleMenu srm = new SysRoleMenu();
                srm.setCreatetime(Calendar.getInstance().getTime());
                srm.setRoleid(roleid);
                srm.setMenuid(Integer.parseInt(mid));
                sysRoleMenuMapper.insert(srm);
            }
        }
        return roleid;
    }

    @Override
    @Transactional
    public int update(SysRoleForm form) {
        SysRole sysRole = this.mapper.selectByPrimaryKey(form.getId());
        if (sysRole == null) {
            return 0;
        }
        if(duplicationCheck(form)>0){
            return -1;
        }
        if (form.Changed(sysRole)) {
            sysRole.setRolename(form.getRoleName());
            sysRole.setRemark(form.getRemark());
            sysRole.setStatus((byte) form.getStatus());
            sysRole.setUpdatetime(Calendar.getInstance().getTime());
            mapper.updateByPrimaryKey(sysRole);
        }
        SysRoleMenuCriteria example = new SysRoleMenuCriteria();
        example.createCriteria().andRoleidEqualTo(sysRole.getId());
        List<SysRoleMenu> list = sysRoleMenuMapper.selectByExample(example);
        List<Integer> removed = remove(list, form.getMenuids());
        List<Integer> added = add(list, form.getMenuids());
        //需要删除的
        if (removed != null && removed.size() > 0) {
            SysRoleMenuCriteria removedExample = new SysRoleMenuCriteria();
            removedExample.createCriteria().andRoleidEqualTo(sysRole.getId()).andMenuidIn(removed);
            sysRoleMenuMapper.deleteByExample(removedExample);
        }
        //新增的
        if (added != null && added.size() > 0) {
            for (Integer mid : added) {
                SysRoleMenu srm = new SysRoleMenu();
                srm.setCreatetime(Calendar.getInstance().getTime());
                srm.setRoleid(sysRole.getId());
                srm.setMenuid(mid);
                sysRoleMenuMapper.insert(srm);
            }
        }
        return sysRole.getId();
    }

    @Override
    @Transactional
    public int del(int id) {
        SysRole record = new SysRole();
        record.setId(id);
        record.setStatus(Const.Status_Del);
        record.setUpdatetime(Calendar.getInstance().getTime());
        SysRoleMenuCriteria example = new SysRoleMenuCriteria();
        example.createCriteria().andRoleidEqualTo(id);
        sysRoleMenuMapper.deleteByExample(example);
        return this.mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<Integer> findMenuids(int roleid) {
        SysRoleMenuCriteria example = new SysRoleMenuCriteria();
        example.createCriteria().andRoleidEqualTo(roleid);
        List<SysRoleMenu> listR = sysRoleMenuMapper.selectByExample(example);
        List<Integer> list = new ArrayList<Integer>();
        for (SysRoleMenu m : listR) {
            list.add(m.getMenuid());
        }
        return list;
    }

    private List<Integer> add(List<SysRoleMenu> list, List<String> menuids) {
        // 更新的
        Set<Integer> update = new TreeSet<Integer>();
        if (menuids!=null) {
            for (String mid : menuids) {
                if(StringUtils.hasText(mid)){
                    update.add(Integer.parseInt(mid));
                }
            }
        } else {
            return null;
        }

        // 更新之前的
        Set<Integer> old = new TreeSet<Integer>();
        for (SysRoleMenu srm : list) {
            old.add(srm.getMenuid());
        }

        //新增的
        List<Integer> srms = new ArrayList<Integer>();
        for (Integer u : update) {
            if (!old.contains(u)) {
                srms.add(u);
            }
        }
        return srms;
    }

    private List<Integer> remove(List<SysRoleMenu> list, List<String> menuids) {

        // 更新之前的
        List<Integer> old = new ArrayList<Integer>();
        for (SysRoleMenu srm : list) {
            old.add(srm.getMenuid());
        }

        // 更新的
        Set<Integer> update = new TreeSet<Integer>();
        if (menuids!=null) {
            for (String mid : menuids) {
                if(StringUtils.hasText(mid)){
                    update.add(Integer.parseInt(mid));
                }
            }
        } else {
            return old;
        }
        //需要删除的
        List<Integer> srms = new ArrayList<Integer>();
        for (Integer o : old) {
            if (!update.contains(o)) {
                srms.add(o);
            }
        }
        return srms;
    }

    @Override
    public long countQuery(SysRoleQuery query) {
        SysRoleCriteria criteria = new SysRoleCriteria();
        SysRoleCriteria.Criteria ec = criteria.createCriteria();
        //ec.andStatusEqualTo(Const.Status_Normal);
        if(StringUtils.hasText(query.getRolename())){
            ec.andRolenameLike("%"+query.getRolename()+"%");
        }
        if(query.getStatus()!=null && query.getStatus()>0){
            ec.andStatusEqualTo(query.getStatus());
        }
        return mapper.countByExample(criteria);
    }

    @Override
    public List<SysRole> pageQuery(Pagination pagination, SysRoleQuery query) {
        SysRoleCriteria criteria = new SysRoleCriteria();
        criteria.setLimitStart(pagination.getOffset());
        criteria.setLimitLength(pagination.getPageSize());
        criteria.setOrderByClause(" id desc ");
        SysRoleCriteria.Criteria ec = criteria.createCriteria();
       // ec.andStatusEqualTo(Const.Status_Normal);
        if(StringUtils.hasText(query.getRolename())){
            ec.andRolenameLike("%"+query.getRolename()+"%");
        }
        if(query.getStatus()!=null && query.getStatus()>0){
            ec.andStatusEqualTo(query.getStatus());
        }
        return mapper.selectByExample(criteria);
    }
}
