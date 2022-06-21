package com.team03.issuetracker.common.config;

import com.team03.issuetracker.oauth.common.AccessTokenResolver;
import com.team03.issuetracker.oauth.common.LoginMemberResolver;
import com.team03.issuetracker.oauth.common.RefreshTokenResolver;
import com.team03.issuetracker.oauth.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final LoginInterceptor loginInterceptor;

	private final AccessTokenResolver accessTokenResolver;

	private final RefreshTokenResolver refreshTokenResolver;

	private final LoginMemberResolver loginMemberResolver;

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
