package com.example.issue_tracker.data.remote.milestone

import com.example.issue_tracker.data.dto.MilestoneDto
import com.example.issue_tracker.domain.model.MilestoneRequestDto

class MileStoneRemoteDataSource(private val api:MileStoneApi):MileStoneDataSource {
    override suspend fun getMileStoneList(): MilestoneDto {
        return api.getMileStoneList()
    }

    override suspend fun registerMilestone(description: String, dueDate: String, title: String) {
        return api.registerMilestone(MilestoneRequestDto(description, dueDate, title))
    }

    override suspend fun updateMilestone(
        id: Int,
        description: String,
        dueDate: String,
        title: String
    ): MilestoneDto {
        return api.updateMilestone(id, MilestoneRequestDto(description, dueDate, title))
    }

}