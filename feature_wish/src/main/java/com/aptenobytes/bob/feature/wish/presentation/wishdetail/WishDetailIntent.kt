package com.aptenobytes.bob.feature.wish.presentation.wishdetail

import com.aptenobytes.bob.library.base.presentation.mvi.BaseIntent

sealed class WishDetailIntent : BaseIntent {
    class GetWishDetailIntent(val wishId: Long = 0) : WishDetailIntent()
}
