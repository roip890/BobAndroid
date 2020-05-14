package com.aptenobytes.bob.feature.notification.data.prefs

import android.content.Context
import android.content.SharedPreferences

private const val NOTIFICATION_SHARED_PREFERENCES_NAME = "notifications"
private const val NOTIFICATION_SHARED_PREFERENCES_MODE = Context.MODE_PRIVATE

abstract class NotificationSharedPreferences: SharedPreferences {

    companion object {
        @Volatile
        private var instance : SharedPreferences? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: initNotificationSharedPreferences(
                    context
                )
                    .also { instance = it }
        }

        private fun initNotificationSharedPreferences(context: Context) : SharedPreferences {
            return context.getSharedPreferences(NOTIFICATION_SHARED_PREFERENCES_NAME, NOTIFICATION_SHARED_PREFERENCES_MODE)
        }
    }
}
