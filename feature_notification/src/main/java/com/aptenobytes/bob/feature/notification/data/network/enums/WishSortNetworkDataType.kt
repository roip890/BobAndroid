package com.aptenobytes.bob.feature.notification.data.network.enums

import com.aptenobytes.bob.feature.notification.domain.enums.notificationsort.NotificationSortType
import com.squareup.moshi.Json

enum class NotificationSortNetworkDataType(val value: String) {

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

fun NotificationSortNetworkDataType.toDomainEnum() = NotificationSortType.valueOf(this.name)

fun NotificationSortType.toNetworkEnum() = NotificationSortNetworkDataType.valueOf(this.name)
