package com.aptenobytes.bob.feature.notification.presentation.notificationlist

import com.aptenobytes.bob.feature.notification.domain.model.notification.NotificationDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseViewState

data class NotificationListViewState(
    val refresh: Boolean = false,
    val isLoading: Boolean = false,
    val notifications: List<NotificationDomainModel> = emptyList(),
    val error: Throwable? = null
) : BaseViewState {
    companion object {
        fun initial() = NotificationListViewState(
            refresh = false,
            isLoading = false,
            notifications = emptyList(),
            error = null
        )
    }
}
