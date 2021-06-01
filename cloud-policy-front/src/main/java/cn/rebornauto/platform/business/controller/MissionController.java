package cn.rebornauto.platform.business.controller;

import cn.rebornauto.platform.business.query.MissionAcceptQuery;
import cn.rebornauto.platform.business.service.MissionAcceptService;
import cn.rebornauto.platform.business.vo.MissionAgentAcceptVo;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.data.request.Form;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.configuration.WxProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ligewei
 * @create 2020/06/04 15:14
 */

@RestController
@RequestMapping("/gzh/mission")
public class MissionController extends BaseController {

    @Autowired
    private WxProperties wxProperties;

    @Autowired
    private MissionAcceptService missionAcceptService;

    @PostMapping("/list")
    public Response list(@RequestBody Request<Form,MissionAcceptQuery> request,HttpServletRequest httpServletRequest){
        Pagination pagination = request.getPagination();
        MissionAcceptQuery query = request.getQuery();
        //验证openid
        //ServletContext application = httpServletRequest.getSession().getServletContext();
        //if(application.getAttribute(wxProperties.getApplicationWxOpenid()+query.getOpenid())==null){
        //    return Response.factory().code(-1).message("未授权");
        //}
        TableBody body = new TableBody();
        List<MissionAgentAcceptVo> list = missionAcceptService.selectByQuery(pagination,query);
        body.setList(list);
        body.setPagination(pagination);
        return Response.ok().body(body);
    }
}
