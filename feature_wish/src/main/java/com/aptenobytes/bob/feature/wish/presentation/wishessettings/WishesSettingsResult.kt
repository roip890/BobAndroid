package com.aptenobytes.bob.feature.wish.presentation.wishessettings

import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishesSettingsDomainModel
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseResult

sealed class WishesSettingsResult : BaseResult {
    sealed class GetWishesSettingsResult : WishesSettingsResult() {
        object Loading : GetWishesSettingsResult()
        data class Success(val wishesSettings: WishesSettingsDomainModel) : GetWishesSettingsResult()
        data class Failure(val error: Throwable) : GetWishesSettingsResult()
    }

    sealed class SetWishesSettingsResult : WishesSettingsResult() {
        object Loading : SetWishesSettingsResult()
        data class Success(val wishesSettings: WishesSettingsDomainModel) : SetWishesSettingsResult()
        data class Failure(val error: Throwable) : SetWishesSettingsResult()
    }

    sealed class GetDepartmentsListResult : WishesSettingsResult() {
        object Loading : GetDepartmentsListResult()
        data class Success(val departments: List<DepartmentDomainModel>) : GetDepartmentsListResult()
        data class Failure(val error: Throwable) : GetDepartmentsListResult()
    }
}
