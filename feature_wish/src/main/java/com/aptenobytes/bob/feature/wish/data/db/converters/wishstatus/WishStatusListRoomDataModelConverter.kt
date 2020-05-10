package com.aptenobytes.bob.feature.wish.data.db.converters.wishstatus

import androidx.room.TypeConverter
import com.aptenobytes.bob.feature.wish.data.db.enums.WishStatusRoomDataType
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type


internal class WishStatusListRoomDataModelConverter() {

    @TypeConverter
    fun fromWishDepartmentRoomDataModel(wishDepartmentListRoomDataModel: List<WishStatusRoomDataType>?): String? {
        if (wishDepartmentListRoomDataModel != null) {
            val listMyData: Type = Types.newParameterizedType(List::class.java, WishStatusRoomDataType::class.java)
            val adapter: JsonAdapter<List<WishStatusRoomDataType>> = Moshi.Builder().build().adapter(listMyData)
            return adapter.toJson(wishDepartmentListRoomDataModel)
        }
        return null
    }

    @TypeConverter
    fun toWishDepartmentRoomDataModel(value: String?): List<WishStatusRoomDataType>? {
        if (value != null) {
            val listMyData: Type = Types.newParameterizedType(List::class.java, WishStatusRoomDataType::class.java)
            val adapter: JsonAdapter<List<WishStatusRoomDataType>> = Moshi.Builder().build().adapter(listMyData)
            return adapter.fromJson(value)
        }
        return null
    }

}
