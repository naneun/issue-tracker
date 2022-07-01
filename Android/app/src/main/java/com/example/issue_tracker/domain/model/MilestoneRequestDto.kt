package com.example.issue_tracker.domain.model

import com.google.gson.annotations.SerializedName

data class MilestoneRequestDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("dueDate")
    val dueDate: String,
    @SerializedName("title")
    val title: String
)