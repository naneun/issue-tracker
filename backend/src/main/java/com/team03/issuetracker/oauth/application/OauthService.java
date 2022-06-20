package com.team03.issuetracker.oauth.application;

import com.team03.issuetracker.common.domain.dto.LoginMemberResponse;
import com.team03.issuetracker.oauth.dto.OauthAccessToken;

public interface OauthService {

	OauthAccessToken obtainAccessToken(String code);

	OauthAccessToken renewAccessToken();

	LoginMemberResponse obtainUserInfo(OauthAccessToken accessToken);
}
