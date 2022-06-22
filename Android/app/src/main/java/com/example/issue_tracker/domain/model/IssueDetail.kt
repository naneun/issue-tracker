package com.example.issue_tracker.domain.model

import java.time.LocalDateTime

data class IssueDetail(
    val title: String,
    val state: Boolean,
    val time: LocalDateTime,
    val writer: User,
    val comment: List<Comment>
)
