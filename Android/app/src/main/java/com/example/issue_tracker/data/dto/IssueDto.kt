package com.example.issue_tracker.data.dto


import com.example.issue_tracker.domain.model.Issue
import com.example.issue_tracker.domain.model.Label
import com.google.gson.annotations.SerializedName

class IssueDto : ArrayList<IssueDtoItem>()

data class IssueDtoItem(

    @SerializedName("issueId")
    val id:Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("label")
    val label: com.example.issue_tracker.data.dto.IssueLabel,
    @SerializedName("milestone")
    val milestone: Milestone,
    @SerializedName("title")
    val title: String
)

data class IssueLabel(
    @SerializedName("backgroundColor")
    val backgroundColor: String,
    @SerializedName("title")
    val title: String
)

data class Milestone(
    @SerializedName("title")
    val title: String
)

fun IssueDtoItem.toIssue():Issue= Issue(id, milestone.title,title,content, Label( label.title,"",label.backgroundColor))