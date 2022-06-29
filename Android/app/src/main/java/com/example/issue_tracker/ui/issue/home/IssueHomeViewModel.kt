package com.example.issue_tracker.ui.issue.home

import androidx.lifecycle.ViewModel
import com.example.issue_tracker.domain.model.Issue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class IssueHomeViewModel : ViewModel() {
    private val _openIssueList = MutableStateFlow<MutableList<Issue>>(mutableListOf())
    val openIssueList: StateFlow<List<Issue>> = _openIssueList

    private val _closeIssueList = MutableStateFlow<MutableList<Issue>>(mutableListOf())
    val closeIssueList: StateFlow<List<Issue>> = _closeIssueList

    private val _checkList = MutableStateFlow<MutableList<Int>>(mutableListOf())
    val checkList: StateFlow<List<Int>> = _checkList

    init {
        makeDummyIssueList()
    }

    private fun makeDummyIssueList() {
        _openIssueList.value = mutableListOf<Issue>(
            Issue(1, "마일스톤", "제목", "설명", "label"),
            Issue(2, "마스터즈 코스1", "이슈트래커1", "6월 13일에서 20일까지", "ABCDEF"),
            Issue(3, "마스터즈 코스2", "이슈트래커2", "7월 9일에서 12일까지", "asdfef")
        )
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
}