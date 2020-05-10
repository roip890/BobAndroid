package com.aptenobytes.bob.feature.wish.domain.enums.wishstatus

import com.squareup.moshi.JsonClass

enum class WishStatusType {
    NONE,
    WAITING,
    PENDING,
    IN_PROGRESS,
    DONE,
    UNKNOWN
}
