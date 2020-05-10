package com.aptenobytes.bob.app.data.network.retrofit.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AppStatusResponse(

    @field:Json(name = "msg")
    val message: String?,

    @field:Json(name = "code")
    val code: Int?,

    @field:Json(name = "status")
    val status: String?

)
