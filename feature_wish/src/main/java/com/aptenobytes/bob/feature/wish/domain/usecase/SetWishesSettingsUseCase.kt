package com.aptenobytes.bob.feature.wish.domain.usecase

import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishesSettingsDomainModel
import com.aptenobytes.bob.feature.wish.domain.repository.WishRepository

class SetWishesSettingsUseCase(
    private val wishRepository: WishRepository
) {
    suspend fun execute(wishesSettings: WishesSettingsDomainModel): WishesSettingsDomainModel {
        return wishRepository.setWishesSettings(wishesSettings)
    }
}
