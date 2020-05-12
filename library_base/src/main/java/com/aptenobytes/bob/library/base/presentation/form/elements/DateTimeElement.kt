package com.aptenobytes.bob.library.base.presentation.form.elements

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateTimeElement(
    tag: Int = -1,
    initialValue: Date? = null,
    var dateFormat: String = "yyyy-MM-dd hh:mm:ss a"
    ): EditTextElement<Date>(
    tag = tag,
    initialValue = initialValue,
    viewValue = {value ->
        try { value?.let { SimpleDateFormat(dateFormat, Locale.ENGLISH).format(it) } ?: run { null } }
        catch (e: Exception) { null }
    },
    parseValue = {text ->
        try { text?.let { SimpleDateFormat(dateFormat, Locale.ENGLISH).parse(text) } ?: run { null } }
        catch (e: Exception) { null }
    }) {

    init {
        text.removeObserver(valueTextObserver)
    }

    override fun onCleared() {
        super.onCleared()
    }

}
