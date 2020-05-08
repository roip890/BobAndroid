package com.aptenobytes.bob.feature.wish.domain.datasource

import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishesSettingsDomainModel

interface WishCacheDataSource {

    // wishes
    suspend fun getWishes(
        wishId: Long? = null,

        userId: Long? = null,
        guestId: Long? = null,
        bookingId: Long? = null,

        minTimestamp: String? = null,
        maxTimestamp: String? = null,
        statuses: ArrayList<String>? = null,
        departments: ArrayList<String>? = null,

        sort: String? = null,
        index: Int? = 0,
        limit: Int? = 20
    ): List<WishDomainModel>

    suspend fun insertAll(wishes: List<WishDomainModel>)

    // settings
    suspend fun getWishesSettings(): WishesSettingsDomainModel?

    suspend fun setWishesSettings(wishesSettings: WishesSettingsDomainModel?)

}
