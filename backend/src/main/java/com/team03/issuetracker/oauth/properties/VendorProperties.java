package com.team03.issuetracker.oauth.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VendorProperties {

    private final String clientId;

    private final String clientSecret;

    private final String accessTokenUri;

    private final String userInfoUri;

    private final String redirectUri;

    private final String userEmailInfoUri;

    private final String codeRequestUri;
}
