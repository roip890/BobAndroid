package com.aptenobytes.bob.library.base.form.bindings.adapters

import android.content.res.ColorStateList
import android.widget.TextView
import androidx.databinding.BindingAdapter

// text view text color
@BindingAdapter("layout_text_color")
fun setTextViewTextColor(view: TextView, textColor: Int?) {
    textColor?.let {
        view.setTextColor(ColorStateList.valueOf(textColor))
    }
}

// text view text size
@BindingAdapter("layout_text_size")
fun setTextViewTextSize(view: TextView, textSize: Int?) {
    textSize?.let {
        view.textSize = textSize.toFloat()
    }
}
