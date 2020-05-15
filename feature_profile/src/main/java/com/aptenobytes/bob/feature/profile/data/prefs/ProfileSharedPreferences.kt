package com.aptenobytes.bob.feature.profile.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.aptenobytes.bob.feature.profile.FEATURE_NAME

private const val PROFILE_SHARED_PREFERENCES_NAME = FEATURE_NAME
private const val PROFILE_SHARED_PREFERENCES_MODE = Context.MODE_PRIVATE

abstract class ProfileSharedPreferences: SharedPreferences {

    companion object {
        @Volatile
        private var instance : SharedPreferences? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: initProfileSharedPreferences(
                    context
                )
                    .also { instance = it }
        }

        private fun initProfileSharedPreferences(context: Context) : SharedPreferences {
            return context.getSharedPreferences(
                PROFILE_SHARED_PREFERENCES_NAME,
                PROFILE_SHARED_PREFERENCES_MODE
            )
        }
    }
}
