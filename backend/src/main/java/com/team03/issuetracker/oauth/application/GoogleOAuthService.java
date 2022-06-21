package com.team03.issuetracker.oauth.application;

import com.team03.issuetracker.oauth.dto.GoogleAccessTokenRequest;
import com.team03.issuetracker.oauth.dto.GoogleUserInfo;
import com.team03.issuetracker.oauth.dto.OAuthAccessToken;
import com.team03.issuetracker.oauth.dto.OAuthUser;
import com.team03.issuetracker.oauth.exception.OAuthException;
import com.team03.issuetracker.oauth.properties.OAuthProperties;
import com.team03.issuetracker.oauth.properties.VendorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static com.team03.issuetracker.oauth.utils.OAuthUtils.AUTHORIZATION_CODE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service("google")
public class GoogleOAuthService implements OAuthService {

    private final VendorProperties vendorProperties;

    @Autowired
    public GoogleOAuthService(OAuthProperties oAuthProperties) {
        this.vendorProperties = oAuthProperties.getVendorProperties("google");
    }

    @Override
    public OAuthAccessToken obtainAccessToken(String code) {

        return WebClient.create().post()
                .uri(vendorProperties.getAccessTokenUri())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(
                        new GoogleAccessTokenRequest(
                                vendorProperties.getClientId(),
                                vendorProperties.getClientSecret(),
                                code,
                                AUTHORIZATION_CODE,
                                vendorProperties.getRedirectUri()
                        )
                )
                .retrieve()
                .bodyToMono(OAuthAccessToken.class)
                .blockOptional()
                .orElseThrow(OAuthException::new);
    }

    @Override
    public OAuthUser obtainUserInfo(OAuthAccessToken accessToken) {

        GoogleUserInfo googleUserInfo = WebClient.create().get()
                .uri(vendorProperties.getUserInfoUri())
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, accessToken.fullInfo())
                .retrieve()
                .bodyToMono(GoogleUserInfo.class)
                .blockOptional()
                .orElseThrow(OAuthException::new);

        return googleUserInfo.toOAuthUser(accessToken);
    }
}
