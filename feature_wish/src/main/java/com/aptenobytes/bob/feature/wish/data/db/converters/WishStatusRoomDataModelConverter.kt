package com.aptenobytes.bob.feature.wish.data.db.converters

import androidx.room.TypeConverter
import com.aptenobytes.bob.feature.wish.data.db.enums.WishStatusRoomDataType
import com.squareup.moshi.Moshi

internal class WishStatusRoomDataTypeConverter() {

    @TypeConverter
    fun fromWishStatusRoomDataType(wishStatusRoomDataType: WishStatusRoomDataType?): String? {
        if (wishStatusRoomDataType != null) {
            return Moshi.Builder().build().adapter(WishStatusRoomDataType::class.java)
                .toJson(wishStatusRoomDataType)
        }
        return null
    }

    @TypeConverter
    fun toWishStatusRoomDataType(value: String?): WishStatusRoomDataType? {
        if (value != null) {
            return Moshi.Builder().build().adapter(WishStatusRoomDataType::class.java)
                .fromJson(value)
        }
        return null
    }

}
