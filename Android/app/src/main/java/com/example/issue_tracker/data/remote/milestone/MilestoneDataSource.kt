package com.example.issue_tracker.data.remote.milestone

import com.example.issue_tracker.data.dto.MilestoneDto
import com.example.issue_tracker.domain.model.MilestoneRequestDto

interface MileStoneDataSource {
    suspend fun getMileStoneList(): MilestoneDto

    suspend fun registerMilestone(description: String, dueDate: String, title: String)

    suspend fun updateMilestone(id: Int, description: String, dueDate: String, title: String): MilestoneDto
}