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
import cn.rebornauto.platform.common.exception.BizException;
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
@RequestMapping("/missionRelease")
public class MissionReleaseController extends BaseController {

    @Autowired
    private MissionService missionService;

    @Autowired
    private BusiLogService busiLogService;

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Response list(@RequestBody Request<MissionForm,MissionQuery> request){
        Pagination pagination = request.getPagination();
        MissionQuery query = request.getQuery();
        SysUser sysUser = currentUser();

        //判断是否为平台角色
        boolean administrator = isAdministrator(sysUser.getCustomerId());
        if (administrator) {
            query.setCustomerId(null);
        }else{
            query.setCustomerId(sysUser.getCustomerId());
        }
        TableBody body = new TableBody();
        List<MissionVO> list = missionService.selectMissionReleaseByQuery(pagination,query);
        body.setList(list);
        body.setPagination(pagination);
        return Response.ok().body(body);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Response add(@RequestBody Request<MissionForm,MissionQuery> request, HttpServletRequest req)throws BizException{
        MissionForm form = request.getForm();
        setCurrUser(form);
        SysUser currentUser = currentUser();
        if(missionService.add(form)<1){
            throw new BussinessException(BizExceptionEnum.SERVER_ERROR);
        }
        busiLogService.add(req, Const.busi_log_busi_type_12, Const.busi_log_option_type_xinzeng,"新增任务", currentUser);
        return list(request);
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public Response edit(@RequestBody Request<MissionForm,MissionQuery> request, HttpServletRequest req){
        MissionForm form = request.getForm();
        setCurrUser(form);
        SysUser currentUser = currentUser();
        if(missionService.edit(form)<1){
            throw new BussinessException(BizExceptionEnum.SERVER_ERROR);
        }
        busiLogService.add(req, Const.busi_log_busi_type_12, Const.busi_log_option_type_xiugai,"修改任务", currentUser);
        return list(request);
    }

    @RequestMapping(value = "/detail",method = RequestMethod.GET, params = {"missionNo"})
    public Response detail(@RequestParam(required = true)String missionNo){
        return Response.ok().body(missionService.selectDetailByMissionNo(missionNo));
    }


    @RequestMapping(value = "/toExamine",method = RequestMethod.POST)
    public Response toExamine(@RequestBody Request<MissionForm,MissionQuery> request,HttpServletRequest req){
        MissionForm form = request.getForm();
        setCurrUser(form);
        MissionVO vo = missionService.selectDetailByMissionNo(form.getMissionNo());
        SysUser sysUser = currentUser();
        boolean administrator = isAdministrator(sysUser.getCustomerId());
        if(!sysUser.getCustomerId().equals(vo.getCustomerId())&&!administrator){
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        if(missionService.toExamine(form)<1){
            throw new BussinessException(BizExceptionEnum.SERVER_ERROR);
        }
        busiLogService.add(req, Const.busi_log_busi_type_12, Const.busi_log_option_type_shenqing,"提交审核", sysUser);
        return list(request);
    }


}
