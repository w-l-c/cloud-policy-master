package cn.rebornauto.platform.qrcode.utils;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import cn.rebornauto.platform.upload.entity.UploadInfo;

public class QRCodeTool {
	
    private static DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyyMM/dd");

      /**
       * 
       * @param qrW 二维码宽度
       * @param qrH 二维码高度
       * @param qrContext 二维码内容
       * @param uploadInfo 服务器路径
       * @param relativeLogoUrl 服务器相对路径客户头像url
       * @param logoPath  服务器客户头像下载到本地的路径
       * @return
       * @throws Exception
       */
	public static String toGenerateQRCode(String qrContext,UploadInfo uploadInfo, String relativeLogoUrl) throws Exception {		
		// TODO Auto-generated method stub		
		String local=uploadInfo.getLocal();
        //判断服务器第一层路径是否创建
        File tmppath = new File(local);
        if (!tmppath.exists() || !tmppath.isDirectory()) {
            tmppath.mkdirs();
        }	
        //判断服务器第二层路径是否创建
        LocalDate ld = LocalDate.now();
        String subdir = ld.format(formatters);
        File subdirFile = new File(local,subdir);
        if (!subdirFile.exists() || !subdirFile.isDirectory()) {
            subdirFile.mkdirs();
        }
        String name = UUID.randomUUID().toString().replace("-","")+".jpg";      
        String url =uploadInfo.getUrl()+"/"+subdir+"/"+name;  
         //调用二维码成
    	QRCodeUtil.encode(qrContext, 512, 512,relativeLogoUrl,url);			
        return url; 
	}
}

