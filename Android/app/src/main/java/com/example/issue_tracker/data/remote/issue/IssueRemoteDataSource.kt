package com.example.issue_tracker.data.remote.issue

import com.example.issue_tracker.data.dto.IssueDto

class IssueRemoteDataSource(private val api:IssueApi):IssueDataSource {
    override suspend fun getIssueList(state:String): IssueDto {
        return api.getIssueList(state)
    }

    override suspend fun filterIssueList(state: String, writeId: Int?, labelId: Int?, mileStoneId: Int?): IssueDto {
        return api.filterIssueList(state,writeId,labelId,mileStoneId)
    }

}