package com.example.issue_tracker.di

import com.example.issue_tracker.data.remote.issue.IssueApi
import com.example.issue_tracker.data.remote.issue.IssueDataSource
import com.example.issue_tracker.data.remote.issue.IssueRemoteDataSource
import com.example.issue_tracker.data.remote.login.LoginApi
import com.example.issue_tracker.data.remote.login.LoginDataSource
import com.example.issue_tracker.data.remote.login.LoginRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideLoginDataSource(api: LoginApi): LoginDataSource {
        return LoginRemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun provideIssueDataSource(api: IssueApi): IssueDataSource {
        return IssueRemoteDataSource(api)
    }
}