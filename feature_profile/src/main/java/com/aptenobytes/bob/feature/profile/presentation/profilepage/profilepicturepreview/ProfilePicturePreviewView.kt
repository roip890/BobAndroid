package com.aptenobytes.bob.feature.profile.presentation.profilepage.profilepicturepreview

import kotlinx.coroutines.flow.Flow

interface ProfilePicturePreviewView {
    fun intents(): Flow<ProfilePicturePreviewIntent>
  }
