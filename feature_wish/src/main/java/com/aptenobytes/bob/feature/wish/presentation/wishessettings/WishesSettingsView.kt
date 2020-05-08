package com.aptenobytes.bob.feature.wish.presentation.wishessettings

import kotlinx.coroutines.flow.Flow

interface WishesSettingsView {
    fun intents(): Flow<WishesSettingsIntent>
  }
