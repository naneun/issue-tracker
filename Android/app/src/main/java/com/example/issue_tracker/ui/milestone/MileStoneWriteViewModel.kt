package com.example.issue_tracker.ui.milestone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.domain.repository.HomeRepository
import com.google.gson.annotations.SerializedName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MileStoneWriteViewModel @Inject constructor(private val repository: HomeRepository) :
    ViewModel() {

    private val _mileStoneDescription = MutableStateFlow("")
    val mileStoneDescription = _mileStoneDescription.asStateFlow()

    private val _mileStoneDueDate = MutableStateFlow("")
    val mileStoneDueDate = _mileStoneDueDate.asStateFlow()

    private val _mileStoneTitle = MutableStateFlow("")
    val mileStoneTitle = _mileStoneTitle.asStateFlow()

    fun registerMileStone() {
        viewModelScope.launch {
            repository.registerMilestone(_mileStoneDescription.value, _mileStoneDueDate.value, _mileStoneTitle.value)
        }
    }

    fun loadDescription(description: String) {
        _mileStoneDescription.value = description
    }

    fun loadDueDate(dueDate: String) {
        _mileStoneDueDate.value = dueDate
    }

    fun loadTitle(title: String) {
        _mileStoneTitle.value = title
    }
}