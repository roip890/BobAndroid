package com.aptenobytes.bob.feature.wish.data.network.retrofit.response

import com.aptenobytes.bob.feature.wish.data.network.model.WishNetworkDataModel
import com.squareup.moshi.Json

internal data class GetWishesResponse(
    @field:Json(name = "requests")
    val wishes: List<WishNetworkDataModel>?
)

internal data class GetWishesResponseWrapper(
    @field:Json(name = "response")
    val response: GetWishesResponse?
)
