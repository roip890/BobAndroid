package com.aptenobytes.bob.library.base.presentation.form.elements

import android.view.View

open class ButtonElement(
    tag: Int = -1,
    var onClickHandler: (View) -> Unit = {}
): TextViewElement<Unit>(tag = tag) {
    init {}
}
