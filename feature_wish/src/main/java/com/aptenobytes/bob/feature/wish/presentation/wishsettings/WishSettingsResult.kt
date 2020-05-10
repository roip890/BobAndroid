package com.aptenobytes.bob.feature.wish.presentation.wishsettings

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wishsettings.WishSettingsDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseResult

sealed class WishSettingsResult : BaseResult {
    sealed class GetWishSettingsResult : WishSettingsResult() {
        object Loading : GetWishSettingsResult()
        data class Success(val wishSettings: WishSettingsDomainModel) : GetWishSettingsResult()
        data class Failure(val error: Throwable) : GetWishSettingsResult()
    }

    sealed class SetWishSettingsResult : WishSettingsResult() {
        object Loading : SetWishSettingsResult()
        data class Success(val wishSettings: WishSettingsDomainModel) : SetWishSettingsResult()
        data class Failure(val error: Throwable) : SetWishSettingsResult()
    }

    sealed class GetDepartmentsListResult : WishSettingsResult() {
        object Loading : GetDepartmentsListResult()
        data class Success(val departments: List<DepartmentDomainModel>) : GetDepartmentsListResult()
        data class Failure(val error: Throwable) : GetDepartmentsListResult()
    }
}
