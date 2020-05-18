package com.aptenobytes.bob.library.base.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.aptenobytes.bob.library.base.presentation.bottomsheetdialogfragment.InjectionBottomSheetDialogFragment
import timber.log.Timber

abstract class BaseContainerFragment : InjectionFragment() {

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
