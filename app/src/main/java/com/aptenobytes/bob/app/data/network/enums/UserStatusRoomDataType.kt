package com.aptenobytes.bob.app.data.network.enums

import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.squareup.moshi.Json

internal enum class UserStatusNetworkDataType {

    @field:Json(name = "none")
    NONE,
    @field:Json(name = "active")
    ACTIVE,
    @field:Json(name = "inactive")
    INACTIVE,
    @field:Json(name = "")
    UNKNOWN
}

internal fun UserStatusNetworkDataType.toDomainEnum() = UserStatusType.valueOf(this.name)
