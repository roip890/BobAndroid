package com.aptenobytes.bob.feature.notification.data.db.converters

import androidx.room.TypeConverter
import java.util.*

internal class DateConverter {

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.let {
            date.time
        } ?: run {
            null
        }
    }

    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let {
            Date(dateLong)
        } ?: run {
            null
        }
    }

}
