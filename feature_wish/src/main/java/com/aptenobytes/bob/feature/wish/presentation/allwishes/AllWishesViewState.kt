package com.aptenobytes.bob.feature.wish.presentation.allwishes

import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseViewState

data class AllWishesViewState(
    val isLoading: Boolean = false,
    val wishes: List<WishDomainModel> = emptyList(),
    val error: Throwable? = null
) : BaseViewState {
    companion object {
        fun initial() = AllWishesViewState(
            wishes = emptyList(),
            isLoading = false,
            error = null
        )
    }
}
