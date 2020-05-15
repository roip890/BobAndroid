package com.aptenobytes.bob.library.base.extensions.dialog

import android.content.Context
import android.content.DialogInterface
import androidx.annotation.ArrayRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import java.util.Calendar

fun Fragment.itemDialog(
    title: String,
    builder: AlertDialog.Builder.() -> Unit
): AlertDialog.Builder? = context?.let {
    AlertDialog.Builder(it).apply {
        setTitle(title)
        builder()
    }
}

fun Fragment.itemDialog(
    @StringRes titleRes: Int,
    builder: AlertDialog.Builder.() -> Unit
): AlertDialog.Builder? = context?.let {
    AlertDialog.Builder(it).apply {
        setTitle(titleRes)
        builder()
    }
}

fun AlertDialog.Builder.items(
    @ArrayRes resArray: Int,
    func: AlertDialog.Builder.(item: Int) -> Unit
) {
    setItems(resArray) { _, item -> func(item) }
}

fun Fragment.dialog(
    @StringRes titleRes: Int,
    message: String,
    builder: AlertDialog.Builder.() -> Unit
): AlertDialog.Builder? = context?.let {
    AlertDialog.Builder(it).apply {
        setTitle(titleRes)
        setMessage(message)
        builder()
    }
}

fun Context.dialog(
    @StringRes titleRes: Int,
    @StringRes message: Int? = null,
    builder: AlertDialog.Builder.() -> Unit
): AlertDialog.Builder =
    AlertDialog.Builder(this).apply {
        setTitle(titleRes)
        message?.let { setMessage(it) }
        builder()
    }

fun AlertDialog.Builder.positiveButton(
    @StringRes resTitle: Int,
    func: AlertDialog.Builder.() -> Unit
) {
    setPositiveButton(resTitle) { _: DialogInterface?, _: Int -> func() }
}

fun AlertDialog.Builder.negativeButton(
    @StringRes resTitle: Int,
    func: AlertDialog.Builder.() -> Unit
) {
    setNegativeButton(resTitle) { _: DialogInterface?, _: Int -> func() }
}

fun AlertDialog.Builder.view(@LayoutRes viewRes: Int) {
    setView(viewRes)
}
