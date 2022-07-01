package com.example.issue_tracker.data.repository

import com.example.issue_tracker.data.dto.toComment
import com.example.issue_tracker.data.dto.toIssue
import com.example.issue_tracker.data.dto.toIssueDetail
import com.example.issue_tracker.data.remote.issue.IssueDataSource
import com.example.issue_tracker.domain.model.*
import com.example.issue_tracker.domain.repository.IssueRepository
import okhttp3.MultipartBody

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

    override suspend fun registerIssue(issue: IssueRequestDto) {
        dataSource.registerIssue(issue)
    }

    override suspend fun loadImage(data: MultipartBody.Part): String {
        return dataSource.loadImage(data)
    }

    override suspend fun deleteIssues(selectedIssues: List<Int>) {
        dataSource.deleteIssues(selectedIssues)
    }

    override suspend fun closeIssues(selectedIssues: List<Int>) {
        dataSource.closeIssues(selectedIssues)
    }

    override suspend fun loadDetail(id: Int): IssueDetail {
        return dataSource.getIssueDetail(id).toIssueDetail()
    }

    override suspend fun loadComments(id: Int, page: Int): List<CommentItem.Comment> {
        return dataSource.getComments(id,page).content.map {
            it.toComment()
        }
    }


}