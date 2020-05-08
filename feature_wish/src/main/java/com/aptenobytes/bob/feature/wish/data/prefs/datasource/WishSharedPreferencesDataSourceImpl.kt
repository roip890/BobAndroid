package com.aptenobytes.bob.feature.wish.data.prefs.datasource

import android.content.SharedPreferences
import com.aptenobytes.bob.feature.wish.domain.datasource.WishSharedPreferencesDataSource
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishesSettingsDomainModel
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val WISH_SHARED_PREFERENCES_SETTINGS_NAME = "wishesSettings"

class WishSharedPreferencesDataSourceImpl(
    private val prefs: SharedPreferences,
    private val moshi: Moshi
) : WishSharedPreferencesDataSource {

    // settings
    override suspend fun getWishesSettings(): WishesSettingsDomainModel? {
        return withContext(Dispatchers.IO) {
            if (prefs.contains(WISH_SHARED_PREFERENCES_SETTINGS_NAME)) {
                moshi.adapter(WishesSettingsDomainModel::class.java)
                    .fromJson(prefs.getString(WISH_SHARED_PREFERENCES_SETTINGS_NAME, null)!!)
            }
            null
        }
    }

    override suspend fun setWishesSettings(wishesSettings: WishesSettingsDomainModel?) {
        if (wishesSettings != null) {
            this.prefs.edit()
                .putString(WISH_SHARED_PREFERENCES_SETTINGS_NAME,
                    moshi.adapter(WishesSettingsDomainModel::class.java).toJson(wishesSettings))
                .apply()
        }
    }

}
