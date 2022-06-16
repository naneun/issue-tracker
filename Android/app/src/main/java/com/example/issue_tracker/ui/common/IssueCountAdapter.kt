package com.example.issue_tracker.ui.common

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.issue_tracker.R

@BindingAdapter("totalCount", "finishCount")
fun calculateProgress(view: TextView, totalCount: Int, finishCount: Int) {
    val progress = (100 / (totalCount / finishCount))
    view.text = view.context.getString(R.string.milestone_item_progress, progress)
}

@BindingAdapter("opeIssueCount")
fun setOpenIssueCount(view: TextView, openIssueCount: Int) {
    view.text = view.context.getString(R.string.milestone_item_open_issue_count, openIssueCount)
}

@BindingAdapter("closedIssueCount")
fun setClosedIssueCount(view: TextView, closedIssueCount: Int) {
    view.text = view.context.getString(R.string.milestone_item_close_issue_count, closedIssueCount)
}