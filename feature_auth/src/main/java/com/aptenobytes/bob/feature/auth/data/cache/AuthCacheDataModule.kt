package com.aptenobytes.bob.feature.auth.data.cache

import com.aptenobytes.bob.feature.auth.FEATURE_NAME
import com.aptenobytes.bob.feature.auth.data.cache.datasource.AuthCacheDataSourceImpl
import com.aptenobytes.bob.feature.auth.domain.datasource.AuthCacheDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

internal val cacheDataModule = Kodein.Module("${FEATURE_NAME}CacheDataModule") {

    bind<AuthCacheDataSource>() with singleton { AuthCacheDataSourceImpl() }

}
