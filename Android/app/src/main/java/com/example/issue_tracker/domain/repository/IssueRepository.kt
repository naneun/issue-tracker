package com.example.issue_tracker.domain.repository

import com.example.issue_tracker.domain.model.*
import okhttp3.MultipartBody

interface IssueRepository {

    suspend fun getIssueList(): List<Issue>

    suspend fun filterIssueList(state: IssueState, writerId:Int?, labelId:Int?, milestoneId:Int?):List<Issue>

    suspend fun registerIssue(issue:IssueRequestDto)

    suspend fun loadImage(data:MultipartBody.Part):String

    suspend fun deleteIssues(selectedIssues:List<Int>)

    suspend fun closeIssues(selectedIssues: List<Int>)

    suspend fun loadDetail(id:Int): IssueDetail

    suspend fun loadComments(id:Int, page:Int) :List<CommentItem.Comment>
}