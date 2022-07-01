package com.example.issue_tracker.ui.common

import android.widget.Button
import androidx.databinding.BindingAdapter
import com.example.issue_tracker.domain.model.Label

@BindingAdapter("label")
fun displayLabel(view: Button, label: Label) {
    view.text = label.title
    view.setBackgroundColor(("FF${label.backgroundColor.replace("#","")}".toLong(16).toInt()))
}