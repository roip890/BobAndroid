package com.aptenobytes.bob.feature.wish.domain.usecase

import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishSettingsDomainModel
import com.aptenobytes.bob.feature.wish.domain.repository.WishRepository

class SetWishSettingsUseCase(
    private val wishRepository: WishRepository
) {
    suspend fun execute(wishSettings: WishSettingsDomainModel): WishSettingsDomainModel {
        return wishRepository.setWishSettings(wishSettings)
    }
}
