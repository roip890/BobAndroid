package com.aptenobytes.bob.feature.notification.data.db.model

import androidx.annotation.NonNull
import androidx.room.*
import com.aptenobytes.bob.feature.notification.domain.model.notification.NotificationDomainModel
import com.squareup.moshi.Json
import java.util.*

@Entity(
    tableName = "notification_table"
)
data class NotificationRoomDataModel(
    @ColumnInfo(name = "notification_id") @PrimaryKey @NonNull
    @field:Json(name = "notificationId")
    val id: Long,

    @ColumnInfo(name = "guest_id")
    @field:Json(name = "guestId")
    val guestId: Long?,
    @ColumnInfo(name = "user_id")
    @field:Json(name = "userId")
    val userId: Long?,
    @ColumnInfo(name = "booking_id")
    @field:Json(name = "bookingId")
    val bookingId: Long?,

    @ColumnInfo(name = "content")
    @field:Json(name = "content")
    val content: String?,
    @ColumnInfo(name = "type")
    @field:Json(name = "type")
    val type: String?,
    @ColumnInfo(name = "timeStamp")
    @field:Json(name = "ts")
    val timeStamp: Date?,
    @ColumnInfo(name = "icon_url")
    @field:Json(name = "icon")
    val iconUrl: String?,
    @ColumnInfo(name = "value")
    @field:Json(name = "value")
    val value: String?,
    @ColumnInfo(name = "is_read")
    @field:Json(name = "isRead")
    val isRead: Boolean?
    ) {
}

internal fun NotificationRoomDataModel.toDomainModel(): NotificationDomainModel {
    return NotificationDomainModel(
        id = this.id,

        guestId = this.guestId,
        userId = this.userId,
        bookingId = this.bookingId,

        content = this.content,
        type = this.type,
        timeStamp = this.timeStamp,
        iconUrl = this.iconUrl,
        value = this.value,
        isRead = this.isRead
    )
}

internal fun NotificationDomainModel.toRoomModel(): NotificationRoomDataModel {

    return NotificationRoomDataModel(
        id = this.id,

        guestId = this.guestId,
        userId = this.userId,
        bookingId = this.bookingId,

        content = this.content,
        type = this.type,
        timeStamp = this.timeStamp,
        iconUrl = this.iconUrl,
        value = this.value,
        isRead = this.isRead
    )
}

