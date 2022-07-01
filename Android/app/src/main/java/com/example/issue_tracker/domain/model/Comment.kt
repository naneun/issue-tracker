package com.example.issue_tracker.domain.model

sealed class CommentItem {

    data class Comment(
        val writer: User,
        val time: String,
        val editable: Boolean,
        val content: String
    ):CommentItem()

    object CommentProgressBar :CommentItem()

}