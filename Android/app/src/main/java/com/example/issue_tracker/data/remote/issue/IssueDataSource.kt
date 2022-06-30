package com.example.issue_tracker.data.remote.issue

import com.example.issue_tracker.data.dto.IssueDto

interface IssueDataSource {
    suspend fun getIssueList(state:String):IssueDto

    suspend fun filterIssueList(state: String, writeId:Int?=null, labelId:Int?=null, mileStoneId:Int?=null): IssueDto
}