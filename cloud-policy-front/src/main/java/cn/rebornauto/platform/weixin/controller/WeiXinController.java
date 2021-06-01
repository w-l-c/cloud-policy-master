package cn.rebornauto.platform.weixin.controller;

import cn.rebornauto.platform.weixin.servlet.WeixinSupport;
import cn.rebornauto.platform.wx.conf.ApiConfig;
import cn.rebornauto.platform.wx.conf.WechatConst;
import cn.rebornauto.platform.wx.message.BaseMsg;
import cn.rebornauto.platform.wx.message.TextMsg;
import cn.rebornauto.platform.wx.message.req.TextReqMsg;
import cn.rebornauto.platform.wx.service.TemplateMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
@RequestMapping(value = "/api/weixin/validate")
public class WeiXinController extends WeixinSupport {
    private static final Logger LOG = LoggerFactory.getLogger(WeiXinController.class);

    @Autowired
    private ApiConfig apiConfig;

    @Autowired
    private TemplateMsgService templateMsgService;

    @Override
    protected String getToken() {
        return WechatConst.TOKEN;
    }

    @Override
    protected String getAppId() {
        return WechatConst.APPID;
    }

    @Override
    protected String getAESKey() {
        return WechatConst.AESKEY;
    }


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    protected final String bind(HttpServletRequest request) {
        if (isLegal(request)) {
            //绑定微信服务器成功
            return request.getParameter("echostr");
        } else {
            //绑定微信服务器失败
            return "";
        }
    }

    /**
     * 微信消息交互处理
     *
     * @param request  http 请求对象
     * @param response http 响应对象
     * @throws ServletException 异常
     * @throws IOException      IO异常
     */
    @RequestMapping(method = RequestMethod.POST)
    protected final void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isLegal(request)) {
            String result = processRequest(request);
            //设置正确的 content-type 以防止中文乱码
            response.setContentType("text/xml;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(result);
            writer.close();
        }
    }

    @Override
    protected BaseMsg handleTextMsg(TextReqMsg msg) {
        TextMsg textMsg = new TextMsg();
        //String content = msg.getContent() + "";
       // String str = content.replace("吗", "").replace("?", "!").replace("？", "!");
        textMsg.addln("有任何业务问题，请拨打人工客服电话：400-077-1099");
        return textMsg;
    }


}
