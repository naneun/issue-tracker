package com.team03.issuetracker.oauth.application;

import com.team03.issuetracker.common.domain.dto.LoginMemberResponse;
import com.team03.issuetracker.oauth.dto.OAuthAccessToken;

public interface OAuthService {

    OAuthAccessToken obtainAccessToken(String code);

    LoginMemberResponse obtainUserInfo(OAuthAccessToken accessToken);
}
