package com.aptenobytes.bob.feature.wish.data.network.datasource

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.data.network.constants.WISH_DATE_FORMAT
import com.aptenobytes.bob.feature.wish.data.network.enums.toNetworkEnum
import com.aptenobytes.bob.feature.wish.data.network.model.WishNetworkDataModel
import com.aptenobytes.bob.feature.wish.data.network.model.toDomainModel
import com.aptenobytes.bob.feature.wish.data.network.model.toNetworkModel
import com.aptenobytes.bob.feature.wish.data.network.retrofit.request.SetWishStatusRequest
import com.aptenobytes.bob.feature.wish.data.network.retrofit.request.SetWishStatusRequestWrapper
import com.aptenobytes.bob.feature.wish.data.network.retrofit.service.WishRetrofitService
import com.aptenobytes.bob.feature.wish.domain.datasource.WishNetworkDataSource
import com.aptenobytes.bob.feature.wish.domain.enums.wishsort.WishSortType
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.library.base.extensions.collections.toArrayList
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

internal class WishNetworkDataSourceImpl(
    private val wishRetrofitService: WishRetrofitService
) : WishNetworkDataSource {

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

    ) =
        wishRetrofitService.getWishesAsync(
            wishId = wishId,
            userId = userId,
            guestId = guestId,
            bookingId = bookingId,
            minTimestamp = minTimestamp?.let { SimpleDateFormat(WISH_DATE_FORMAT, Locale.ENGLISH).format(minTimestamp)} ?: run { null },
            maxTimestamp = minTimestamp?.let { SimpleDateFormat(WISH_DATE_FORMAT, Locale.ENGLISH).format(minTimestamp)} ?: run { null },
            statuses = statuses?.map {
                it.toNetworkEnum().value
            }?.toArrayList(),
            departments = departments?.map {
                it.name
            }?.toArrayList(),
            sort = sort?.toNetworkEnum()?.value,
            index = index,
            limit = limit
        )
            ?.response
            ?.wishes
            ?.map { it.toDomainModel() }
            ?: listOf()

    // wish status
    override suspend fun setWishStatus(
        wishId: Long,
        status: WishStatusType): WishDomainModel? {
        return wishRetrofitService.setWishStatusAsync(
                    setWishStatusRequestWrapper = SetWishStatusRequestWrapper(
                    request = SetWishStatusRequest(
                        wish = WishNetworkDataModel(
                            id = wishId,
                            status = status.toNetworkEnum()
                        )
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
