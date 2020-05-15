package com.aptenobytes.bob.feature.wish.domain.usecase

import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.domain.repository.WishRepository

class SetWishStatusUseCase(
    private val wishRepository: WishRepository
) {
    suspend fun execute(wishId: Long, status: WishStatusType): WishDomainModel? {
        return wishRepository.setWishStatus(wishId = wishId, status = status)
    }
}
