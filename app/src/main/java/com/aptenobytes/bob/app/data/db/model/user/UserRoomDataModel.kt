package com.aptenobytes.bob.app.data.db.model.user

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aptenobytes.bob.app.data.utils.moshi.SingleToArray
import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.squareup.moshi.Json
import java.util.*

@Entity(
    tableName = "user_table"
)
data class UserRoomDataModel(
    @ColumnInfo(name = "id") @PrimaryKey @NonNull
    @field:Json(name = "id")
    val id: Long,

    @ColumnInfo(name = "email")
    @field:Json(name = "email")
    val email: String?,
    @ColumnInfo(name = "username")
    @field:Json(name = "username")
    val username: String?,
    @ColumnInfo(name = "first_name")
    @field:Json(name = "firstName")
    val firstName: String?,
    @ColumnInfo(name = "last_name")
    @field:Json(name = "lastName")
    val lastName: String?,
    @ColumnInfo(name = "password")
    @field:Json(name = "password")
    val password: String?,
    @ColumnInfo(name = "phone")
    @field:Json(name = "phone")
    val phone: String?,
    @ColumnInfo(name = "image_url")
    @field:Json(name = "imageUrl")
    val imageUrl: String?,
    @ColumnInfo(name = "status")
    @field:Json(name = "status")
    val status: UserStatusType?,
    @ColumnInfo(name = "birthday")
    @field:Json(name = "birthday")
    val birthday: Date?,
    @ColumnInfo(name = "permissionLevel")
    @field:Json(name = "permissionLevel")
    val permissionLevel: String?,
    @SingleToArray
    @ColumnInfo(name = "departments")
    @field:Json(name = "departments")
    val departments: List<String>?,
    @ColumnInfo(name = "hotel_id")
    @field:Json(name = "hotelId")
    val hotelId: Long?
)

fun UserRoomDataModel.toDomainModel(): UserDomainModel {
    return UserDomainModel(
        id = this.id,

        email = this.email,
        username = this.username,
        firstName = this.firstName,
        lastName = this.lastName,
        password = this.password,
        phone = this.phone,
        imageUrl = this.imageUrl,
        status = this.status,
        birthday = this.birthday,
        permissionLevel = this.permissionLevel,
        departments = this.departments ?: listOf(),
        hotelId = this.hotelId
    )
}

fun UserDomainModel.toRoomModel(): UserRoomDataModel =
    UserRoomDataModel(
        id = this.id,

        email = this.email,
        username = this.username,
        firstName = this.firstName,
        lastName = this.lastName,
        password = this.password,
        phone = this.phone,
        imageUrl = this.imageUrl,
        status = this.status,
        birthday = this.birthday,
        permissionLevel = this.permissionLevel,
        departments = this.departments ?: listOf(),
        hotelId = this.hotelId
    )
