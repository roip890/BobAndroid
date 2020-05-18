package com.aptenobytes.bob.feature.profile.presentation.profilepicturemenu

import kotlinx.coroutines.flow.Flow

interface ProfilePictureMenuView {
    fun intents(): Flow<ProfilePictureMenuIntent>
  }
