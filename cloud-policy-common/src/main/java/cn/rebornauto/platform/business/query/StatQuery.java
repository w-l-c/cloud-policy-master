package cn.rebornauto.platform.business.query;

import java.time.LocalDate;

import cn.rebornauto.platform.common.data.request.Query;
import lombok.Data;

@Data
public class StatQuery extends Query {
    /**
     *  客户编号
     */
    private String customerId;
    /**
     * 年
     */
    private Integer year;
    /**
     * 月
     */
    private Integer month;
    /**
     * 日
     */
    private Integer day;
    
    private LocalDate date;
    
    private String queryType;
    
    private String dateStr;
    
    /**
	 * 查询时间
	 */
	private String queryTime;
	/**
	 * 起始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	
	public void setQueryTime(String queryTime) {
        this.queryTime = queryTime;
        if (!"".equals(queryTime) && queryTime != null) {
            String[] split = queryTime.split("~");
            this.startTime = split[0];
            this.endTime = split[1];
        }
    }
	
	/**
	 * 完成时间
	 */
	private String completeTime;
	
	public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
        if (!"".equals(completeTime) && completeTime != null) {
            String[] split = completeTime.split("~");
            if(split.length == 1) {
            	this.startTime = split[0];
                this.endTime = split[0];
            }else {
            	this.startTime = split[0];
                this.endTime = split[1];
            }
        }
    }
	
	/**
	 * 代征区域
	 * 天津  tianjin
	 * 上海  shanghai
	 * 厦门  xiamen
	 * 汕头  shantou
	 */
	private String daiZhengArea;
}
