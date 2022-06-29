package com.example.issue_tracker.domain.model

data class Issue(
    val id: Int,
    val milestone: String,
    val title: String,
    val description: String,
    val label: Label,
    var editable:Boolean = false,

)
