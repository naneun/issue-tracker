package com.example.issue_tracker.ui.common

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.issue_tracker.R

@BindingAdapter("totalCount", "finishCount")
fun calculateProgress(view: TextView, totalCount: Int, finishCount: Int) {
    val progress = if (finishCount != 0) 100 / (totalCount / finishCount)
    else 0
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

@BindingAdapter("writeIssueCount")
fun setWriteIssueCount(view: TextView, writeIssueCount: Int) {
    view.text = view.context.getString(R.string.my_account_write_issue_count_value, writeIssueCount)
}

@BindingAdapter("assignIssueCount")
fun setAssignIssueCount(view: TextView, assignIssueCount: Int) {
    view.text = view.context.getString(R.string.my_account_assign_issue_count_value, assignIssueCount)
}

@BindingAdapter("writeCommentCount")
fun setCommentCount(view: TextView, writeCommentCount: Int) {
    view.text = view.context.getString(R.string.my_account_write_issue_count_value, writeCommentCount)
}
