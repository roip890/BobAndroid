package com.aptenobytes.bob.app.data.db.converters.guest

import androidx.room.TypeConverter
import com.aptenobytes.bob.app.data.db.model.guest.GuestRoomDataModel
import com.squareup.moshi.Moshi


class GuestRoomDataModelConverter() {

    @TypeConverter
    fun fromGuestRoomDataModel(guestRoomDataModel: GuestRoomDataModel?): String? {
        if (guestRoomDataModel != null) {
            return Moshi.Builder().build().adapter(GuestRoomDataModel::class.java)
                .toJson(guestRoomDataModel)
        }
        return null
    }

    @TypeConverter
    fun toGuestRoomDataModel(value: String?): GuestRoomDataModel? {
        if (value != null) {
            return Moshi.Builder().build().adapter(GuestRoomDataModel::class.java)
                .fromJson(value)
        }
        return null
    }

}
