package com.aptenobytes.bob.library.base.form.elements

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import timber.log.Timber


@Suppress("UNCHECKED_CAST")
open class TextViewElement<T>(
    tag: Int = -1,
    initialValue: T? = null,
    viewValue: ((T?) -> String?)? = null,
    parseValue: ((String?) -> T?)? = null,
    var text: MutableLiveData<String> = MutableLiveData<String>(),
    var textSize: MutableLiveData<Int> = MutableLiveData<Int>(),
    var textColor: MutableLiveData<Int> = MutableLiveData<Int>()
    ): BaseFormElement<T>(tag = tag, initialValue = MutableLiveData<T>(initialValue)) {

    var valueTextObserver = Observer<String> {text: String? ->
        Timber.v("valueTextObserver")
        val valueParsed = parseValue(text)
        if (valueParsed != value.value) value.postValue(valueParsed)
    }

    var textValueObserver = Observer<T> {value: T? ->
        Timber.v("textValueObserver")
        val valueViewed = viewValue(value)
        if (valueViewed != text.value) text.postValue(valueViewed)
    }

    var initialValueObserver = Observer<T> {initialValue: T? ->
        value.postValue(initialValue)
        text.postValue(viewValue(initialValue))
    }

    lateinit var viewValue: (T?) -> String?
    lateinit var parseValue: (String?) -> T?

    init {
        parseValue?.let {
            this.parseValue = parseValue
        } ?: run {
            this.parseValue = {text ->
                when(this.value.value) {
                    is String, is String? -> text as T
                    else -> null
                }
            }
        }
        viewValue?.let {
            this.viewValue = viewValue
        } ?: run {
            if (viewValue == null) {
                this.viewValue = { value -> value?.toString() }
            }
        }
        text.observeForever(valueTextObserver)
        value.observeForever(textValueObserver)
        this.initialValue.observeForever(initialValueObserver)
    }

    override fun onCleared() {
        super.onCleared()
        value.removeObserver(textValueObserver)
        this.initialValue.removeObserver(initialValueObserver)
    }

}
