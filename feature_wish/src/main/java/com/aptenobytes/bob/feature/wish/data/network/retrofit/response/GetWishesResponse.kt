package com.aptenobytes.bob.feature.wish.data.network.retrofit.response

import com.aptenobytes.bob.app.data.network.retrofit.response.AppStatusResponse
import com.aptenobytes.bob.feature.wish.data.network.model.WishNetworkDataModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class SetWishStatusResponse(

    @field:Json(name = "statusResponse")
    val statusResponse: AppStatusResponse?,

    @field:Json(name = "requests")
    val wishes: List<WishNetworkDataModel>?

)

@JsonClass(generateAdapter = true)
internal data class SetWishStatusResponseWrapper(
    @field:Json(name = "response")
    val response: GetWishesResponse?
)
