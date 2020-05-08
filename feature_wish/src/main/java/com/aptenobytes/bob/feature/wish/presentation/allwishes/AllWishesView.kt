package com.aptenobytes.bob.feature.wish.presentation.allwishes

import kotlinx.coroutines.flow.Flow

interface AllWishesView {
    fun intents(): Flow<AllWishesIntent>
  }
