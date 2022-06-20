package com.team03.issuetracker.oauth.application;

import com.team03.issuetracker.common.domain.dto.LoginMemberResponse;
import com.team03.issuetracker.oauth.dto.GithubAccessTokenRequest;
import com.team03.issuetracker.oauth.dto.GithubUserInfo;
import com.team03.issuetracker.oauth.dto.OauthAccessToken;
import com.team03.issuetracker.oauth.exception.OauthException;
import com.team03.issuetracker.oauth.properties.OauthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service("github")
public class GithubOauthService implements OauthService {

	private final String clientId;
	private final String clientSecret;
	private final String accessTokenUri;
	private final String userInfoUri;
	private final LoginService loginService;

	@Autowired
	public GithubOauthService(OauthProperties properties, LoginService loginService) {
		this.clientId = properties.getGithubClientId();
		this.clientSecret = properties.getGithubClientSecret();
		this.accessTokenUri = properties.getGithubAccessTokenUri();
		this.userInfoUri = properties.getGithubUserInfoUri();
		this.loginService = loginService;
	}

	// Todo: state을 검증하는 과정을 넣으려면 애초에 code에 대한 요청도 서버에서 해야하는건가?
	@Override
	public OauthAccessToken obtainAccessToken(String code) {

		return WebClient.create().post()
			.uri(accessTokenUri)
			.accept(MediaType.APPLICATION_JSON)
			.bodyValue(new GithubAccessTokenRequest(
				clientId,
				clientSecret,
				code))
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

		GithubUserInfo userInfo = WebClient.create().get()
			.uri(userInfoUri)
			.accept(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION, accessToken.getTokenType() + " " + accessToken.getAccessToken())
			.retrieve()
			.bodyToMono(GithubUserInfo.class)
			.blockOptional()
			.orElseThrow(OauthException::new);

		return loginService.login(userInfo.toEntity(accessToken));
	}
}
