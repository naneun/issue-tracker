package com.example.issue_tracker.data.repository

import com.example.issue_tracker.common.AccessToken
import com.example.issue_tracker.common.Constants
import com.example.issue_tracker.data.remote.login.LoginDataSource
import com.example.issue_tracker.domain.repository.LoginRepository
import com.example.issue_tracker.ui.common.SharedPreferenceManager

class LoginRepositoryImpl(private val loginDataSource: LoginDataSource):LoginRepository {
    override suspend fun getAccessToken(): String {
        AccessToken.token = loginDataSource.getAccessToken().jwtAccessToken
        SharedPreferenceManager.putString(Constants.ACCESS_TOKEN_KEY, AccessToken.token)
        return  AccessToken.token
    }
}