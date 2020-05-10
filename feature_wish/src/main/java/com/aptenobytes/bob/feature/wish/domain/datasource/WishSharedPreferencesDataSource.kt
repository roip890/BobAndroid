package com.aptenobytes.bob.feature.wish.domain.datasource

import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishesSettingsDomainModel

interface WishSharedPreferencesDataSource {

    // settings
    suspend fun getWishesSettings(): WishesSettingsDomainModel?

    suspend fun setWishesSettings(wishesSettings: WishesSettingsDomainModel?): WishesSettingsDomainModel?

}
