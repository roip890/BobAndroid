package com.aptenobytes.bob.feature.notification.presentation.notificationlist

import com.aptenobytes.bob.library.base.presentation.mvi.BaseAction

sealed class NotificationListAction : BaseAction {
    class GetNotificationListAction(val index: Int = 0, val limit: Int = 20, val refresh: Boolean = false) : NotificationListAction()
}
