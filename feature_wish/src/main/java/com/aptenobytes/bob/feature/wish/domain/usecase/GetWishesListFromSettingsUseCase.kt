package com.aptenobytes.bob.feature.wish.domain.usecase

import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishSettingsDomainModel
import com.aptenobytes.bob.feature.wish.domain.repository.WishRepository

class GetWishesListFromSettingsUseCase(
    private val wishRepository: WishRepository
) {
    suspend fun execute(index: Int = 0, limit: Int = 20): List<WishDomainModel> {
        val wishSettingsDomainModel: WishSettingsDomainModel = wishRepository.getWishSettings()
        return wishRepository.getWishes(
            wishId = wishSettingsDomainModel.filter?.wishId,
            userId = wishSettingsDomainModel.filter?.userId,
            guestId = wishSettingsDomainModel.filter?.guestId,
            bookingId = wishSettingsDomainModel.filter?.bookingId,
            minTimestamp = wishSettingsDomainModel.filter?.minTimestamp,
            maxTimestamp = wishSettingsDomainModel.filter?.maxTimestamp,
            statuses = wishSettingsDomainModel.filter?.statuses,
            departments = wishSettingsDomainModel.filter?.departments,
            sort = wishSettingsDomainModel.sort,
            index = index,
            limit = limit
        )
    }
}
