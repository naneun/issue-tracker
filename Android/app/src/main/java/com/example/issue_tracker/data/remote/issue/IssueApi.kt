package com.example.issue_tracker.data.remote.issue

import com.example.issue_tracker.data.dto.IssueDto
import com.example.issue_tracker.domain.model.IssueRequestDto
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface IssueApi {

    @GET("api/issues")
    suspend fun getIssueList(@Query("state") state:String):IssueDto

    @GET("api/search/issues")
    suspend fun filterIssueList(@Query("state") state: String, @Query("creatorId") writerId:Int?=null ,@Query("labelId") labelId:Int?=null , @Query("milestoneId") mileStoneId:Int?=null):IssueDto

    @POST("api/issues")
    suspend fun registerIssue(@Body issue:IssueRequestDto)

    @Multipart
    @POST("images")
    suspend fun loadImage(@Part image:MultipartBody.Part):ResponseBody
}