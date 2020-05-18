package com.aptenobytes.bob.feature.profile.presentation.profilepage.profilepicturepreview

import com.aptenobytes.bob.library.base.presentation.mvi.BaseViewState

data class ProfilePicturePreviewViewState(
    val isLoading: Boolean = false,
    val imageUrl: String? = null,
    val error: Throwable? = null
) : BaseViewState {
    companion object {
        fun initial() = ProfilePicturePreviewViewState(
            isLoading = false,
            imageUrl = null,
            error = null
        )
    }
}
