package com.aptenobytes.bob.feature.notification.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aptenobytes.bob.feature.notification.data.db.converters.DateConverter
import com.aptenobytes.bob.feature.notification.data.db.model.NotificationRoomDataModel

@Database(entities = [(NotificationRoomDataModel::class)], version = 1, exportSchema = false)
@TypeConverters(
    DateConverter::class
)
abstract class NotificationDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile
        private var instance : NotificationDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: buildDatabase(
                        context
                    )
                        .also { instance = it }
            }

        private fun buildDatabase(context: Context) : NotificationDatabase {
            return Room.databaseBuilder(context.applicationContext, NotificationDatabase::class.java,
                NOTIFICATION_DATABASE
            ).build()
        }
    }
}

private const val NOTIFICATION_DATABASE = "notifications"
