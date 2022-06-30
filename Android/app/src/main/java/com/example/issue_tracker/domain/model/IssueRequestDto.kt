package com.example.issue_tracker.domain.model


import com.google.gson.annotations.SerializedName

data class IssueRequestDto(
    @SerializedName("assigneeId")
    val assigneeId: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("labelId")
    val labelId: Int,
    @SerializedName("milestoneId")
    val milestoneId: Int,
    @SerializedName("title")
    val title: String
)