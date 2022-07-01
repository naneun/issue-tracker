package com.example.issue_tracker.data.remote.issue

import com.example.issue_tracker.data.dto.CommentDto
import com.example.issue_tracker.data.dto.IssueDetailDto
import com.example.issue_tracker.data.dto.IssueDto
import com.example.issue_tracker.domain.model.IssueRequestDto
import okhttp3.MultipartBody

interface IssueDataSource {
    suspend fun getIssueList(state:String):IssueDto

    suspend fun filterIssueList(state: String, writeId:Int?=null, labelId:Int?=null, mileStoneId:Int?=null): IssueDto

    suspend fun registerIssue(issue:IssueRequestDto)

    suspend fun loadImage(data:MultipartBody.Part):String

    suspend fun deleteIssues(issues:List<Int>)

    suspend fun closeIssues(issues: List<Int>)

    suspend fun getIssueDetail(id:Int):IssueDetailDto

    suspend fun getComments(id:Int, page:Int):CommentDto
}