package com.aptenobytes.bob.feature.profile.presentation.profilepicturemenu.recyclerview

import androidx.lifecycle.MutableLiveData
import com.aptenobytes.bob.feature.profile.presentation.profilepicturemenu.ProfilePictureMenuItem

class ProfilePictureMenuItemViewModel(
    val profilePictureMenuItem: MutableLiveData<ProfilePictureMenuItem> = MutableLiveData<ProfilePictureMenuItem>()
) {

    val itemTitle = MutableLiveData<String>()

    init {
    }
}

fun ProfilePictureMenuItem.toViewModel(): ProfilePictureMenuItemViewModel {
    return ProfilePictureMenuItemViewModel(
        profilePictureMenuItem = MutableLiveData<ProfilePictureMenuItem>(this)
    )
}

fun ProfilePictureMenuItemViewModel.toDomainModel(): ProfilePictureMenuItem? = this.profilePictureMenuItem.value
