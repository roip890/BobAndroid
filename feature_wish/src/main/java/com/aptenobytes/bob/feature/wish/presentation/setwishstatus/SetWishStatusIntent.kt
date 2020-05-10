package com.aptenobytes.bob.feature.wish.presentation.setwishstatus

import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseIntent

sealed class SetWishStatusIntent : BaseIntent {
    class SetStatusIntent(val wish: WishDomainModel, val status: WishStatusType) : SetWishStatusIntent()
}
