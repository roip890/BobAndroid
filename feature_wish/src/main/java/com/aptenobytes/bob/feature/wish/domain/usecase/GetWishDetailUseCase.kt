package com.aptenobytes.bob.feature.wish.domain.usecase

import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wishsettings.WishSettingsDomainModel
import com.aptenobytes.bob.feature.wish.domain.repository.WishRepository

class GetWishDetailUseCase(
    private val wishRepository: WishRepository
) {
    suspend fun execute(wishId: Long = -1, index: Int = 0, limit: Int = 1): WishDomainModel? {
        return wishRepository.getWishes(
            wishId = wishId,
            index = index,
            limit = limit
        )
        .firstOrNull()
    }
}
