package com.aptenobytes.bob.library.base.form.elements


class SingleChoiceElement<T>(
    tag: Int = -1,
    viewItemValue: ((T?) -> String?)? = { value -> value?.toString() },
    initialValue: T? = null,
    items: ArrayList<T>? = arrayListOf()
): ListElement<T>(
    tag = tag,
    viewItemValue = viewItemValue,
    initialValue = initialValue,
    items = items
) {}
