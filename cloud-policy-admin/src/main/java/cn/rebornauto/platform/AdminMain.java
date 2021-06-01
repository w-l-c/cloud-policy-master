package cn.rebornauto.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;
import java.time.LocalDateTime;

@ServletComponentScan(value = {"cn.rebornauto.platform.*.servlets"})
@SpringBootApplication
@EnableCaching
@MapperScan("cn.rebornauto.platform.*.dao")
public class AdminMain  implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(AdminMain.class);

    public static void main(String[] args) {
        SpringApplication.run(AdminMain.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("系统启动成功{}"+LocalDateTime.now());
    }

}
