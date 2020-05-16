package com.aptenobytes.bob.feature.wish.presentation.setwishstatus

import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseResult

sealed class SetWishStatusResult : BaseResult {
    sealed class SetStatusResult : SetWishStatusResult() {
        object Loading : SetStatusResult()
        data class Success(val wish: WishDomainModel?) : SetStatusResult()
        data class Failure(val error: Throwable) : SetStatusResult()
    }
}
