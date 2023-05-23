package com.bindada.config;

import com.bindada.interceptors.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/user/**","/course/**","/file/**","/sources/**","/subject/**","/exam/**")                        //拦截所有接口
                .excludePathPatterns("/user/login","/user/register","/user/getEmailCode","/course/video/play","/course/video/play1","/file/downLoad");         //其他接口放行
    }
}
