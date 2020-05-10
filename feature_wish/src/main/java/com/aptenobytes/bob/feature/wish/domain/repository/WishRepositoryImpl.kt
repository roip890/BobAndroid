package com.aptenobytes.bob.feature.wish.domain.repository

import com.aptenobytes.bob.feature.wish.domain.datasource.WishCacheDataSource
import com.aptenobytes.bob.feature.wish.domain.datasource.WishLocalDataSource
import com.aptenobytes.bob.feature.wish.domain.datasource.WishNetworkDataSource
import com.aptenobytes.bob.feature.wish.domain.datasource.WishSharedPreferencesDataSource
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishesSettingsDomainModel

class WishRepositoryImpl(
    private val wishNetworkDataSource: WishNetworkDataSource,
    private val wishLocalDataSource: WishLocalDataSource,
    private val wishCacheDataSource: WishCacheDataSource,
    private val wishSharedPreferencesDataSource: WishSharedPreferencesDataSource
) : WishRepository {

    override suspend fun getWishes(
        wishId: Long?,
        userId: Long?,
        guestId: Long?,
        bookingId: Long?,
        minTimestamp: String?,
        maxTimestamp: String?,
        statuses: ArrayList<String>?,
        departments: ArrayList<String>?,
        sort: String?,
        index: Int?,
        limit: Int?
    ): List<WishDomainModel> {
        return wishNetworkDataSource.getWishes(
            wishId = wishId,
            userId = userId,
            guestId = guestId,
            bookingId = bookingId,
            minTimestamp = minTimestamp,
            maxTimestamp = maxTimestamp,
            statuses = statuses,
            departments = departments,
            sort = sort,
            index = index,
            limit = limit
        )
    }

    override suspend fun getWishesSettings(): WishesSettingsDomainModel {
        return wishSharedPreferencesDataSource.getWishesSettings() ?: WishesSettingsDomainModel()
    }

    override suspend fun setWishesSettings(wishesSettings: WishesSettingsDomainModel): WishesSettingsDomainModel {
        return wishSharedPreferencesDataSource.setWishesSettings(wishesSettings) ?: WishesSettingsDomainModel()
    }


}
