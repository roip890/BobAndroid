package com.aptenobytes.bob.feature.wish.data.network.retrofit.request

import com.aptenobytes.bob.feature.wish.data.network.model.WishNetworkDataModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class SetWishStatusRequest(

    @field:Json(name = "wish")
    val wish: WishNetworkDataModel

)

@JsonClass(generateAdapter = true)
internal data class SetWishStatusRequestWrapper(
    @field:Json(name = "request")
    val request: SetWishStatusRequest?
)
