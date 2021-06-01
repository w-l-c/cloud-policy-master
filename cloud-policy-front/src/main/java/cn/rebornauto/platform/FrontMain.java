package cn.rebornauto.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.spring.annotation.MapperScan;

import java.time.LocalDateTime;

@ServletComponentScan(value = {"cn.rebornauto.platform.*.servlets"})
@SpringBootApplication
@EnableCaching
@MapperScan("cn.rebornauto.platform.*.dao")
@RestController
public class FrontMain implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(FrontMain.class);

    public static void main(String[] args) {
        SpringApplication.run(FrontMain.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("系统启动成功{}"+LocalDateTime.now());
    }

    @RequestMapping("/index")
    public String index(){
        return LocalDateTime.now().toString();
    }

}
