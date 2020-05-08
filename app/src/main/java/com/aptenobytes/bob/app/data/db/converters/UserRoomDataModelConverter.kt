package com.aptenobytes.bob.app.data.db.converters

import androidx.room.TypeConverter
import com.aptenobytes.bob.app.data.db.model.user.UserRoomDataModel
import com.squareup.moshi.Moshi


internal class UserRoomDataModelConverter() {

    @TypeConverter
    fun fromUserRoomDataModel(userRoomDataModel: UserRoomDataModel?): String? {
        if (userRoomDataModel != null) {
            return Moshi.Builder().build().adapter(UserRoomDataModel::class.java)
                .toJson(userRoomDataModel)
        }
        return null
    }

    @TypeConverter
    fun toUserRoomDataModel(value: String?): UserRoomDataModel? {
        if (value != null) {
            return Moshi.Builder().build().adapter(UserRoomDataModel::class.java)
                .fromJson(value)
        }
        return null
    }

}
