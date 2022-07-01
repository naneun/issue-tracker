package com.example.issue_tracker.data.remote.label

import com.example.issue_tracker.data.dto.LabelDto
import com.example.issue_tracker.data.dto.LabelDtoItem

interface LabelDataSource {
    suspend fun getLabelList():LabelDto

    suspend fun registerLabel(title: String, content: String, color: String)

    suspend fun deleteLabels(selectedList:List<Int>)
}