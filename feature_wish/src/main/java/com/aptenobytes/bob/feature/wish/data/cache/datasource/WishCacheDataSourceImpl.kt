package com.aptenobytes.bob.feature.wish.data.cache.datasource

import com.aptenobytes.bob.feature.wish.domain.datasource.WishCacheDataSource
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishesSettingsDomainModel

class WishCacheDataSourceImpl() : WishCacheDataSource {

    private var wishesSettings = WishesSettingsDomainModel()
    private val wishes = mutableListOf<WishDomainModel>()

    // wishes
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
        return wishes
    }

    override suspend fun insertAll(
        wishes: List<WishDomainModel>
    ) {
        if (this.wishes.isEmpty()) this.wishes.addAll(wishes)
    }

    // settings
    override suspend fun getWishesSettings(): WishesSettingsDomainModel? {
        return wishesSettings
    }

    override suspend fun setWishesSettings(wishesSettings: WishesSettingsDomainModel?) {
        if (wishesSettings != null) this.wishesSettings = wishesSettings
    }

}
