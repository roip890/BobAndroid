package com.aptenobytes.bob.feature.wish.data.network.model.user

import com.aptenobytes.bob.app.data.utils.moshi.SingleToArray
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.squareup.moshi.Json

internal data class UserNetworkDataModel(
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
    @field:Json(name = "imageUrl")
    val imageUrl: String?,
    @field:Json(name = "status")
    val status: String?,
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

internal fun UserNetworkDataModel.toDomainModel(): UserDomainModel {
    return UserDomainModel(
        id = this.id,

        email = this.email ?: "",
        username = this.username ?: "",
        firstName = this.firstName ?: "",
        lastName = this.lastName ?: "",
        password = this.password ?: "",
        phone = this.phone ?: "",
        imageUrl = this.imageUrl ?: "",
        status = this.status ?: "",
        birthday = this.birthday ?: "",
        permissionLevel = this.permissionLevel ?: "",
        departments = this.departments ?: listOf(),
        hotelId = this.hotelId ?: 0
    )
}

//val id: Long = 0,
//
//val email: String = "",
//val username: String = "",
//val firstName:String = "",
//val lastName: String = "",
//val password: String = "",
//val phone: String = "",
//val status: String = "",
//val birthday: String = "",
//val imageUrl: String = "",
//val permissionLevel: String = "",
//val departments: List<String> = listOf(),
//val hotelId: Long = 0
