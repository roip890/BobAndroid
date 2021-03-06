package com.aptenobytes.bob.feature.wish.data.network.retrofit.response

import com.aptenobytes.bob.app.data.network.retrofit.response.AppStatusResponse
import com.aptenobytes.bob.app.data.utils.moshi.SingleToArray
import com.aptenobytes.bob.feature.wish.data.network.model.WishNetworkDataModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class GetWishesResponse(

    @field:Json(name = "statusResponse")
    val statusResponse: AppStatusResponse?,

    @SingleToArray
    @field:Json(name = "requests")
    val wishes: List<WishNetworkDataModel>?

)

@JsonClass(generateAdapter = true)
internal data class GetWishesResponseWrapper(
    @field:Json(name = "response")
    val response: GetWishesResponse?
)
