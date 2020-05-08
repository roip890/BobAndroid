package com.aptenobytes.bob.feature.wish.presentation.allwishes

import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseAction

sealed class AllWishesAction : BaseAction {
  object GetAllWishesAction : AllWishesAction()
}
