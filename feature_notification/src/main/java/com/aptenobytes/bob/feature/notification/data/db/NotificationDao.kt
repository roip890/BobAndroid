package com.aptenobytes.bob.feature.notification.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aptenobytes.bob.feature.notification.data.db.model.NotificationRoomDataModel

@Dao
interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: NotificationRoomDataModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notification: List<NotificationRoomDataModel>)

    @Query("DELETE FROM notification_table")
    suspend fun clearNotificationList()

    @Query("SELECT * FROM notification_table ORDER BY notification_id ASC")
    suspend fun getNotificationList(): List<@JvmSuppressWildcards NotificationRoomDataModel>
}
