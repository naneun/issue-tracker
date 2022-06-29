package com.example.issue_tracker.data.remote.login

import com.example.issue_tracker.data.dto.OAuthInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginApi {

    @GET("login/github/callback")
    suspend fun getJWT(@Query("code") code: String): OAuthInfo

}



