package com.example.issue_tracker.ui.common

import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.issue_tracker.domain.model.Label

@BindingAdapter("label")
fun displayLabel(view: Button, label: Label) {
    view.text = label.title
    view.setBackgroundColor(("FF${label.color.replace("#","")}".toLong(16).toInt()))
}