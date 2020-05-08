package com.aptenobytes.bob.library.base.livedata

import androidx.lifecycle.MutableLiveData

class PreSetMutableLiveData<T>: MutableLiveData<T> {

    private var preSet: ((T) -> Unit)? = null

    constructor(preSet: ((T) -> Unit)? = null): super() {
        this.preSet = preSet
    }

    constructor(value: T, preSet: ((T) -> Unit)? = null): super(value) {
        this.preSet = preSet
    }

    override fun postValue(value: T) {
        if (preSet != null) preSet?.let { it(value) }
        super.postValue(value)
    }

    override fun setValue(value: T) {
        if (preSet != null) preSet?.let { it(value) }
        super.setValue(value)
    }

}
