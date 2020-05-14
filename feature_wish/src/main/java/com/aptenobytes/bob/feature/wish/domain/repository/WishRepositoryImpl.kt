package com.aptenobytes.bob.feature.wish.domain.repository

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.domain.datasource.WishCacheDataSource
import com.aptenobytes.bob.feature.wish.domain.datasource.WishLocalDataSource
import com.aptenobytes.bob.feature.wish.domain.datasource.WishNetworkDataSource
import com.aptenobytes.bob.feature.wish.domain.datasource.WishSharedPreferencesDataSource
import com.aptenobytes.bob.feature.wish.domain.enums.wishsort.WishSortType
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishSettingsDomainModel
import java.util.*
import kotlin.collections.ArrayList

class WishRepositoryImpl(
    private val wishNetworkDataSource: WishNetworkDataSource,
    private val wishLocalDataSource: WishLocalDataSource,
    private val wishCacheDataSource: WishCacheDataSource,
    private val wishSharedPreferencesDataSource: WishSharedPreferencesDataSource
) : WishRepository {

    // wishes
    override suspend fun getWishes(
        wishId: Long?,

        userId: Long?,
        guestId: Long?,
        bookingId: Long?,

        minTimestamp: Date?,
        maxTimestamp: Date?,
        statuses: ArrayList<WishStatusType>?,
        departments: ArrayList<DepartmentDomainModel>?,

        sort: WishSortType?,

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

    // wish status
    override suspend fun setWishStatus(wish: WishDomainModel, status: WishStatusType): WishDomainModel? {
        return wishNetworkDataSource.setWishStatus(wish = wish, status = status)
    }

    // wish settings
    override suspend fun getWishSettings(): WishSettingsDomainModel {
        return wishSharedPreferencesDataSource.getWishSettings() ?: WishSettingsDomainModel()
    }

    override suspend fun setWishSettings(wishSettings: WishSettingsDomainModel): WishSettingsDomainModel {
        return wishSharedPreferencesDataSource.setWishSettings(wishSettings) ?: WishSettingsDomainModel()
    }


}
