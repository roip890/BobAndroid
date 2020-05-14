package com.aptenobytes.bob.feature.wish.domain.repository

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.domain.enums.wishsort.WishSortType
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishSettingsDomainModel
import java.util.*
import kotlin.collections.ArrayList

interface WishRepository {

    // wishes
    suspend fun getWishes(
        wishId: Long? = null,

        userId: Long? = null,
        guestId: Long? = null,
        bookingId: Long? = null,

        minTimestamp: Date? = null,
        maxTimestamp: Date? = null,
        statuses: ArrayList<WishStatusType>? = arrayListOf(),
        departments: ArrayList<DepartmentDomainModel>? = arrayListOf(),

        sort: WishSortType? = null,
        index: Int? = 0,
        limit: Int? = 20
    ): List<WishDomainModel>

    // wish status
    suspend fun setWishStatus(wish: WishDomainModel, status: WishStatusType): WishDomainModel?

    // wish settings
    suspend fun getWishSettings(): WishSettingsDomainModel

    suspend fun setWishSettings(wishSettings: WishSettingsDomainModel): WishSettingsDomainModel

}
