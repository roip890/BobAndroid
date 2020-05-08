package com.aptenobytes.bob.feature.wish.presentation.wishessettings

import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishesSettingsDomainModel
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseResult

sealed class WishesSettingsResult : BaseResult {
    sealed class GetWishesSettingsResult : WishesSettingsResult() {
        object Loading : GetWishesSettingsResult()
        data class Success(val wishesSettings: WishesSettingsDomainModel) : GetWishesSettingsResult()
        data class Failure(val error: Throwable) : GetWishesSettingsResult()
    }

    sealed class SaveWishesSettingsResult : WishesSettingsResult() {
        object Loading : SaveWishesSettingsResult()
        data class Success(val wishesSettings: WishesSettingsDomainModel) : SaveWishesSettingsResult()
        data class Failure(val error: Throwable) : SaveWishesSettingsResult()
    }
}
