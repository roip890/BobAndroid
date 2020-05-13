package com.aptenobytes.bob.app.data.network.retrofit.request

import com.aptenobytes.bob.app.data.network.model.user.UserNetworkDataModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class EmailLoginRequest(

    @field:Json(name = "user")
    val user: UserNetworkDataModel?

)

@JsonClass(generateAdapter = true)
internal data class EmailLoginRequestWrapper(
    @field:Json(name = "request")
    val request: EmailLoginRequest?
)
