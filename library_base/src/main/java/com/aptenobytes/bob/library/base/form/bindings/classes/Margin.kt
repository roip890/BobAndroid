package com.aptenobytes.bob.library.base.form.bindings.classes

class Margin(var start: Int = 0, var end: Int = 0,var left: Int = 0, var right: Int = 0, var top: Int = 0, var bottom: Int = 0) {}

fun margin(start: Int = 0, end: Int = 0, left: Int = 0, right: Int = 0, top: Int = 0, bottom: Int = 0): Margin {
    return Margin(
        start = start,
        end = end,
        left = left,
        right = right,
        top = top,
        bottom = bottom
    )
}

fun margin(vertical: Int = 0, horizontal: Int = 0): Margin {
    return Margin(
        start = horizontal,
        end = horizontal,
        top = vertical,
        bottom = vertical
    )
}

fun marginAll(margin: Int = 0): Margin {
    return Margin(
        start = margin,
        end = margin,
        left = margin,
        right = margin,
        top = margin,
        bottom = margin
    )
}

fun marginVertical(margin: Int = 0): Margin {
    return Margin(
        start = margin,
        end = margin
    )
}

fun marginHorizontal(margin: Int = 0): Margin {
    return Margin(
        top = margin,
        bottom = margin
    )
}
