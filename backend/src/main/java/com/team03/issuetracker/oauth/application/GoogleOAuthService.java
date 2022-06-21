package com.team03.issuetracker.oauth.application;

import com.team03.issuetracker.common.domain.dto.LoginMemberResponse;
import com.team03.issuetracker.oauth.dto.GoogleAccessTokenRequest;
import com.team03.issuetracker.oauth.dto.GoogleUserInfo;
import com.team03.issuetracker.oauth.dto.OAuthAccessToken;
import com.team03.issuetracker.oauth.exception.OAuthException;
import com.team03.issuetracker.oauth.properties.OAuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static com.team03.issuetracker.oauth.utils.OAuthUtils.AUTHORIZATION_CODE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service("google")
public class GoogleOAuthService implements OAuthService {

	private final String clientId;
	private final String clientSecret;
	private final String accessTokenUri;
	private final String userInfoUri;
	private final String redirectUri;
	private final LoginService loginService;

	@Autowired
	public GoogleOAuthService(OAuthProperties properties, LoginService loginService) {
		this.clientId = properties.getGoogleClientId();
		this.clientSecret = properties.getGoogleClientSecret();
		this.accessTokenUri = properties.getGoogleAccessTokenUri();
		this.userInfoUri = properties.getGoogleUserInfoUri();
		this.redirectUri = properties.getGoogleRedirectUri();
		this.loginService = loginService;
	}

	@Override
	public OAuthAccessToken obtainAccessToken(String code) {

		return WebClient.create().post()
			.uri(accessTokenUri)
			.accept(MediaType.APPLICATION_JSON)
			.bodyValue(new GoogleAccessTokenRequest(
				clientId,
				clientSecret,
				code,
				AUTHORIZATION_CODE,
				redirectUri
			))
			.retrieve()
			.bodyToMono(OAuthAccessToken.class)
			.blockOptional()
			.orElseThrow(OAuthException::new);
	}

	@Override
	public LoginMemberResponse obtainUserInfo(OAuthAccessToken accessToken) {
		GoogleUserInfo userInfo = WebClient.create().get()
			.uri(userInfoUri)
			.accept(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION, accessToken.fullInfo())
			.retrieve()
			.bodyToMono(GoogleUserInfo.class)
			.blockOptional()
			.orElseThrow(OAuthException::new);

		return loginService.login(userInfo.toEntity(accessToken));
	}
}
