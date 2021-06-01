package cn.rebornauto.platform.business.controller;

import cn.rebornauto.platform.business.form.MissionForm;
import cn.rebornauto.platform.business.query.MissionQuery;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.service.MissionService;
import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.business.vo.MissionVO;
import cn.rebornauto.platform.common.Const;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.ResponseCode;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.sys.entity.SysUser;
import cn.rebornauto.platform.upload.entity.UploadInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ligewei
 * @create 2020/06/04 15:15
 */

@RestController
@RequestMapping("/missionAccept")
public class MissionAcceptController extends BaseController {

    @Autowired
    private MissionService missionService;

    @Autowired
    private BusiLogService busiLogService;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private SysDicService sysDicService;

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Response list(@RequestBody Request<MissionForm,MissionQuery> request){
        Pagination pagination = request.getPagination();
        MissionQuery query = request.getQuery();
        TableBody body = new TableBody();
        List<MissionVO> list = missionService.selectMissionAcceptByQuery(pagination,query);
        body.setList(list);
        body.setPagination(pagination);
        return Response.ok().body(body);
    }


    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public Response upload(@RequestBody Request<MissionForm,MissionQuery> request, HttpServletRequest req){
        MissionForm form = request.getForm();
        setCurrUser(form);
        SysUser sysUser = currentUser();
        Response response = missionService.upload(form);
        if(ResponseCode.ERROR.value()==response.getCode()){
            return response;
        }
        busiLogService.add(req, Const.busi_log_busi_type_12, Const.busi_log_option_type_success,"任务认领成功", sysUser);
        return list(request);
    }

    @RequestMapping(value = "/detail",method = RequestMethod.GET, params = {"missionNo"})
    public Response detail(@RequestParam(required = true)String missionNo){
        UploadInfo uploadInfo=sysConfigService.getUploadInfo(sysDicService.selectSysPaySwitch());
        MissionVO missionVO = missionService.selectDetailByMissionNo(missionNo);
        if(StringUtils.isNotEmpty(missionVO.getExcel())) {
            missionVO.setExcel(uploadInfo.getDomain() + missionVO.getExcel());
        }
        if(StringUtils.isNotEmpty(missionVO.getFile())) {
            String[] files = missionVO.getFile().split(",");
            StringBuffer sb1 = new StringBuffer();
            for (String file : files) {
                sb1.append(uploadInfo.getDomain()).append(file).append(",");
            }
            missionVO.setFile(sb1.toString().substring(0, sb1.length() - 1));
        }
        if(StringUtils.isNotEmpty(missionVO.getImage())) {
            String[] images = missionVO.getImage().split(",");
            StringBuffer sb2 = new StringBuffer();
            for (String image : images) {
                sb2.append(uploadInfo.getDomain()).append(image).append(",");
            }
            missionVO.setImage(sb2.toString().substring(0, sb2.length() - 1));
        }
        return Response.ok().body(missionVO);
    }
}
