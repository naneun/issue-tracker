package com.example.issue_tracker.data.dto


import com.google.gson.annotations.SerializedName

data class OAuthInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("jwtAccessToken")
    val jwtAccessToken: String,
    @SerializedName("jwtRefreshToken")
    val jwtRefreshToken: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileImage")
    val profileImage: String,
    @SerializedName("resourceServer")
    val resourceServer: String,
    @SerializedName("serialNumber")
    val serialNumber: String
)