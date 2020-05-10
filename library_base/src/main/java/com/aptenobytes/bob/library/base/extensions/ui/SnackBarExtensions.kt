package com.aptenobytes.bob.library.base.extensions.ui

import android.view.View
import com.google.android.material.snackbar.Snackbar

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, init: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.init()
    snack.show()
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}
