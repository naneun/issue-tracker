package com.example.issue_tracker.di

import com.example.issue_tracker.data.remote.issue.IssueDataSource
import com.example.issue_tracker.data.remote.label.LabelDataSource
import com.example.issue_tracker.data.remote.login.LoginDataSource
import com.example.issue_tracker.data.repository.HomeRepositoryImpl
import com.example.issue_tracker.data.repository.IssueRepositoryImpl
import com.example.issue_tracker.data.repository.LoginRepositoryImpl
import com.example.issue_tracker.domain.repository.HomeRepository
import com.example.issue_tracker.domain.repository.IssueRepository
import com.example.issue_tracker.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideLoginRepository(loginDataSource: LoginDataSource): LoginRepository {
        return LoginRepositoryImpl(loginDataSource)
    }

    @Singleton
    @Provides
    fun provideIssueRepository(dataSource: IssueDataSource): IssueRepository {
        return IssueRepositoryImpl(dataSource)
    }

    @Singleton
    @Provides
    fun provideHomeRepository(labelDataSource: LabelDataSource): HomeRepository {
        return HomeRepositoryImpl(labelDataSource)
    }
}