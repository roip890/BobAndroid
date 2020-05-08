package com.aptenobytes.bob.library.base.form.elements

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout
import com.aptenobytes.bob.library.base.form.bindings.classes.CornerRadius

open class EditTextElement<T>(
    tag: Int = -1,
    initialValue: T? = null,
    viewValue: ((T?) -> String?)? = null,
    parseValue: ((String?) -> T?)? = null,
    var startDrawable: MutableLiveData<Drawable> = MutableLiveData<Drawable>(),
    var endDrawable: MutableLiveData<Drawable> = MutableLiveData<Drawable>(),
    var hint: MutableLiveData<String> = MutableLiveData<String>(),
    var inputType: MutableLiveData<Int> = MutableLiveData<Int>(),
    var hintColor: MutableLiveData<Int> = MutableLiveData<Int>(),
    var placeholderColor: MutableLiveData<Int> = MutableLiveData<Int>(),
    var errorColor: MutableLiveData<Int> = MutableLiveData<Int>(),
    var boxBackgroundColor: MutableLiveData<Int> = MutableLiveData<Int>(),
    var boxStrokeColor: MutableLiveData<Int> = MutableLiveData<Int>(),
    var boxBackgroundMode: MutableLiveData<Int> = MutableLiveData<Int>(),
    var boxCornerRadius: MutableLiveData<CornerRadius> = MutableLiveData<CornerRadius>()
    ): TextViewElement<T>(tag = tag, initialValue = initialValue, viewValue = viewValue, parseValue = parseValue) {

    @TextInputLayout.BoxBackgroundMode
    fun boxMode(mode: Int): Int {
        if (mode == TextInputLayout.BOX_BACKGROUND_OUTLINE) {
            return TextInputLayout.BOX_BACKGROUND_OUTLINE
        } else if (mode == TextInputLayout.BOX_BACKGROUND_FILLED) {
            return TextInputLayout.BOX_BACKGROUND_FILLED
        } else {
            return TextInputLayout.BOX_BACKGROUND_NONE
        }
    }


}
