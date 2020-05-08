package com.aptenobytes.bob.app.data.db.model.guest

import com.aptenobytes.bob.app.domain.model.guest.GuestDomainModel
import com.squareup.moshi.Json

data class GuestRoomDataModel(
    @field:Json(name = "id")
    val id: Long,

    @field:Json(name = "email")
    val email: String?,
    @field:Json(name = "firstName")
    val firstName: String?,
    @field:Json(name = "lastName")
    val lastName: String?,
    @field:Json(name = "password")
    val password: String?,
    @field:Json(name = "phone")
    val phone: String?,
    @field:Json(name = "imageUrl")
    val imageUrl: String?,
    @field:Json(name = "room")
    val room: Long?,
    @field:Json(name = "hotelId")
    val hotelId: Long?
)

fun GuestRoomDataModel.toDomainModel(): GuestDomainModel {
    return GuestDomainModel(
        id = this.id,

        email = this.email ?: "",
        firstName = this.firstName ?: "",
        lastName = this.lastName ?: "",
        password = this.password ?: "",
        phone = this.phone ?: "",
        imageUrl = this.imageUrl ?: "",
        room = this.room ?: 0,
        hotelId = this.hotelId ?: 0
    )
}
