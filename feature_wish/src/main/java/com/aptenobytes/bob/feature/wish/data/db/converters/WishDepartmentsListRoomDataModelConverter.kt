package com.aptenobytes.bob.feature.wish.data.db.converters

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type


internal class WishDepartmentsListRoomDataModelConverter() {

    @TypeConverter
    fun fromWishDepartmentRoomDataModel(wishDepartmentListRoomDataModel: List<String>?): String? {
        if (wishDepartmentListRoomDataModel != null) {
            val listMyData: Type = Types.newParameterizedType(List::class.java, String::class.java)
            val adapter: JsonAdapter<List<String>> = Moshi.Builder().build().adapter(listMyData)
            return adapter.toJson(wishDepartmentListRoomDataModel)
        }
        return null
    }

    @TypeConverter
    fun toWishDepartmentRoomDataModel(value: String?): List<String>? {
        if (value != null) {
            val listMyData: Type = Types.newParameterizedType(List::class.java, String::class.java)
            val adapter: JsonAdapter<List<String>> = Moshi.Builder().build().adapter(listMyData)
            return adapter.fromJson(value)
        }
        return null
    }

}
