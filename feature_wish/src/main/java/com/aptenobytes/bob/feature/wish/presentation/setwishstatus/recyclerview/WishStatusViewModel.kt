package com.aptenobytes.bob.feature.wish.presentation.setwishstatus.recyclerview

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType

class WishStatusViewModel(
    val status: MutableLiveData<WishStatusType> = MutableLiveData<WishStatusType>()
) {

    val statusString = MutableLiveData<String>()
    val statusIcon = MutableLiveData<Drawable>()

    init {
    }
}

fun WishStatusType.toViewModel(): WishStatusViewModel {
    return WishStatusViewModel(
        status = MutableLiveData<WishStatusType>(this)
    )
}

fun WishStatusViewModel.toDomainModel(): WishStatusType? = this.status.value
