package com.aptenobytes.bob.feature.wish.presentation.wishsettings

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishSettingsDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseViewState

data class WishSettingsViewState(
    val isLoading: Boolean = false,
    val departments: List<DepartmentDomainModel> = listOf<DepartmentDomainModel>(),
    val wishSettings: WishSettingsDomainModel = WishSettingsDomainModel(),
    val error: Throwable? = null,
    val submit: Boolean = false
) : BaseViewState {
    companion object {
        fun initial() = WishSettingsViewState(
            wishSettings = WishSettingsDomainModel(),
            departments = listOf<DepartmentDomainModel>(),
            isLoading = false,
            error = null,
            submit = false
        )
    }
}
