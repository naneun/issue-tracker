package com.example.issue_tracker.domain.model

import java.time.LocalDateTime

data class IssueDetail(
    val id:Int,
    val title: String,
    val state: Boolean,
    val time: LocalDateTime,
    val writer: User,
    val labelName:String,
    val mileStoneName:String,
    val assigneeName:String
)
