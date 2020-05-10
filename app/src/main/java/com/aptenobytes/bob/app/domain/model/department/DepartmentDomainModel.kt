package com.aptenobytes.bob.app.domain.model.department

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DepartmentDomainModel(
    val name: String = ""
)
