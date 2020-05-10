package com.aptenobytes.bob.feature.wish.presentation.wishlist

import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseResult

sealed class WishListResult : BaseResult {
  sealed class GetWishListResult : WishListResult() {
      object Loading : GetWishListResult()
      data class Success(val wishes: List<WishDomainModel>) : GetWishListResult()
      data class Failure(val error: Throwable) : GetWishListResult()
  }
}
