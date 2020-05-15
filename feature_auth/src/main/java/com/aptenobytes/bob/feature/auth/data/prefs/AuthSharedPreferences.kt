package com.aptenobytes.bob.feature.auth.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.aptenobytes.bob.feature.auth.FEATURE_NAME

private const val AUTH_SHARED_PREFERENCES_NAME = FEATURE_NAME
private const val AUTH_SHARED_PREFERENCES_MODE = Context.MODE_PRIVATE

abstract class AuthSharedPreferences: SharedPreferences {

    companion object {
        @Volatile
        private var instance : SharedPreferences? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: initAuthSharedPreferences(
                    context
                )
                    .also { instance = it }
        }

        private fun initAuthSharedPreferences(context: Context) : SharedPreferences {
            return context.getSharedPreferences(
                AUTH_SHARED_PREFERENCES_NAME,
                AUTH_SHARED_PREFERENCES_MODE
            )
        }
    }
}
