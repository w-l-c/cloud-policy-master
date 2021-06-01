package cn.rebornauto.platform.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.rebornauto.platform.business.vo.ExcelTemplateVO;
import cn.rebornauto.platform.common.Const;

public class ExcelUtil {
	private  FileOutputStream output;  
    private Sheet sheet;  
    private SXSSFWorkbook wb;  
    private Integer countRow=0;  
    private Map<String, PropertyDescriptor>objPropertyMap;  
    

    /** 
     * 初始化 
     * @param xls_write_Address 
     * @param fieldNames 
     * @throws FileNotFoundException 
     */  
    public void init_Excel( String path,String fileName,String[] fieldNames) throws FileNotFoundException{  
          
        File pathfile=new File(path);  
        if(!pathfile.exists()){  
            pathfile.mkdirs();  
        }  
          
          
        output = new FileOutputStream(new File(path+fileName));  //读取的文件路径     
        wb = new SXSSFWorkbook(1000);//内存中保留 1000 条数据，以免内存溢出，其余写入 硬盘        
        sheet = wb.createSheet(String.valueOf("sheet"));    
        wb.setSheetName(0, "sheet");     
        sheet.autoSizeColumn(1);  
        Row row = sheet.createRow(countRow++);   
        for(int i=0;i<fieldNames.length;i++){  
            Cell cell = row.createCell(i);                       
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式                      
            //sheet.setColumnWidth(i, fieldNames[i].length()*384); //设置单元格宽度    
            cell.setCellValue(fieldNames[i]);//写入内容    
        }  
          
    }  
      
    /** 
     *  
     * @param datalist 
     * @param dataFields 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws InvocationTargetException 
     * @throws IOException 
     * @throws IntrospectionException 
     */  
    public void write_data_Excel(List<Object> datalist,String[] dataFields) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, IntrospectionException{  
        write_data_Excel(datalist,dataFields,null,null);  
    }  
      
    public void write_data_Excel(List<Object> datalist,String[] dataFields,Map<String, Map<String, String>> dataDic) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, IntrospectionException{  
        write_data_Excel(datalist,dataFields,dataDic,null);  
    }  
      
