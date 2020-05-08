package com.aptenobytes.bob.feature.wish.presentation.allwishes

import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseIntent

sealed class AllWishesIntent : BaseIntent {
    object InitialIntent : AllWishesIntent()
    object GetAllWishesIntent : AllWishesIntent()
}
