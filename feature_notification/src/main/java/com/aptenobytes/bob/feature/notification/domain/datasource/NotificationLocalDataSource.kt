package com.aptenobytes.bob.feature.notification.domain.datasource

import com.aptenobytes.bob.feature.notification.domain.model.notification.NotificationDomainModel

interface NotificationLocalDataSource {

    suspend fun getNotifications(
        notificationId: Long? = null,

        userId: Long? = null,
        guestId: Long? = null,
        bookingId: Long? = null,

        sort: String? = null,

        index: Int? = 0,
        limit: Int? = 20
    ): List<NotificationDomainModel>

    suspend fun setNotifications(
        notifications: List<NotificationDomainModel>
    )


}
