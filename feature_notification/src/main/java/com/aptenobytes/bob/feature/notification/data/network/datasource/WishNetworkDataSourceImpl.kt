package com.aptenobytes.bob.feature.notification.data.network.datasource

import com.aptenobytes.bob.feature.notification.data.network.constants.NOTIFICATION_DATE_FORMAT
import com.aptenobytes.bob.feature.notification.data.network.enums.toNetworkEnum
import com.aptenobytes.bob.feature.notification.data.network.model.toDomainModel
import com.aptenobytes.bob.feature.notification.data.network.retrofit.service.NotificationRetrofitService
import com.aptenobytes.bob.feature.notification.domain.datasource.NotificationNetworkDataSource
import com.aptenobytes.bob.feature.notification.domain.enums.notificationsort.NotificationSortType
import java.text.SimpleDateFormat
import java.util.*

internal class NotificationNetworkDataSourceImpl(
    private val notificationRetrofitService: NotificationRetrofitService
) : NotificationNetworkDataSource {

    // notifications
    override suspend fun getNotifications(
        notificationId: Long?,

        userId: Long?,
        guestId: Long?,
        bookingId: Long?,

        sort: NotificationSortType?,
        index: Int?,
        limit: Int?

    ) =
        notificationRetrofitService.getNotificationsAsync(
            notificationId = notificationId,
            userId = userId,
            guestId = guestId,
            bookingId = bookingId,

            sort = sort?.toNetworkEnum()?.value,
            index = index,
            limit = limit
        )
            ?.response
            ?.notifications
            ?.map { it.toDomainModel() }
            ?: listOf()

}

fun Date?.toNetworkFormat(): String? {
    return this?.let {
        SimpleDateFormat(NOTIFICATION_DATE_FORMAT, Locale.ENGLISH).format(this)
    }
}
