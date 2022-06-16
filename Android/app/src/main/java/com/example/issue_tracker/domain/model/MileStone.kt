package com.example.issue_tracker.domain.model

data class MileStone(
    val id: Int,
    val title: String,
    val content: String,
    val completeDay: String,
    val openIssueCount: Int,
    val closeIssueCount: Int
)