package com.example.issue_tracker.data.remote.user

import com.example.issue_tracker.data.dto.MemberDto

interface UserDataSource {
    suspend fun getMemberList():MemberDto
}