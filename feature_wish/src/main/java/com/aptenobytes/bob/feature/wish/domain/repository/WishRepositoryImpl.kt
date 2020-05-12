package com.aptenobytes.bob.feature.wish.domain.repository

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.domain.datasource.WishCacheDataSource
import com.aptenobytes.bob.feature.wish.domain.datasource.WishLocalDataSource
import com.aptenobytes.bob.feature.wish.domain.datasource.WishNetworkDataSource
import com.aptenobytes.bob.feature.wish.domain.datasource.WishSharedPreferencesDataSource
import com.aptenobytes.bob.feature.wish.domain.enums.wishsort.WishSortType
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wishsettings.WishSettingsDomainModel
import com.pawegio.kandroid.w
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
        val wishSettingsDomainModel: WishSettingsDomainModel = this.getWishSettings()
        return wishNetworkDataSource.getWishes(
            wishId = wishSettingsDomainModel.filter?.wishId,
            userId = wishSettingsDomainModel.filter?.userId,
            guestId = wishSettingsDomainModel.filter?.guestId,
            bookingId = wishSettingsDomainModel.filter?.bookingId,
            minTimestamp = wishSettingsDomainModel.filter?.minTimestamp,
            maxTimestamp = wishSettingsDomainModel.filter?.maxTimestamp,
            statuses = wishSettingsDomainModel.filter?.statuses,
            departments = wishSettingsDomainModel.filter?.departments,
            sort = wishSettingsDomainModel.sort,
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
