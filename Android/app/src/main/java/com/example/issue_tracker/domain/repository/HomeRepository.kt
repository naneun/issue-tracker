package com.example.issue_tracker.domain.repository

import com.example.issue_tracker.domain.model.Label

interface HomeRepository {
    suspend fun getLabelList():List<Label>

    suspend fun registerLabel(title:String, content:String, color:String)
}