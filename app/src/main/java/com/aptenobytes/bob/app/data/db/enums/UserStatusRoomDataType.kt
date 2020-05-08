package com.aptenobytes.bob.app.data.db.enums

import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.squareup.moshi.Json

enum class UserStatusRoomDataType {

    @field:Json(name = "none")
    NONE,
    @field:Json(name = "active")
    ACTIVE,
    @field:Json(name = "inactive")
    INACTIVE,
    @field:Json(name = "")
    UNKNOWN
}

fun UserStatusRoomDataType.toDomainEnum() = UserStatusType.valueOf(this.name)
