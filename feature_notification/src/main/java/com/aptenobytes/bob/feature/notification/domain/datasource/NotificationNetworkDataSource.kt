package com.aptenobytes.bob.feature.notification.domain.datasource

import com.aptenobytes.bob.feature.notification.domain.enums.notificationsort.NotificationSortType
import com.aptenobytes.bob.feature.notification.domain.model.notification.NotificationDomainModel

interface NotificationNetworkDataSource {

    suspend fun getNotifications(
        notificationId: Long? = null,

        userId: Long? = null,
        guestId: Long? = null,
        bookingId: Long? = null,

        sort: NotificationSortType? = null,

        index: Int? = 0,
        limit: Int? = 20
    ): List<NotificationDomainModel>

}
