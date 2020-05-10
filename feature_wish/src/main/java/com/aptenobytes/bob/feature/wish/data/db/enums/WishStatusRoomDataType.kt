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

fun WishStatusType.toRoomModel(): WishStatusRoomDataType =
    when(this) {
        WishStatusType.NONE -> WishStatusRoomDataType.NONE
        WishStatusType.WAITING -> WishStatusRoomDataType.WAITING
        WishStatusType.PENDING -> WishStatusRoomDataType.PENDING
        WishStatusType.IN_PROGRESS -> WishStatusRoomDataType.IN_PROGRESS
        WishStatusType.DONE -> WishStatusRoomDataType.DONE
        else -> WishStatusRoomDataType.UNKNOWN
    }
