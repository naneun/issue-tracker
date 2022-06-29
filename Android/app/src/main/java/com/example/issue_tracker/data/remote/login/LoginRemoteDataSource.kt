package com.example.issue_tracker.data.remote.login

import com.example.issue_tracker.common.AccessToken
import com.example.issue_tracker.data.dto.OAuthInfo
import com.example.issue_tracker.data.remote.login.LoginApi
import com.example.issue_tracker.data.remote.login.LoginDataSource
import com.example.issue_tracker.ui.common.SharedPreferenceManager

class LoginRemoteDataSource(private val api: LoginApi) : LoginDataSource {
    override suspend fun getAccessToken(): OAuthInfo {
        return api.getJWT(AccessToken.code)
    }
}