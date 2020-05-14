package com.aptenobytes.bob.feature.wish.domain.datasource

import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishSettingsDomainModel

interface WishSharedPreferencesDataSource {

    // settings
    suspend fun getWishSettings(): WishSettingsDomainModel?

    suspend fun setWishSettings(wishSettings: WishSettingsDomainModel?): WishSettingsDomainModel?

}
