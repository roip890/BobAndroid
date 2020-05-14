package com.aptenobytes.bob.feature.notification.presentation.notificationlist

import com.aptenobytes.bob.feature.notification.domain.model.notification.NotificationDomainModel
import com.aptenobytes.bob.library.base.presentation.mvi.BaseResult

sealed class NotificationListResult : BaseResult {
  sealed class GetNotificationListResult : NotificationListResult() {
      object Loading : GetNotificationListResult()
      data class Success(val notifications: List<NotificationDomainModel>, val refresh: Boolean) : GetNotificationListResult()
      data class Failure(val error: Throwable) : GetNotificationListResult()
  }
}
