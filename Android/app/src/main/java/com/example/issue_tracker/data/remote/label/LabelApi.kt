package com.example.issue_tracker.data.remote.label

import com.example.issue_tracker.data.dto.LabelDto
import com.example.issue_tracker.domain.model.LabelRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LabelApi {
    @GET("api/labels")
    suspend fun getLabelList():LabelDto

    @POST("api/labels")
    suspend fun registerLabel(@Body label:LabelRequestDto)
}