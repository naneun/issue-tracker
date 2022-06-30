package com.example.issue_tracker.data.repository

import com.example.issue_tracker.data.dto.toIssue
import com.example.issue_tracker.data.remote.issue.IssueDataSource
import com.example.issue_tracker.domain.model.Issue
import com.example.issue_tracker.domain.model.IssueState
import com.example.issue_tracker.domain.repository.IssueRepository

class IssueRepositoryImpl(private val dataSource: IssueDataSource):IssueRepository {
    override suspend fun getIssueList(): List<Issue> {
        return dataSource.getIssueList("OPEN").map {
            it.toIssue()
        }
    }

    override suspend fun filterIssueList(state:IssueState, writerId:Int?, labelId:Int?, milestoneId:Int?): List<Issue> {
        return if(state==IssueState.CLOSE){
            dataSource.filterIssueList("CLOSE", writerId, labelId, milestoneId).map {
                it.toIssue()
            }
        } else{
            dataSource.filterIssueList("OPEN", writerId, labelId, milestoneId).map {
                it.toIssue()
            }
        }
    }
}