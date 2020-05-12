package com.aptenobytes.bob.feature.wish.data.network.enums

import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.squareup.moshi.Json

enum class WishStatusNetworkDataType(val value: String) {

    @field:Json(name = "none")
    NONE(value = "none"),
    @field:Json(name = "waiting")
    WAITING(value = "waiting"),
    @field:Json(name = "pending")
    PENDING(value = "pending"),
    @field:Json(name = "in_progress")
    IN_PROGRESS(value = "in_progress"),
    @field:Json(name = "done")
    DONE(value = "done"),
    @field:Json(name = "")
    UNKNOWN(value = "")
}

fun WishStatusNetworkDataType.toDomainEnum() = WishStatusType.valueOf(this.name)

fun WishStatusType.toNetworkEnum() = WishStatusNetworkDataType.valueOf(this.name)
