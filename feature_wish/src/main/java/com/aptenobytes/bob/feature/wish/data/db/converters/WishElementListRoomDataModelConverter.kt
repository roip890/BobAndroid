package com.aptenobytes.bob.feature.wish.data.db.converters

import androidx.room.TypeConverter
import com.aptenobytes.bob.feature.wish.data.db.model.WishElementRoomDataModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type


internal class WishElementListRoomDataModelConverter() {

    @TypeConverter
    fun fromWishElementRoomDataModel(wishElementListRoomDataModel: List<WishElementRoomDataModel>?): String? {
        if (wishElementListRoomDataModel != null) {
            val listMyData: Type = Types.newParameterizedType(List::class.java, WishElementRoomDataModel::class.java)
            val adapter: JsonAdapter<List<WishElementRoomDataModel>> = Moshi.Builder().build().adapter(listMyData)
            return adapter.toJson(wishElementListRoomDataModel)
        }
        return null
    }

    @TypeConverter
    fun toWishElementRoomDataModel(value: String?): List<WishElementRoomDataModel>? {
        if (value != null) {
            val listMyData: Type = Types.newParameterizedType(List::class.java, WishElementRoomDataModel::class.java)
            val adapter: JsonAdapter<List<WishElementRoomDataModel>> = Moshi.Builder().build().adapter(listMyData)
            return adapter.fromJson(value)
        }
        return null
    }

}
