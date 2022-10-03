package org.gift.Servlet.Dao;

import org.gift.Utiliy.JwtInterceptor;
import org.gift.Utiliy.ReplaceToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    // jwt拦截器
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

    @Bean
    public ReplaceToken replaceToken(){
        return new ReplaceToken();
    }

    // 添加jwt拦截器，并指定拦截路径
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        // 更新token
        registry.addInterceptor(replaceToken()).addPathPatterns("/customer/**").addPathPatterns("/blog/**")
                .addPathPatterns("/message/**").addPathPatterns("/dispose/**").addPathPatterns("/photo/**")
                .addPathPatterns("/photoalbum/**");

        // 校验token
        registry.addInterceptor(jwtInterceptor()).addPathPatterns("/blog/submit").addPathPatterns("/blog/delete")
                .addPathPatterns("/message/statistics").addPathPatterns("/message/read")
                .addPathPatterns("/message/list").addPathPatterns("/photoalbum/submit")
                .addPathPatterns("/photoalbum/delete").addPathPatterns("/photoalbum/frontcover")
                .addPathPatterns("/photo/submit").addPathPatterns("/photo/delete");
    }
}
























