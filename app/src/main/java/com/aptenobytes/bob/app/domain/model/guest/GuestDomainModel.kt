package com.aptenobytes.bob.app.domain.model.guest

data class GuestDomainModel(
    val id: Long = 0,

    val email: String? = "",
    val firstName:String? = "",
    val lastName: String? = "",
    val password: String? = "",
    val phone: String? = "",
    val imageUrl: String? = "",
    val room: Long? = 0,
    val hotelId: Long? = 0
)
