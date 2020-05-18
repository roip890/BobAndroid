package com.aptenobytes.bob.feature.profile.presentation.setuserstatus

import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseViewState

data class SetUserStatusViewState(
    val user: UserDomainModel? = null,
    val status: UserStatusType? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null
) : BaseViewState {
    companion object {
        fun initial() = SetUserStatusViewState(
            user = null,
            status = null,
            isLoading = false,
            error = null
        )
    }
}
