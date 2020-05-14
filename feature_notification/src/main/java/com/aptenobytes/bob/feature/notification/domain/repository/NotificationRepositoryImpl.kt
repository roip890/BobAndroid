package com.aptenobytes.bob.feature.notification.domain.repository

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.notification.domain.datasource.NotificationCacheDataSource
import com.aptenobytes.bob.feature.notification.domain.datasource.NotificationLocalDataSource
import com.aptenobytes.bob.feature.notification.domain.datasource.NotificationNetworkDataSource
import com.aptenobytes.bob.feature.notification.domain.datasource.NotificationSharedPreferencesDataSource
import com.aptenobytes.bob.feature.notification.domain.enums.notificationsort.NotificationSortType
import com.aptenobytes.bob.feature.notification.domain.model.notification.NotificationDomainModel
import java.util.*
import kotlin.collections.ArrayList

class NotificationRepositoryImpl(
    private val notificationNetworkDataSource: NotificationNetworkDataSource,
    private val notificationLocalDataSource: NotificationLocalDataSource,
    private val notificationCacheDataSource: NotificationCacheDataSource,
    private val notificationSharedPreferencesDataSource: NotificationSharedPreferencesDataSource
) : NotificationRepository {

    // notifications
    override suspend fun getNotifications(
        notificationId: Long?,

        userId: Long?,
        guestId: Long?,
        bookingId: Long?,

        sort: NotificationSortType?,

        index: Int?,
        limit: Int?
    ): List<NotificationDomainModel> {
        return notificationNetworkDataSource.getNotifications(
            notificationId = notificationId,
            userId = userId,
            guestId = guestId,
            bookingId = bookingId,
            sort = sort,
            index = index,
            limit = limit
        )
    }



}
