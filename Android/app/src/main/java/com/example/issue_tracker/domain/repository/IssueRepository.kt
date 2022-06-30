package com.example.issue_tracker.domain.repository

import com.example.issue_tracker.domain.model.Issue
import com.example.issue_tracker.domain.model.IssueState

interface IssueRepository {

    suspend fun getIssueList(): List<Issue>

    suspend fun filterIssueList(state: IssueState, writerId:Int?, labelId:Int?, milestoneId:Int?):List<Issue>
}