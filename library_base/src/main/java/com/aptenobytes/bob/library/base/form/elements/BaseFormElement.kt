package com.aptenobytes.bob.library.base.form.elements

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.aptenobytes.bob.library.base.extensions.ui.dpToPx
import com.aptenobytes.bob.library.base.form.bindings.classes.*
import com.aptenobytes.bob.library.base.form.builder.FormDsl

@FormDsl
open class BaseFormElement<T>(
    var tag: Int = -1,
    var key: MutableLiveData<String> = MutableLiveData<String>(),
    var value: MutableLiveData<T> = MutableLiveData<T>(),
    var initialValue: MutableLiveData<T> = MutableLiveData<T>(),
    var error: MutableLiveData<String> = MutableLiveData<String>(),
    var validate: ((T?) -> String?)? = null,
    var valid: MutableLiveData<Boolean> = MutableLiveData<Boolean>(true),
    var width: MutableLiveData<Int> = MutableLiveData<Int>(ViewGroup.LayoutParams.MATCH_PARENT),
    var height: MutableLiveData<Int> = MutableLiveData<Int>(ViewGroup.LayoutParams.WRAP_CONTENT),
    var margin: MutableLiveData<Margin> = MutableLiveData<Margin>(
        marginAll(8.dpToPx)
    ),
    var padding: MutableLiveData<Padding> = MutableLiveData<Padding>(
        paddingAll(0)
    ),
    var backgroundColor: MutableLiveData<Int> = MutableLiveData<Int>(),
    var background: MutableLiveData<Drawable> = MutableLiveData<Drawable>(),
    var horizontalGravity: MutableLiveData<HorizontalGravity> = MutableLiveData<HorizontalGravity>(
        horizontalGravity(center = true)
    ),
    var verticalGravity: MutableLiveData<VerticalGravity> = MutableLiveData<VerticalGravity>(
        verticalGravity(center = true)
    )
): ViewModel() {

    var valueValidationObserver: Observer<T?> = Observer { value: T? ->
        this.error.postValue(validate?.invoke(value))
    }
    var errorValidObserver: Observer<String?> = Observer { error: String? ->
        this.valid.postValue(error == null)
    }

    init {
        value.observeForever(valueValidationObserver)
        error.observeForever(errorValidObserver)
    }

    override fun onCleared() {
        super.onCleared()
        value.removeObserver(valueValidationObserver)
        error.removeObserver(errorValidObserver)
    }

    fun clear() {}
}
