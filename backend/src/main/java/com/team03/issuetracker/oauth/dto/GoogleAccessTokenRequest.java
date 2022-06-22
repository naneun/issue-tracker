package com.team03.issuetracker.oauth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.team03.issuetracker.oauth.properties.VendorProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GoogleAccessTokenRequest {

    public static final String AUTHORIZATION_CODE = "authorization_code";

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String code;
    private final String grantType = AUTHORIZATION_CODE;

    public static GoogleAccessTokenRequest of(String code, VendorProperties vendorProperties) {
        return new GoogleAccessTokenRequest(vendorProperties.getClientId(),
            vendorProperties.getClientSecret(),
            vendorProperties.getRedirectUri(), code);
    }
}
