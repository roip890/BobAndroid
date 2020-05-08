package com.aptenobytes.bob.app.data.cache

import com.aptenobytes.bob.app.data.cache.datasource.AppCacheDataSourceImpl
import com.aptenobytes.bob.app.domain.datasource.AppCacheDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

internal val cacheDataModule = Kodein.Module("AppCacheDataModule") {

    bind<AppCacheDataSource>() with singleton {AppCacheDataSourceImpl()}

}
