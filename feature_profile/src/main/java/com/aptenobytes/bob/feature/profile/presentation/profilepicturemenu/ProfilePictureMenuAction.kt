package com.aptenobytes.bob.feature.profile.presentation.profilepicturemenu

import com.aptenobytes.bob.library.base.presentation.mvi.BaseAction

sealed class ProfilePictureMenuAction : BaseAction {
    data class UploadProfilePictureAction(val imagePath: String) : ProfilePictureMenuAction()
    object RemoveProfilePictureAction : ProfilePictureMenuAction()
}
