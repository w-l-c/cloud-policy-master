package cn.rebornauto.platform.generator;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GeneratorMain {

    public static void main(String[] args) {
        try {
            List<String> warnings = new ArrayList<>();
            boolean overwrite = true;
            ConfigurationParser cp = new ConfigurationParser(warnings);

            String file = "generatorBusiness-tk.xml";
            InputStream is = GeneratorMain.class.getClassLoader().getResourceAsStream(file);

            Configuration config = cp.parseConfiguration(is);
            config.getContext("");
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);

            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

            myBatisGenerator.generate(null);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
