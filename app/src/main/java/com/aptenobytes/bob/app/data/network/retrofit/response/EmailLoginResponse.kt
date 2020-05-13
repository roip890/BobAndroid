package com.aptenobytes.bob.app.data.network.retrofit.response

import com.aptenobytes.bob.app.data.network.model.user.UserNetworkDataModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class EmailLoginResponse(

    @field:Json(name = "statusResponse")
    val statusResponse: AppStatusResponse?,

    @field:Json(name = "user")
    val user: UserNetworkDataModel?

)

@JsonClass(generateAdapter = true)
internal data class EmailLoginResponseWrapper(
    @field:Json(name = "response")
    val response: EmailLoginResponse?
)
