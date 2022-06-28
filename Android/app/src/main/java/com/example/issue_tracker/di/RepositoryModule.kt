package com.example.issue_tracker.di

import com.example.issue_tracker.data.remote.login.LoginDataSource
import com.example.issue_tracker.data.repository.LoginRepositoryImpl
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
    fun provideLoginRepository(loginDataSource: LoginDataSource):LoginRepository{
        return LoginRepositoryImpl(loginDataSource)
    }
}