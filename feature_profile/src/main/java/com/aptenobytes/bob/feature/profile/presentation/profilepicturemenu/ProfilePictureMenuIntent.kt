package com.aptenobytes.bob.feature.profile.presentation.profilepicturemenu

import com.aptenobytes.bob.library.base.presentation.mvi.BaseIntent

sealed class ProfilePictureMenuIntent : BaseIntent {
    data class UploadProfilePictureIntent(val imagePath: String) : ProfilePictureMenuIntent()
    object RemoveProfilePictureIntent : ProfilePictureMenuIntent()
}
