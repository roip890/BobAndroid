package com.aptenobytes.bob.feature.wish.presentation.wishessettings

import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishesSettingsDomainModel
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseAction

sealed class WishesSettingsAction : BaseAction {
    object GetDepartmentsListAction : WishesSettingsAction()
    object GetWishesSettingsAction : WishesSettingsAction()
    class SetWishesSettingsAction(val wishesSettings: WishesSettingsDomainModel) : WishesSettingsAction()
}
