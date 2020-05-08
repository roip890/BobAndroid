package com.aptenobytes.bob.app.data.network.retrofit.response

import com.squareup.moshi.Json

internal data class GetDepartmentsResponse(
    @field:Json(name = "departments")
    val departments: List<String>?
)

internal data class GetDepartmentsResponseWrapper(
    @field:Json(name = "response")
    val response: GetDepartmentsResponse?
)
