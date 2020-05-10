package com.aptenobytes.bob.feature.wish.data.prefs.datasource

import android.content.SharedPreferences
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.domain.datasource.WishSharedPreferencesDataSource
import com.aptenobytes.bob.feature.wish.domain.enums.wishsort.WishSortType
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishesSettingsDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.filter.WishFilterDomainModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.reflect.Type

private const val WISH_SHARED_PREFERENCES_SETTINGS_NAME = "wishesSettings"

class WishSharedPreferencesDataSourceImpl(
    private val prefs: SharedPreferences,
    private val moshi: Moshi
) : WishSharedPreferencesDataSource {

    // settings
    override suspend fun getWishesSettings(): WishesSettingsDomainModel? {
        return withContext(Dispatchers.IO) {
            if (prefs.contains(WISH_SHARED_PREFERENCES_SETTINGS_NAME)) {
                return@withContext moshi.adapter(WishesSettingsDomainModel::class.java).fromJson(prefs.getString(WISH_SHARED_PREFERENCES_SETTINGS_NAME, null)!!)
            }
            return@withContext null
        }
    }

    override suspend fun setWishesSettings(wishesSettings: WishesSettingsDomainModel?): WishesSettingsDomainModel? {
        return withContext(Dispatchers.IO) {
            if (wishesSettings != null) {
                prefs.edit()
                    .putString(WISH_SHARED_PREFERENCES_SETTINGS_NAME, moshi.adapter(WishesSettingsDomainModel::class.java)
                        .toJson(wishesSettings)).apply()
            }
            if (prefs.contains(WISH_SHARED_PREFERENCES_SETTINGS_NAME)) {
                return@withContext moshi.adapter(WishesSettingsDomainModel::class.java).fromJson(prefs.getString(WISH_SHARED_PREFERENCES_SETTINGS_NAME, null)!!)
            }
            return@withContext null
        }
    }

}
