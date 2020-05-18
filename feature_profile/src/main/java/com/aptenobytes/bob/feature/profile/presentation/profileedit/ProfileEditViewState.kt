package com.aptenobytes.bob.feature.profile.presentation.profileedit

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseViewState

data class ProfileEditViewState(
    val isLoading: Boolean = false,
    val user: UserDomainModel?,
    val error: Throwable? = null,
    val submit: Boolean = false
) : BaseViewState {
    companion object {
        fun initial() = ProfileEditViewState(
            user = null,
            isLoading = false,
            error = null,
            submit = false
        )
    }
}
