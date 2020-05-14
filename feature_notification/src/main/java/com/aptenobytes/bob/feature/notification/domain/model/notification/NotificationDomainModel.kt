package com.aptenobytes.bob.feature.notification.domain.model.notification

import java.util.*

data class NotificationDomainModel(

    val id: Long = 0,

    val guestId: Long? = null,
    val userId: Long? = null,
    val bookingId: Long? = null,

    val content: String? = null,
    val type: String? = null,
    val timeStamp: Date? = null,
    val iconUrl: String? = null,
    var value: String? = null,
    val isRead: Boolean? = false

)
