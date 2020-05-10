package com.aptenobytes.bob.feature.wish.presentation.wishessettings

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishesSettingsDomainModel
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseViewState

data class WishesSettingsViewState(
    val isLoading: Boolean = false,
    val departments: List<DepartmentDomainModel> = listOf<DepartmentDomainModel>(),
    val wishesSettings: WishesSettingsDomainModel = WishesSettingsDomainModel(),
    val error: Throwable? = null
) : BaseViewState {
    companion object {
        fun initial() = WishesSettingsViewState(
            wishesSettings = WishesSettingsDomainModel(),
            departments = listOf<DepartmentDomainModel>(),
            isLoading = false,
            error = null
        )
    }
}
