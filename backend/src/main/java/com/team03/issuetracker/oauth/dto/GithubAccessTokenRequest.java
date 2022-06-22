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
public class GithubAccessTokenRequest {

    private final String clientId;
    private final String clientSecret;
    private final String code;

    public static GithubAccessTokenRequest of(String code, VendorProperties vendorProperties) {
        return new GithubAccessTokenRequest(vendorProperties.getClientId(),
            vendorProperties.getClientSecret(), code);
    }
}
