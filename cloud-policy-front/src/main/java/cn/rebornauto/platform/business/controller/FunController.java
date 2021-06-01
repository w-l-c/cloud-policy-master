package cn.rebornauto.platform.business.controller;

import cn.rebornauto.platform.business.entity.RegionOption;
import cn.rebornauto.platform.business.service.AreaService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.configuration.WxProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gzh/fun")
public class FunController {
    //枚举字典类型
    //地区管理

    @Autowired
    private AreaService areaService;

    @Autowired
    private WxProperties wxProperties;

    @Autowired
    private SysDicService dicService;


    @GetMapping("/region/province")
    public Response province() {
        ////验证openid
        //ServletContext application = httpServletRequest.getSession().getServletContext();
        //if(application.getAttribute(wxProperties.getApplicationWxOpenid()+openid)==null){
        //    return Response.factory().code(-1).message("未授权");
        //}
        List<RegionOption> provinces = areaService.listProvince();
        return Response.ok().body(provinces);
    }


}
