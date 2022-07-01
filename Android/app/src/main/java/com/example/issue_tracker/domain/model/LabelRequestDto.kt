package com.example.issue_tracker.domain.model

import com.google.gson.annotations.SerializedName

data class LabelRequestDto(
    @SerializedName("backgroundColor")
    val backgroundColor: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("title")
    val title: String
)
