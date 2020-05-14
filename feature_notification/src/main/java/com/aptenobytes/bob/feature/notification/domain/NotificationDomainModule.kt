package com.aptenobytes.bob.feature.notification.domain

import com.aptenobytes.bob.feature.notification.FEATURE_NAME
import com.aptenobytes.bob.feature.notification.domain.repository.NotificationRepository
import com.aptenobytes.bob.feature.notification.domain.repository.NotificationRepositoryImpl
import com.aptenobytes.bob.feature.notification.domain.usecase.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal val domainModule = Kodein.Module("${FEATURE_NAME}DomainModule") {

    bind<NotificationRepository>() with singleton { NotificationRepositoryImpl(instance(), instance(), instance(), instance()) }

    bind() from singleton { GetNotificationsListUseCase(instance(), instance()) }

}
