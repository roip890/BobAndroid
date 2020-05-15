package com.aptenobytes.bob.feature.profile.presentation.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType

fun userStatusTypeToString(status: UserStatusType): String {
    return when(status) {
        UserStatusType.ACTIVE -> "Active"
        UserStatusType.INACTIVE -> "Inactive"
        else -> ""
    }
}

fun userStatusTypeToIcon(status: UserStatusType, context: Context): Drawable {
    return GradientDrawable().apply {
        this.shape = GradientDrawable.OVAL
        this.color = ColorStateList.valueOf(userStatusTypeToColor(status, context = context))
    }
}

fun userStatusTypeToColor(status: UserStatusType, context: Context): Int {
    return when(status) {
        UserStatusType.ACTIVE -> ContextCompat.getColor(context, com.aptenobytes.bob.R.color.faded_green)
        UserStatusType.INACTIVE -> ContextCompat.getColor(context, com.aptenobytes.bob.R.color.faded_red)
        else -> ContextCompat.getColor(context, com.aptenobytes.bob.R.color.faded_orange)
    }
}
