package com.aptenobytes.bob.feature.wish.presentation.wishlist

import kotlinx.coroutines.flow.Flow

interface WishListView {
    fun intents(): Flow<WishListIntent>
  }
