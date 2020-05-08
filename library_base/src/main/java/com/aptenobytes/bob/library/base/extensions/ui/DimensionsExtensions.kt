package com.aptenobytes.bob.library.base.extensions.ui

import android.content.res.Resources

val Int?.pxToDp: Int
    get() = ((this?.toFloat() ?: 0f) / Resources.getSystem().displayMetrics.density).toInt()

val Int?.dpToPx: Int
    get() = ((this?.toFloat() ?: 0f) * Resources.getSystem().displayMetrics.density).toInt()

val Int?.pxToSp: Int
    get() = ((this?.toFloat() ?: 0f) / Resources.getSystem().displayMetrics.scaledDensity).toInt()

val Int?.spToPx: Int
    get() = ((this?.toFloat() ?: 0f) * Resources.getSystem().displayMetrics.scaledDensity).toInt()
