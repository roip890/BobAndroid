package com.aptenobytes.bob.app.data.db.converters.userstatus

import androidx.room.TypeConverter
import com.aptenobytes.bob.app.data.db.enums.UserStatusRoomDataType
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type


class UserStatusListRoomDataModelConverter() {

    @TypeConverter
    fun fromUserDepartmentRoomDataModel(userDepartmentListRoomDataModel: List<UserStatusRoomDataType>?): String? {
        if (userDepartmentListRoomDataModel != null) {
            val listMyData: Type = Types.newParameterizedType(List::class.java, UserStatusRoomDataType::class.java)
            val adapter: JsonAdapter<List<UserStatusRoomDataType>> = Moshi.Builder().build().adapter(listMyData)
            return adapter.toJson(userDepartmentListRoomDataModel)
        }
        return null
    }

    @TypeConverter
    fun toUserDepartmentRoomDataModel(value: String?): List<UserStatusRoomDataType>? {
        if (value != null) {
            val listMyData: Type = Types.newParameterizedType(List::class.java, UserStatusRoomDataType::class.java)
            val adapter: JsonAdapter<List<UserStatusRoomDataType>> = Moshi.Builder().build().adapter(listMyData)
            return adapter.fromJson(value)
        }
        return null
    }

}
