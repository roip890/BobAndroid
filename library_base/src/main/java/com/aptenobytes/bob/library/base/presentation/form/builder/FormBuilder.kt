package com.aptenobytes.bob.library.base.presentation.form.builder

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.aptenobytes.bob.library.base.presentation.form.adapter.formAdapter
import com.aptenobytes.bob.library.base.presentation.form.elements.*
import java.util.*
import kotlin.collections.ArrayList

@DslMarker
annotation class FormDsl

fun form(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    recyclerView: RecyclerView,
    init: Form.() -> Unit): Form {
    val form = Form(
            context = context,
            recyclerView = recyclerView,
            adapter = formAdapter(
                context = context,
                lifecycleOwner = lifecycleOwner
            )
    )
    form.init()
    form.adapter.notifyDataSetChanged()
    return form
}

fun <T> Form.text(
    tag: Int = -1,
    initialValue: T? = null,
    viewValue: ((T?) -> String?)? = null,
    parseValue: ((String?) -> T?)? = null,
    init: EditTextElement<T>.() -> Unit = {}
): EditTextElement<T> {
    return addFormElement(EditTextElement<T>(tag = tag, initialValue = initialValue, viewValue = viewValue, parseValue = parseValue).apply(init))
}

fun <T> Form.label(
    tag: Int = -1,
    initialValue: T? = null,
    viewValue: ((T?) -> String?)? = null,
    parseValue: ((String?) -> T?)? = null,
    init: TextViewElement<T>.() -> Unit = {}
): TextViewElement<T> {
    return addFormElement(TextViewElement<T>(tag = tag, initialValue = initialValue, viewValue = viewValue, parseValue = parseValue).apply(init))
}

fun Form.datetime(
    tag: Int = -1,
    initialValue: Date? = null,
    init: DateTimeElement.() -> Unit = {}
): DateTimeElement {
    return addFormElement(DateTimeElement(tag = tag, initialValue = initialValue).apply(init))
}

fun <T> Form.multiChoice(
    tag: Int = -1,
    items: ArrayList<T> = arrayListOf(),
    initialValue: ArrayList<T> = arrayListOf(),
    viewItemValue: ((T?) -> String?)? = null,
    init: MultiChoiceElement<T>.() -> Unit = {}
): MultiChoiceElement<T> {
    return addFormElement(MultiChoiceElement<T>(tag = tag, items = items, initialValue = initialValue, viewItemValue = viewItemValue).apply(init))
}

fun <T> Form.singleChoice(
    tag: Int = -1,
    items: ArrayList<T> = arrayListOf(),
    initialValue: T? = null,
    viewItemValue: ((T?) -> String?)? = null,
    init: SingleChoiceElement<T>.() -> Unit = {}
): SingleChoiceElement<T> {
    return addFormElement(SingleChoiceElement<T>(tag = tag, items = items, initialValue = initialValue, viewItemValue = viewItemValue).apply(init))
}

fun <T> Form.list(
    tag: Int = -1,
    items: ArrayList<T> = arrayListOf(),
    initialValue: T? = null,
    viewItemValue: ((T?) -> String?)? = null,
    init: ListElement<T>.() -> Unit = {}
): ListElement<T> {
    return addFormElement(ListElement<T>(tag = tag, items = items, initialValue = initialValue, viewItemValue = viewItemValue).apply(init))
}

fun Form.button(
    tag: Int = -1,
    init: ButtonElement.() -> Unit = {}
): ButtonElement {
    return addFormElement(ButtonElement(tag = tag).apply(init))
}
