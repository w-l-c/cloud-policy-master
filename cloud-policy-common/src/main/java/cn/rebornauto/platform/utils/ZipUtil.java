package cn.rebornauto.platform.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.IOUtils;

import cn.rebornauto.platform.business.form.UploadZipForm;
import cn.rebornauto.platform.common.util.LogUtil;

public class ZipUtil {

    public static void main(String[] args) throws Exception{
//        zipDir("D:/data/inventory-financing/pdf/98/pdf", "D:/data/inventory-financing/pdf/98/zip/1.zip");
        String filename = "/Users/si/Desktop/郭彦刚.zip";
        String path = "/Users/si/Desktop/unZip/";
        ZipUtil.unZipAgentInfo(filename, path);
    }

    public static void zipFile(String srcFile, String zipFile) {
        File f = new File(srcFile);
        if (f.exists() && f.isFile()) {
            zipFiles(Arrays.asList(f), zipFile);
        }
    }

    public static void zipDir(String srcDir, String zipFile) {
        File src = new File(srcDir);
        if (src.exists() && src.isDirectory()) {
            List<File> files = Arrays.asList(src.listFiles());
            zipFiles(files, zipFile);
        }
    }

    public static void zipFiles(List<File> files, String zipDestFile) {
        File zipFile = new File(zipDestFile);
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            zipPutFiles(files, zos, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void zipPutFiles(List<File> files, ZipOutputStream zos, String zipDir) {
        files.forEach(f -> {
            File file = f;
            if (f.isFile()) {
                try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
                    String baseDir = zipDir;
                    ZipEntry entry = new ZipEntry(baseDir + file.getName());
                    zos.putNextEntry(entry);
                    int count;
                    byte[] buf = new byte[1024];
                    while ((count = bis.read(buf)) != -1) {
                        zos.write(buf, 0, count);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (f.isDirectory()) {
                String baseDir = zipDir + f.getName() + "/";
                try {
                    zipPutFiles(Arrays.asList(f.listFiles()), zos, baseDir);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public static List<UploadZipForm> unZipAgentInfo(String filePath, String descDir) {
    	List<UploadZipForm> list = new ArrayList<UploadZipForm>();
        try (ZipArchiveInputStream inputStream = getZipFile(filePath)) {
            File pathFile = new File(descDir);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            ZipArchiveEntry entry = null;
            while ((entry = inputStream.getNextZipEntry()) != null) {
                if (entry.isDirectory()) {
                    File directory = new File(descDir, entry.getName());
                    directory.mkdirs();
                } else {
                    OutputStream os = null;
                    try {
                        os = new BufferedOutputStream(new FileOutputStream(new File(descDir, entry.getName())));
                        String path = descDir + entry.getName();
                        //输出文件路径信息
                        System.out.println("解压文件路径为:"+descDir + entry.getName());
                        LogUtil.info("解压文件路径为:"+descDir + entry.getName());
                        //excel文件
                        if(path.substring(path.lastIndexOf("."), path.length()).equalsIgnoreCase(".xlsx")) {
                        	
                        }else if(path.substring(path.lastIndexOf("."), path.length()).equalsIgnoreCase(".pdf")) {
                        	
                        }else{
                        	
                        }
                        IOUtils.copy(inputStream, os);
                    } finally {
                        IOUtils.closeQuietly(os);
                    }
                }
            }
//            final File[] files = pathFile.listFiles();
//            if (files != null && files.length == 1 && files[0].isDirectory()) {
//                // 说明只有一个文件夹
//                FileUtils.copyDirectory(files[0], pathFile);
//                //免得删除错误， 删除的文件必须在/data/demand/目录下。
//                boolean isValid = files[0].getPath().contains("/data/www/");
//                if (isValid) {
//                    FileUtils.forceDelete(files[0]);
//                }
//            }
            System.out.println("******************解压完毕********************");
            LogUtil.info("******************解压完毕********************");

        } catch (Exception e) {
        	LogUtil.error("[unzip] 解压zip文件出错"+ e.getMessage());
        }
		return list;
    }

    private static ZipArchiveInputStream getZipFile(String filePath) throws Exception {
    	File zipFile = new File(filePath);
        return new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
    }
}
