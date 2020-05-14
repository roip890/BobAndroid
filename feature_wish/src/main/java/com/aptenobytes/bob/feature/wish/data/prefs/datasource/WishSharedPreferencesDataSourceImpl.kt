package com.aptenobytes.bob.feature.wish.data.prefs.datasource

import android.content.SharedPreferences
import com.aptenobytes.bob.feature.wish.domain.datasource.WishSharedPreferencesDataSource
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishSettingsDomainModel
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val WISH_SHARED_PREFERENCES_SETTINGS_NAME = "wishSettings"

class WishSharedPreferencesDataSourceImpl(
    private val prefs: SharedPreferences,
    private val moshi: Moshi
) : WishSharedPreferencesDataSource {

    // settings
    override suspend fun getWishSettings(): WishSettingsDomainModel? {
        return withContext(Dispatchers.IO) {
            if (prefs.contains(WISH_SHARED_PREFERENCES_SETTINGS_NAME)) {
                return@withContext moshi.adapter(WishSettingsDomainModel::class.java).fromJson(prefs.getString(WISH_SHARED_PREFERENCES_SETTINGS_NAME, null)!!)
            }
            return@withContext null
        }
    }

    override suspend fun setWishSettings(wishSettings: WishSettingsDomainModel?): WishSettingsDomainModel? {
        return withContext(Dispatchers.IO) {
            if (wishSettings != null) {
                prefs.edit()
                    .putString(WISH_SHARED_PREFERENCES_SETTINGS_NAME, moshi.adapter(WishSettingsDomainModel::class.java)
                        .toJson(wishSettings)).apply()
            }
            if (prefs.contains(WISH_SHARED_PREFERENCES_SETTINGS_NAME)) {
                return@withContext moshi.adapter(WishSettingsDomainModel::class.java).fromJson(prefs.getString(WISH_SHARED_PREFERENCES_SETTINGS_NAME, null)!!)
            }
            return@withContext null
        }
    }

}
