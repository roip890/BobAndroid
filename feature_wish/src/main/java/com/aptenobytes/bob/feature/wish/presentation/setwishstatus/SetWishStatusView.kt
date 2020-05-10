package com.aptenobytes.bob.feature.wish.presentation.setwishstatus

import kotlinx.coroutines.flow.Flow

interface SetWishStatusView {
    fun intents(): Flow<SetWishStatusIntent>
  }
