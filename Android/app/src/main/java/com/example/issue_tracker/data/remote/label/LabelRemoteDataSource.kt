package com.example.issue_tracker.data.remote.label

import com.example.issue_tracker.data.dto.LabelDto
import com.example.issue_tracker.data.dto.LabelDtoItem
import com.example.issue_tracker.domain.model.LabelRequestDto

class LabelRemoteDataSource(private val api:LabelApi):LabelDataSource {
    override suspend fun getLabelList(): LabelDto {
        return api.getLabelList()
    }

    override suspend fun registerLabel(title: String, content: String, color: String) {
        return api.registerLabel(LabelRequestDto(color,content,title))
    }

    override suspend fun deleteLabels(selectedList: List<Int>) {
        return api.deleteLabels(selectedList)
    }
}