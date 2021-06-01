package cn.rebornauto.platform.business.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.rebornauto.platform.business.query.BusiLogQuery;
import cn.rebornauto.platform.business.service.BusiLogService;
import cn.rebornauto.platform.business.vo.BusiLogVo;
import cn.rebornauto.platform.common.controller.BaseController;
import cn.rebornauto.platform.common.controller.ExportExcel;
import cn.rebornauto.platform.common.data.request.Form;
import cn.rebornauto.platform.common.data.request.Pagination;
import cn.rebornauto.platform.common.data.request.Request;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.common.data.view.TableBody;
import cn.rebornauto.platform.common.util.DateUtils;
import cn.rebornauto.platform.common.util.LogUtil;
import cn.rebornauto.platform.pay.tonglian.utils.DateUtil;
import cn.rebornauto.platform.sys.entity.SysUser;

@RestController
@RequestMapping("/busiLog")
public class BusiLogController extends BaseController {
    @Autowired
    private BusiLogService busiLogService;

    /**
     * 查看日志文件
     * @param request
     * @return
     */
    @PostMapping("/list")
    @RequiresPermissions("busiLog:list")
    public Response list(@RequestBody Request<Form, BusiLogQuery> request){
        Pagination pagination = request.getPagination();
        BusiLogQuery query = request.getQuery();
        //获取操作人信息
        SysUser sysUser = currentUser();
        //判断是否为平台角色
        boolean administrator = isAdministrator(sysUser.getCustomerId());
        if (!administrator) {
            //不是平台角色需添加客户角色查询限制
            query.setCustomerId(sysUser.getCustomerId());
        }
        TableBody body = TableBody.factory();
        int rowcount = busiLogService.count(query);
        pagination.setTotal(rowcount);
        body.setPagination(pagination);
        List<BusiLogVo> list = busiLogService.list(query,pagination);
        body.setList(list);
        return Response.ok().body(body);
    }
    
    
    /**
	 * 日志导出
	 * @param response
	 * @param request
	 * @return
	 */
	@SuppressWarnings("all")
    @GetMapping("/exportBusiLog")
    public String exportTransflowStat(HttpServletResponse response, HttpServletRequest request){
		String customerId = request.getParameter("customerId");
		
    	String createtime = request.getParameter("createtime");
    	BusiLogQuery query = new BusiLogQuery();
    	query.setCreatetime(createtime);
    	if(StringUtils.isBlank(createtime)) {
    		LogUtil.info("日期不能为空!");
    		return "日期不能为空!";
    	}else{
    		if(DateUtil.getDaySub(query.getStartTime(), query.getEndTime())>31) {
    			LogUtil.info("日期范围不能超过一个月!");
        		return "日期范围不能超过一个月!";
    		}
    	}
    	
    	
        LogUtil.info("导出 日志 数据");
        try{
        	//获取操作人信息
            SysUser sysUser = currentUser();
        	//判断是否为平台角色
            boolean administrator = isAdministrator(sysUser.getCustomerId());
            if (!administrator) {
                //不是平台角色需添加客户角色查询限制
                query.setCustomerId(sysUser.getCustomerId());
            }else {
            	if(StringUtils.isNotBlank(customerId)) {
            		query.setCustomerId(customerId);
            	}
            }
            
            
            response.setCharacterEncoding("UTF-8");
            String filedisplay = "日志数据.xls";
            filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
            response.setContentType("application/x-download");

            List<BusiLogVo> busiList = busiLogService.selectBusiLogExcelByQuery(query);
            
            List<Object[]> dataList=new ArrayList<Object[]>();
            for(int i=0;i<busiList.size();i++){
            	int s0 = i+1;
            	String s1 = DateUtils.LocalDateTimeToString(busiList.get(i).getCreatetime());
            	String s2= 	busiList.get(i).getCustomerName();
            	String s3 = busiList.get(i).getCustomerId();
            	String s4 = busiList.get(i).getCreateoper();
            	String s5 = busiList.get(i).getIp();
            	String s6 = busiList.get(i).getOptionContent();
            	
                
                Object[] datas=new Object[]{s0,s1,s2,s3,s4,s5,s6};
                dataList.add(datas);
            }
            String [] rowName = new String[] {
            		"序号",
            		"创建时间",
            		"客户名称",
                    "客户编号",
                    "用户名",
                    "ip地址",
                    "操作内容"
            };
            ExportExcel exportExcel = new ExportExcel(response,"日志数据",rowName,dataList);
            exportExcel.export();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return null;
    }
}