    /** 
     * 写数据 
     * @param datalist 
     * @param dataFields 
     * @throws IOException 
     * @throws IntrospectionException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws InvocationTargetException 
     */  
    public  void write_data_Excel(List<Object> datalist,String[] dataFields,Map<String, Map<String, String>> dataDic,Map<String,Integer> dataFormat) throws IOException, IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {  
          
        CellStyle cellStyle =  wb.createCellStyle();    
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));                     
        ZipSecureFile.setMinInflateRatio(0l);  
          
        for(int i=0;i<datalist.size();i++){    
            Row row = sheet.createRow(countRow++);    
            Object obj=datalist.get(i);             
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            if(objPropertyMap==null){  
                objPropertyMap=new HashMap<String, PropertyDescriptor>();  
                for(PropertyDescriptor des:propertyDescriptors){  
                    objPropertyMap.put(des.getName(), des);  
                }  
            }  
            for(int cols=0;cols<dataFields.length;cols++){    
                String dataField=dataFields[cols];  
                Object value="";  
                if (objPropertyMap.get(dataField)!=null) {    
                    // 得到property对应的getter方法    
                    Method gettter = objPropertyMap.get(dataField).getReadMethod();    
                    value=gettter.invoke(obj);  
                    if(value instanceof Date){  
                        Date date=(Date)value;  
                        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        value = dateFm.format(date);
                        value=value.toString().replaceAll(" 00:00:00", "");  
                        //数据字典的匹配  
                    }else {  
                        if(dataDic!=null && dataDic.get(dataField)!=null){  
                            value=dataDic.get(dataField).get(value+"");  
                        }  
                    }  
                }    
                  
                if(value==null){  
                    value="";  
                }  
                Cell cell = row.createCell(cols);       
                  
                if(dataFormat!=null && dataFormat.get(dataField)!=null){  
                    int format=dataFormat.get(dataField);  
  
                    switch(format){  
                        case 10:  
                            cell.setCellStyle(cellStyle);    
                            cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);//数字格式  
                            if(!StringUtils.isBlank(value.toString())){  
                                cell.setCellValue(Double.valueOf(value.toString()));//写入内容    
                            }  
                            break;  
                        case XSSFCell.CELL_TYPE_NUMERIC:  
                            cell.setCellType( XSSFCell.CELL_TYPE_NUMERIC);//数字格式  
                            if(!StringUtils.isBlank(value.toString())){  
                                cell.setCellValue(Double.valueOf(value.toString()));//写入内容    
                            }  
                            break;  
                    }  
  
                }else{  
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式      
                    cell.setCellValue(value.toString());//写入内容    
                }  
                  
               // sheet.setColumnWidth(cols, value.toString().length()*384); //设置单元格宽度    
                
            }     
        }      
            
    }    
    /** 
     * 写文件 
     * @throws IOException 
     */  
    public void write_excel_disk() throws IOException{  
        wb.write(output);  
        output.close();   
    }  
	
	//默认单元格内容为数字时格式  
    private static DecimalFormat df = new DecimalFormat("0");  
    // 默认单元格格式化日期字符串   
    private static SimpleDateFormat sdf = new SimpleDateFormat(  "yyyy-MM-dd HH:mm:ss");   
    // 格式化数字  
    private static DecimalFormat nf = new DecimalFormat("0.00");    
    public static ArrayList<ExcelTemplateVO> readExcel(File file){  
        if(file == null){  
            return null;  
        }  
        if(file.getName().endsWith("xlsx")){  
            //处理ecxel2007  
            return readExcel2007(file);  
        }else{  
            //处理ecxel2003  
            return readExcel2003(file);  
        }  
    }  
    /*  
     * @return 将返回结果存储在ArrayList内，存储结构与二位数组类似  
     * lists.get(0).get(0)表示过去Excel中0行0列单元格  
     */  
    public static ArrayList<ExcelTemplateVO> readExcel2003(File file){  
        try{  
        	//空行标记 如果有10行空白，则退出
        	int nullRowFlag = 0;
        	ArrayList<ExcelTemplateVO> templateList = new ArrayList<ExcelTemplateVO>();  
            ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();  
            ArrayList<Object> colList;  
            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));  
            HSSFSheet sheet = wb.getSheetAt(0);  
            HSSFRow row;  
            HSSFCell cell;  
            Object value;  
            /****判断是否新模版****/
        	ExcelTemplateVO excelTemplateVO = new ExcelTemplateVO();
        	row = sheet.getRow(0);  
            cell = row.getCell(0);  
            switch(cell.getCellType()){  
            	case XSSFCell.CELL_TYPE_STRING:    
                   value = cell.getStringCellValue(); 
                   String msg = (String) value;
                   if(!msg.startsWith(Const.TEMPLATE_YUN_XIAO_WEI)) {
                	   excelTemplateVO.setPolicyPerson(Const.TEMPLATE_ERROR);
                	   templateList.add(excelTemplateVO);
                	   return templateList;
                   }
                   break;    
               default:    
                   value = cell.toString();    
           }
            /****判断是否新模版****/
            for(int i = sheet.getFirstRowNum()+4 , rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows()-1 ; i++ ){ 
            	ExcelTemplateVO vo = new ExcelTemplateVO();
                row = sheet.getRow(i);  
                colList = new ArrayList<Object>();  
                if(row == null){  
                	System.out.println(i);
                    //当读取行为空时  
                    if(i != sheet.getPhysicalNumberOfRows()){//判断是否是最后一行  
                        rowList.add(colList);  
                    }  
                    nullRowFlag++;
                    if(nullRowFlag>10) {
                    	break;
                    }
                    continue;  
                }else{  
                    rowCount++;  
                }  
                for( int j = row.getFirstCellNum() ; j <= row.getLastCellNum() ;j++){  
                    cell = row.getCell(j);  
                    if(cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK){  
                        //当该单元格为空  
                        if(j != row.getLastCellNum()){//判断是否是该行中最后一个单元格  
                            colList.add("");  
                        }  
                        continue;  
                    }  
                    switch(cell.getCellType()){  
                     case XSSFCell.CELL_TYPE_STRING:    
                            System.out.println(i + "行" + j + " 列 is String type");    
                            value = cell.getStringCellValue();  
//                            System.out.println(value);
                            if(j==0) {
                            	vo.setAgentName(value.toString().trim());
                            }else if(j==1) {
                            	vo.setOpenBankNo(value.toString().replaceAll(" ", ""));
                            }else if(j==2) {
                            	vo.setAgentCommission(new BigDecimal(value.toString().replaceAll(" ", "")));
                            }else if(j==3) {
                            	vo.setIdcardno(value.toString());
                            }else if(j==5) {
                            	vo.setRemark(value.toString());
                            }else if(j==6) {
                            	vo.setRemark2(value.toString());
                            }else if(j==7) {
                            	vo.setRemark3(value.toString());
                            }
                            break;    
                        case XSSFCell.CELL_TYPE_NUMERIC:    
                            if ("@".equals(cell.getCellStyle().getDataFormatString())) {    
                                value = nf.format(cell.getNumericCellValue());    
                            } else if ("General".equals(cell.getCellStyle()    
                                    .getDataFormatString())) {    
                                value = nf.format(cell.getNumericCellValue());    
                            } else {    
                                value = sdf.format(HSSFDateUtil.getJavaDate(cell    
                                        .getNumericCellValue()));    
                            }    
//                            System.out.println(i + "行" + j    
//                                    + " 列 is Number type ; DateFormt:"    
//                                    + value.toString());   
                            if(j==0) {
                            	vo.setAgentName(value.toString().trim());
                            }else if(j==1) {
                            	vo.setOpenBankNo(value.toString().replaceAll(" ", ""));
                            }else if(j==2) {
                            	vo.setAgentCommission(new BigDecimal(value.toString().replaceAll(" ", "")));
                            }else if(j==3) {
                            	vo.setIdcardno(value.toString());
                            }else if(j==5) {
                            	vo.setRemark(value.toString());
                            }else if(j==6) {
                            	vo.setRemark2(value.toString());
                            }else if(j==7) {
                            	vo.setRemark3(value.toString());
                            }
                            break;    
                        case XSSFCell.CELL_TYPE_BOOLEAN:    
//                            System.out.println(i + "行" + j + " 列 is Boolean type");    
                            value = Boolean.valueOf(cell.getBooleanCellValue());  
                            break;    
                        case XSSFCell.CELL_TYPE_BLANK:    
//                            System.out.println(i + "行" + j + " 列 is Blank type");    
                            value = "";    
                            break;    
                        default:    
//                            System.out.println(i + "行" + j + " 列 is default type");    
                            value = cell.toString();    
                    }// end switch  
//                    colList.add(value);  
                }//end for j  
//                rowList.add(colList);  
                if(null!=vo.getAgentCommission()
                ||null!=vo.getAgentName()
                ||null!=vo.getIdcardno()
                ||null!=vo.getOpenBankNo()) {
               	templateList.add(vo);
               }else {
               	//空行标记累加
               	nullRowFlag++;
               }
               //如果有10行空白，则退出
               if(nullRowFlag>10) {
               	break;
               }
            }//end for i  
