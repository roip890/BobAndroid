package com.aptenobytes.bob.feature.profile.presentation.setuserstatus.recyclerview

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType

class UserStatusViewModel(
    val status: MutableLiveData<UserStatusType> = MutableLiveData<UserStatusType>()
) {

    val statusString = MutableLiveData<String>()
    val statusIcon = MutableLiveData<Drawable>()

    init {
    }
}

fun UserStatusType.toViewModel(): UserStatusViewModel {
    return UserStatusViewModel(
        status = MutableLiveData<UserStatusType>(this)
    )
}

fun UserStatusViewModel.toDomainModel(): UserStatusType? = this.status.value
