package cn.rebornauto.platform.sys.controller;

import cn.rebornauto.platform.common.utils.json.JsonInt2StrValueFilter;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.sys.entity.SysRole;
import cn.rebornauto.platform.sys.entity.SysRoleCriteria;
import cn.rebornauto.platform.sys.form.SysRoleForm;
import cn.rebornauto.platform.sys.query.SysRoleQuery;
import cn.rebornauto.platform.sys.service.SysRoleService;
import cn.rebornauto.platform.common.utils.json.JsonNameFilter;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public Response list(@RequestBody Request<SysRoleForm, SysRoleQuery> request) {
        Pagination pagination = request.getPagination();
        SysRoleQuery query = request.getQuery();

        TableBody body = TableBody.factory();
        body.setPagination(pagination);
        //获取总条数
        long rowcount = sysRoleService.countQuery(query);
        pagination.setTotal(rowcount);
        //获取分页数据
        List<SysRole> list = sysRoleService.pageQuery(pagination, query);
        if (list != null) {
            for (SysRole r : list) {
                List<Integer> menuids = sysRoleService.findMenuids(r.getId());
                r.setMenuids(menuids);
            }
        }
        body.setList(list);
        body.putDictionary("status", statusDic());
        return Response.ok().body(body);
    }

    @RequestMapping("/dic")
    public Response dic() {
        SysRoleCriteria example = new SysRoleCriteria();
        example.createCriteria().andStatusEqualTo(Const.Status_Normal);
        example.setOrderByClause(" id desc ");
        //获取分页数据
        List<SysRole> list = sysRoleService.findPageByExample(example);
        if (list == null) {
            return Response.ok();
        }
        SimplePropertyPreFilter spp = new SimplePropertyPreFilter("id", "rolename");
        JsonNameFilter namefilter = new JsonNameFilter("id>key;rolename>value");
        ValueFilter vf = new JsonInt2StrValueFilter("key");
        String str = JSONObject.toJSONString(list, new SerializeFilter[]{spp, namefilter,vf});
        return Response.ok().body(JSONObject.parse(str));
    }

    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public Response save(@RequestBody @Validated Request<SysRoleForm, SysRoleQuery> request) {
        SysRoleForm form = request.getForm();
        int code = sysRoleService.save(form);
        if (code <= 0) {
            return Response.factory().code(code).message("error");
        }
        return list(request);
    }

    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public Response update(@RequestBody @Validated Request<SysRoleForm, SysRoleQuery> request) {
        SysRoleForm form = request.getForm();
        int code = sysRoleService.update(form);
        if (code <= 0) {
            return Response.factory().code(code).message("error");
        }
        return list(request);
    }

    //todo
    @RequestMapping("/get")
    @RequiresPermissions("sys:role:get")
    public Response get(int id) {
        SysRoleForm sysRoleForm = new SysRoleForm();
        SysRole sysRole = sysRoleService.findById(id);
        if (sysRole != null) {
            sysRoleForm.setId(sysRole.getId());
            sysRoleForm.setRemark(sysRole.getRemark());
            sysRoleForm.setRoleName(sysRole.getRolename());
            sysRoleForm.setStatus(sysRole.getStatus());
            List<Integer> menuids = sysRoleService.findMenuids(sysRole.getId());
            List<String> menuidstr = new ArrayList<>();
            for (int i = 0; i < menuids.size(); i++) {
                menuidstr.add(menuids.get(i).toString());
            }
            sysRoleForm.setMenuids(menuidstr);
        }
        return Response.ok().body(sysRoleForm);
    }

    @RequestMapping("/del")
    @RequiresPermissions("sys:role:del")
    public Response del(@RequestBody Request<SysRoleForm, SysRoleQuery> request) {
        int id = request.getForm().getId();
        sysRoleService.del(id);
        return list(request);
    }
}
