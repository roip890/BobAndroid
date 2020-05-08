package com.aptenobytes.bob.feature.wish.data.network.datasource

import com.aptenobytes.bob.feature.wish.data.network.model.toDomainModel
import com.aptenobytes.bob.feature.wish.data.network.retrofit.service.WishRetrofitService
import com.aptenobytes.bob.feature.wish.domain.datasource.WishNetworkDataSource

internal class WishNetworkDataSourceImpl(
    private val wishRetrofitService: WishRetrofitService
) : WishNetworkDataSource {

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

}
