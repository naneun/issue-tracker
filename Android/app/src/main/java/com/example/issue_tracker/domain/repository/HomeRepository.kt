package com.example.issue_tracker.domain.repository

import com.example.issue_tracker.domain.model.Label
import com.example.issue_tracker.domain.model.User

interface HomeRepository {
    suspend fun getLabelList():List<Label>

    suspend fun registerLabel(title:String, content:String, color:String)

    suspend fun deleteLabels(selectedList:List<Int>)

    suspend fun getUserList():List<User>
}