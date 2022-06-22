package com.team03.issuetracker.oauth.application;

import com.team03.issuetracker.oauth.dto.*;
import com.team03.issuetracker.oauth.exception.OAuthException;
import com.team03.issuetracker.oauth.properties.AuthProperties;
import com.team03.issuetracker.oauth.properties.VendorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service("github")
public class GithubOAuthService implements OAuthService {

    private final VendorProperties vendorProperties;

    @Autowired
    public GithubOAuthService(AuthProperties oAuthProperties) {
        this.vendorProperties = oAuthProperties.getVendorProperties("github");
    }

    @Override
    public OAuthAccessToken obtainAccessToken(String code) {

        return WebClient.create().post()
                .uri(vendorProperties.getAccessTokenUri())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(
                        new GithubAccessTokenRequest(
                                vendorProperties.getClientId(),
                                vendorProperties.getClientSecret(),
                                code
                        )
                )
                .retrieve()
                .bodyToMono(OAuthAccessToken.class)
                .blockOptional()
                .orElseThrow(OAuthException::new);
    }

    @Override
    public OAuthUser obtainUserInfo(OAuthAccessToken accessToken) {

        GithubUserInfo githubUserInfo = WebClient.create().get()
                .uri(vendorProperties.getUserInfoUri())
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, accessToken.fullInfo())
                .retrieve()
                .bodyToMono(GithubUserInfo.class)
                .blockOptional()
                .orElseThrow(OAuthException::new);

        GithubEmailInfo[] githubEmailInfos = WebClient.create().get()
                .uri(vendorProperties.getUserEmailInfoUri())
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, accessToken.fullInfo())
                .retrieve()
                .bodyToMono(GithubEmailInfo[].class)
                .blockOptional()
                .orElseThrow(OAuthException::new);

        return githubUserInfo.toOAuthUser(accessToken, githubEmailInfos[0].getEmail());
    }
}
