package com.atyomi.boot.configures;

import com.atyomi.boot.common.JacksonObjectMapper;
import com.atyomi.boot.configures.interceptors.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("正在进行静态资源映射");
       registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor()).excludePathPatterns("/backend/page/login/login.html","/employee/login","/employee/logout","/employee/test","/common/download"
//        ,"/common/upload","/front/page/login.html");
        registry.addInterceptor(new LoginInterceptor()).excludePathPatterns("/backend/**","/front/**","/employee/login","/employee/logout","/common/**","/user/sendMsg","/user/login","/user");
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
       MappingJackson2HttpMessageConverter messageConverter  = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        converters.add(messageConverter);
    }
}
