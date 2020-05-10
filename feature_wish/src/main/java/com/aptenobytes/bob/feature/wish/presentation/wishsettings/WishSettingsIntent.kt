package com.aptenobytes.bob.feature.wish.presentation.wishsettings

import com.aptenobytes.bob.feature.wish.domain.model.wishsettings.WishSettingsDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseIntent

sealed class WishSettingsIntent : BaseIntent {
    object GetDepartmentsListIntent : WishSettingsIntent()
    object GetWishSettingsIntent : WishSettingsIntent()
    class SetWishSettingsIntent(val wishSettings: WishSettingsDomainModel) : WishSettingsIntent()
}