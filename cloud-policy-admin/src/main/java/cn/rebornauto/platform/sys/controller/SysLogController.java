package cn.rebornauto.platform.sys.controller;

import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.data.request.Form;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.sys.entity.SysLogs;
import cn.rebornauto.platform.sys.query.SysLogQuery;
import cn.rebornauto.platform.sys.service.SysLogsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/sys/log")
public class SysLogController extends BaseController{

    @Autowired
    private SysLogsService sysLogsService;

    @RequestMapping("/list")
    @RequiresPermissions("sys:log:list")
    public Response list(@RequestBody Request<Form,SysLogQuery> request){
        Pagination pagination = request.getPagination();
        SysLogQuery query = request.getQuery();

        TableBody body = TableBody.factory();
        body.setPagination(pagination);
        //获取总条数
        long rowcount = sysLogsService.countQuery(query);
        pagination.setTotal(rowcount);
        //获取分页数据
        List<SysLogs> list = sysLogsService.pageQuery(pagination, query);
        body.setList(list);
        body.putDictionary("status",statusDic());
        return Response.ok().body(body);

    }
}
