package com.aptenobytes.bob.feature.notification.data.network.model

import com.aptenobytes.bob.feature.notification.data.network.constants.NOTIFICATION_DATE_FORMAT
import com.aptenobytes.bob.feature.notification.domain.model.notification.NotificationDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class NotificationNetworkDataModel(
    @field:Json(name = "notificationId")
    val id: Long,

    @field:Json(name = "guestId")
    val guestId: Long?,
    @field:Json(name = "userId")
    val userId: Long?,
    @field:Json(name = "bookingId")
    val bookingId: Long?,

    @field:Json(name = "content")
    val content: String?,
    @field:Json(name = "type")
    val type: String?,
    @field:Json(name = "ts")
    val timeStamp: String?,
    @field:Json(name = "icon")
    val iconUrl: String?,
    @field:Json(name = "value")
    val value: String?,
    @field:Json(name = "isRead")
    val isRead: Boolean?
    )

fun NotificationNetworkDataModel.toDomainModel(): NotificationDomainModel {

    return NotificationDomainModel(
        id = this.id,

        guestId = this.guestId,
        userId = this.userId,
        bookingId = this.bookingId,

        content = this.content,
        type = this.type,
        timeStamp = this.timeStamp?.let { SimpleDateFormat(NOTIFICATION_DATE_FORMAT, Locale.ENGLISH).parse(this.timeStamp)} ?: run { null },
        iconUrl = this.iconUrl,
        value = this.value,
        isRead = this.isRead
    )
}

fun NotificationDomainModel.toNetworkModel(): NotificationNetworkDataModel {
    return NotificationNetworkDataModel(
        id = this.id,

        guestId = this.guestId,
        userId = this.userId,
        bookingId = this.bookingId,

        content = this.content,
        type = this.type,
        timeStamp = this.timeStamp?.let { SimpleDateFormat(NOTIFICATION_DATE_FORMAT, Locale.ENGLISH).format(this.timeStamp)} ?: run { null },
        iconUrl = this.iconUrl,
        value = this.value,
        isRead = this.isRead
    )
}
