package com.example.issue_tracker.ui.issue.home

import androidx.lifecycle.ViewModel
import com.example.issue_tracker.domain.model.Issue
import com.example.issue_tracker.domain.model.IssueState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class IssueHomeViewModel : ViewModel() {
    private val _stateList = MutableStateFlow<List<IssueState>>(listOf())
    val stateList: StateFlow<List<IssueState>> = _stateList

    private val _issueList = MutableStateFlow<List<Issue>>(listOf())
    val issueList: StateFlow<List<Issue>> = _issueList

    init {
        initStateList()
        makeDummyIssueList()
    }

    private fun initStateList() {
        _stateList.value = listOf(IssueState.OPEN, IssueState.WRITE_MYSELF, IssueState.ASSIGN_MYSELF, IssueState.WRITE_COMMENT, IssueState.CLOSE)
    }

    private fun makeDummyIssueList() {
        _issueList.value = listOf<Issue>(
            Issue(1, "마일스톤", "제목", "설명", "label"),
            Issue(2, "마스터즈 코스1", "이슈트래커1", "6월 13일에서 20일까지", "ABCDEF"),
            Issue(3, "마스터즈 코스2", "이슈트래커2", "7월 9일에서 12일까지", "asdfef")
        )
    }
}