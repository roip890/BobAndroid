package com.aptenobytes.bob.feature.profile.presentation.profilepage.profilepicturepreview

import androidx.lifecycle.MutableLiveData
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class ProfilePicturePreviewViewModel(
    private val args: ProfilePicturePreviewFragmentArgs
): BaseViewModel<ProfilePicturePreviewViewState, ProfilePicturePreviewAction>(ProfilePicturePreviewViewState.initial()) {

    val imageUrlLiveData: MutableLiveData<String> = MutableLiveData<String>()

    init {
        imageUrlLiveData.postValue(args.imageUrl)
    }

}


