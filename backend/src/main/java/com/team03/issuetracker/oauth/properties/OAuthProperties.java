package com.team03.issuetracker.oauth.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "oauth")
public class OAuthProperties {

	private final String githubClientId;
	private final String githubClientSecret;
	private final String githubAccessTokenUri;
	private final String githubUserInfoUri;

	private final String googleClientId;
	private final String googleClientSecret;
	private final String googleAccessTokenUri;
	private final String googleUserInfoUri;
	private final String googleRedirectUri;

}
