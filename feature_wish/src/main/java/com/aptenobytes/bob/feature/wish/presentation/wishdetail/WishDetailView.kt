package com.aptenobytes.bob.feature.wish.presentation.wishdetail

import kotlinx.coroutines.flow.Flow

interface WishDetailView {
    fun intents(): Flow<WishDetailIntent>
  }
