package com.pproject.board.config;

import com.pproject.board.interceptor.HttpInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/login/connect") //  해당 경로는 인터셉터가 가로채지 않는다.
                .excludePathPatterns("/join")
                .excludePathPatterns("/join/create")
                .excludePathPatterns("/logout");
    }
}