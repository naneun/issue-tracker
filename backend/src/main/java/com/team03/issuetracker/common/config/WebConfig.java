package com.team03.issuetracker.common.config;

import com.team03.issuetracker.oauth.common.AccessTokenResolver;
import com.team03.issuetracker.oauth.common.LoginMemberResolver;
import com.team03.issuetracker.oauth.common.RefreshTokenResolver;
import com.team03.issuetracker.oauth.interceptor.LoginInterceptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
			.addPathPatterns("/api/**", "/login/update/jwt-access-token");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(accessTokenResolver);
		argumentResolvers.add(refreshTokenResolver);
		argumentResolvers.add(loginMemberResolver);
	}
}
