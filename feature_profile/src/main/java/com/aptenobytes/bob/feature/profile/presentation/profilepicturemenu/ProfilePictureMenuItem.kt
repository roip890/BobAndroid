package com.aptenobytes.bob.feature.profile.presentation.profilepicturemenu

import android.graphics.drawable.Drawable

sealed class ProfilePictureMenuItem(open val title: String, open val icon: Drawable) {
    data class ShowProfilePicture(override val title: String, override val icon: Drawable) : ProfilePictureMenuItem(title = title, icon = icon)
    data class PickFromGallery(override val title: String, override val icon: Drawable) : ProfilePictureMenuItem(title = title, icon = icon)
    data class PickFromCamera(override val title: String, override val icon: Drawable) : ProfilePictureMenuItem(title = title, icon = icon)
    data class RemoveProfilePicture(override val title: String, override val icon: Drawable) : ProfilePictureMenuItem(title = title, icon = icon)
}
