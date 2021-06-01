package cn.rebornauto.platform.wx.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rebornauto.platform.wx.api.TemplateMsgAPI;
import cn.rebornauto.platform.wx.api.WxSmallProgramAPI;
import cn.rebornauto.platform.wx.conf.ApiConfig;


@Service
@Slf4j
public class TemplateMsgService {

    @Autowired
    private TemplateMsgAPI templateMsgAPI;

/*    @Autowired
    private OrderMapper orderMapper;*/

    @Autowired
    private ApiConfig apiConfig;

    @Autowired
    private WxSmallProgramAPI smallProgramAPI;

    /**
     * 售卖端竞拍结果通知
     * @param orderId
     */
/*    public void dealerAuctionMsg(int orderId){
        List<AuctionMsg> list = orderMapper.selectDealerMsgByOrderId(orderId,apiConfig.getAppid(),smallProgramAPI.getDealerAppId());
        list.forEach(platformMsg -> {
             TemplateMsg msg = new TemplateMsg();
             msg.setTemplateId(WechatConst.MSG_TEMPLATE_AUCTION_ID);
             msg.setTouser(platformMsg.getOpenid());
             Map<String, TemplateParam> data = new HashMap<>();
             data.put("first",new TemplateParam("尊敬的会员，您发起的拍卖结果如下"));
             data.put("keyword1",new TemplateParam(platformMsg.getModelName()));
             data.put("keyword2",new TemplateParam(DateUtils.LocalDateTimeToChineseString(platformMsg.getBidTime())));
             data.put("keyword3",new TemplateParam(platformMsg.getStatus()));
             data.put("remark",new TemplateParam("谢谢您的参与"));
             msg.setData(data);
             SendTemplateResponse r = templateMsgAPI.send(msg);
             log.info("售卖端竞拍通知"+r.toJsonString());
        });
    }*/

    /**
     * 竞价端竞拍结果通知
     * @param orderId
     */
/*    public void bidderAuctionMsg(int orderId){
        List<AuctionMsg> list = orderMapper.selectBidderMsgByOrderId(orderId,apiConfig.getAppid(),smallProgramAPI.getBidderAppId());
        list.forEach(platformMsg -> {
            TemplateMsg msg = new TemplateMsg();
            msg.setTemplateId(WechatConst.MSG_TEMPLATE_AUCTION_ID);
            msg.setTouser(platformMsg.getOpenid());
            Map<String, TemplateParam> data = new HashMap<>();
            data.put("first",new TemplateParam("尊敬的会员，您参与的拍卖结果如下"));
            data.put("keyword1",new TemplateParam(platformMsg.getModelName()));
            data.put("keyword2",new TemplateParam(DateUtils.LocalDateTimeToChineseString(platformMsg.getBidTime())));
            data.put("keyword3",new TemplateParam("1".equals(platformMsg.getIsWinner())?"中标":"未中标"));
            data.put("remark",new TemplateParam("谢谢您的参与"));
            msg.setData(data);
            SendTemplateResponse r = templateMsgAPI.send(msg);
            log.info("竞价端竞拍通知"+r.toJsonString());
        });
    }*/
}
