package com.aptenobytes.bob.feature.notification.domain.usecase

import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.app.domain.repository.AppRepository
import com.aptenobytes.bob.feature.notification.domain.model.notification.NotificationDomainModel
import com.aptenobytes.bob.feature.notification.domain.repository.NotificationRepository

class GetNotificationsListUseCase(
    private val appRepository: AppRepository,
    private val notificationRepository: NotificationRepository
) {
    suspend fun execute(index: Int = 0, limit: Int = 20): List<NotificationDomainModel> {
        val userDomainModel: UserDomainModel? = appRepository.getUserSession()
        userDomainModel?.let { user ->
            return notificationRepository.getNotifications(
                userId = user.id,
                index = index,
                limit = limit
            )
        } ?: run {
            return arrayListOf()
        }
    }
}
