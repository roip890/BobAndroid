package com.aptenobytes.bob.app.data.network.retrofit.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class GetDepartmentsResponse(

    @field:Json(name = "statusResponse")
    val statusResponse: AppStatusResponse?,

    @field:Json(name = "departments")
    val departments: List<String>?

)

@JsonClass(generateAdapter = true)
internal data class GetDepartmentsResponseWrapper(
    @field:Json(name = "response")
    val response: GetDepartmentsResponse?
)
