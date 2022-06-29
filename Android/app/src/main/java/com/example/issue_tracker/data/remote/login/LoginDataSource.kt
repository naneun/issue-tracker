package com.example.issue_tracker.data.remote.login

import com.example.issue_tracker.data.dto.OAuthInfo


interface LoginDataSource {
    suspend fun getAccessToken(): OAuthInfo
}