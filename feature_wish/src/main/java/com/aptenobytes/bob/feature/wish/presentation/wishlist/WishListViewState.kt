package com.aptenobytes.bob.feature.wish.presentation.wishlist

import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.presentation.wishlist.WishListViewState
import com.aptenobytes.bob.library.base.presentation.mvi.BaseViewState

data class WishListViewState(
    val refresh: Boolean = false,
    val isLoading: Boolean = false,
    val wishes: List<WishDomainModel> = emptyList(),
    val error: Throwable? = null
) : BaseViewState {
    companion object {
        fun initial() = WishListViewState(
            refresh = false,
            isLoading = false,
            wishes = emptyList(),
            error = null
        )
    }
}
