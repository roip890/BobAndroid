package com.aptenobytes.bob.library.base.form.bindings.classes

class VerticalGravity(var center: Boolean = false, var top: Boolean = false, var bottom: Boolean = false) {}

class HorizontalGravity(var center: Boolean = false, var start: Boolean = false, var end: Boolean = false,
                        var left: Boolean = false, var right: Boolean = false) {}

fun verticalGravity(center: Boolean = false, top: Boolean = false, bottom: Boolean = false): VerticalGravity {
    return VerticalGravity(
        center = center,
        top = top,
        bottom = bottom
    )
}

fun horizontalGravity(center: Boolean = false, start: Boolean = false, end: Boolean = false,
                      left: Boolean = false, right: Boolean = false): HorizontalGravity {
    return HorizontalGravity(
        center = center,
        start = start,
        end = end,
        left = left,
        right = right
    )
}
