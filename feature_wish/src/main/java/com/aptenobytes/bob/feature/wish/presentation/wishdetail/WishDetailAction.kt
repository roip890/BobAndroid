package com.aptenobytes.bob.feature.wish.presentation.wishdetail

import com.aptenobytes.bob.library.base.presentation.mvi.BaseAction

sealed class WishDetailAction : BaseAction {
    class GetWishDetailAction(val wishId: Long = 0) : WishDetailAction()
}
