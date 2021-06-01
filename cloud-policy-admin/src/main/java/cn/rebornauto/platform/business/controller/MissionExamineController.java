package cn.rebornauto.platform.business.controller;

import cn.rebornauto.platform.business.form.MissionForm;
import cn.rebornauto.platform.business.query.MissionQuery;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.service.MissionService;
import cn.rebornauto.platform.business.vo.MissionVO;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.common.exception.BizExceptionEnum;
import cn.rebornauto.platform.common.exception.BussinessException;
import cn.rebornauto.platform.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ligewei
 * @create 2020/06/04 15:15
 */

@RestController
@RequestMapping("/missionExamine")
public class MissionExamineController extends BaseController {

    @Autowired
    private MissionService missionService;

    @Autowired
    private BusiLogService busiLogService;

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Response list(@RequestBody Request<MissionForm,MissionQuery> request){
        Pagination pagination = request.getPagination();
        MissionQuery query = request.getQuery();
        TableBody body = new TableBody();
        List<MissionVO> list = missionService.selectMissionExamineByQuery(pagination,query);
        body.setList(list);
        body.setPagination(pagination);
        return Response.ok().body(body);
    }

    @RequestMapping(value = "/adopt",method = RequestMethod.POST)
    public Response adopt(@RequestBody Request<MissionForm,MissionQuery> request, HttpServletRequest req){
        MissionForm form = request.getForm();
        setCurrUser(form);
        SysUser sysUser = currentUser();
        if(missionService.adopt(form)<1){
            throw new BussinessException(BizExceptionEnum.SERVER_ERROR);
        }
        busiLogService.add(req, Const.busi_log_busi_type_12, Const.busi_log_option_type_tongguo,"审核通过", sysUser);
        return list(request);
    }

    @RequestMapping(value = "/reject",method = RequestMethod.POST)
    public Response reject(@RequestBody Request<MissionForm,MissionQuery> request, HttpServletRequest req){
        MissionForm form = request.getForm();
        setCurrUser(form);
        SysUser sysUser = currentUser();
        if(missionService.reject(form)<1){
            throw new BussinessException(BizExceptionEnum.SERVER_ERROR);
        }
        busiLogService.add(req, Const.busi_log_busi_type_12, Const.busi_log_option_type_bohui,"审核驳回", sysUser);
        return list(request);
    }

    @RequestMapping(value = "/detail",method = RequestMethod.GET, params = {"missionNo"})
    public Response detail(@RequestParam(required = true)String missionNo){
        return Response.ok().body(missionService.selectDetailByMissionNo(missionNo));
    }
}
