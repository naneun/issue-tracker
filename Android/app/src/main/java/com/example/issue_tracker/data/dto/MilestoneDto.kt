package com.example.issue_tracker.data.dto

import com.example.issue_tracker.domain.model.Label
import com.example.issue_tracker.domain.model.MileStone
import com.google.gson.annotations.SerializedName

class MilestoneDto : ArrayList<MilestoneDtoItem>()

data class MilestoneDtoItem(
    @SerializedName("closedIssueCount")
    val closedIssueCount: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("dueDate")
    val dueDate: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("openIssueCount")
    val openIssueCount: Int,

    @SerializedName("title")
    val title: String
)

fun MilestoneDtoItem.toMileStone(): MileStone = MileStone(id, title, description, dueDate, openIssueCount, closedIssueCount)