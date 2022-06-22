package com.team03.issuetracker.oauth.application;

import com.team03.issuetracker.oauth.dto.OAuthAccessToken;
import com.team03.issuetracker.oauth.dto.OAuthUser;

public interface OAuthService {

    OAuthAccessToken obtainAccessToken(String code);

    OAuthUser obtainUserInfo(OAuthAccessToken accessToken);
}
