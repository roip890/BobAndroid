package com.aptenobytes.bob.feature.wish.domain.usecase

import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishSettingsDomainModel
import com.aptenobytes.bob.feature.wish.domain.repository.WishRepository

class GetWishSettingsUseCase(
    private val wishRepository: WishRepository
) {
    suspend fun execute(): WishSettingsDomainModel {
        return wishRepository.getWishSettings()
    }
}
