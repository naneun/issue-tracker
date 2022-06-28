package com.example.issue_tracker.domain.model

data class Comment(
    val writer: User,
    val time: String,
    val editable: Boolean,
    val content: String
)


