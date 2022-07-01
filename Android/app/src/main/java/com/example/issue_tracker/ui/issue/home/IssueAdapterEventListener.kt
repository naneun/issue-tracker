package com.example.issue_tracker.ui.issue.home

interface IssueAdapterEventListener {
    fun switchToEditMode(itemId: Int)
    fun addInCheckList(itemId: Int)
    fun deleteInCheckList(itemId: Int)
}