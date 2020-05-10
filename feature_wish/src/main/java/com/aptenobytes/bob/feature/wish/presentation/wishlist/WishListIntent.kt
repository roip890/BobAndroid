package com.aptenobytes.bob.feature.wish.presentation.wishlist

import com.aptenobytes.bob.library.base.presentation.mvi.BaseIntent

sealed class WishListIntent : BaseIntent {
    object InitialIntent : WishListIntent()
    object GetWishListIntent : WishListIntent()
}
