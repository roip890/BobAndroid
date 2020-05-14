package com.aptenobytes.bob.feature.notification.presentation.notificationlist

import kotlinx.coroutines.flow.Flow

interface NotificationListView {
    fun intents(): Flow<NotificationListIntent>
  }
