package com.aptenobytes.bob.app.data.prefs

import android.content.Context
import android.content.SharedPreferences

private const val APP_SHARED_PREFERENCES_NAME = "app"
private const val APP_SHARED_PREFERENCES_MODE = Context.MODE_PRIVATE

abstract class AppSharedPreferences: SharedPreferences {

    companion object {
        @Volatile
        private var instance : SharedPreferences? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: initAppSharedPreferences(
                    context
                )
                    .also { instance = it }
        }

        private fun initAppSharedPreferences(context: Context) : SharedPreferences {
            return context.getSharedPreferences(APP_SHARED_PREFERENCES_NAME, APP_SHARED_PREFERENCES_MODE)
        }
    }
}
