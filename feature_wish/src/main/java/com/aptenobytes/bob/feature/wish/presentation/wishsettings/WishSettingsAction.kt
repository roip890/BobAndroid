package com.aptenobytes.bob.feature.wish.presentation.wishsettings

import com.aptenobytes.bob.feature.wish.domain.model.wishsettings.WishSettingsDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseAction

sealed class WishSettingsAction : BaseAction {
    object GetDepartmentsListAction : WishSettingsAction()
    object GetWishSettingsAction : WishSettingsAction()
    class SetWishSettingsAction(val wishSettings: WishSettingsDomainModel) : WishSettingsAction()
}
