package com.aptenobytes.bob.feature.wish.data.db.datasource

import com.aptenobytes.bob.feature.wish.data.db.WishDao
import com.aptenobytes.bob.feature.wish.data.db.model.toDomainModel
import com.aptenobytes.bob.feature.wish.data.db.model.toRoomModel
import com.aptenobytes.bob.feature.wish.domain.datasource.WishLocalDataSource
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import java.util.*
import kotlin.collections.ArrayList

class WishRoomDataSourceImpl(private val dao: WishDao) : WishLocalDataSource {

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
        return dao.getWishList()
            .map { it.toDomainModel() }
    }

    override suspend fun insertAll(
        wishes: List<WishDomainModel>
    ) {
        return dao.insertAll(wishes.map { it.toRoomModel() })
    }

}
