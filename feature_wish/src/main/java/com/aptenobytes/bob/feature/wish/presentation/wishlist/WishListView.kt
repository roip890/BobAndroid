package com.aptenobytes.bob.feature.wish.presentation.wishlist

import com.aptenobytes.bob.feature.wish.presentation.wishlist.WishListIntent
import kotlinx.coroutines.flow.Flow

interface WishListView {
    fun intents(): Flow<WishListIntent>
  }
