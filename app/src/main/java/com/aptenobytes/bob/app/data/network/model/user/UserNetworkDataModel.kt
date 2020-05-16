package com.aptenobytes.bob.app.data.network.model.user

import com.aptenobytes.bob.app.data.network.constants.USER_DATE_FORMAT
import com.aptenobytes.bob.app.data.network.enums.UserStatusNetworkDataType
import com.aptenobytes.bob.app.data.network.enums.toDomainEnum
import com.aptenobytes.bob.app.data.network.enums.toNetworkEnum
import com.aptenobytes.bob.app.data.utils.moshi.SingleToArray
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class UserNetworkDataModel(
    @field:Json(name = "id")
    val id: Long,

    @field:Json(name = "email")
    val email: String?,
    @field:Json(name = "username")
    val username: String?,
    @field:Json(name = "firstName")
    val firstName: String?,
    @field:Json(name = "lastName")
    val lastName: String?,
    @field:Json(name = "password")
    val password: String?,
    @field:Json(name = "phone")
    val phone: String?,
    @field:Json(name = "image")
    val imageUrl: String?,
    @field:Json(name = "status")
    val status: UserStatusNetworkDataType?,
    @field:Json(name = "birthday")
    val birthday: String?,
    @field:Json(name = "permissionLevel")
    val permissionLevel: String?,
    @SingleToArray
    @field:Json(name = "departments")
    val departments: List<String>?,
    @field:Json(name = "hotelId")
    val hotelId: Long?
)

fun UserNetworkDataModel.toDomainModel(): UserDomainModel {
    return UserDomainModel(
        id = this.id,

        email = this.email,
        username = this.username,
        firstName = this.firstName,
        lastName = this.lastName,
        password = this.password,
        phone = this.phone,
        imageUrl = this.imageUrl,
        status = this.status?.toDomainEnum(),
        birthday = this.birthday?.let { SimpleDateFormat(USER_DATE_FORMAT, Locale.ENGLISH).parse(this.birthday)} ?: run { null },
        permissionLevel = this.permissionLevel,
        departments = this.departments ?: listOf(),
        hotelId = this.hotelId ?: 0
    )
}

fun UserDomainModel.toNetworkModel(): UserNetworkDataModel {
    return UserNetworkDataModel(
        id = this.id,

        email = this.email,
        username = this.username,
        firstName = this.firstName,
        lastName = this.lastName,
        password = this.password,
        phone = this.phone,
        imageUrl = this.imageUrl,
        status = this.status?.toNetworkEnum(),
        birthday = this.birthday?.let { SimpleDateFormat(USER_DATE_FORMAT, Locale.ENGLISH).format(this.birthday)} ?: run { null },
        permissionLevel = this.permissionLevel,
        departments = this.departments ?: listOf(),
        hotelId = this.hotelId ?: 0
    )
}
