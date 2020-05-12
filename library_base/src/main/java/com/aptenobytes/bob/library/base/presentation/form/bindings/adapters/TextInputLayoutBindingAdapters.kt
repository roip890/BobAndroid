package com.aptenobytes.bob.library.base.presentation.form.bindings.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.aptenobytes.bob.library.base.presentation.form.bindings.classes.CornerRadius

// text input layout error
@BindingAdapter("layout_text_error")
fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    view.error = errorMessage
}

// text input layout background
@BindingAdapter("layout_box_background_mode")
fun setTextInputLayoutBoxBackgroundMode(view: TextInputLayout, backgroundMode: Int?) {
    backgroundMode?.let {
        view.boxBackgroundMode = backgroundMode
    }
}

@BindingAdapter("layout_box_background_color")
fun setTextInputLayoutBoxBackgroundColor(view: TextInputLayout, backgroundColor: Int?) {
    if (view.boxBackgroundMode == TextInputLayout.BOX_BACKGROUND_FILLED) {
        backgroundColor?.let {
            view.boxBackgroundColor = backgroundColor
        }
    }
}

@BindingAdapter("layout_box_stroke_color")
fun setTextInputLayoutBoxStrokeColor(view: TextInputLayout, strokeColor: Int?) {
    if (view.boxBackgroundMode == TextInputLayout.BOX_BACKGROUND_OUTLINE) {
        strokeColor?.let {
            view.boxStrokeColor = strokeColor
        }
    }
}

@BindingAdapter("layout_box_corner_radius")
fun setTextInputLayoutBoxCornerRadius(view: TextInputLayout, cornerRadius: CornerRadius?) {
    if (view.boxBackgroundMode == TextInputLayout.BOX_BACKGROUND_OUTLINE) {
        cornerRadius?.let {
            view.setBoxCornerRadii(
                cornerRadius.topStart.toFloat(),
                cornerRadius.topEnd.toFloat(),
                cornerRadius.bottomStart.toFloat(),
                cornerRadius.bottomEnd.toFloat()
            )
        }
    }
}

// text input layout hint color
@BindingAdapter("layout_hint_color")
fun setTextInputLayoutHintColor(view: TextInputLayout, hintColor: Int?) {
    hintColor?.let {
        view.hintTextColor = ColorStateList.valueOf(hintColor)
//        view.defaultHintTextColor = ColorStateList.valueOf(Color.CYAN)
        view.editText?.let {
            it.setHintTextColor(ColorStateList.valueOf(Color.CYAN))
        }
    }
}

// text input layout placeholder color
@BindingAdapter("layout_placeholder_color")
fun setTextInputLayoutPlaceholderColor(view: TextInputLayout, placeholderColor: Int?) {
    placeholderColor?.let {
//        view.defaultHintTextColor = ColorStateList.valueOf(placeholderColor)
    }
}

// text input layout error color
@BindingAdapter("layout_error_color")
fun setTextInputLayoutErrorColor(view: TextInputLayout, errorColor: Int?) {
    errorColor?.let {
        view.setErrorTextColor(ColorStateList.valueOf(errorColor))
    }
}
