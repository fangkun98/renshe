package com.ciyun.renshe.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 配置类
 *
 * @Date 2019/9/5 13:28
 * @Author Admin
 * @Version 1.0
 */
@Slf4j
@EnableSwagger2
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${localUpload.addResourceLocations}")
    private String addResourceLocation;

    //定义时间格式转换器
    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        converter.setObjectMapper(mapper);
        return converter;
    }

    //添加转换器
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //将我们定义的时间格式转换器添加到转换器列表中,
        //这样jackson格式化时候但凡遇到Date类型就会转换成我们定义的格式
        converters.add(jackson2HttpMessageConverter());
    }

    /**
     * 配置 swagger2
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为有@Api注解的Controller生成API文档
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //为有@ApiOperation注解的方法生成API文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("Yisong Kong", "", "15253908761@163.com");
        return new ApiInfoBuilder()
                .title("人社局项目")
                .description("人社局 API 接口文档")
                .contact(contact)
                .version("1.0")
                .build();
    }

    /**
     * 配置swagger2的静态资源路径，以及上传文件的静态路径
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 解决静态资源无法访问
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        // 解决swagger无法访问
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        // 解决静态资源无法访问,  只要访问 /uploadFile 路径，会去 文件系统 /opt/renshe/file/ 下去寻找相关文件
        // 如：/uploadFile/1.jpg   就是访问文件系统下的/opt/renshe/file/1.jpg
        registry.addResourceHandler("/uploadFile/**")
                .addResourceLocations(addResourceLocation);
    }

    //解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOrigins("*")
                // 是否允许证书，不再默认开启
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("*")
                // 跨域允许时间
                .maxAge(3600);
        /*registry.addMapping("/**");*/
    }

    /**
     * 修改SpringBoot中的默认静态文件路径
     *
     * @param registry
     */
    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        *//*registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");*//*
     *//*registry.addResourceHandler("/plate/**")
                .addResourceLocations("file:d:/opt/renshe/file/");*//*

    }*/

    /**
     * 使用fastjson代替jackson
     *
     * @param converters
     */
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        for (int i = converters.size() - 1; i >= 0; i--) {
//            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
//                converters.remove(i);
//            }
//        }
//        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//        //自定义 fastjson 配置
//        FastJsonConfig config = new FastJsonConfig();
//        config.setSerializerFeatures(
//                // 是否输出值为null的字段,默认为false,我们将它打开
//                SerializerFeature.WriteMapNullValue,
//                // 将Collection类型字段的字段空值输出为[]
//                SerializerFeature.WriteNullListAsEmpty,
//                // 将字符串类型字段的空值输出为空字符串
//                SerializerFeature.WriteNullStringAsEmpty,
//                // 将数值类型字段的空值输出为0
//                //SerializerFeature.WriteNullNumberAsZero,
//                SerializerFeature.WriteDateUseDateFormat,
//                // 禁用循环引用
//                SerializerFeature.DisableCircularReferenceDetect
//        );
//        fastJsonHttpMessageConverter.setFastJsonConfig(config);
//        // 添加支持的MediaTypes;不添加时默认为*/*,也就是默认支持全部
//        // 但是MappingJackson2HttpMessageConverter里面支持的MediaTypes为application/json
//        // 参考它的做法, fastjson也只添加application/json的MediaType
//        List<MediaType> fastMediaTypes = new ArrayList<>();
//        fastMediaTypes.add(MediaType.APPLICATION_JSON);
//        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
//        converters.add(fastJsonHttpMessageConverter);
//    }
}
