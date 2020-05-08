package com.aptenobytes.bob.feature.wish.data.db.enums

import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.squareup.moshi.Json

enum class WishStatusRoomDataType {

    @field:Json(name = "none")
    NONE,
    @field:Json(name = "waiting")
    WAITING,
    @field:Json(name = "pending")
    PENDING,
    @field:Json(name = "in_progress")
    IN_PROGRESS,
    @field:Json(name = "done")
    DONE,
    @field:Json(name = "")
    UNKNOWN
}

fun WishStatusRoomDataType.toDomainEnum() = WishStatusType.valueOf(this.name)
