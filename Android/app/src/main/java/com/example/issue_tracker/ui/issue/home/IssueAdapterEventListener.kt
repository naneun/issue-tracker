package com.example.issue_tracker.ui.issue.home

import com.example.issue_tracker.domain.model.Issue

interface IssueAdapterEventListener {
    fun updateIssueState(itemId: Int, boolean: Boolean)
    fun switchToEditMode(itemId: Int)
    fun addInCheckList(itemId: Int)
    fun deleteInCheckList(itemId: Int)
    fun getIntoDetail(issue: Issue)
}