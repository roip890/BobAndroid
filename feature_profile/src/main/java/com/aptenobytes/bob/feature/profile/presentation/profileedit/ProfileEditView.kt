package com.aptenobytes.bob.feature.profile.presentation.profileedit

import kotlinx.coroutines.flow.Flow

interface ProfileEditView {
    fun intents(): Flow<ProfileEditIntent>
  }
