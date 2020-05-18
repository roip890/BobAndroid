package com.aptenobytes.bob.feature.profile.presentation.profilepage

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseViewState

data class ProfilePageViewState(
    val refresh: Boolean= false,
    val isLoading: Boolean = false,
    val user: UserDomainModel? = null,
    val error: Throwable? = null
) : BaseViewState {
    companion object {
        fun initial() = ProfilePageViewState(
            refresh = false,
            isLoading = false,
            user = null,
            error = null
        )
    }
}
