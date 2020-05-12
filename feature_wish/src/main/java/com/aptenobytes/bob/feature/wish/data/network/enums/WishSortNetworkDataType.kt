package com.aptenobytes.bob.feature.wish.data.network.enums

import com.aptenobytes.bob.feature.wish.domain.enums.wishsort.WishSortType
import com.squareup.moshi.Json

enum class WishSortNetworkDataType(val value: String) {

    @field:Json(name = "none")
    NONE(value = "none"),
    @field:Json(name = "time")
    TIMESTAMP(value = "time"),
    @field:Json(name = "status")
    STATUS(value = "status"),
    @field:Json(name = "type")
    TYPE(value = "type"),
    @field:Json(name = "")
    UNKNOWN(value = "")
}

fun WishSortNetworkDataType.toDomainEnum() = WishSortType.valueOf(this.name)

fun WishSortType.toNetworkEnum() = WishSortNetworkDataType.valueOf(this.name)
