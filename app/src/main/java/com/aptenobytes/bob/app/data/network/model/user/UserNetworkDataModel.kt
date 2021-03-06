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
    val email: String? = null,
    @field:Json(name = "username")
    val username: String? = null,
    @field:Json(name = "firstName")
    val firstName: String? = null,
    @field:Json(name = "lastName")
    val lastName: String? = null,
    @field:Json(name = "password")
    val password: String? = null,
    @field:Json(name = "phone")
    val phone: String? = null,
    @field:Json(name = "image")
    val imageUrl: String? = null,
    @field:Json(name = "status")
    val status: UserStatusNetworkDataType? = null,
    @field:Json(name = "bday")
    val birthday: String? = null,
    @field:Json(name = "permissionLevel")
    val permissionLevel: String? = null,
    @SingleToArray
    @field:Json(name = "departments")
    val departments: List<String>? = null,
    @field:Json(name = "hotelId")
    val hotelId: Long? = null
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
        departments = this.departments,
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
        departments = this.departments,
        hotelId = this.hotelId ?: 0
    )
}
