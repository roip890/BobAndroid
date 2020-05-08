package com.aptenobytes.bob.feature.wish.presentation.allwishes

import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseResult

sealed class AllWishesResult : BaseResult {
  sealed class GetAllWishesResult : AllWishesResult() {
      object Loading : GetAllWishesResult()
      data class Success(val wishes: List<WishDomainModel>) : GetAllWishesResult()
      data class Failure(val error: Throwable) : GetAllWishesResult()
  }
}
