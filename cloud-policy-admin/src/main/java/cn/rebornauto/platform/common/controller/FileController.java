package cn.rebornauto.platform.common.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.rebornauto.platform.business.service.SysConfigService;
import cn.rebornauto.platform.business.service.SysDicService;
import cn.rebornauto.platform.common.data.view.Response;
import cn.rebornauto.platform.upload.entity.UploadInfo;

@RestController
@RequestMapping("/sys/file")
public class FileController extends BaseController {

    private static SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
    private static DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyyMM/dd");

    //@Autowired
    //private UploadProperties uploadProperties;
    
    @Autowired
    private   SysDicService  sysDicService;
    
    @Autowired
    private   SysConfigService  sysConfigService;

    @PostMapping(value = "/upload")
    @ResponseBody
    public Response upload(HttpServletRequest request, @RequestParam MultipartFile file) {
        try {
        	UploadInfo  uploadInfo=sysConfigService.getUploadInfo(sysDicService.selectSysPaySwitch());
        	String local=uploadInfo.getLocal();
            //String local = uploadProperties.getLocal();
            File tmppath = new File(local);
            if (!tmppath.exists() || !tmppath.isDirectory()) {
                tmppath.mkdirs();
            }
            String originName = file.getOriginalFilename();
            String ext = getExtensionName(originName);
            if (ext == null) {
                return Response.error().message("??????????????????");
            }
            LocalDate ld = LocalDate.now();
            String subdir = ld.format(formatters);
            File subdirFile = new File(local,subdir);
            if (!subdirFile.exists() || !subdirFile.isDirectory()) {
                subdirFile.mkdirs();
            }
            String name = UUID.randomUUID().toString().replace("-","") + "." + ext;
            //????????????
            //String url = uploadProperties.getDomain()+uploadProperties.getUrl()+"/"+subdir+"/"+name;
            //????????????
            String url =uploadInfo.getUrl()+"/"+subdir+"/"+name;
            File f = new File(subdirFile, name);
            InputStream is = file.getInputStream();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            byte[] by = new byte[1024];
            int s = 0;
            while ((s = is.read(by)) > 0) {
                bos.write(by, 0, s);
            }
            bos.flush();
            bos.close();
            is.close();
            return Response.ok().body(url);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.error().message(e.getMessage());
        }
    }


    /**
     * ??????????????????
     *
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

}
