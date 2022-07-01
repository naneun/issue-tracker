package com.example.issue_tracker.data.remote.milestone

import com.example.issue_tracker.data.dto.Milestone
import com.example.issue_tracker.data.dto.MilestoneDto
import com.example.issue_tracker.domain.model.LabelRequestDto
import com.example.issue_tracker.domain.model.MilestoneRequestDto
import retrofit2.http.*

interface MileStoneApi {
    @GET("api/milestones")
    suspend fun getMileStoneList():MilestoneDto

    @POST("api/milestones")
    suspend fun registerMilestone(@Body milestone: MilestoneRequestDto)

    @PATCH("api/milestones/{id}")
    suspend fun updateMilestone(@Path("id") id: Int, @Body milestone: MilestoneRequestDto): MilestoneDto
}