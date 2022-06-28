package com.example.issue_tracker.domain.repository

interface LoginRepository {
    suspend fun getAccessToken():String
}