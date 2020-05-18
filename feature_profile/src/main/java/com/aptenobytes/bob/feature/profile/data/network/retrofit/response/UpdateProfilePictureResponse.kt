package com.aptenobytes.bob.feature.profile.data.network.retrofit.response

import com.aptenobytes.bob.app.data.network.model.user.UserNetworkDataModel
import com.aptenobytes.bob.app.data.network.retrofit.response.AppStatusResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateProfilePictureResponse(

    @field:Json(name = "statusResponse")
    val statusResponse: AppStatusResponse?,

    @field:Json(name = "url")
    val url: String?

)

@JsonClass(generateAdapter = true)
data class UpdateProfilePictureResponseWrapper(
    @field:Json(name = "response")
    val response: UpdateProfilePictureResponse?
)
