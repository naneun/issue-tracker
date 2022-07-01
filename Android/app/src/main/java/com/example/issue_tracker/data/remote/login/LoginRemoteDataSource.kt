package com.example.issue_tracker.data.remote.login

import com.example.issue_tracker.common.AccessToken
import com.example.issue_tracker.data.dto.OAuthInfo

class LoginRemoteDataSource(private val api: LoginApi) : LoginDataSource {
    override suspend fun getAccessToken(): OAuthInfo {
        return api.getJWT(AccessToken.code)
    }
}