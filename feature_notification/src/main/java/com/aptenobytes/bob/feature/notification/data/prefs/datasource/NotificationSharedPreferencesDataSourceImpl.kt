package com.aptenobytes.bob.feature.notification.data.prefs.datasource

import android.content.SharedPreferences
import com.aptenobytes.bob.feature.notification.domain.datasource.NotificationSharedPreferencesDataSource
import com.squareup.moshi.Moshi

class NotificationSharedPreferencesDataSourceImpl(
    private val prefs: SharedPreferences,
    private val moshi: Moshi
) : NotificationSharedPreferencesDataSource {

}
