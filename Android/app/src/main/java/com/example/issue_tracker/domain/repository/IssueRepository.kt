package com.example.issue_tracker.domain.repository

import com.example.issue_tracker.domain.model.Issue

interface IssueRepository {

    suspend fun getIssueList(): List<Issue>

}