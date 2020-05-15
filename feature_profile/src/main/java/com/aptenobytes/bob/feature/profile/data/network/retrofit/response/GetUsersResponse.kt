package com.aptenobytes.bob.feature.profile.data.network.retrofit.response

import com.aptenobytes.bob.app.data.network.model.user.UserNetworkDataModel
import com.aptenobytes.bob.app.data.network.retrofit.response.AppStatusResponse
import com.aptenobytes.bob.app.data.utils.moshi.SingleToArray
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class GetUsersResponse(

    @field:Json(name = "statusResponse")
    val statusResponse: AppStatusResponse?,

    @SingleToArray
    @field:Json(name = "users")
    val users: List<UserNetworkDataModel>?

)

@JsonClass(generateAdapter = true)
data class GetUsersResponseWrapper(
    @field:Json(name = "response")
    val response: GetUsersResponse?
)
