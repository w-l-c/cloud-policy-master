package cn.rebornauto.platform.business.service;

import cn.rebornauto.platform.business.form.AgentCustomerForm;
import cn.rebornauto.platform.business.form.AgentInfoForm;
import cn.rebornauto.platform.business.query.AgentInfoQuery;
import cn.rebornauto.platform.business.query.PaymentStatisticsQuery;
import cn.rebornauto.platform.business.vo.*;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.pay.tonglian.entity.TongLianInfo;
import cn.rebornauto.platform.sys.entity.SysUser;

import java.util.List;

/**
 * <p>
 * Title: OauthController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zjl
 * @date 2019年4月28日
 */
public interface AgentInfoService {

	/**
	 * GZH根据customerId和openid判断返回页 0返回注册页面 1返回用户信息页面 2返回错误信息页面
	 * 
	 * @param query
	 * @return
	 */
	Byte getTypeByQuery(AgentInfoQuery query);

	/**
	 * GZH根据openid查询当前用户的信息
	 * 
	 * @param query
	 * @return
	 */
	GzhUserInfoVo selectUserInfoByOpenid(String openid);

	/**
	 * GZH根据openid查询代理人的信息
	 * 
	 * @param openid
	 * @return
	 */
	GzhAgentInfoVo selectAgentInfoByOpenid(String openid);

	/**
	 * GZH根据代理人id查询保险公司(客户)以及代征信息
	 * 
	 * @param id
	 * @return
	 */
	List<GzhAgentCustomerVo> selectAgentCustomerListByAgentId(int agentId);

	/**
	 * GZH录入代理人信息
	 * 
	 * @param form
	 * @throws Exception
	 */
	void addAgentInfo(AgentInfoForm form) throws Exception;

	/**
	 * 获取总条数
	 * 
	 * @param query
	 * @return
	 */
	int countByQuery(AgentInfoQuery query);

	/**
	 * 获取分页数据
	 * 
	 * @param pagination
	 * @param query
	 * @return
	 */
	List<AgentVo> pageQuery(Pagination pagination, AgentInfoQuery query);

	/**
	 * 代理人信息修改
	 * 
	 * @param form
	 * @return
	 */
	int edit(AgentInfoForm form);

	/**
	 * 添加银行卡
	 * 
	 * @param form
	 */
	Response addBankCardNo(AgentInfoForm form, TongLianInfo tongLianInfo);

	/**
	 * 代理人签约客户
	 * 
	 * @param form
	 * @throws Exception
	 */
	String agentSignCustomer(AgentCustomerForm form) throws Exception;

	/**
	 * 根据代理人客户中间表id查询合同所需数据
	 * 
	 * @param id
	 * @return
	 */
	GzhAgentCustomerVo selectAgentCustomerInfoById(int id);

	/**
	 * 全部代理人认证通过
	 * 
	 * @param form
	 */
	void allAuth(AgentInfoForm form);
	
	
    /**
     * 获取分页付款统计数据
     * @param pagination
     * @param query
     * @return
     */
	List<PaymentStatisticsVo> queryPaymentStatistics(Pagination pagination,
			PaymentStatisticsQuery query);
   /**
    *  获取付款统计获取总条数
    * @param query
    * @return
    */
	long countByQueryPaymentStatistics(PaymentStatisticsQuery query);
	
	
   /**
    * 导出付款统计
    * @param query
    * @return
    */
	List<PaymentStatisticsVo> queryPaymentStatisticsForExport(
			PaymentStatisticsQuery query);

	/**
	 * 定时任务执行个人证书申请查询
	 * 
	 * @param personalCertificateStatusVo
	 */
	void timingPersonalCertificateQuery(
			PersonalCertificateStatusVo personalCertificateStatusVo);

	/**
	 * 上上签回调接口
	 * @param contractId
	 * @return
	 */
	String callbackSign(String contractId);
	/**
	 * GZH录入代理人信息
	 * 
	 * @param form
	 * @throws Exception
	 */
	Response addAgentInfoSingle(AgentInfoForm form,SysUser user) throws Exception;

}
