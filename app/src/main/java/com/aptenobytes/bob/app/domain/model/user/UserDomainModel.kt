package com.aptenobytes.bob.app.domain.model.user

import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDomainModel(
    val id: Long = 0,

    val hotelId: Long? = null,
    val email: String? = null,
    val username: String? = null,
    val firstName:String? = null,
    val lastName: String? = null,
    val password: String? = null,
    val phone: String? = null,
    val status: UserStatusType? = null,
    val birthday: String? = null,
    val imageUrl: String? = null,
    val permissionLevel: String? = null,
    val departments: List<String>? = listOf()
)
