package com.aptenobytes.bob.feature.wish.domain.model.user

data class UserDomainModel(
    val id: Long = 0,

    val email: String? = "",
    val username: String? = "",
    val firstName:String? = "",
    val lastName: String? = "",
    val password: String? = "",
    val phone: String? = "",
    val status: String? = "",
    val birthday: String? = "",
    val imageUrl: String? = "",
    val permissionLevel: String? = "",
    val departments: List<String>? = listOf(),
    val hotelId: Long? = 0
)
