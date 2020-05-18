package com.aptenobytes.bob.feature.profile.presentation.profilepicturemenu

import com.aptenobytes.bob.library.base.presentation.mvi.BaseResult

sealed class ProfilePictureMenuResult : BaseResult {
    sealed class UploadProfilePictureResult : ProfilePictureMenuResult() {
        object Loading : UploadProfilePictureResult()
        data class Success(val imageUrl: String?) : UploadProfilePictureResult()
        data class Failure(val error: Throwable) : UploadProfilePictureResult()
    }

    sealed class RemoveProfilePictureResult : ProfilePictureMenuResult() {
        object Loading : RemoveProfilePictureResult()
        data class Success(val imageUrl: String?) : RemoveProfilePictureResult()
        data class Failure(val error: Throwable) : RemoveProfilePictureResult()
    }
}
