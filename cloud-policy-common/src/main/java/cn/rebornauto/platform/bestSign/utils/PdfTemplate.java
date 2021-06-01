package cn.rebornauto.platform.bestSign.utils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class PdfTemplate {
	/**
	 * 赋值并生成新的PDF文档
	 * @param templatePDF    pdf模版
	 * @param hashMap    templatePDF对应的数据
	 * @author  JIA-G-Y 
	 * 
	 */ 
	public static void doSomeThing(String templatePDF,HashMap<String,String> hashMap,HttpServletResponse response){
		try {
			/*FileOutputStream fos = new FileOutputStream(outFile);*/
			OutputStream fos = response.getOutputStream();
			PdfReader reader = new PdfReader(templatePDF);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfStamper stamp = new PdfStamper(reader,baos);
			AcroFields form = stamp.getAcroFields();
			//5给表单添加中文字体 这里采用系统字体。不设置的话，中文可能无法显示
			BaseFont bf = BaseFont.createFont("http://fc.chunlvbank.com/aliyunNAS/pdf/SIMSUN.TTC,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			/*BaseFont bf = BaseFont.createFont("http://192.168.1.60/home/SIMSUN.TTC,1",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);*/
			form.addSubstitutionFont(bf);
			form=setField(form,hashMap);
			stamp.setFormFlattening(true);
			stamp.close();
			Document doc = new Document();
			PdfCopy pdfCopy = new PdfCopy(doc, fos);
			doc.open();
			PdfImportedPage impPage = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
			pdfCopy.addPage(impPage);
			doc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	public static AcroFields setField(AcroFields form, HashMap<String,String> fieldMap) {
		Set it=fieldMap.keySet();
		Iterator itr=it.iterator();
		while(itr.hasNext()){
			try {
				Object temp = itr.next();
				form.setField(temp.toString(), fieldMap.get(temp.toString()));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		return form;
	}
	
	/**
	 * chun_xiao
	 * @param pdfUrl
	 * @param hashMap
	 * @param filePathPdf
	 */
	public static void doSomeThingTest(String pdfUrl, HashMap<String, String> hashMap, String filePathPdf,String simsun) {
		try {
			FileOutputStream fos = new FileOutputStream(pdfUrl);
			PdfReader reader = new PdfReader(filePathPdf);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfStamper stamp = new PdfStamper(reader,baos);
			AcroFields form = stamp.getAcroFields();
			//5给表单添加中文字体 这里采用系统字体。不设置的话，中文可能无法显示
			BaseFont bf = BaseFont.createFont(simsun,BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

/*			BaseFont bf = BaseFont.createFont(PdfTemplate.class.getClassLoader().getResource("/").toString().substring(5,PdfTemplate.class.getClassLoader().getResource("/").toString().length())+"static/simsun/SIMSUN.TTC,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
*/			form.addSubstitutionFont(bf);
			form=setField(form,hashMap);
			stamp.setFormFlattening(true);
			stamp.close();
			Document doc = new Document();
			PdfCopy pdfCopy = new PdfCopy(doc, fos);
			doc.open();
			PdfReader pd = new PdfReader(baos.toByteArray());
			for(int i= 1;i<=pd.getNumberOfPages();i++){
				PdfImportedPage impPage = pdfCopy.getImportedPage(pd, i);
				pdfCopy.addPage(impPage);
			}
			doc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
}
