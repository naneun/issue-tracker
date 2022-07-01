package com.example.issue_tracker.di

import com.example.issue_tracker.common.AccessToken
import com.example.issue_tracker.common.Constants
import com.example.issue_tracker.data.remote.issue.IssueApi
import com.example.issue_tracker.data.remote.label.LabelApi
import com.example.issue_tracker.data.remote.login.LoginApi
import com.example.issue_tracker.data.remote.milestone.MileStoneApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Client
    fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Singleton
    @Provides
    @ClientWithToken
    fun okHttpClientWithToken(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor {
            it.proceed(
                it.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer ${AccessToken.token}")
                    .build()
            )
        }
        .build()

    @Singleton
    @Provides
    @LoginRetrofit
    fun retrofit(@Client okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @IssueRetrofit
    fun orderRetrofit(@ClientWithToken okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .build()


    @Singleton
    @Provides
    fun loginService(@LoginRetrofit retrofit: Retrofit): LoginApi =
        retrofit.create(LoginApi::class.java)

    @Singleton
    @Provides
    fun issueService(@IssueRetrofit retrofit: Retrofit): IssueApi =
        retrofit.create(IssueApi::class.java)

    @Singleton
    @Provides
    fun labelService(@IssueRetrofit retrofit: Retrofit): LabelApi = retrofit.create(LabelApi::class.java)

    @Singleton
    @Provides
    fun milestoneService(@IssueRetrofit retrofit: Retrofit): MileStoneApi = retrofit.create(MileStoneApi::class.java)
}