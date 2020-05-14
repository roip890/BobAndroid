package com.aptenobytes.bob.feature.notification.data.db.datasource

import com.aptenobytes.bob.feature.notification.data.db.NotificationDao
import com.aptenobytes.bob.feature.notification.data.db.model.toDomainModel
import com.aptenobytes.bob.feature.notification.data.db.model.toRoomModel
import com.aptenobytes.bob.feature.notification.domain.datasource.NotificationLocalDataSource
import com.aptenobytes.bob.feature.notification.domain.model.notification.NotificationDomainModel

class NotificationRoomDataSourceImpl(private val dao: NotificationDao) : NotificationLocalDataSource {

    override suspend fun getNotifications(
        notificationId: Long?,

        userId: Long?,
        guestId: Long?,
        bookingId: Long?,

        sort: String?,
        index: Int?,
        limit: Int?
    ): List<NotificationDomainModel> {
        return dao.getNotificationList()
            .map { it.toDomainModel() }
    }

    override suspend fun setNotifications(
        notifications: List<NotificationDomainModel>
    ) {
        return dao.insertAll(notifications.map { it.toRoomModel() })
    }

}
