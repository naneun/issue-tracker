package com.example.issue_tracker.domain.model


import com.google.gson.annotations.SerializedName

data class IssueRequestDto(
    @SerializedName("assigneeId")
    val assigneeId: Int?=null,
    @SerializedName("content")
    val content: String,
    @SerializedName("labelId")
    val labelId: Int?=null,
    @SerializedName("milestoneId")
    val milestoneId: Int?=null,
    @SerializedName("title")
    val title: String
)