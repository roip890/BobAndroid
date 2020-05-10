package com.aptenobytes.bob.library.base.presentation.bottomsheetdialogfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import timber.log.Timber

abstract class BaseContainerBottomSheetDialogFragment : InjectionBottomSheetDialogFragment() {

    @get:LayoutRes
    protected abstract val layoutResourceId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return this.view?.let {
            this.view
        } ?: run {
            inflater.inflate(layoutResourceId, null).also {
                Timber.v("onCreateView ${javaClass.simpleName}")
            }
        }
    }
}
