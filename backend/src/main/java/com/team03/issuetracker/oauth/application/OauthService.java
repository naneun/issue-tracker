package com.team03.issuetracker.oauth.application;

import com.team03.issuetracker.oauth.dto.OauthAccessToken;
import com.team03.issuetracker.oauth.dto.OauthUserInfo;

public interface OauthService {

	OauthAccessToken obtainAccessToken(String code);

	OauthAccessToken renewAccessToken();

	OauthUserInfo obtainUserInfo(OauthAccessToken accessToken);
}
