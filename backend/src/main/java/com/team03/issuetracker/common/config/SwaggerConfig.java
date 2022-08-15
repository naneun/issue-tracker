package com.team03.issuetracker.common.config;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

	private final TypeResolver typeResolver;

	private ApiInfo swaggerInfo() {
		return new ApiInfoBuilder().title("Issue-Tracker API")
			.description("team-03 API Docs").build();
	}

	/* Actuator 의존성 추가 시 Failed to start bean 'documentationPluginsBootstrapper' 에러 발생하는 문제 해결을 위해 추가 */
	@Bean
	public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier,
		ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier,
		EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties,
		WebEndpointProperties webEndpointProperties, Environment environment) {
		List<ExposableEndpoint<?>> allEndpoints = new ArrayList();
		Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
		allEndpoints.addAll(webEndpoints);
		allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
		allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
		String basePath = webEndpointProperties.getBasePath();
		EndpointMapping endpointMapping = new EndpointMapping(basePath);
		boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment,
			basePath);
		return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes,
			corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath),
			shouldRegisterLinksMapping, null);
	}

	private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment,
		String basePath) {
		return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) ||
																	ManagementPortType.get(environment)
																		.equals(ManagementPortType.DIFFERENT));
	}
	/*-------------------------------------------------------------------------------------------------*/

	@Bean
	public Docket swaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Pageable.class),
				typeResolver.resolve(Page.class)))
			.consumes(getConsumeContentTypes())
			.produces(getProduceContentTypes())
			.apiInfo(swaggerInfo())
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.team03.issuetracker"))
			.build()
			.useDefaultResponseMessages(false);
	}

	private Set<String> getConsumeContentTypes() {
		Set<String> consumes = new HashSet<>();
		consumes.add("application/json;charset=UTF-8");
		consumes.add("application/x-www-form-urlencoded");
		return consumes;
	}

	private Set<String> getProduceContentTypes() {
		Set<String> produces = new HashSet<>();
		produces.add("application/json;charset=UTF-8");
		return produces;
	}

	@Getter
	@Setter
	@ApiModel
	private static class Page {

		@ApiModelProperty(value = "페이지 번호(0..N)")
		private Integer page;

		@ApiModelProperty(value = "페이지 크기", allowableValues = "range[0, 100]")
		private Integer size;

		@ApiModelProperty(value = "정렬(사용법: 컬럼명,ASC|DESC)")
		private List<String> sort;
	}
}
