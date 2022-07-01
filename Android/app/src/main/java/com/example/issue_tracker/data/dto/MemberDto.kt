package com.example.issue_tracker.data.dto


import com.example.issue_tracker.common.Constants
import com.example.issue_tracker.domain.model.User
import com.google.gson.annotations.SerializedName

class MemberDto : ArrayList<MemberDtoItem>()

data class MemberDtoItem(
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileImage")
    val profileImage: String?
)

fun MemberDtoItem.toUser():User = User(memberId,name,profileImage?:Constants.DEFAULT_PROFILE_IMAGE)