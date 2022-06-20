package com.example.issue_tracker.ui.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.size.Scale
import coil.transform.CircleCropTransformation


@BindingAdapter("imageUrl")
fun loadProfileImage(view: ImageView, url: String) {
    view.load(url) {
        scale(Scale.FIT)
        transformations(CircleCropTransformation())
    }
}