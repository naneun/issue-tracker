package com.example.issue_tracker.ui.common

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.issue_tracker.R

@BindingAdapter("userName", "time")
fun displayLogMessage(view: TextView, userName: String, time: String) {
    val timeDiff = getTimeDiff(time)
    view.text = view.context.getString(R.string.issue_detail_issue_log, userName, timeDiff)
}