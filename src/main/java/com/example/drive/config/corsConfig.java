package com.example.drive.config;

import com.example.drive.common.JacksonObjectMapper;
import com.google.common.base.Predicates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
@Slf4j
@Configuration //声明全局配置类
public class corsConfig extends WebMvcConfigurationSupport {
    static final String[] ORIGINS = new String[]{"GET", "POST", "PUT", "DELETE"};

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods(ORIGINS)
                .maxAge(3600);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/backend/**")
                .addResourceLocations("classpath:/backend/");
    }

    /*扩展MVC框架的消息转换器*/
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转化器...");
        //创建消息转换器对象
        final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter=
                new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层使用自定义的Json对象映射器将Java对象转换为json
        mappingJackson2HttpMessageConverter.setObjectMapper(new JacksonObjectMapper());
        //将上面的消息转换器对象追加到mvc框架的转换器集合中
        converters.add(0,mappingJackson2HttpMessageConverter);

    }

    @Bean
    public Docket createRestApi(){
        ApiInfo apiInfo=new ApiInfoBuilder().title("DMS-APP")
                .version("1.0")
                .description("接口文档描述")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(
                        RequestHandlerSelectors.basePackage("com.example.drive.driveController")
                                .or(RequestHandlerSelectors.basePackage("com.example.drive.controller"))
                )
                .paths(PathSelectors.any())

                .build();

        return docket;
    }
}