package com.aptenobytes.bob.library.base.presentation.form.elements

class PhoneElement(
    tag: Int = -1,
    initialValue: String? = null
    ): EditTextElement<String>(
    tag = tag,
    initialValue = initialValue
    ) {

    init {
        text.removeObserver(valueTextObserver)
        text.removeObserver(textValueObserver)
        text.removeObserver(initialValueObserver)
    }

}
