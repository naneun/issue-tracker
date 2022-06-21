package com.team03.issuetracker.oauth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@RequiredArgsConstructor
public class GoogleAccessTokenRequest {

    private final String clientId;
    private final String clientSecret;
    private final String code;
    private final String grantType;
    private final String redirectUri;

}
