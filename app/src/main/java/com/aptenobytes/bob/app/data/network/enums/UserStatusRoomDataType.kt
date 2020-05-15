package com.aptenobytes.bob.app.data.network.enums

import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.squareup.moshi.Json

enum class UserStatusNetworkDataType(val value: String)  {

    @field:Json(name = "none")
    NONE(value = "none"),
    @field:Json(name = "active")
    ACTIVE(value = "active"),
    @field:Json(name = "inactive")
    INACTIVE(value = "inactive"),
    @field:Json(name = "")
    UNKNOWN(value = "")
}

fun UserStatusNetworkDataType.toDomainEnum() = UserStatusType.valueOf(this.name)

fun UserStatusType.toNetworkEnum() = UserStatusNetworkDataType.valueOf(this.name)
