package com.aptenobytes.bob.feature.auth.data.prefs.datasource

import android.content.SharedPreferences
import com.aptenobytes.bob.feature.auth.domain.datasource.AuthSharedPreferencesDataSource
import com.squareup.moshi.Moshi


class AuthSharedPreferencesDataSourceImpl(
    private val prefs: SharedPreferences,
    private val moshi: Moshi
) : AuthSharedPreferencesDataSource {

}