//              System.out.println(templateList);
            return templateList;  
        }catch(Exception e){  
            e.printStackTrace(); 
        }
		return null;  
    }  
      
    public static ArrayList<ExcelTemplateVO> readExcel2007(File file){  
        try{  
        	//空行标记  如果有10行空白，则退出
        	int nullRowFlag = 0;
        	ArrayList<ExcelTemplateVO> templateList = new ArrayList<ExcelTemplateVO>();  
            ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();  
            ArrayList<Object> colList;  
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));  
            XSSFSheet sheet = wb.getSheetAt(0);  
            XSSFRow row;  
            XSSFCell cell;  
            Object value;  
            /****判断是否新模版****/
        	ExcelTemplateVO excelTemplateVO = new ExcelTemplateVO();
        	row = sheet.getRow(0);  
            cell = row.getCell(0);  
            switch(cell.getCellType()){  
            	case XSSFCell.CELL_TYPE_STRING:    
                   value = cell.getStringCellValue(); 
                   String msg = (String) value;
                   if(!msg.startsWith(Const.TEMPLATE_YUN_XIAO_WEI)) {
                	   excelTemplateVO.setPolicyPerson(Const.TEMPLATE_ERROR);
                	   templateList.add(excelTemplateVO);
                	   return templateList;
                   }
                   break;    
               default:    
                   value = cell.toString();    
           }
            /****判断是否新模版****/
            for(int i = sheet.getFirstRowNum()+4 , rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows()-1 ; i++ ){  
            	ExcelTemplateVO vo = new ExcelTemplateVO();
            	row = sheet.getRow(i);  
                colList = new ArrayList<Object>();  
                if(row == null){  
                    //当读取行为空时  
                    if(i != sheet.getPhysicalNumberOfRows()){//判断是否是最后一行  
                        rowList.add(colList);  
                    }  
                    nullRowFlag++;
                    if(nullRowFlag>10) {
                    	break;
                    }
                    continue;  
                }else{  
                    rowCount++;  
                }  
                for( int j = row.getFirstCellNum() ; j <= row.getLastCellNum() ;j++){  
                    cell = row.getCell(j);  
                    if(cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK){  
                        //当该单元格为空  
                        if(j != row.getLastCellNum()){//判断是否是该行中最后一个单元格  
                            colList.add("");  
                        }  
                        continue;  
                    }  
                    switch(cell.getCellType()){  
                     case XSSFCell.CELL_TYPE_STRING:    
//                            System.out.println(i + "行" + j + " 列 is String type");    
                            value = cell.getStringCellValue(); 
                            if(j==0) {
                            	vo.setAgentName(value.toString().trim());
                            }else if(j==1) {
                            	vo.setOpenBankNo(value.toString().replaceAll(" ", ""));
                            }else if(j==2) {
                            	vo.setAgentCommission(new BigDecimal(value.toString().replaceAll(" ", "")));
                            }else if(j==3) {
                            	vo.setIdcardno(value.toString());
                            }else if(j==5) {
                            	vo.setRemark(value.toString());
                            }else if(j==6) {
                            	vo.setRemark2(value.toString());
                            }else if(j==7) {
                            	vo.setRemark3(value.toString());
                            }
                            break;    
                        case XSSFCell.CELL_TYPE_NUMERIC:    
                            if ("@".equals(cell.getCellStyle().getDataFormatString())) {    
                                value = nf.format(cell.getNumericCellValue());    
                            } else if ("General".equals(cell.getCellStyle()    
                                    .getDataFormatString())) {    
                                value = nf.format(cell.getNumericCellValue());    
                            } else {    
                                value = sdf.format(HSSFDateUtil.getJavaDate(cell    
                                        .getNumericCellValue()));    
                            }    
//                            System.out.println(i + "行" + j    
//                                    + " 列 is Number type ; DateFormt:"    
//                                    + value.toString());   
                            if(j==0) {
                            	vo.setAgentName(value.toString().trim());
                            }else if(j==1) {
                            	vo.setOpenBankNo(value.toString().replaceAll(" ", ""));
                            }else if(j==2) {
                            	vo.setAgentCommission(new BigDecimal(value.toString().replaceAll(" ", "")));
                            }else if(j==3) {
                            	vo.setIdcardno(value.toString());
                            }else if(j==5) {
                            	vo.setRemark(value.toString());
                            }else if(j==6) {
                            	vo.setRemark2(value.toString());
                            }else if(j==7) {
                            	vo.setRemark3(value.toString());
                            }
                            break;    
                        case XSSFCell.CELL_TYPE_BOOLEAN:    
                            //System.out.println(i + "行" + j + " 列 is Boolean type");    
                            value = Boolean.valueOf(cell.getBooleanCellValue());  
                            break;    
                        case XSSFCell.CELL_TYPE_BLANK:    
                            //System.out.println(i + "行" + j + " 列 is Blank type");    
                            value = "";    
                            break;    
                        default:    
//                            System.out.println(i + "行" + j + " 列 is default type");    
                            value = cell.toString();    
                    }// end switch  
//                    colList.add(value);  
                }//end for j  
//                rowList.add(colList); 
                if(null!=vo.getAgentCommission()
                 ||null!=vo.getAgentName()
                 ||null!=vo.getOpenBankNo()
                 ||null!=vo.getIdcardno()) {
                	templateList.add(vo);
                }else {
                	//空行标记累加
                	nullRowFlag++;
                }
                //如果有10行空白，则退出
                if(nullRowFlag>10) {
                	break;
                }
            }//end for i  
              
            return templateList;  
        }catch(Exception e){  
        	e.printStackTrace();
        }
		return null;  
    }  
      
    public static void writeExcel(ArrayList<ArrayList<Object>> result,String path){  
        if(result == null){  
            return;  
        }  
        HSSFWorkbook wb = new HSSFWorkbook();  
        HSSFSheet sheet = wb.createSheet("sheet1");  
        for(int i = 0 ;i < result.size() ; i++){  
             HSSFRow row = sheet.createRow(i);  
            if(result.get(i) != null){  
                for(int j = 0; j < result.get(i).size() ; j ++){  
                    HSSFCell cell = row.createCell(j);  
                    cell.setCellValue(result.get(i).get(j).toString());  
                }  
            }  
        }  
        ByteArrayOutputStream os = new ByteArrayOutputStream();  
        try  
        {  
            wb.write(os);  
        } catch (IOException e){  
            e.printStackTrace();  
        }  
        byte[] content = os.toByteArray();  
        File file = new File(path);//Excel文件生成后存储的位置。  
        OutputStream fos  = null;  
        try  
        {  
            fos = new FileOutputStream(file);  
            fos.write(content);  
            os.close();  
            fos.close();  
        }catch (Exception e){  
            e.printStackTrace();  
        }             
    }  
      
    public static DecimalFormat getDf() {  
        return df;  
    }  
    public static void setDf(DecimalFormat df) {  
        ExcelUtil.df = df;  
    }  
    public static SimpleDateFormat getSdf() {  
        return sdf;  
    }  
    public static void setSdf(SimpleDateFormat sdf) {  
        ExcelUtil.sdf = sdf;  
    }  
    public static DecimalFormat getNf() {  
        return nf;  
    }  
    public static void setNf(DecimalFormat nf) {  
        ExcelUtil.nf = nf;  
    }  
      
    /** List order not maintained **/      
    public static List<String> removeDuplicate(List<String> arlList)      
    {      
	    HashSet<String> h = new HashSet<String>(arlList);      
	    arlList.clear();      
	    arlList.addAll(h);
		return arlList;      
    }  
    
    
    /**
     * @方法描述：获取两个ArrayList的差集
     * @param firstArrayList 第一个ArrayList
     * @param secondArrayList 第二个ArrayList
     * @return resultList 差集ArrayList
     */
    public static List<ExcelTemplateVO> receiveDefectList(List<ExcelTemplateVO> firstArrayList, List<ExcelTemplateVO> secondArrayList) {
        List<ExcelTemplateVO> resultList = new ArrayList<ExcelTemplateVO>();
        LinkedList<ExcelTemplateVO> result = new LinkedList<ExcelTemplateVO>(firstArrayList);// 大集合用linkedlist  
        HashSet<ExcelTemplateVO> othHash = new HashSet<ExcelTemplateVO>(secondArrayList);// 小集合用hashset  
        Iterator<ExcelTemplateVO> iter = result.iterator();// 采用Iterator迭代器进行数据的操作  
        while(iter.hasNext()){  
            if(othHash.contains(iter.next())){  
                iter.remove();            
            }     
        }  
        resultList = new ArrayList<ExcelTemplateVO>(result);
        return resultList;
    }
    
    public static void main(String[] args) {  
    	File csv = new File("/Users/si/Downloads/f0c6110f4f0646aa8b054b9785d93006.xlsx");  // CSV文件路径导入excel格式副本.xlsx
//    	File csv = new File("/Users/si/Downloads/云小微批量打款模板.xlsx");  // CSV文件路径导入
    	ArrayList<ExcelTemplateVO> list = readExcel(csv);
    	System.out.println(list);
    }  
      
}
