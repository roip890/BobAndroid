package com.aptenobytes.bob.feature.notification.presentation.notificationlist

import com.aptenobytes.bob.library.base.presentation.mvi.BaseIntent

sealed class NotificationListIntent : BaseIntent {
    class GetNotificationListIntent(val index: Int = 0, val limit: Int = 20, val refresh: Boolean = false) : NotificationListIntent()
}
