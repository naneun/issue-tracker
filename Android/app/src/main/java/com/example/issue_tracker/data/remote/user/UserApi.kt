package com.example.issue_tracker.data.remote.user

import com.example.issue_tracker.data.dto.MemberDto
import retrofit2.http.GET

interface UserApi {

    @GET("api/members")
    suspend fun getMemberList():MemberDto
}