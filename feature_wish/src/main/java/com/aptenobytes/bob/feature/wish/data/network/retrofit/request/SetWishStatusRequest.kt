package com.aptenobytes.bob.feature.wish.data.network.retrofit.request

import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class SetWishStatusRequest(

    @field:Json(name = "statusResponse")
    val wish: WishDomainModel

)

@JsonClass(generateAdapter = true)
internal data class SetWishStatusRequestWrapper(
    @field:Json(name = "request")
    val response: SetWishStatusRequest?
)
