package com.aptenobytes.bob.feature.wish.domain.model.wishelement

data class WishElementDomainModel(

    val id: Long = 0,

    val key: String? = "",
    val value: String? = "",
    val type: String? = "",
    val order: Long? = 0

)
