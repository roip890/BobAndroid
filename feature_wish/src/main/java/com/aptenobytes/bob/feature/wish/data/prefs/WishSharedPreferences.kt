package com.aptenobytes.bob.feature.wish.data.prefs

import android.content.Context
import android.content.SharedPreferences

private const val WISH_SHARED_PREFERENCES_NAME = "wishes"
private const val WISH_SHARED_PREFERENCES_MODE = Context.MODE_PRIVATE

abstract class WishSharedPreferences: SharedPreferences {

    companion object {
        @Volatile
        private var instance : SharedPreferences? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: initWishSharedPreferences(
                    context
                )
                    .also { instance = it }
        }

        private fun initWishSharedPreferences(context: Context) : SharedPreferences {
            return context.getSharedPreferences(WISH_SHARED_PREFERENCES_NAME, WISH_SHARED_PREFERENCES_MODE)
        }
    }
}
