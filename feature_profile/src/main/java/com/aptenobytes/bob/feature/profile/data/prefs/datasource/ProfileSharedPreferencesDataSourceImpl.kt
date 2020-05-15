package com.aptenobytes.bob.feature.profile.data.prefs.datasource

import android.content.SharedPreferences
import com.aptenobytes.bob.feature.profile.domain.datasource.ProfileSharedPreferencesDataSource
import com.squareup.moshi.Moshi


class ProfileSharedPreferencesDataSourceImpl(
    private val prefs: SharedPreferences,
    private val moshi: Moshi
) : ProfileSharedPreferencesDataSource {

}
