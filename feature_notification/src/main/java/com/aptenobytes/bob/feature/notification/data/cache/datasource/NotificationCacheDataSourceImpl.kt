package com.aptenobytes.bob.feature.notification.data.cache.datasource

import com.aptenobytes.bob.feature.notification.domain.datasource.NotificationCacheDataSource
import com.aptenobytes.bob.feature.notification.domain.model.notification.NotificationDomainModel

class NotificationCacheDataSourceImpl() : NotificationCacheDataSource {

    private val notifications = mutableListOf<NotificationDomainModel>()

    // notifications
    override suspend fun getNotifications(
        notificationId: Long?,

        userId: Long?,
        guestId: Long?,
        bookingId: Long?,


        sort: String?,

        index: Int?,
        limit: Int?
    ): List<NotificationDomainModel> {
        return notifications
    }

    override suspend fun setNotifications(
        notifications: List<NotificationDomainModel>
    ) {
        if (this.notifications.isEmpty()) this.notifications.addAll(notifications)
    }

}
