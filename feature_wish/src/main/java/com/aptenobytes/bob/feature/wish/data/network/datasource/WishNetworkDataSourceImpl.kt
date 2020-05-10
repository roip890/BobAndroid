package com.aptenobytes.bob.feature.wish.data.network.datasource

import com.aptenobytes.bob.feature.wish.data.network.model.toDomainModel
import com.aptenobytes.bob.feature.wish.data.network.model.toNetworkModel
import com.aptenobytes.bob.feature.wish.data.network.retrofit.request.SetWishStatusRequest
import com.aptenobytes.bob.feature.wish.data.network.retrofit.request.SetWishStatusRequestWrapper
import com.aptenobytes.bob.feature.wish.data.network.retrofit.service.WishRetrofitService
import com.aptenobytes.bob.feature.wish.domain.datasource.WishNetworkDataSource
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel

internal class WishNetworkDataSourceImpl(
    private val wishRetrofitService: WishRetrofitService
) : WishNetworkDataSource {

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

    ) =
        wishRetrofitService.getWishesAsync(
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
            ?.response
            ?.wishes
            ?.map { it.toDomainModel() }
            ?: listOf()

    // wish status
    override suspend fun setWishStatus(
        wish: WishDomainModel,
        status: WishStatusType): WishDomainModel? {
        wish.status = status
        return wishRetrofitService.setWishStatusAsync(
                    setWishStatusRequestWrapper = SetWishStatusRequestWrapper(
                    request = SetWishStatusRequest(
                        wish = wish.toNetworkModel()
                    )
                    )
        )
            ?.response
            ?.wishes
            ?.map {
                it.toDomainModel()
            }
            ?.firstOrNull()
    }

}
