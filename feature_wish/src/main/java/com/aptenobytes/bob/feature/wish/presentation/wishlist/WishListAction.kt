package com.aptenobytes.bob.feature.wish.presentation.wishlist

import com.aptenobytes.bob.library.base.presentation.mvi.BaseAction

sealed class WishListAction : BaseAction {
    class GetWishListAction(val index: Int = 0, val limit: Int = 20, val refresh: Boolean = false) : WishListAction()
}
