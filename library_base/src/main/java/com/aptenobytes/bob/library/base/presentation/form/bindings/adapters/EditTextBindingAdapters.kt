package com.aptenobytes.bob.library.base.presentation.form.bindings.adapters

import android.text.InputType
import android.widget.EditText
import androidx.databinding.BindingAdapter

// read only edit text
@BindingAdapter("layout_text_read_only")
fun setEditTextReadyOnly(view: EditText, readOnly: Boolean) {
    if (readOnly) view.inputType = InputType.TYPE_NULL
}

// read only edit text
@BindingAdapter("layout_text_input_type")
fun setEditTextInputType(view: EditText, inputType: Int?) {
    inputType?.let {
        view.inputType = InputType.TYPE_NULL
    }
}
