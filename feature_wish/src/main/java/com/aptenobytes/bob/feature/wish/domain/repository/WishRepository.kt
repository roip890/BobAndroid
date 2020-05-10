package com.aptenobytes.bob.feature.wish.domain.repository

import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wishsettings.WishSettingsDomainModel

interface WishRepository {

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

    // wish status
    suspend fun setWishStatus(wish: WishDomainModel, status: WishStatusType): WishDomainModel?

    // wish settings
    suspend fun getWishSettings(): WishSettingsDomainModel

    suspend fun setWishSettings(wishSettings: WishSettingsDomainModel): WishSettingsDomainModel

}
