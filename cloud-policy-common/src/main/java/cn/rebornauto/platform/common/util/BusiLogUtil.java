package cn.rebornauto.platform.common.util;

import cn.rebornauto.platform.business.entity.BusiLog;
import cn.rebornauto.platform.common.data.request.Form;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 操作日志 optionType 1 用于查询展示
 */

public class BusiLogUtil {
    /**
     * 账户登录日志记录
     * @param user
     * @param ip
     * @return
     */
    public static BusiLog getLogin(Form user,String ip) {
        BusiLog busiLog = getBusiLog(user, ip);
        busiLog.setOptionContent("账户登录");
        busiLog.setOptionType(6);
        return busiLog;
    }
    /**
     * 账户修改信息日志记录
     * @param user 用户
     * @param ip IP地址
     * @return
     */
    public static BusiLog update(Form user,String ip) {
        BusiLog busiLog = getBusiLog(user, ip);
        busiLog.setOptionContent("账户修改信息");
        busiLog.setOptionType(2);
        return busiLog;
    }
     /**
     * 上传充值凭证日志记录
     * @param user 用户
     * @param ip IP地址
     * @return
     */
    public static BusiLog rechargeUploadRechargeVoucher(Form user,String ip) {
        BusiLog busiLog = getBusiLog(user, ip);
        busiLog.setOptionContent("上传充值到账凭证");
        busiLog.setOptionType(1);
        return busiLog;
    }
    /**
     * 账户提交充值申请日志记录
     * @param user 用户
     * @param ip IP地址
     * @return
     */
    public static BusiLog rechargeAdd(Form user,String ip) {
        BusiLog busiLog = getBusiLog(user, ip);
        busiLog.setOptionContent("账户提交充值申请");
        busiLog.setOptionType(1);
        return busiLog;
    }

    /**
     * 客户对代理人操作日志记录
     * @param user
     * @param ip
     * @param type 操作类型  1 认证通过  2 认证拒绝  3 暂停代理人  4 启用代理人
     * @param agentName
     * @return
     */
    public static BusiLog agent(Form user,String ip,int type, String agentName) {
        BusiLog busiLog = getBusiLog(user, ip);
        StringBuffer sb = new StringBuffer();
        switch (type) {
            case 1:
                sb.append("通过了代理人").append(agentName).append("的认证");
                break;
            case 2:
                sb.append("拒绝了代理人").append(agentName).append("的认证");
                break;
            case 3:
                sb.append("暂停了代理人").append(agentName);
                break;
            case 4:
                sb.append("启用了代理人").append(agentName);
                break;

        }
        busiLog.setOptionContent(sb.toString());
        busiLog.setOptionType(1);
        return busiLog;
    }
    /**
     * 账户提交付款申请日志记录
     * @param user 用户
     * @param m 付款总金额
     * @param ip IP地址
     * @return
     */
    public static BusiLog paymentApply(Form user, String ip, BigDecimal m) {
        BusiLog busiLog = getBusiLog(user, ip);
        StringBuffer sb = new StringBuffer();
        sb.append("提交付款金额为").append(m).append("元的付款申请");
        busiLog.setOptionContent(sb.toString());
        busiLog.setOptionType(1);
        return busiLog;
    }
    /**
     * 账户付款审核日志记录
     * @param user 用户
     * @param m 付款总金额
     * @param ip IP地址
     * @param type 操作类型  1 审核通过  2 审核驳回
     * @return
     */
    public static BusiLog financialPayment(Form user, String ip, BigDecimal m,int type) {
        BusiLog busiLog = getBusiLog(user, ip);
        StringBuffer sb = new StringBuffer();
        switch (type) {
            case 1:
                sb.append("通过了");
                break;
            case 2:
                sb.append("拒绝了");
                break;
        }
        sb.append("付款金额为").append(m).append("元的付款申请");
        busiLog.setOptionContent(sb.toString());
        busiLog.setOptionType(1);
        return busiLog;
    }
    /**
     * 上传付款清单
     * @param user 用户
     * @param ip IP地址
     * @return
     */
    public static BusiLog uploadPaymentInfo(Form user,String ip) {
        BusiLog busiLog = getBusiLog(user, ip);
        busiLog.setOptionContent("上传付款清单");
        busiLog.setOptionType(1);
        return busiLog;
    }

    public static BusiLog getBusiLog(Form user, String ip) {
        BusiLog busiLog = new BusiLog();
        busiLog.setCustomerId(user.getUserCustomerId());
        busiLog.setIp(ip);
        busiLog.setCreatetime(LocalDateTime.now());
        busiLog.setCreateoper(user.getCurrUserName());
        return busiLog;
    }
}
