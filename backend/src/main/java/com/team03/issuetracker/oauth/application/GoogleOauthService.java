package com.team03.issuetracker.oauth.application;

import com.team03.issuetracker.common.domain.dto.LoginMemberResponse;
import com.team03.issuetracker.oauth.dto.GoogleAccessTokenRequest;
import com.team03.issuetracker.oauth.dto.GoogleUserInfo;
import com.team03.issuetracker.oauth.dto.OauthAccessToken;
import com.team03.issuetracker.oauth.exception.OauthException;
import com.team03.issuetracker.oauth.properties.OauthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static com.team03.issuetracker.oauth.utils.OAuthUtils.AUTHORIZATION_CODE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service("google")
public class GoogleOauthService implements OauthService {

	private final String clientId;
	private final String clientSecret;
	private final String accessTokenUri;
	private final String userInfoUri;
	private final String redirectUri;
	private final LoginService loginService;

	@Autowired
	public GoogleOauthService(OauthProperties properties, LoginService loginService) {
		this.clientId = properties.getGoogleClientId();
		this.clientSecret = properties.getGoogleClientSecret();
		this.accessTokenUri = properties.getGoogleAccessTokenUri();
		this.userInfoUri = properties.getGoogleUserInfoUri();
		this.redirectUri = properties.getGoogleRedirectUri();
		this.loginService = loginService;
	}

	@Override
	public OauthAccessToken obtainAccessToken(String code) {

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
			.bodyToMono(OauthAccessToken.class)
			.blockOptional()
			.orElseThrow(OauthException::new);
	}

	@Override
	public OauthAccessToken renewAccessToken() {
		return null;
	}

	@Override
	public LoginMemberResponse obtainUserInfo(OauthAccessToken accessToken) {
		GoogleUserInfo userInfo = WebClient.create().get()
			.uri(userInfoUri)
			.accept(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION, accessToken.getTokenType() + " " + accessToken.getAccessToken())
			.retrieve()
			.bodyToMono(GoogleUserInfo.class)
			.blockOptional()
			.orElseThrow(OauthException::new);

		return loginService.login(userInfo.toEntity(accessToken));
	}
}
