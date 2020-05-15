package com.aptenobytes.bob.feature.wish.domain.datasource

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.domain.enums.wishsort.WishSortType
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import java.util.*
import kotlin.collections.ArrayList

interface WishNetworkDataSource {

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

    suspend fun setWishStatus(wishId: Long, status: WishStatusType): WishDomainModel?
}
