package cn.rebornauto.platform.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.rebornauto.platform.business.vo.ExcelTemplateVO;
import cn.rebornauto.platform.common.util.LogUtil;
import cn.rebornauto.platform.pay.tonglian.utils.DateUtil;

/**
 * 文件工具类
 * 
 * @author si
 *
 */
public class FileUtil {

	public static String UPLOADFILE_PATH = "/Users/si/Desktop/upload/";
	
	/**
	 * 返回上传文件路径
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String fileUpload(InputStream fileInputStream, String uploadPath,String fileName)  {
		String msg = "";
//		String dst = uploadPath+DateUtil.date_yyyyMMdd()+"/";// 复制到目录下
		String dst = uploadPath+"/";;
		try {
			// 如果文件夹不存在则创建
			if (!new File(dst).exists() && !new File(dst).isDirectory()) {
				LogUtil.debug("创建目录 "+dst);
				new File(dst).mkdir();
			}else {
				LogUtil.debug("存在存放目录 "+dst);
			}
			dst = dst+DateUtil.format(new Date(), "yyyyMMdd")+"/";
			File path = new File(dst);
			// 如果文件夹不存在则创建
			if (!path.exists() && !path.isDirectory()) {
				path.mkdir();
			}
			LogUtil.debug(">>>>>准备上传 " + dst + fileName);
			SaveFileFromInputStream(fileInputStream, dst, fileName);
			LogUtil.debug(">>>>>上传成功文件 " + dst + fileName);
		} catch (Exception e) {
			LogUtil.error("上传失败   "+e.getMessage());
			e.printStackTrace();
		}
		return msg;
	}
	
	
	/**
	 * 返回上传文件路径
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String fileUploadAndBak(String fileName ,InputStream fileInputStream,String uploadPath) throws IOException {
		String msg = "";
//		String fileName = file.getOriginalFilename();
		String dst = uploadPath;// 复制到目录下
		String bakFilename = fileName.substring(0, fileName.lastIndexOf("."))+"_"+DateUtil.format(new Date(), "yyyyMMddHHmmss")+fileName.substring(fileName.lastIndexOf("."), fileName.length());
		LogUtil.debug(">>>>>准备备份文件到此目录 " + dst+"bak/"+bakFilename);
		File fileBak = new File(dst+"bak/");
		// 如果文件夹不存在则创建
		if (!fileBak.exists() && !fileBak.isDirectory()) {
			fileBak.mkdir();
		}
		copy(dst+fileName, dst+"bak/"+bakFilename);
		LogUtil.debug(">>>>>准备上传 " + dst + fileName);
		SaveFileFromInputStream(fileInputStream, dst, fileName);
		LogUtil.debug(">>>>>上传成功文件 " + dst + fileName);
		return msg;
	}

	/**
	 * 保存文件
	 * 
	 * @param stream
	 * @param path
	 * @param filename
	 * @throws IOException
	 */
	public static void SaveFileFromInputStream(InputStream stream, String path,
			String filename) throws IOException {
		File file = new File(path);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			LogUtil.debug("没有路径"+path);
			file.mkdir();
			LogUtil.debug("文件夹创建成功");
		}else {
			LogUtil.debug("存在文件夹无需创建"+path);
		}
		LogUtil.debug("上传服务器文件路径"+path + filename);
		FileOutputStream fs = new FileOutputStream(path + filename);
		byte[] buffer = new byte[1024 * 1024];
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		LogUtil.debug("上传服务器完毕"+path + filename);
		fs.close();
		stream.close();
	}

	/**
	 * 保存网络图片
	 * 
	 * @param netImageUrl
	 * @param path
	 * @param fileName
	 */
	public static int saveToFile(String netImageUrl, String path,
			String fileName) {
		int uploadFlag = 0;
		LogUtil.debug("保存网络图片start");
		File file = new File(path);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		int BUFFER_SIZE = 1024;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		try {
			url = new URL(netImageUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			// fos = new
			// FileOutputStream("/Users/si/Desktop/uploadfile/20160213/haha.jpeg");
			fos = new FileOutputStream(path + fileName);
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			fos.flush();
			LogUtil.debug("保存网络图片finish");
		} catch (IOException e) {
			uploadFlag = 1;
			LogUtil.error(e.getMessage());
		} catch (ClassCastException e) {
			uploadFlag = 1;
			LogUtil.error(e.getMessage());
		} finally {
			try {
				fos.close();
				bis.close();
				httpUrl.disconnect();
			} catch (IOException e) {
			} catch (NullPointerException e) {
			}
		}
		return uploadFlag;
	}

	public static void copy(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				LogUtil.debug("原目录"+oldPath);
				LogUtil.debug("备份目录"+newPath);
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				LogUtil.debug(bytesum);
				inStream.close();
			}
		} catch (Exception e) {
			LogUtil.error("文件复制出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取txt内容
	 */
	public static List<String> readTxt(String fileName) {
		List<String> list = new ArrayList<String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));//构造一个BufferedReader类来读取文件
            String s = null;
            LogUtil.debug("开始读取>>>");
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
//                System.out.println(s);
                list.add(s);
            }
            LogUtil.debug("读取完毕>>>"+list.size());
            br.close();    
        }catch(Exception e){
        	LogUtil.error(e.getStackTrace());
        }
        return list;
	}
	
	/**
     * 读取csv文件
     * @param fileName
     * @param list
     */
    public static List<ExcelTemplateVO> readCsv(String fileName) {
    	List<ExcelTemplateVO> list = new ArrayList<ExcelTemplateVO>();
    	File csv = new File(fileName);  // CSV文件路径
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(csv));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        String line = "";
        String everyLine = "";
        try {
                List<String> allString = new ArrayList<String>();
                while ((line = br.readLine()) != null)  //读取到的内容给line变量
                {
                    everyLine = line;
                    System.out.println(everyLine);
//                    list.add(everyLine);
//                    allString.add(everyLine);
                }
//                System.out.println("csv表格中所有行数："+allString.size());
                br.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
		return list;
    }
    
    public static void main(String[] args) {
    	readCsv("/Users/si/Documents/瑞博恩工作/需求/师域云保/导入excel格式.xls");
	}
	
}
