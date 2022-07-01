package com.example.issue_tracker.data.dto


import com.example.issue_tracker.common.Constants
import com.example.issue_tracker.domain.model.CommentItem
import com.example.issue_tracker.domain.model.User
import com.example.issue_tracker.ui.common.LoginUser
import com.google.gson.annotations.SerializedName

data class CommentDto(
    @SerializedName("content")
    val content: List<Content>,
    @SerializedName("empty")
    val empty: Boolean,
    @SerializedName("first")
    val first: Boolean,
    @SerializedName("last")
    val last: Boolean,
    @SerializedName("number")
    val number: Int,
    @SerializedName("numberOfElements")
    val numberOfElements: Int,
    @SerializedName("pageable")
    val pageable: Pageable,
    @SerializedName("size")
    val size: Int,
    @SerializedName("sort")
    val sort: Sort,
    @SerializedName("totalElements")
    val totalElements: Int,
    @SerializedName("totalPages")
    val totalPages: Int
)

data class Content(
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("emoji")
    val emoji: Emoji,
    @SerializedName("writer")
    val writer: Writer
)

data class Emoji(
    @SerializedName("emojiId")
    val emojiId: Int,
    @SerializedName("unicode")
    val unicode: String
)


data class Pageable(
    @SerializedName("page")
    val page: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("sort")
    val sort: List<String>
)


data class Sort(
    @SerializedName("empty")
    val empty: Boolean,
    @SerializedName("sorted")
    val sorted: Boolean,
    @SerializedName("unsorted")
    val unsorted: Boolean
)

data class Writer(
    @SerializedName("writerId")
    val writerId: Int,
    @SerializedName("writerName")
    val writerName: String
)

fun Content.toComment():CommentItem.Comment{
    return CommentItem.Comment(User(writer.writerId, writer.writerName, Constants.DEFAULT_PROFILE_IMAGE), createdAt, LoginUser.id==writer.writerName, content )
}