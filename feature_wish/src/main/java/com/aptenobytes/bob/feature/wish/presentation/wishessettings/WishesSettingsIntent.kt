package com.aptenobytes.bob.feature.wish.presentation.wishessettings

import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishesSettingsDomainModel
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseIntent

sealed class WishesSettingsIntent : BaseIntent {
    object InitialIntent : WishesSettingsIntent()
    object GetWishesSettingsIntent : WishesSettingsIntent()
    class SaveWishesSettingsIntent(wishesSettings: WishesSettingsDomainModel) : WishesSettingsIntent()
}
