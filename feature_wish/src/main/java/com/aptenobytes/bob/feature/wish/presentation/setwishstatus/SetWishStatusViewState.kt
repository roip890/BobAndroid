package com.aptenobytes.bob.feature.wish.presentation.setwishstatus

import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseViewState

data class SetWishStatusViewState(
    val wish: WishDomainModel? = null,
    val status: WishStatusType? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null
) : BaseViewState {
    companion object {
        fun initial() = SetWishStatusViewState(
            wish = null,
            status = null,
            isLoading = false,
            error = null
        )
    }
}
