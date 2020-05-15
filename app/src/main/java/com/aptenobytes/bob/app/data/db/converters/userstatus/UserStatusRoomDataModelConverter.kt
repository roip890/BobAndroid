package com.aptenobytes.bob.app.data.db.converters.userstatus

import androidx.room.TypeConverter
import com.aptenobytes.bob.app.data.db.enums.UserStatusRoomDataType
import com.squareup.moshi.Moshi

internal class UserStatusRoomDataTypeConverter() {

    @TypeConverter
    fun fromUserStatusRoomDataType(userStatusRoomDataType: UserStatusRoomDataType?): String? {
        if (userStatusRoomDataType != null) {
            return Moshi.Builder().build().adapter(UserStatusRoomDataType::class.java)
                .toJson(userStatusRoomDataType)
        }
        return null
    }

    @TypeConverter
    fun toUserStatusRoomDataType(value: String?): UserStatusRoomDataType? {
        if (value != null) {
            return Moshi.Builder().build().adapter(UserStatusRoomDataType::class.java)
                .fromJson(value)
        }
        return null
    }

}
