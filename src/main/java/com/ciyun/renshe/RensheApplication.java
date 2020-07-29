package com.ciyun.renshe;

import com.ciyun.renshe.common.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import javax.servlet.MultipartConfigElement;

@EnableAsync    //异步
@EnableScheduling   //定时任务
@ServletComponentScan   //Servlet、Filter、Listener可以直接通过@WebServlet、@WebFilter、@WebListener注解自动注册
@SpringBootApplication
@EnableTransactionManagement    //事务注解管理
@MapperScan("com.ciyun.renshe.mapper")  //扫描指定包中的接口
//继承SpringBootServletInitializer    若打包成war包，使用外置的tomcat启动
public class RensheApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RensheApplication.class, args);
    }

    @Value("${localUpload.filePath}")
    private String localUploadPath;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(RensheApplication.class);
    }

    @Bean
    public IdWorker getIdWorker() {
        // 这两个值不要改动
        return new IdWorker(1, 31);
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(localUploadPath);
        return factory.createMultipartConfig();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
