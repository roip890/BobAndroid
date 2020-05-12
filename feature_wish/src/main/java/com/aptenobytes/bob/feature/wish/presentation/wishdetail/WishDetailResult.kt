package com.aptenobytes.bob.feature.wish.presentation.wishdetail

import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseResult

sealed class WishDetailResult : BaseResult {
  sealed class GetWishDetailResult : WishDetailResult() {
      object Loading : GetWishDetailResult()
      data class Success(val wish: WishDomainModel?) : GetWishDetailResult()
      data class Failure(val error: Throwable) : GetWishDetailResult()
  }
}
