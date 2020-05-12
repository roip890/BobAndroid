package com.aptenobytes.bob.feature.wish.presentation.wishdetail

import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseViewState

data class WishDetailViewState(
    val isLoading: Boolean = false,
    val wish: WishDomainModel? = null,
    val error: Throwable? = null
) : BaseViewState {
    companion object {
        fun initial() = WishDetailViewState(
            isLoading = false,
            wish = null,
            error = null
        )
    }
}
