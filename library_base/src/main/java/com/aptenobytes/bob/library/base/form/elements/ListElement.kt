package com.aptenobytes.bob.library.base.form.elements


open class ListElement<T>(
    tag: Int = -1,
    viewItemValue: ((T?) -> String?)? = { value -> value?.toString() },
    initialValue: T? = null,
    var items: ArrayList<T>? = arrayListOf()
): EditTextElement<T>(
    tag = tag,
    initialValue = initialValue
) {

    lateinit var viewItemValue: (T?) -> String?
    init {
        viewItemValue?.let {
            this.viewItemValue = viewItemValue
        } ?: run {
            this.viewItemValue = { value -> value?.toString() }
        }
        text.removeObserver(valueTextObserver)
    }

    override fun onCleared() {
        super.onCleared()
    }

}
