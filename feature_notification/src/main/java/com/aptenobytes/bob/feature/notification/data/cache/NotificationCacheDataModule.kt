package com.aptenobytes.bob.feature.notification.data.cache

import com.aptenobytes.bob.feature.notification.FEATURE_NAME
import com.aptenobytes.bob.feature.notification.data.cache.datasource.NotificationCacheDataSourceImpl
import com.aptenobytes.bob.feature.notification.domain.datasource.NotificationCacheDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

internal val cacheDataModule = Kodein.Module("${FEATURE_NAME}CacheDataModule") {

    bind<NotificationCacheDataSource>() with singleton {NotificationCacheDataSourceImpl()}

}
