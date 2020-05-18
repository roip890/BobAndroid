package com.aptenobytes.bob.feature.profile.presentation.profilepicturemenu

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseViewState

data class ProfilePictureMenuViewState(
    val isProfilePictureChanged: Boolean? = false,
    val imageUrl: String? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null
) : BaseViewState {
    companion object {
        fun initial() = ProfilePictureMenuViewState(
            isProfilePictureChanged = false,
            imageUrl = null,
            isLoading = false,
            error = null
        )
    }
}
