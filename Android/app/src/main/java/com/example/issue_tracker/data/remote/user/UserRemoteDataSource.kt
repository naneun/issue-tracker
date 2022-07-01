package com.example.issue_tracker.data.remote.user

import com.example.issue_tracker.data.dto.MemberDto

class UserRemoteDataSource(private val api:UserApi):UserDataSource {
    override suspend fun getMemberList(): MemberDto {
        return api.getMemberList()
    }
}