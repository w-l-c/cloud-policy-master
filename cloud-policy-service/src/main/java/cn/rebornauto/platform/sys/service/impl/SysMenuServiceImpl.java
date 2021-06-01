package cn.rebornauto.platform.sys.service.impl;

import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.SysConst;
import cn.rebornauto.platform.common.service.impl.BaseServiceImpl;
import cn.rebornauto.platform.common.exception.BizException;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Query;
import cn.rebornauto.platform.sys.dao.SysMenuMapper;
import cn.rebornauto.platform.sys.entity.*;
import cn.rebornauto.platform.sys.service.SysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu, Integer, SysMenuCriteria, SysMenuMapper, Query>
        implements SysMenuService {

    @Override
    @Transactional
    public int save(SysMenu sysMenu) {
        if (sysMenu == null || sysMenu.getParentid() == null) {
            return 0;
        }
        SysMenu parentMenu = mapper.selectByPrimaryKey(sysMenu.getParentid());
        if (parentMenu == null && sysMenu.getParentid() != 0) {
            return 0;
        }
        if (sysMenu.getMenutype() == Const.MENU_ITEM_BUT
                || sysMenu.getMenutype() == Const.MENU_ITEM_LIST) {
            if (parentMenu.getMenutype() != Const.MENU_ITEM) {
                throw new BizException("权限的父级只能是菜单选项");
            }
        }
        if (sysMenu.getMenutype() == Const.MENU_ITEM
                || sysMenu.getMenutype() == Const.MENU_ITEM_GROUP) {
            if (sysMenu.getParentid() != 0 && parentMenu.getMenutype() != Const.MENU_ITEM_GROUP) {
                throw new BizException("菜单或者目录的父级只能是目录");
            }
        }
        SysMenuCriteria example = new SysMenuCriteria();
        SysMenuCriteria.Criteria ec = example.createCriteria();
        ec.andParentidEqualTo(sysMenu.getParentid());
        ec.andMenutypeEqualTo(sysMenu.getMenutype());
        ec.andMenunameEqualTo(sysMenu.getMenuname());
        ec.andStatusEqualTo(Const.Status_Normal);
        List<SysMenu> list = mapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return -1;
        }
        int type = sysMenu.getMenutype();
        sysMenu.setCreatetime(new Date());
        sysMenu.setMenulevel(parentMenu.getMenulevel() + 1);
        int code = mapper.insertSelective(sysMenu);
        int code2 = 0;
        if (code == 1 && Const.MENU_ITEM == type) {
            //创建列表权限
            SysMenu m2 = new SysMenu();
            m2.setMenuname(sysMenu.getMenuname() + "列表");
            m2.setMenulevel(sysMenu.getMenulevel() + 1);
            m2.setParentid(sysMenu.getId());
            m2.setPermission(sysMenu.getPermission());
            m2.setRouter(sysMenu.getRouter());
            m2.setMenuorder(1);
            m2.setStatus(sysMenu.getStatus());
            m2.setCreatetime(new Date());
            m2.setMenutype(Const.MENU_ITEM_LIST);
            code2 = mapper.insertSelective(m2);
        }
        return code + code2;
    }


    @Override
    public int update(SysMenu sysMenu) {
        if (sysMenu == null || sysMenu.getParentid() == null || sysMenu.getId() == null) {
            return 0;
        }
        SysMenu persist = mapper.selectByPrimaryKey(sysMenu.getId());
        if (persist == null) {
            return 0;
        }
        SysMenu parentMenu = mapper.selectByPrimaryKey(sysMenu.getParentid());
        if (parentMenu == null) {
            return 0;
        }
        SysMenuCriteria example = new SysMenuCriteria();
        SysMenuCriteria.Criteria ec = example.createCriteria();
        ec.andIdNotEqualTo(persist.getId());
        ec.andParentidEqualTo(persist.getParentid());
        ec.andMenutypeEqualTo(persist.getMenutype());
        ec.andMenunameEqualTo(sysMenu.getMenuname());
        ec.andStatusEqualTo(Const.Status_Normal);
        List<SysMenu> list = mapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return -1;
        }
        sysMenu.setMenulevel(parentMenu.getMenulevel() + 1);
        sysMenu.setUpdatetime(new Date());
        sysMenu.setMenutype(persist.getMenutype());
        //修改菜单是必须修改他的列表
        if (sysMenu.getMenutype() == Const.MENU_ITEM) {
            SysMenuCriteria listExample = new SysMenuCriteria();
            listExample.createCriteria()
                    .andParentidEqualTo(sysMenu.getId())
                    .andMenutypeEqualTo(Const.MENU_ITEM_LIST)
                    .andPermissionEqualTo(persist.getPermission());
            List<SysMenu> itemList = mapper.selectByExample(listExample);
            if(itemList!=null && itemList.size()>0){
                itemList.forEach(item->{
                    item.setUpdatetime(new Date());
                    item.setMenuname(sysMenu.getMenuname() + "列表");
                    item.setMenulevel(sysMenu.getMenulevel() + 1);
                    item.setParentid(sysMenu.getId());
                    item.setPermission(sysMenu.getPermission());
                    item.setRouter(sysMenu.getRouter());
                    item.setStatus(sysMenu.getStatus());
                    item.setMenutype(Const.MENU_ITEM_LIST);
                    mapper.updateByPrimaryKeySelective(item);
                });
            }

        }
        return mapper.updateByPrimaryKeySelective(sysMenu);
    }

    @Override
    public long countQuery(Query query) {
        return 0;
    }

    @Override
    public List<SysMenu> pageQuery(Pagination pagination, Query query) {
        return null;
    }

    @Override
    public List<SysMenu> findChildByParentId(int parentid) {
        SysMenuCriteria sysMenuCriteria = new SysMenuCriteria();
        sysMenuCriteria.createCriteria().andStatusEqualTo(Const.Status_Normal).andParentidEqualTo(parentid);
        List<SysMenu> sysMenus = mapper.selectByExample(sysMenuCriteria);
        if (sysMenus != null && sysMenus.size() > 0) {
            for (SysMenu s : sysMenus) {
                List<SysMenu> childs = findChildByParentId(s.getId());
                s.setChildren(childs);
            }
        }
        return sysMenus;
    }

    @Override
    public SysMenu rootMenu() {
        SysMenuCriteria sysMenuCriteria = new SysMenuCriteria();
        sysMenuCriteria.createCriteria().andStatusEqualTo(Const.Status_Normal).andParentidEqualTo(0);
        List<SysMenu> sysMenus = mapper.selectByExample(sysMenuCriteria);
        if (sysMenus != null && sysMenus.size() == 1) {
            return sysMenus.get(0);
        }
        return null;
    }

    @Override
    @Transactional
    public int del(int id) {
        SysMenu record = new SysMenu();
        record.setStatus(Const.Status_Del);
        record.setUpdatetime(Calendar.getInstance().getTime());
        List<Integer> ids = childIds(id);
        ids.add(id);
        SysMenuCriteria example = new SysMenuCriteria();
        example.createCriteria().andIdIn(ids);
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public List<SysMenu> findSysMenuByRoleId(int roleId) {
        return mapper.findSysMenuByRoleId(roleId);
    }

    @Override
    public List<SysMenu> findSysMenuByRoleIds(List<Integer> roleIds) {
        return mapper.findSysMenuByRoleIds(roleIds);
    }

    @Override
    public List<SysMenu> findSysMenuByUserId(int userId) {
        return mapper.findSysMenuByUserId(userId);
    }

    @Override
    public List<Integer> findSysMenuIdsByUserId(int userId) {
        return mapper.findSysMenuIdsByUserId(userId);
    }

    /**
     * 用户的所有按钮
     *
     * @param user
     * @return
     */
    @Override
    public List<SysMenu> findButtonByUserId(SysUser user) {
        boolean issys = user.getIssys() == Const.ISSYS;
        List<SysMenu> list = null;
        if (issys) {
            SysMenuCriteria criteria = new SysMenuCriteria();
            criteria.createCriteria().andStatusEqualTo(Const.Status_Normal).andMenutypeEqualTo(Const.MENU_ITEM_BUT);
            list = mapper.selectByExample(criteria);
        } else {
            list = mapper.findSysMenuByUserIdAndType(user.getId(), Const.MENU_ITEM_BUT);
        }
        return list;
    }

    /**
     * 用户的目录和菜单
     *
     * @param user
     * @return
     */
    @Override
    public List<SysMenu> findMenusByUserId(SysUser user) {
        boolean issys = user.getIssys() == Const.ISSYS;
        Integer rootid = rootMenu().getId();
        List<SysMenu> list = findMenuItemByParentId(rootid);
        if (!issys) {
            List<Integer> itemList = mapper.findSysMenuIdsByUserIdAndType(user.getId(), Const.MENU_ITEM_LIST);
            list = this.findMenuItemByParentIdAndUserId(list, itemList);
        }
        return list;
    }

    /**
     * 所有的菜单组和菜单
     *
     * @param parentid
     * @return
     */
    private List<SysMenu> findMenuItemByParentId(int parentid) {
        SysMenuCriteria sysMenuCriteria = new SysMenuCriteria();
        List<Byte> items = new ArrayList<>();
        items.add(Const.MENU_ITEM_GROUP);
        items.add(Const.MENU_ITEM);
        sysMenuCriteria.createCriteria().andStatusEqualTo(Const.Status_Normal).andParentidEqualTo(parentid).andMenutypeIn(items);
        sysMenuCriteria.setOrderByClause(" menuorder asc ");
        List<SysMenu> sysMenus = mapper.selectByExample(sysMenuCriteria);
        if (sysMenus != null && sysMenus.size() > 0) {
            for (SysMenu s : sysMenus) {
                List<SysMenu> childs = findMenuItemByParentId(s.getId());
                s.setChildren(childs);
            }
        }
        return sysMenus;
    }

    /**
     * 递归查询用户的菜单组和菜单
     *
     * @return
     */
    private List<SysMenu> findMenuItemByParentIdAndUserId(List<SysMenu> list, List<Integer> itemList) {
        if (itemList == null || itemList.size() == 0) {
            list.clear();
            return list;
        }
        List<SysMenu> selfMenus = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (SysMenu s : list) {
                List<Integer> childIds = this.childIds(s.getId());
                if (CollectionUtils.containsAny(childIds, itemList)) {
                    List<SysMenu> childs = findMenuItemByParentIdAndUserId(s.getChildren(), itemList);
                    if (childs != null && childs.size() > 0) {
                        s.setChildren(childs);
                    }
                    selfMenus.add(s);
                }
            }
        }
        return selfMenus;
    }

    /**
     * 获取parentId所有子孙节点的id集合
     *
     * @param parentId
     * @return
     */
    private List<Integer> childIds(int parentId) {
        List<Integer> list = new ArrayList<Integer>();
        SysMenuCriteria example = new SysMenuCriteria();
        example.createCriteria().andParentidEqualTo(parentId).andStatusEqualTo(Const.Status_Normal);
        List<SysMenu> menus = mapper.selectByExample(example);
        for (SysMenu d : menus) {
            list.add(d.getId());
            list.addAll(childIds(d.getId()));
        }
        return list;
    }
}
