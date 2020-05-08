package com.aptenobytes.bob.library.base.form.elements


class MultiChoiceElement<T>(
    tag: Int = -1,
    viewItemValue: ((T?) -> String?)? = { value -> value?.toString() },
    initialValue: ArrayList<T>? = arrayListOf(),
    var items: ArrayList<T>? = arrayListOf()
): EditTextElement<ArrayList<T>>(
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
        viewValue = { list: ArrayList<T>? ->
            list?.joinToString(separator = ", ") {value ->
                this.viewItemValue.invoke(value).toString()
            }
        }
        text.removeObserver(valueTextObserver)
    }

    override fun onCleared() {
        super.onCleared()
    }

}
