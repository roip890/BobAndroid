package com.aptenobytes.bob.feature.wish.domain.usecase

import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.domain.repository.WishRepository

class GetWishesListUseCase(
    private val wishRepository: WishRepository
) {
    suspend fun execute(): List<WishDomainModel> {
        return wishRepository.getWishes()
    }
}
