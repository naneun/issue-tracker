package com.example.issue_tracker.data.dto


import com.example.issue_tracker.domain.model.Label
import com.google.gson.annotations.SerializedName

class LabelDto : ArrayList<LabelDtoItem>()

data class LabelDtoItem(
    @SerializedName("backgroundColor")
    val backgroundColor: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
)

fun LabelDtoItem.toLabel():Label = Label(id, title, description, backgroundColor)