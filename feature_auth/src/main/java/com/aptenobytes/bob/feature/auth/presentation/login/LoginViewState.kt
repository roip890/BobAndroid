package com.aptenobytes.bob.feature.auth.presentation.login

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseViewState

data class LoginViewState(
    val isLoading: Boolean = false,
    val user: UserDomainModel? = null,
    val error: Throwable? = null
) : BaseViewState {
    companion object {
        fun initial() = LoginViewState(
            user = null,
            isLoading = false,
            error = null
        )
    }
}
