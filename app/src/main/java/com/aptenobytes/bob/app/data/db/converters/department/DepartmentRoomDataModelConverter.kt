package com.aptenobytes.bob.app.data.db.converters.department

import androidx.room.TypeConverter
import com.aptenobytes.bob.app.data.db.model.department.DepartmentRoomDataModel
import com.squareup.moshi.Moshi


class DepartmentRoomDataModelConverter() {

    @TypeConverter
    fun fromWishElementRoomDataModel(wishElementRoomDataModel: DepartmentRoomDataModel?): String? {
        if (wishElementRoomDataModel != null) {
            return Moshi.Builder().build().adapter(DepartmentRoomDataModel::class.java)
                .toJson(wishElementRoomDataModel)
        }
        return null
    }

    @TypeConverter
    fun toWishElementRoomDataModel(value: String?): DepartmentRoomDataModel? {
        if (value != null) {
            return Moshi.Builder().build().adapter(DepartmentRoomDataModel::class.java)
                .fromJson(value)
        }
        return null
    }

}
