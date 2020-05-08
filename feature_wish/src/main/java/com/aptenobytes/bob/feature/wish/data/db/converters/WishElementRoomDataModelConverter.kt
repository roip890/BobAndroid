package com.aptenobytes.bob.feature.wish.data.db.converters

import androidx.room.TypeConverter
import com.aptenobytes.bob.feature.wish.data.db.model.WishElementRoomDataModel
import com.squareup.moshi.Moshi


internal class WishElementRoomDataModelConverter() {

    @TypeConverter
    fun fromWishElementRoomDataModel(wishElementRoomDataModel: WishElementRoomDataModel?): String? {
        if (wishElementRoomDataModel != null) {
            return Moshi.Builder().build().adapter(WishElementRoomDataModel::class.java)
                .toJson(wishElementRoomDataModel)
        }
        return null
    }

    @TypeConverter
    fun toWishElementRoomDataModel(value: String?): WishElementRoomDataModel? {
        if (value != null) {
            return Moshi.Builder().build().adapter(WishElementRoomDataModel::class.java)
                .fromJson(value)
        }
        return null
    }

}
