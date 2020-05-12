package com.aptenobytes.bob.library.base.databinding

import android.content.res.ColorStateList
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

// text input layout background
@BindingAdapter("fab_background_color")
fun setFloatingActionButtonBackgroundColor(view: FloatingActionButton, backgroundColor: Int?) {
    backgroundColor?.let {
        view.backgroundTintList = ColorStateList.valueOf(backgroundColor)
    }
}
