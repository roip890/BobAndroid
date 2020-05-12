package com.aptenobytes.bob.feature.wish.domain.usecase

import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.domain.repository.WishRepository

class GetWishesListUseCase(
    private val wishRepository: WishRepository
) {
    suspend fun execute(index: Int = 0, limit: Int = 20): List<WishDomainModel> {
        return wishRepository.getWishes(
            index = index,
            limit = limit
        )
    }
}
