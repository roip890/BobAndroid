package com.aptenobytes.bob.feature.wish.presentation.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.data.network.constants.WISH_DATE_FORMAT
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import java.text.SimpleDateFormat
import java.util.*

fun wishStatusTypeToString(status: WishStatusType): String {
    return when(status) {
        WishStatusType.WAITING -> "Wating"
        WishStatusType.PENDING -> "Pending"
        WishStatusType.IN_PROGRESS -> "In Progress"
        WishStatusType.DONE -> "Done"
        else -> ""
    }
}

fun wishStatusTypeToIcon(status: WishStatusType, context: Context): Drawable {
    return GradientDrawable().apply {
        this.shape = GradientDrawable.OVAL
        this.color = ColorStateList.valueOf(wishStatusTypeToColor(status, context = context))
    }
}

fun wishStatusTypeToColor(status: WishStatusType, context: Context): Int {
    return when(status) {
        WishStatusType.WAITING -> ContextCompat.getColor(context, com.aptenobytes.bob.R.color.faded_red)
        WishStatusType.PENDING -> ContextCompat.getColor(context, com.aptenobytes.bob.R.color.faded_orange)
        WishStatusType.IN_PROGRESS -> ContextCompat.getColor(context, com.aptenobytes.bob.R.color.faded_yellow)
        WishStatusType.DONE -> ContextCompat.getColor(context, com.aptenobytes.bob.R.color.faded_green)
        else -> ContextCompat.getColor(context, com.aptenobytes.bob.R.color.faded_blue)
    }
}

fun wishDepartmentsToString(departments: List<DepartmentDomainModel>): String {
    return departments.joinToString(separator = ", ") { it.name }
}

fun wishTimeStampToString(timeStamp: Date): String {
    return SimpleDateFormat(WISH_DATE_FORMAT, Locale.ENGLISH).format(timeStamp)
}
