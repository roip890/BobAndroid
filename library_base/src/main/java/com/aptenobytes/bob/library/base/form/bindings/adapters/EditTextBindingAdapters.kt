package com.aptenobytes.bob.library.base.form.bindings.adapters

import android.text.InputType
import android.widget.EditText
import androidx.databinding.BindingAdapter

// read only edit text
@BindingAdapter("layout_text_read_only")
fun setReadyOnly(view: EditText, readOnly: Boolean) {
    if (readOnly) view.inputType = InputType.TYPE_NULL
}
