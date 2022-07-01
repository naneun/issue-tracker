package com.example.issue_tracker.data.repository

import com.example.issue_tracker.data.dto.toLabel
import com.example.issue_tracker.data.dto.toUser
import com.example.issue_tracker.data.remote.label.LabelDataSource
import com.example.issue_tracker.data.remote.user.UserDataSource
import com.example.issue_tracker.domain.model.Label
import com.example.issue_tracker.domain.model.User
import com.example.issue_tracker.domain.repository.HomeRepository
import kotlin.system.measureTimeMillis

class HomeRepositoryImpl(private val labelDataSource: LabelDataSource, private val userDataSource: UserDataSource):HomeRepository {
    override suspend fun getLabelList(): List<Label> {
        return labelDataSource.getLabelList().map {
            it.toLabel()
        }
    }

    override suspend fun registerLabel(title: String, content: String, color: String){
        labelDataSource.registerLabel(title,content,color)
    }

    override suspend fun deleteLabels(selectedList: List<Int>) {
        labelDataSource.deleteLabels(selectedList)
    }

    override suspend fun getUserList(): List<User> {
        return userDataSource.getMemberList().map {
            it.toUser()
        }
    }

}