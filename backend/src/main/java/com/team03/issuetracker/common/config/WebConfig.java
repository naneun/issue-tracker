package com.team03.issuetracker.common.config;

import com.team03.issuetracker.oauth.common.AccessTokenResolver;
import com.team03.issuetracker.oauth.common.LoginUserResolver;
import com.team03.issuetracker.oauth.common.RefreshTokenResolver;
import com.team03.issuetracker.oauth.filter.LoginFilter;
import com.team03.issuetracker.oauth.interceptor.AuthInterceptor;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor loginInterceptor;

    private final AccessTokenResolver accessTokenResolver;

    private final RefreshTokenResolver refreshTokenResolver;

    private final LoginUserResolver loginMemberResolver;

    private final LoginFilter loginFilter;

    @Bean
    public FilterRegistrationBean<Filter> setFilterRegistration() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(loginFilter);
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/login/github", "/login/google");
        return filterRegistrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(accessTokenResolver);
        argumentResolvers.add(refreshTokenResolver);
        argumentResolvers.add(loginMemberResolver);
    }
}
