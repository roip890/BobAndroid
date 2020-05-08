package com.aptenobytes.bob.feature.wish.data.db.model

import androidx.annotation.NonNull
import androidx.room.*
import com.aptenobytes.bob.app.data.moshi.SingleToArray
import com.aptenobytes.bob.feature.wish.data.db.enums.WishStatusRoomDataType
import com.aptenobytes.bob.feature.wish.data.db.enums.toDomainEnum
import com.aptenobytes.bob.feature.wish.data.db.model.guest.GuestRoomDataModel
import com.aptenobytes.bob.feature.wish.data.db.model.guest.toDomainModel
import com.aptenobytes.bob.feature.wish.data.db.model.user.UserRoomDataModel
import com.aptenobytes.bob.feature.wish.data.db.model.user.toDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.squareup.moshi.Json

@Entity(
    tableName = "wish_table"
)
data class WishRoomDataModel(
    @ColumnInfo(name = "wish_id") @PrimaryKey @NonNull
    @field:Json(name = "wishId")
    val id: Long,

    @ColumnInfo(name = "guest")
    @field:Json(name = "guest")
    val guest: GuestRoomDataModel?,
    @ColumnInfo(name = "user_assigned")
    @field:Json(name = "userAssigned")
    val user: UserRoomDataModel?,
    @ColumnInfo(name = "booking_id")
    @field:Json(name = "bookingId")
    val bookingId: Long?,

    @ColumnInfo(name = "wish_details")
    @field:Json(name = "wishDetails")
    val details: String?,
    @ColumnInfo(name = "wish_type")
    @field:Json(name = "wishType")
    val type: String?,
    @ColumnInfo(name = "insert_ts")
    @field:Json(name = "insertTs")
    val timeStamp: String?,
    @ColumnInfo(name = "wish_icon_url")
    @field:Json(name = "wishIconUrl")
    val iconUrl: String?,
    @ColumnInfo(name = "wish_status")
    @field:Json(name = "wishStatus")
    val status: WishStatusRoomDataType?,
    @ColumnInfo(name = "is_favorite")
    @field:Json(name = "isFavorite")
    val isFavorite: Boolean?,

    @SingleToArray
    @ColumnInfo(name = "departments")
    @field:Json(name = "departments")
    val departments: List<String>?,
    @SingleToArray
    @ColumnInfo(name = "elements")
    @field:Json(name = "elements")
    val elements: List<WishElementRoomDataModel>?
)

internal fun WishRoomDataModel.toDomainModel(): WishDomainModel {
    val elements = this.elements
        ?.map { it.toDomainModel() }
    val guest = this.guest
        ?.toDomainModel()
    val user = this.user
        ?.toDomainModel()
    val status = this.status
        ?.toDomainEnum()

    return WishDomainModel(
        id = this.id,

        guest = guest,
        user = user,
        bookingId = this.bookingId ?: 0,

        details = this.details ?: "",
        type = this.type ?: "",
        timeStamp = this.timeStamp ?: "",
        iconUrl = this.iconUrl ?: "",
        status = status,
        isFavorite = this.isFavorite ?: false,

        departments = this.departments ?: listOf(),
        elements = elements ?: listOf()
    )
}

internal fun WishDomainModel.toRoomModel(): WishRoomDataModel {

    return WishRoomDataModel(
        id = this.id,

        guest = null,
        user = null,
        bookingId = this.bookingId ?: 0,

        details = this.details ?: "",
        type = this.type ?: "",
        timeStamp = this.timeStamp ?: "",
        iconUrl = this.iconUrl ?: "",
        status = WishStatusRoomDataType.DONE,
        isFavorite = this.isFavorite ?: false,

        departments = this.departments ?: listOf(),
        elements = listOf()
    )
}

