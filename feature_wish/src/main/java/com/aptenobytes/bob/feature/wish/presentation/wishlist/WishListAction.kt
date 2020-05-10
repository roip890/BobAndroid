package com.aptenobytes.bob.feature.wish.presentation.wishlist

import com.aptenobytes.bob.library.base.presentation.mvi.BaseAction

sealed class WishListAction : BaseAction {
  object GetWishListAction : WishListAction()
}
