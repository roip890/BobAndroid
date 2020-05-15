package com.aptenobytes.bob.feature.wish.data.cache.datasource

import com.aptenobytes.bob.feature.wish.domain.datasource.WishCacheDataSource
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishSettingsDomainModel
import java.util.*
import kotlin.collections.ArrayList

class WishCacheDataSourceImpl() : WishCacheDataSource {

    private var wishSettings = WishSettingsDomainModel()
    private val wishes = mutableListOf<WishDomainModel>()

    // wishes
    override suspend fun getWishes(
        wishId: Long?,
        userId: Long?,
        guestId: Long?,
        bookingId: Long?,
        minTimestamp: Date?,
        maxTimestamp: Date?,
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
    override suspend fun getWishSettings(): WishSettingsDomainModel? {
        return wishSettings
    }

    override suspend fun setWishSettings(wishSettings: WishSettingsDomainModel?) {
        if (wishSettings != null) this.wishSettings = wishSettings
    }

}
