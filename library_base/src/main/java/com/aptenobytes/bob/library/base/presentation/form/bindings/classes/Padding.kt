package com.aptenobytes.bob.library.base.presentation.form.bindings.classes

class Padding(var start: Int = 0, var end: Int = 0,var left: Int = 0, var right: Int = 0, var top: Int = 0, var bottom: Int = 0) {}

fun padding(start: Int = 0, end: Int = 0, left: Int = 0, right: Int = 0, top: Int = 0, bottom: Int = 0): Padding {
    return Padding(
        start = start,
        end = end,
        left = left,
        right = right,
        top = top,
        bottom = bottom
    )
}

fun paddingAll(padding: Int = 0): Padding {
    return Padding(
        start = padding,
        end = padding,
        left = padding,
        right = padding,
        top = padding,
        bottom = padding
    )
}

fun paddingVertical(padding: Int = 0): Padding {
    return Padding(
        start = padding,
        end = padding
    )
}

fun paddingHorizontal(padding: Int = 0): Padding {
    return Padding(
        top = padding,
        bottom = padding
    )
}
