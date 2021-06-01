package cn.rebornauto.platform.sys.controller;

import cn.rebornauto.platform.common.utils.json.JsonInt2StrValueFilter;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import cn.rebornauto.platform.common.data.request.Form;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.sys.entity.SysMenu;
import cn.rebornauto.platform.sys.form.SysMenuForm;
import cn.rebornauto.platform.sys.service.SysMenuService;
import cn.rebornauto.platform.common.utils.json.JsonNameFilter;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;


    /**
     * 返回菜单树
     *
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions("sys:menu:list")
    public Response list() {
        SysMenu root = sysMenuService.rootMenu();
        List<SysMenu> first = sysMenuService.findChildByParentId(root.getId());
        return Response.ok().body(first);
    }

    /**
     * 返回菜单树
     *
     * @return
     */
    @RequestMapping("tree")
    @RequiresPermissions("sys:menu:list")
    public Response tree() {
        SysMenu root = sysMenuService.rootMenu();
        List<SysMenu> first = sysMenuService.findChildByParentId(root.getId());
        root.setChildren(first);
        List<SysMenu> trees = new ArrayList<>();
        trees.add(root);
        return Response.ok().body(trees);
    }

    /**
     *
     */
    @RequestMapping("dic")
    public Response dic() {
        SysMenu root = sysMenuService.rootMenu();
        List<SysMenu> first = sysMenuService.findChildByParentId(root.getId());
        root.setChildren(first);
        List<SysMenu> trees = new ArrayList<>();
        trees.add(root);
        SimplePropertyPreFilter spp = new SimplePropertyPreFilter("id", "menuname", "parentid", "children", "menutype");
        JsonNameFilter namefilter = new JsonNameFilter("id>key;menuname>value;parentid>parentKey");
        ValueFilter vf = new JsonInt2StrValueFilter("key", "parentKey");
        String str = JSONObject.toJSONString(trees, new SerializeFilter[]{spp, namefilter,vf});
        return Response.ok().body(JSONObject.parse(str));
    }

    /**
     * 新增目录菜单，按钮
     *
     * @param sysMenuForm
     * @return 顶级目录
     * 目录
     * 菜单
     * 列表 按钮 按钮
     * 菜单
     * 列表 按钮 按钮
     * 目录
     * 菜单
     */
    @RequestMapping("save")
    @RequiresPermissions("sys:menu:save")
    public Response save(HttpServletRequest request, @RequestBody @Validated SysMenuForm sysMenuForm) {
        SysMenu sysMenu = sysMenuForm.toSysMenu();
        int code = sysMenuService.save(sysMenu);
        if (code > 0) {
            return tree();
        } else {
            return Response.factory().code(code).message("error");
        }
    }

    @RequestMapping("update")
    @RequiresPermissions("sys:menu:update")
    public Response update(@RequestBody @Validated SysMenuForm sysMenuForm) {
        SysMenu sysMenu = sysMenuForm.toSysMenu();
        //类型不能改
        sysMenu.setMenutype(null);
        int code = sysMenuService.update(sysMenu);
        if (code > 0) {
            return tree();
        } else {
            return Response.factory().code(code).message("error");
        }
    }

    /**
     * 根据id查询一条
     *
     * @return
     */
    @RequestMapping("/get")
    @RequiresPermissions("sys:menu:get")
    public Response get(@RequestParam int id) {
        SysMenu sysMenu = sysMenuService.findById(id);
        return Response.ok().body(sysMenu);
    }

    @PostMapping("/del")
    @RequiresPermissions("sys:menu:del")
    public Response del(@RequestBody Form form) {
        sysMenuService.del(form.getId());
        return tree();
    }
}
