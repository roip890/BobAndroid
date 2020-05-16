package com.aptenobytes.bob.feature.profile.data.network.retrofit.response

import com.aptenobytes.bob.app.data.network.model.user.UserNetworkDataModel
import com.aptenobytes.bob.app.data.network.retrofit.response.AppStatusResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateUserResponse(

    @field:Json(name = "statusResponse")
    val statusResponse: AppStatusResponse?,

    @field:Json(name = "user")
    val user: UserNetworkDataModel?

)

@JsonClass(generateAdapter = true)
data class UpdateUserResponseWrapper(
    @field:Json(name = "response")
    val response: UpdateUserResponse?
)
