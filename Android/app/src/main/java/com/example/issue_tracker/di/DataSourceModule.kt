package com.example.issue_tracker.di

import com.example.issue_tracker.data.remote.issue.IssueApi
import com.example.issue_tracker.data.remote.issue.IssueDataSource
import com.example.issue_tracker.data.remote.issue.IssueRemoteDataSource
import com.example.issue_tracker.data.remote.label.LabelApi
import com.example.issue_tracker.data.remote.label.LabelDataSource
import com.example.issue_tracker.data.remote.label.LabelRemoteDataSource
import com.example.issue_tracker.data.remote.login.LoginApi
import com.example.issue_tracker.data.remote.login.LoginDataSource
import com.example.issue_tracker.data.remote.login.LoginRemoteDataSource
import com.example.issue_tracker.data.remote.user.UserApi
import com.example.issue_tracker.data.remote.user.UserDataSource
import com.example.issue_tracker.data.remote.user.UserRemoteDataSource
import com.example.issue_tracker.domain.model.User
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

    @Provides
    @Singleton
    fun provideLabelDataSource(api: LabelApi): LabelDataSource {
        return LabelRemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun provideUserDataSource(api:UserApi) : UserDataSource{
        return UserRemoteDataSource(api)
    }
}