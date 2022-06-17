package com.example.issue_tracker.domain.model

data class MyAccount(
    val id: String,
    val profileImage: String,
    val issueWriteCount: Int,
    val issueAssignCount: Int,
    val commentCount: Int
)
