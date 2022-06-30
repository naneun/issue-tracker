package com.example.issue_tracker.data.remote.issue

import com.example.issue_tracker.data.dto.IssueDto
import retrofit2.http.GET
import retrofit2.http.Query

interface IssueApi {

    @GET("api/issues")
    suspend fun getIssueList(@Query("state") state:String):IssueDto

    @GET("api/search/issues")
    suspend fun filterIssueList(@Query("state") state: String, @Query("creatorId") writerId:Int?=null ,@Query("labelId") labelId:Int?=null , @Query("milestoneId") mileStoneId:Int?=null):IssueDto
}