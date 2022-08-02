package com.team03.issuetracker.common.config;

import com.team03.issuetracker.oauth.common.AccessTokenResolver;
import com.team03.issuetracker.oauth.common.LoginUserResolver;
import com.team03.issuetracker.oauth.common.RefreshTokenResolver;
import com.team03.issuetracker.oauth.filter.LoginFilter;
import com.team03.issuetracker.oauth.interceptor.AuthInterceptor;
import java.util.List;
import javax.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor;

	private final AuthInterceptor authInterceptor;
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
		return filterRegistrationBean; /* 왜 필터도 쓰고 인터셉터도 썼지? */
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addWebRequestInterceptor(openEntityManagerInViewInterceptor); /* ??? */

		registry.addInterceptor(authInterceptor)
			.addPathPatterns("/api/**");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(accessTokenResolver);
		argumentResolvers.add(refreshTokenResolver);
		argumentResolvers.add(loginMemberResolver);
	}
}
