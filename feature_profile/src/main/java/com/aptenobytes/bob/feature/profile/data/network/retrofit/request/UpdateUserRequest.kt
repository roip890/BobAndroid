package com.aptenobytes.bob.feature.profile.data.network.retrofit.request

import com.aptenobytes.bob.app.data.network.model.user.UserNetworkDataModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateUserRequest(

    @field:Json(name = "user")
    val user: UserNetworkDataModel

)

@JsonClass(generateAdapter = true)
data class UpdateUserRequestWrapper(
    @field:Json(name = "request")
    val request: UpdateUserRequest?
)
