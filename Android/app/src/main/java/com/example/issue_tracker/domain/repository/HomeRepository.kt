package com.example.issue_tracker.domain.repository

import com.example.issue_tracker.domain.model.Label
import com.example.issue_tracker.domain.model.MileStone
import com.example.issue_tracker.domain.model.MilestoneRequestDto
import com.google.gson.annotations.SerializedName

interface HomeRepository {
    suspend fun getLabelList():List<Label>

    suspend fun registerLabel(title:String, content:String, color:String)

    suspend fun getMileStoneList():List<MileStone>

    suspend fun registerMilestone(description:String, dueDate:String, title:String)

    suspend fun updateMilestone(id: Int, description: String, dueDate: String, title: String)
}