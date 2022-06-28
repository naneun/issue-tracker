package com.example.issue_tracker.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoginRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IssueRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ClientWithToken

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Client
