package com.aptenobytes.bob.feature.wish.presentation.wishlist

import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseViewState

data class WishListViewState(
    val isLoading: Boolean = false,
    val wishes: List<WishDomainModel> = emptyList(),
    val error: Throwable? = null
) : BaseViewState {
    companion object {
        fun initial() = WishListViewState(
            wishes = emptyList(),
            isLoading = false,
            error = null
        )
    }
}
