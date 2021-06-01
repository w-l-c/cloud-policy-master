package cn.rebornauto.platform.business.form;

import lombok.Data;

/** Title: 
 * Description: 
 * Company: 
 * @author kgc
 * @date Mar 25, 2020 3:56:39 PM
 */
@Data
public class UploadZipForm {

	/**
	 * excel数据文件
	 */
	private String excelFile;
	/**
	 * 身份证正面
	 */
	private String idcardnoZm;
	/**
	 * 身份证反面
	 */
	private String idcardnoFm;
	/**
	 * pdf文件
	 */
	private String pdfFile;
}
