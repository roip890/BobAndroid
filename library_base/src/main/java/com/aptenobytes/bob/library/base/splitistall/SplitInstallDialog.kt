package com.aptenobytes.bob.library.base.splitistall

import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.aptenobytes.bob.library.base.R

/**
 * Data class to represent a Confirmation Dialog in Split Install flow.
 */
data class ConfirmationDialog(
    @StringRes var title: Int = R.string.split_confirmation_install_title,
    @StringRes var message: Int = R.string.split_confirmation_install_description
)

/**
 * Data class to represent a Loading Dialog in Split Install flow.
 */
data class LoadingDialog(
    @StringRes var title: Int = R.string.split_downloading_title,
    @StringRes var message: Int = R.string.split_downloading_description,
    @LayoutRes var layout: Int = R.layout.dialog_split_install
)
