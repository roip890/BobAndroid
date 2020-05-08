package com.aptenobytes.bob.library.base.form.bindings.classes

class CornerRadius(var topStart: Int = 0, var topEnd: Int = 0, var bottomStart: Int = 0, var bottomEnd: Int = 0) {}

fun cornersRadius(topStart: Int = 0, topEnd: Int = 0,bottomStart: Int = 0, bottomEnd: Int = 0): CornerRadius {
    return CornerRadius(
        topStart = topStart,
        topEnd = topEnd,
        bottomStart = bottomStart,
        bottomEnd = bottomEnd
    )
}

fun cornersRadiusVertical(top: Int = 0, bottom: Int = 0): CornerRadius {
    return CornerRadius(
        topStart = top,
        topEnd = top,
        bottomStart = bottom,
        bottomEnd = bottom
    )
}

fun cornersRadiusHorizontal(start: Int = 0, end: Int = 0): CornerRadius {
    return CornerRadius(
        topStart = start,
        topEnd = end,
        bottomStart = start,
        bottomEnd = end
    )
}

fun cornersRadiusAll(radius: Int = 0): CornerRadius {
    return CornerRadius(
        topStart = radius,
        topEnd = radius,
        bottomStart = radius,
        bottomEnd = radius
    )
}
