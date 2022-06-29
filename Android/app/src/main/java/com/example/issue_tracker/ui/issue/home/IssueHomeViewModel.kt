package com.example.issue_tracker.ui.issue.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issue_tracker.domain.model.Issue
import com.example.issue_tracker.domain.repository.IssueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueHomeViewModel @Inject constructor(private val repository: IssueRepository) :
    ViewModel() {

    private val _openIssueList = MutableStateFlow<MutableList<Issue>>(mutableListOf())
    val openIssueList: StateFlow<List<Issue>> = _openIssueList

    private val _closeIssueList = MutableStateFlow<MutableList<Issue>>(mutableListOf())
    val closeIssueList: StateFlow<List<Issue>> = _closeIssueList

    private val _checkList = MutableStateFlow<MutableList<Int>>(mutableListOf())
    val checkList: StateFlow<List<Int>> = _checkList

    private val _editMode = MutableStateFlow<Boolean>(false)
    val editMode = _editMode.asStateFlow()

    init {
        loadOpenIssueList()
    }

    private fun loadOpenIssueList() {
        viewModelScope.launch {
            _openIssueList.emit(repository.getIssueList().toMutableList())
        }
    }

    fun addCheckList(itemId: Int) {
        _checkList.value.add(itemId)
    }

    fun removeCheckList(itemId: Int) {
        _checkList.value.removeIf {
            it == itemId
        }
    }

    fun clearCheckList() {
        _checkList.value.clear()
    }

    fun closeIssueList() {
        for (i in 0 until _checkList.value.size) {
            val str = _openIssueList.value.filter {
                it.id == _checkList.value[i]
            }
            _openIssueList.value.removeIf {
                it.id == checkList.value[i]
            }
            _closeIssueList.value.add(str[0])
        }
    }

    fun removeIssueList() {
        for (i in 0 until _checkList.value.size) {
            _openIssueList.value.removeIf {
                it.id == _checkList.value[i]
            }
        }
    }

    fun turnOnEditMode() {
        _editMode.value = true
    }

    fun turnOffEditMode() {
        _editMode.value = false
    }
}