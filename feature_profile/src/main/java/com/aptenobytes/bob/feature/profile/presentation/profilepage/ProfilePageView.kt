package com.aptenobytes.bob.feature.profile.presentation.profilepage

import kotlinx.coroutines.flow.Flow

interface ProfilePageView {
    fun intents(): Flow<ProfilePageIntent>
  }
