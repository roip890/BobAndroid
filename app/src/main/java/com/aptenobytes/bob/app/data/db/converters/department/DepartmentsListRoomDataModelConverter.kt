package com.aptenobytes.bob.app.data.db.converters.department

import androidx.room.TypeConverter
import com.aptenobytes.bob.app.data.db.model.department.DepartmentRoomDataModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type


class DepartmentsListRoomDataModelConverter() {

    @TypeConverter
    fun fromWishDepartmentRoomDataModel(wishDepartmentListRoomDataModel: List<DepartmentRoomDataModel>?): String? {
        if (wishDepartmentListRoomDataModel != null) {
            val listMyData: Type = Types.newParameterizedType(List::class.java, DepartmentRoomDataModel::class.java)
            val adapter: JsonAdapter<List<DepartmentRoomDataModel>> = Moshi.Builder().build().adapter(listMyData)
            return adapter.toJson(wishDepartmentListRoomDataModel)
        }
        return null
    }

    @TypeConverter
    fun toWishDepartmentRoomDataModel(value: String?): List<DepartmentRoomDataModel>? {
        if (value != null) {
            val listMyData: Type = Types.newParameterizedType(List::class.java, DepartmentRoomDataModel::class.java)
            val adapter: JsonAdapter<List<DepartmentRoomDataModel>> = Moshi.Builder().build().adapter(listMyData)
            return adapter.fromJson(value)
        }
        return null
    }

}
