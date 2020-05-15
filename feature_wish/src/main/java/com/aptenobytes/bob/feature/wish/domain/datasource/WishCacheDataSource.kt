package com.aptenobytes.bob.feature.wish.domain.datasource

import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishSettingsDomainModel
import java.util.*
import kotlin.collections.ArrayList

interface WishCacheDataSource {

    // wishes
    suspend fun getWishes(
        wishId: Long? = null,

        userId: Long? = null,
        guestId: Long? = null,
        bookingId: Long? = null,

        minTimestamp: Date? = null,
        maxTimestamp: Date? = null,
        statuses: ArrayList<String>? = null,
        departments: ArrayList<String>? = null,

        sort: String? = null,
        index: Int? = 0,
        limit: Int? = 20
    ): List<WishDomainModel>

    suspend fun insertAll(wishes: List<WishDomainModel>)

    // settings
    suspend fun getWishSettings(): WishSettingsDomainModel?

    suspend fun setWishSettings(wishSettings: WishSettingsDomainModel?)

}
