package com.aptenobytes.bob.feature.notification.data.db

import com.aptenobytes.bob.feature.notification.FEATURE_NAME
import com.aptenobytes.bob.feature.notification.data.db.datasource.NotificationRoomDataSourceImpl
import com.aptenobytes.bob.feature.notification.domain.datasource.NotificationLocalDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

internal val localDataModule = Kodein.Module("${FEATURE_NAME}RoomDataModule") {

    bind<NotificationDatabase>() with singleton {
        NotificationDatabase(
            instance()
        )
    }

    bind<NotificationDao>() with provider { instance<NotificationDatabase>().notificationDao() }

    bind<NotificationLocalDataSource>() with singleton {
        NotificationRoomDataSourceImpl(
            instance()
        )
    }

}
