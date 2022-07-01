package com.example.issue_tracker.data.dto


import android.provider.SyncStateContract
import com.example.issue_tracker.common.Constants
import com.example.issue_tracker.domain.model.IssueDetail
import com.example.issue_tracker.domain.model.User
import com.example.issue_tracker.ui.common.transformStringToTime
import com.google.gson.annotations.SerializedName

data class IssueDetailDto(
    @SerializedName("assignee")
    val assignee: Assignee,
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("creator")
    val creator: Creator,
    @SerializedName("id")
    val id: Int,
    @SerializedName("label")
    val label: Label,
    @SerializedName("milestone")
    val milestone: MilestoneInfo,
    @SerializedName("state")
    val state: String,
    @SerializedName("title")
    val title: String
)

data class Creator(
    @SerializedName("creatorId")
    val creatorId: Int,
    @SerializedName("creatorName")
    val creatorName: String?
)

data class Assignee(
    @SerializedName("assigneeId")
    val assigneeId: Int,
    @SerializedName("assigneeName")
    val assigneeName: String
)

data class Label(
    @SerializedName("labelId")
    val labelId: Int,
    @SerializedName("labelTitle")
    val labelTitle: String
)

data class MilestoneInfo(
    @SerializedName("milestoneId")
    val milestoneId: Int,
    @SerializedName("milestoneTitle")
    val milestoneTitle: String
)

fun IssueDetailDto.toIssueDetail():IssueDetail {
    val state = state=="OPEN"
    return IssueDetail(id, title, state, transformStringToTime(createdAt?:"2022-07-01T02:33:15"), User(creator.creatorId, creator.creatorName?:"Anonymous", Constants.DEFAULT_PROFILE_IMAGE), label.labelTitle, milestone.milestoneTitle, assignee.assigneeName )
}