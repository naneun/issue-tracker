package com.example.issue_tracker.ui.common

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("userName", "time")
fun displayLogMessage(view:TextView, userName:String, time:String){
    val timeDiff = getTimeDiff(time)
    view.text = "${userName}님이 ${timeDiff} 작성하셨습니다"
}