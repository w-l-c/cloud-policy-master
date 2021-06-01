package cn.rebornauto.platform.sys.controller;
import cn.rebornauto.platform.common.utils.json.JsonInt2StrValueFilter;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import cn.rebornauto.platform.common.data.request.Form;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.sys.entity.SysDept;
import cn.rebornauto.platform.sys.form.SysDeptForm;
import cn.rebornauto.platform.sys.service.SysDeptService;
import cn.rebornauto.platform.common.utils.json.JsonNameFilter;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @PostMapping("save")
    @RequiresPermissions("sys:dept:save")
    public Response saveDept(@RequestBody @Validated SysDeptForm sysDeptForm) {
        int z = sysDeptService.save(sysDeptForm);
        if(z==1){
            return tree();
        }
        return Response.factory().code(z);
    }

    @PostMapping("update")
    @RequiresPermissions("sys:dept:update")
    public Response updateDept(@RequestBody @Validated SysDeptForm sysDeptForm) {
        int z = sysDeptService.update(sysDeptForm);
        if(z==1){
            return tree();
        }
        return Response.factory().code(z);
    }


    @RequestMapping("tree")
    @RequiresPermissions("sys:dept:tree")
    public Response tree() {
        SysDept root = sysDeptService.rootDept();
        if (root != null) {
            List<SysDept> childs = sysDeptService.findChildByParentId(root.getId());
            root.setChildren(childs);
        }
        List<SysDept> trees = new ArrayList<>();
        trees.add(root);
        return Response.ok().body(trees);
    }

    @RequestMapping("dic")
    public Response dic() {
        SysDept root = sysDeptService.rootDept();
        if (root != null) {
            List<SysDept> childs = sysDeptService.findChildByParentId(root.getId());
            root.setChildren(childs);
        }
        List<SysDept> trees = new ArrayList<>();
        trees.add(root);
        SimplePropertyPreFilter spp = new SimplePropertyPreFilter("id", "name", "parentid","children");
        JsonNameFilter namefilter = new JsonNameFilter("id>key;name>value;parentid>parentKey");
        ValueFilter vf = new JsonInt2StrValueFilter("key", "parentKey");
        String str = JSONObject.toJSONString(trees, new SerializeFilter[]{spp, namefilter,vf});
        return Response.ok().body(JSONObject.parse(str));
    }

    @PostMapping("del")
    @RequiresPermissions("sys:dept:del")
    public Response remove(@RequestBody Form form) {
        sysDeptService.remove(form.getId());
        return tree();
    }

}
