package com.aptenobytes.bob.feature.profile.presentation.setuserstatus

import kotlinx.coroutines.flow.Flow

interface SetUserStatusView {
    fun intents(): Flow<SetUserStatusIntent>
  }
