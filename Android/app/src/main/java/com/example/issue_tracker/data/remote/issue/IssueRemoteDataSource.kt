package com.example.issue_tracker.data.remote.issue

import com.example.issue_tracker.data.dto.CommentDto
import com.example.issue_tracker.data.dto.IssueDetailDto
import com.example.issue_tracker.data.dto.IssueDto
import com.example.issue_tracker.domain.model.IssueRequestDto
import okhttp3.MultipartBody

class IssueRemoteDataSource(private val api:IssueApi):IssueDataSource {
    override suspend fun getIssueList(state:String): IssueDto {
        return api.getIssueList(state)
    }

    override suspend fun filterIssueList(state: String, writeId: Int?, labelId: Int?, mileStoneId: Int?): IssueDto {
        return api.filterIssueList(state,writeId,labelId,mileStoneId)
    }

    override suspend fun registerIssue(issue: IssueRequestDto) {
        api.registerIssue(issue)
    }

    override suspend fun loadImage(data: MultipartBody.Part): String {
        return api.loadImage(data).string()
    }

    override suspend fun deleteIssues(issues: List<Int>) {
        api.deleteIssues(issues)
    }

    override suspend fun closeIssues(issues: List<Int>) {
       api.closeIssues(issues)
    }

    override suspend fun getIssueDetail(id: Int): IssueDetailDto {
        return api.getIssueDetail(id)
    }

    override suspend fun getComments(id: Int, page: Int): CommentDto {
        return api.getComments(id,page)
    }

}