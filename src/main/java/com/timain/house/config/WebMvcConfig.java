package com.timain.house.config;

import com.timain.house.interceptor.AuthActionInterceptor;
import com.timain.house.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/31 11:04
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthInterceptor authInterceptor;
    @Autowired
    private AuthActionInterceptor authActionInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).excludePathPatterns("/static").addPathPatterns("/**");
        registry.addInterceptor(authActionInterceptor).addPathPatterns("/house/toAdd")
                .addPathPatterns("/accounts/profile").addPathPatterns("/accounts/profileSubmit");
        super.addInterceptors(registry);
    }
}
