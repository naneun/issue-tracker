package com.example.issue_tracker.domain.model

sealed class Comment

data class MyComment(
    val writer: User,
    val time: String,
    val content: String
) : Comment()

data class OtherComment(
    val writer: User,
    val time: String,
    val content: String
) : Comment()
