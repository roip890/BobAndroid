package com.aptenobytes.bob.feature.wish.presentation.wishsettings

import kotlinx.coroutines.flow.Flow

interface WishSettingsView {
    fun intents(): Flow<WishSettingsIntent>
  }
