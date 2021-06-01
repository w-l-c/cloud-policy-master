package cn.rebornauto.platform.configuration;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.annotation.JsonFormat.Features;

import cn.rebornauto.platform.common.handler.intercept.LogHandlerInterceptorAdapter;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties({UploadProperties.class})
public class AppWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT","OPTIONS")
                .maxAge(3600);
    }

    @Bean
    public ValueFilter nullValueFilter() {
        return new ValueFilter() {
            @Override
            public Object process(Object object, String key, Object value) {
                return value == null ? "" : value;
            }
        };
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverters());
    }

    /**
     * Request和Response解析
     * @return
     */


    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverters() {
        //1.需要定义一个convert转换消息的对象;//RequestResponseBodyMethodProcessor
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2:添加fastJson的配置信息;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setDateFormat("yyyy-MM-dd");
        //Null 转换为空字符串
        fastJsonConfig.setSerializeFilters(nullValueFilter());
        //3处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastMediaTypes.add(MediaType.ALL);
        //4.在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);        
        return fastJsonHttpMessageConverter;
        //return new HttpMessageConverters(fastJsonHttpMessageConverter);
    }


    @Bean
    public LogHandlerInterceptorAdapter logHandlerInterceptorAdapter(){
        return new LogHandlerInterceptorAdapter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logHandlerInterceptorAdapter()).addPathPatterns("/**");
    }



}
