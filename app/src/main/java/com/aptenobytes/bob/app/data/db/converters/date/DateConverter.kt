package com.aptenobytes.bob.app.data.db.converters.date

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

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
