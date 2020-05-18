package com.aptenobytes.bob.feature.wish.presentation.wishlist

import com.aptenobytes.bob.feature.wish.presentation.wishlist.WishListIntent
import com.aptenobytes.bob.library.base.presentation.mvi.BaseIntent

sealed class WishListIntent : BaseIntent {
    class GetWishListIntent(val index: Int = 0, val limit: Int = 20, val refresh: Boolean = false) : WishListIntent()
}
